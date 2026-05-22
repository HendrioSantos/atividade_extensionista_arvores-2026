package projeto_ambiental.atividade_extensionista.usuario;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record RequisicaoGuest(
        @NotNull(message = "O identificador do dispositivo é obrigatorio")
        UUID identificador
) {
}
