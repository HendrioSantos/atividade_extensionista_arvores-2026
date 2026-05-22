package projeto_ambiental.atividade_extensionista.usuario;

import jakarta.validation.constraints.NotBlank;

public record RequisicaoLogin(
        @NotBlank(message = "Login Obrigatório")
        String login,
        @NotBlank(message = "Senha Obrigatório")
        String senha
) {
}
