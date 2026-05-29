package atividade_extensionista.projeto_ambiental.model;

import atividade_extensionista.projeto_ambiental.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "ocorrencias")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class Ocorrencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoDano tipoDano;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    private String endereco;

    @Column(nullable = false)
    private String urlFoto;

    private String descricao;

    @Builder.Default
    @Column(nullable = false)
    private LocalDateTime dataRegistro = LocalDateTime.now();

    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusOcorrencia statusOcorrencia = StatusOcorrencia.PENDENTE;
    private String slug;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Builder.Default
    @Column(nullable = false)
    private boolean ativo = true;

}
