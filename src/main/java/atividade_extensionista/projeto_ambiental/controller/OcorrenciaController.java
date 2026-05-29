package atividade_extensionista.projeto_ambiental.controller;

import atividade_extensionista.projeto_ambiental.dto.OcorrenciaListar;
import atividade_extensionista.projeto_ambiental.dto.OcorrenciaRegistrar;
import atividade_extensionista.projeto_ambiental.model.StatusOcorrencia;
import atividade_extensionista.projeto_ambiental.service.OcorrenciaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/ocorrencia")
@RequiredArgsConstructor
public class OcorrenciaController {

    private final OcorrenciaService service;

    @GetMapping
    public ResponseEntity<Page<OcorrenciaListar>> listarTodas(@PageableDefault(size = 10, sort = "nome") Pageable paginacao) {
        return ResponseEntity.ok(service.paginarOcorrencias(paginacao));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OcorrenciaListar> pegarSomenteUm(@PathVariable Long id) {
        var ocorrencia = service.pegarUmSo(id);
        return ResponseEntity.ok(new OcorrenciaListar(ocorrencia));
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<OcorrenciaRegistrar> criarOcorrencia(@ModelAttribute @Valid OcorrenciaRegistrar dto, @RequestParam("foto") MultipartFile foto) {
        try {
            var novaOcorrencia = service.registrarOcorrencia(dto, foto);
            var responseDTO = new OcorrenciaRegistrar(novaOcorrencia);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OcorrenciaRegistrar> atualizarStatusAdmin(@PathVariable Long id, @RequestParam("novoStatus") StatusOcorrencia novoStatus) {
        return ResponseEntity.ok(new OcorrenciaRegistrar(service.atualizarStatus(id, novoStatus)));
    }

    @DeleteMapping("/{id}/{logico}")
    public ResponseEntity<Void> deletarOcorrencia(@PathVariable Long id, @PathVariable boolean logico) {
        service.processarExclusao(id, logico);
        return ResponseEntity.noContent().build();
    }

}
