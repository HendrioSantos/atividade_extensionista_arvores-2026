package atividade_extensionista.projeto_ambiental.repository;

import atividade_extensionista.projeto_ambiental.model.Ocorrencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {
}
