package atividade_extensionista.projeto_ambiental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import projeto_ambiental.atividade_extensionista.model.Ocorrencia;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {
}
