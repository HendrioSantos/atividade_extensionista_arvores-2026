package atividade_extensionista.projeto_ambiental.usuario.service;

import atividade_extensionista.projeto_ambiental.usuario.dto.GuestRequisicao;
import atividade_extensionista.projeto_ambiental.usuario.model.PerfilUsuario;
import atividade_extensionista.projeto_ambiental.usuario.model.Usuario;
import atividade_extensionista.projeto_ambiental.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuestService {

    private final UsuarioRepository repository;

    public Usuario identificarOuCadastrarGuest(GuestRequisicao dto) {
        return repository.findByIdentificadorGuest(dto.identificadorGuest())
                .orElseGet(() -> {
                    // Se for um dispositivo inédito, cria um perfil de Guest limpo
                    Usuario novoGuest = Usuario.builder()
                            .identificadorGuest(dto.identificadorGuest())
                            .login("guest_" + dto.identificadorGuest().substring(0, 8))
                            .perfil(PerfilUsuario.GUEST)
                            .ativo(true)
                            .build();
                    return repository.save(novoGuest);
                });
    }

}
