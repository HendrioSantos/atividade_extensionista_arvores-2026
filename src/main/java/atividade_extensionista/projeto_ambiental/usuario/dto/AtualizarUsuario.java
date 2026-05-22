package atividade_extensionista.projeto_ambiental.usuario.dto;

import atividade_extensionista.projeto_ambiental.usuario.model.PerfilUsuario;
import atividade_extensionista.projeto_ambiental.usuario.model.Usuario;

public record AtualizarUsuario(
        String login,
        String senha,
        PerfilUsuario role,
        boolean ativo
) {
    public AtualizarUsuario(Usuario usuario) {
        this(
                usuario.getLogin(),
                usuario.getSenha(),
                usuario.getPerfil(),
                usuario.isAtivo()
        );
    }
}
