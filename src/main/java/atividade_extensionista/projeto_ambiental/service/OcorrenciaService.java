package atividade_extensionista.projeto_ambiental.service;

import atividade_extensionista.projeto_ambiental.components.FiltradorSlug;
import atividade_extensionista.projeto_ambiental.components.OcorrenciaMetodoExcluir;
import atividade_extensionista.projeto_ambiental.dto.OcorrenciaListar;
import atividade_extensionista.projeto_ambiental.dto.OcorrenciaRegistrar;
import atividade_extensionista.projeto_ambiental.infra.exception.InvalidoException;
import atividade_extensionista.projeto_ambiental.model.Ocorrencia;
import atividade_extensionista.projeto_ambiental.model.StatusOcorrencia;
import atividade_extensionista.projeto_ambiental.repository.OcorrenciaRepository;
import atividade_extensionista.projeto_ambiental.service.status.AlteradorStatusOcorrencia;
import atividade_extensionista.projeto_ambiental.usuario.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OcorrenciaService {

    private final OcorrenciaRepository repository;
    private final GeocodingService service;

    private final AlteradorStatusOcorrencia alteradorStatus;
    private final List<OcorrenciaMetodoExcluir> excluir;
    private final FiltradorSlug slug;

    @Transactional
    public Ocorrencia registrarOcorrencia(OcorrenciaRegistrar dto, MultipartFile foto) throws IOException {

        // 1. Captura o nome da categoria do Enum em letras minúsculas (ex: "queimada")
        String pastaCategoria = dto.tipoDano().toString().toLowerCase();

        // Constrói o caminho final combinando a base com a categoria (ex: "uploads/fotos/queimada")
        String uploadDirBase = "uploads/fotos/";
        Path uploadPath = Paths.get(uploadDirBase, pastaCategoria);

        // Cria as pastas fisicamente caso elas ainda não existam no projeto
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 2. Define um nome único para o arquivo usando o timestamp para evitar que fotos com o mesmo nome se apaguem
        String nomeArquivo = System.currentTimeMillis() + "_" + foto.getOriginalFilename();
        Path arquivoPath = uploadPath.resolve(nomeArquivo);

        // Salva a imagem fisicamente dentro da pasta da categoria correspondente
        Files.copy(foto.getInputStream(), arquivoPath, StandardCopyOption.REPLACE_EXISTING);

        // 3. LOG INTELIGENTE DE LOCALIZAÇÃO (Fallback para Endereço)
        Double latFinal = dto.latitude();
        Double lngFinal = dto.longitude();

        if ((latFinal == null || lngFinal == null) && dto.endereco() != null && !dto.endereco().isBlank()) {
            double[] coordenadas = service.buscarCoordenadas(dto.endereco());
            latFinal = coordenadas[0];
            lngFinal = coordenadas[1];
        } else if (latFinal == null || lngFinal == null) {
            throw new InvalidoException("Informe um endereço valido ou as coordenadas geograficas ");
        }

        // 4. REGRA DE NEGÓCIO: Gera o slug automaticamente baseado no tipo e data atual
        String slugGerado = gerarSlug(dto.tipoDano().toString());

        Usuario autor = (Usuario) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        if (autor == null){
            throw new InvalidoException("Sessão invalida ou expirada", HttpStatus.UNAUTHORIZED);
        }

        // 5. Constrói a entidade unificando todos os dados tratados
        Ocorrencia novaOcorrencia = Ocorrencia.builder()
                .nome(filtradorSlug(dto.nome()))
                .tipoDano(dto.tipoDano())
                .latitude(latFinal)
                .longitude(lngFinal)
                .endereco(dto.endereco())
                .urlFoto(arquivoPath.toString())
                .descricao(dto.descricao())
                .slug(slugGerado)
                .usuario(autor)
                .build();

        return repository.save(novaOcorrencia);
    }

    @Transactional
    public Ocorrencia atualizarStatus(Long id, StatusOcorrencia novoStatus) {
        Ocorrencia ocorrencia = repository.findById(id)
                .orElseThrow(() -> new InvalidoException("Não foi possível encontrar a ocorrencia"));
        alteradorStatus.alterarStatus(ocorrencia, novoStatus);

        return repository.save(ocorrencia);
    }

    @Transactional(readOnly = true)
    public Page<OcorrenciaListar> paginarOcorrencias(Pageable paginacao){
        return repository.findAllByOrderByNomeAsc(paginacao)
                .map(OcorrenciaListar::new);
    }

    @Transactional(readOnly = true)
    public Ocorrencia pegarUmSo(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new InvalidoException("Insira o id e o tipo de dano", HttpStatus.BAD_REQUEST));
    }

    @Transactional
    public void processarExclusao(Long id, boolean logico){
        excluir.stream()
                .filter(e -> e.ExclusaoLogica(logico))
                .findFirst()
                .orElseThrow(() -> new InvalidoException("Metodo de exclusao não suportado"))
                .excluirOcorrencia(id, repository);
    }

    private String gerarSlug(String tipoDano) {
        String dataFormatada = java.time.LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String dataFiltrada = filtradorSlug(dataFormatada);
        return (tipoDano + "-" + dataFiltrada);
    }

    private String filtradorSlug(String palavra){
        return slug.filtrarSlug(palavra);
    }

}
