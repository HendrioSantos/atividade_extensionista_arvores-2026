package atividade_extensionista.projeto_ambiental.usuario.components;

import atividade_extensionista.projeto_ambiental.usuario.repository.UsuarioRepository;

public interface UsuarioMetodoExcluir {
    void excluirUsuario(String login, UsuarioRepository repository);
    boolean seAplica(boolean logico);
}
