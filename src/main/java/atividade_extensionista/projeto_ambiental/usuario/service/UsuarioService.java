package atividade_extensionista.projeto_ambiental.usuario.service;

import atividade_extensionista.projeto_ambiental.infra.exception.InvalidoException;
import atividade_extensionista.projeto_ambiental.usuario.components.UsuarioMetodoExcluir;
import atividade_extensionista.projeto_ambiental.usuario.dto.AtualizarUsuario;
import atividade_extensionista.projeto_ambiental.usuario.dto.DadosCadastroUsuario;
import atividade_extensionista.projeto_ambiental.usuario.model.Usuario;
import atividade_extensionista.projeto_ambiental.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder; // Injeção de segurança
    private final List<UsuarioMetodoExcluir> metodosExclusao; // Injeção do seu Strategy Pattern

    public Usuario registrarUsuario(DadosCadastroUsuario dados) {
        var usuario = buildUsuario(dados);
        return usuarioRepository.save(usuario);
    }

    public Usuario atualizarInformacoes(AtualizarUsuario dados) {
        var usuario = encontrarUsuario(dados.login());
        usuario.atualizarInformacoes(dados);
        return usuarioRepository.save(usuario);
    }

    public void deletarUsuarioPorEstrategia(String login, boolean ehExclusaoLogica) {
        UsuarioMetodoExcluir estrategiaCorreta = metodosExclusao.stream()
                .filter(metodo -> metodo.seAplica(ehExclusaoLogica))
                .findFirst()
                .orElseThrow(() -> new InvalidoException("Estratégia de exclusão não configurada.", HttpStatus.INTERNAL_SERVER_ERROR));

        estrategiaCorreta.excluirUsuario(login, usuarioRepository);
    }

    private Usuario buildUsuario(DadosCadastroUsuario dados) {
        String senhaCriptografada = passwordEncoder.encode(dados.senha());

        return Usuario.builder()
                .login(dados.login())
                .senha(senhaCriptografada)
                .identificadorGuest(dados.identificadorGuest())
                .perfil(dados.role())
                .ativo(true)
                .build();
    }

    private Usuario encontrarUsuario(String login) {
        return usuarioRepository.findByLoginAndAtivoTrue(login)
                .orElseThrow(() -> new InvalidoException("Usuário não encontrado ou inativo no sistema.", HttpStatus.NOT_FOUND));
    }

}
