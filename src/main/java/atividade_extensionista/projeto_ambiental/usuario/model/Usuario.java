package atividade_extensionista.projeto_ambiental.usuario.model;

import atividade_extensionista.projeto_ambiental.usuario.dto.AtualizarUsuario;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // CORRIGIDO: Removido o nullable = false para permitir o registro de convidados (Guests)
    @Column(unique = true)
    private String login;

    @Column(unique = true)
    private String senha;

    @Column(unique = true)
    private String identificadorGuest;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PerfilUsuario perfil;

    @Column(nullable = false)
    private boolean ativo = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + perfil));
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return ativo;
    }

    public void atualizarInformacoes(AtualizarUsuario dados) {
        if (dados.login() != null){
            this.login = dados.login();
        }
        if (dados.senha() != null){
            this.senha = dados.senha();
        }
        if (dados.role() != null){
            this.perfil = dados.role();
        }
        if (!dados.ativo()){
            this.ativo = !dados.ativo();
        }
    }

    public void excluir() {
        this.ativo = false;
    }
}
