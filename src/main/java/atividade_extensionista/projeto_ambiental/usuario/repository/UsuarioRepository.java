package atividade_extensionista.projeto_ambiental.usuario.repository;

import atividade_extensionista.projeto_ambiental.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByLogin(String login);
    Optional<Usuario> findByLoginAndAtivoTrue(String login);

    Optional<Usuario> findByIdentificadorGuest(String identicador);
}
