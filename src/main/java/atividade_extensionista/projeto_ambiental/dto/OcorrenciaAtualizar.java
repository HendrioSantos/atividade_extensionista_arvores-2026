package atividade_extensionista.projeto_ambiental.dto;

import atividade_extensionista.projeto_ambiental.model.Ocorrencia;
import atividade_extensionista.projeto_ambiental.model.StatusOcorrencia;
import atividade_extensionista.projeto_ambiental.model.TipoDano;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

public record OcorrenciaAtualizar(
        String nome,
        TipoDano tipoDano,
        Double latitude,
        Double longitude,
        String endereco,
        @URL
        String urlFoto,
        String descricao,
        LocalDateTime dataRegistro,
        StatusOcorrencia statusOcorrencia
) {
    public OcorrenciaAtualizar(Ocorrencia ocorrencia) {
        this(
                ocorrencia.getNome(),
                ocorrencia.getTipoDano(),
                ocorrencia.getLatitude(),
                ocorrencia.getLongitude(),
                ocorrencia.getEndereco(),
                ocorrencia.getUrlFoto(),
                ocorrencia.getDescricao(),
                ocorrencia.getDataRegistro(),
                ocorrencia.getStatusOcorrencia()
        );
    }
}
