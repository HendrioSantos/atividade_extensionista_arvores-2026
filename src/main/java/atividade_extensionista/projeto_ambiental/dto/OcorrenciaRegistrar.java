package atividade_extensionista.projeto_ambiental.dto;

import atividade_extensionista.projeto_ambiental.model.Ocorrencia;
import atividade_extensionista.projeto_ambiental.model.StatusOcorrencia;
import atividade_extensionista.projeto_ambiental.model.TipoDano;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

public record OcorrenciaRegistrar(
        TipoDano tipoDano,
        Double latitude,
        Double longitude,
        String endereco,
        @URL
        String urlFoto,
        String descricao,
        LocalDateTime dataRegistro,
        StatusOcorrencia statusOcorrencia,
        String slug
) {
    public OcorrenciaRegistrar(Ocorrencia ocorrencia) {
        this(
                ocorrencia.getTipoDano(),
                ocorrencia.getLatitude(),
                ocorrencia.getLongitude(),
                null,
                ocorrencia.getUrlFoto(),
                ocorrencia.getDescricao(),
                ocorrencia.getDataRegistro(),
                ocorrencia.getStatusOcorrencia(),
                ocorrencia.getSlug()
        );
    }
}
