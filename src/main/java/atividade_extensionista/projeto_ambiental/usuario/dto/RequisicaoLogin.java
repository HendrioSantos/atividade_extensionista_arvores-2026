package atividade_extensionista.projeto_ambiental.usuario.dto;

import jakarta.validation.constraints.NotBlank;

public record RequisicaoLogin(
        @NotBlank(message = "Login Obrigatório")
        String login,
        @NotBlank(message = "Senha Obrigatório")
        String senha
) {
}
