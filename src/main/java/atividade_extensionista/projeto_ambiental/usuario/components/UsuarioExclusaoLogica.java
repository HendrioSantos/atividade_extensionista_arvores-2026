package atividade_extensionista.projeto_ambiental.usuario.components;

import atividade_extensionista.projeto_ambiental.infra.exception.InvalidoException;
import atividade_extensionista.projeto_ambiental.usuario.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class UsuarioExclusaoLogica implements UsuarioMetodoExcluir {

    @Override
    public void excluirUsuario(String login, UsuarioRepository repository) {
        var usuario = repository.findByLoginAndAtivoTrue(login)
                .orElseThrow(() -> new InvalidoException("Não foi possível encontrar o usuario", HttpStatus.NOT_FOUND));
        usuario.setAtivo(false);
        repository.save(usuario);
    }

    @Override
    public boolean seAplica(boolean logico) {
        return logico;
    }
}
