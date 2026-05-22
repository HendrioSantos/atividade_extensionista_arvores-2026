package atividade_extensionista.projeto_ambiental.usuario.components;

import atividade_extensionista.projeto_ambiental.usuario.model.Usuario;
import atividade_extensionista.projeto_ambiental.usuario.repository.UsuarioRepository;
import org.springframework.stereotype.Component;

@Component
public class UsuarioExclusaoFisica implements UsuarioMetodoExcluir{
    @Override
    public void excluirUsuario(String login, UsuarioRepository repository) {
        var usuario = repository.findByLogin(login);
        repository.delete((Usuario) usuario);
    }

    @Override
    public boolean seAplica(boolean logico) {
        return logico;
    }
}
