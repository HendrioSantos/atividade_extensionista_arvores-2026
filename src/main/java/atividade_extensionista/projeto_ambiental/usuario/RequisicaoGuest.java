package atividade_extensionista.projeto_ambiental.usuario;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RequisicaoGuest(
        @NotNull(message = "O identificador do dispositivo é obrigatorio")
        UUID identificador
) {
}
