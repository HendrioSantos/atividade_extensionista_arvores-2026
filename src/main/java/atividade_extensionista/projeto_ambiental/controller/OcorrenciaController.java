package atividade_extensionista.projeto_ambiental.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import projeto_ambiental.atividade_extensionista.dto.OcorrenciaRegistrar;
import projeto_ambiental.atividade_extensionista.model.Ocorrencia;
import projeto_ambiental.atividade_extensionista.model.StatusOcorrencia;
import projeto_ambiental.atividade_extensionista.service.OcorrenciaService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/ocorrencia")
@RequiredArgsConstructor
public class OcorrenciaController {

    private final OcorrenciaService service;

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<OcorrenciaRegistrar> criarOcorrencia(
            @ModelAttribute @Valid OcorrenciaRegistrar dto,
            @RequestParam("foto") MultipartFile foto) {
        try {
            // Executa o serviço de salvamento físico e persistência no banco
            Ocorrencia novaOcorrencia = service.registrarOcorrencia(dto, foto);

            // Converte a entidade salva de volta para o DTO usando o construtor do Record
            OcorrenciaRegistrar responseDTO = new OcorrenciaRegistrar(novaOcorrencia);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (IOException e) {
            // Caso ocorra falha na gravação do arquivo físico no servidor
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Endpoint público para listar todas as ocorrências registradas.
     * Retorna uma lista de DTOs para blindar a entidade de banco de dados.
     */
    @GetMapping
    public ResponseEntity<List<OcorrenciaRegistrar>> listarTodas() {
        List<OcorrenciaRegistrar> listaDTOs = service.listarTodas().stream()
                .map(OcorrenciaRegistrar::new) // Converte cada Ocorrencia em OcorrenciaRegistrar DTO
                .collect(Collectors.toList());

        return ResponseEntity.ok(listaDTOs);
    }

    /**
     * Endpoint para uso exclusivo do Administrador/Fiscal.
     * Altera o status de uma ocorrência específica.
     * Rota de acesso: PATCH http://localhost:8080/ocorrencias/{id}/status?novoStatus=EM_ANALISE
     */
    @PatchMapping("/{id}/status")
    public ResponseEntity<OcorrenciaRegistrar> atualizarStatusAdmin(
            @PathVariable Long id,
            @RequestParam("novoStatus") StatusOcorrencia novoStatus) {

        // Executa a alteração através da service que invoca o padrão de projeto SOLID
        Ocorrencia ocorrenciaAtualizada = service.atualizarStatus(id, novoStatus);

        // Devolve o DTO atualizado
        return ResponseEntity.ok(new OcorrenciaRegistrar(ocorrenciaAtualizada));
    }

}
