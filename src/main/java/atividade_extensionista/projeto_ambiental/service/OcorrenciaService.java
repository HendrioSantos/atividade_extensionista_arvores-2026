package atividade_extensionista.projeto_ambiental.service;

import atividade_extensionista.projeto_ambiental.dto.OcorrenciaRegistrar;
import atividade_extensionista.projeto_ambiental.infra.exception.InvalidoException;
import atividade_extensionista.projeto_ambiental.model.Ocorrencia;
import atividade_extensionista.projeto_ambiental.model.StatusOcorrencia;
import atividade_extensionista.projeto_ambiental.repository.OcorrenciaRepository;
import atividade_extensionista.projeto_ambiental.service.status.AlteradorStatusOcorrencia;
import atividade_extensionista.projeto_ambiental.usuario.model.Usuario;
import atividade_extensionista.projeto_ambiental.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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
    private final UsuarioRepository usuarioRepository;
    private final Usuario usuario;
    private final AlteradorStatusOcorrencia alteradorStatus;
    private final GeocodingService service;
    // Modifique o início da sua OcorrenciaService para usar o caminho relativo geral
    private final String uploadDirBase = "uploads/fotos/";

    public Ocorrencia registrarOcorrencia(OcorrenciaRegistrar dto, MultipartFile foto) throws IOException {

        // 1. Captura o nome da categoria do Enum em letras minúsculas (ex: "queimada")
        String pastaCategoria = dto.tipoDano().toString().toLowerCase();

        // Constrói o caminho final combinando a base com a categoria (ex: "uploads/fotos/queimada")
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
            throw new IllegalArgumentException("É necessário fornecer as coordenadas geográficas ou um endereço válido.");
        }

        // 4. REGRA DE NEGÓCIO: Gera o slug automaticamente baseado no tipo e data atual
        String slugGerado = gerarSlug(dto.tipoDano().toString());

        Usuario autor = (Usuario) usuarioRepository.findByLogin(usuario.getLogin());
        if (autor == null){
            throw new InvalidoException("Usuário não encontrado pelo login", HttpStatus.NOT_FOUND);
        }

        // 5. Constrói a entidade unificando todos os dados tratados
        Ocorrencia novaOcorrencia = Ocorrencia.builder()
                .tipoDano(dto.tipoDano())
                .latitude(latFinal)
                .longitude(lngFinal)
                .descricao(dto.descricao())
                .slug(slugGerado)
                .urlFoto(arquivoPath.toString()) // O caminho salvo no banco refletirá a pasta do Enum
                .endereco(dto.endereco())
                .usuario(autor)
                .build();

        return repository.save(novaOcorrencia);
    }

    /**
     * Permite que o administrador altere o estado da denúncia usando o padrão de projeto SOLID.
     */
    public Ocorrencia atualizarStatus(Long id, StatusOcorrencia novoStatus) {
        Ocorrencia ocorrencia = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ocorrência não encontrada com o ID informado."));

        // Delega a validação de transição para a classe especialista (SOLID)
        alteradorStatus.alterarStatus(ocorrencia, novoStatus);

        return repository.save(ocorrencia);
    }

    public List<Ocorrencia> listarTodas() {
        return repository.findAll();
    }

    private String gerarSlug(String tipoDano) {
        String dataFormatada = java.time.LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return (tipoDano + "-" + dataFormatada).toLowerCase();
    }

}
