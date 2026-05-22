package projeto_ambiental.atividade_extensionista.dto;

import org.hibernate.validator.constraints.URL;
import projeto_ambiental.atividade_extensionista.model.Ocorrencia;
import projeto_ambiental.atividade_extensionista.model.StatusOcorrencia;
import projeto_ambiental.atividade_extensionista.model.TipoDano;

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
