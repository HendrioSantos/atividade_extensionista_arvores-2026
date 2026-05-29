package atividade_extensionista.projeto_ambiental.usuario.controller;

import atividade_extensionista.projeto_ambiental.infra.security.TokenService;
import atividade_extensionista.projeto_ambiental.usuario.dto.*;
import atividade_extensionista.projeto_ambiental.usuario.model.Usuario;
import atividade_extensionista.projeto_ambiental.usuario.service.GuestService;
import atividade_extensionista.projeto_ambiental.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/autenticacao")
@RequiredArgsConstructor
public class AutenticacaoController {

    private final AuthenticationManager manager;
    private final TokenService tokenService;
    private final UsuarioService usuarioService;
    private final GuestService guestService;

    @PostMapping("/login")
    public ResponseEntity<DadosLoginResposta> efetuarLogin(@RequestBody DadosAutenticacao dados) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        Authentication authentication = manager.authenticate(authenticationToken);

        var usuario = (Usuario) authentication.getPrincipal();
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());

        var resposta = new DadosLoginResposta(
                usuario.getLogin(),
                tokenJWT,
                "Login realizado com sucesso"
        );

        return ResponseEntity.ok(resposta);
    }

    @PostMapping("/registro")
    public ResponseEntity<Usuario> registrar(@RequestBody @Valid DadosCadastroUsuario dados) {
        var resposta = usuarioService.registrarUsuario(dados);
        return ResponseEntity.ok(resposta);
    }

    @PostMapping("/guest")
    public ResponseEntity<DadosLoginResposta> autenticarGuest(@RequestBody @Valid GuestRequisicao request) {
        // 1. Busca ou registra o dispositivo como usuário Guest no banco de dados
        Usuario guest = guestService.identificarOuCadastrarGuest(request);

        // 2. Cria o token de acesso seguro em cima do deviceId/login gerado
        String tokenJwt = tokenService.gerarToken(guest);

        // 3. Devolve para o front-end salvar no local storage
        return ResponseEntity.ok(new DadosLoginResposta(guest.getLogin(), tokenJwt, guest.getPerfil().name()));
    }
}
