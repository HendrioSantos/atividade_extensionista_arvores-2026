package atividade_extensionista.projeto_ambiental.usuario.dto;

import atividade_extensionista.projeto_ambiental.usuario.model.PerfilUsuario;

public record DadosCadastroUsuario(
        String login,
        String senha,
        PerfilUsuario role,
        boolean ativo,
        String mensagem,
        String identificadorGuest
) {
}
