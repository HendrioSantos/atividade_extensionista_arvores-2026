package atividade_extensionista.projeto_ambiental.usuario.dto;

import jakarta.validation.constraints.NotBlank;

public record GuestRequisicao(
        @NotBlank(message = "Identificador é obrigatório")
        String identificadorGuest
) {
}
