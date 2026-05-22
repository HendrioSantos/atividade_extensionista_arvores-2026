package atividade_extensionista.projeto_ambiental.usuario;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class Usuario implements U{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String login;

    @Column(unique = true, nullable = false)
    private String senha;

    @Column(unique = true, nullable = false)
    private String deviceId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PerfilUsuario perfil;

}
