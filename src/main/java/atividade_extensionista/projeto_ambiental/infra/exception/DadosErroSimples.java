package atividade_extensionista.projeto_ambiental.infra.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record DadosErroSimples(
        String mensagem,
        @JsonFormat(pattern = "dd:HH:yyyy HH:mm:ss")
        LocalDateTime timestamp
) {
}
