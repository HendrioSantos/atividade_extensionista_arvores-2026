package atividade_extensionista.projeto_ambiental.repository;

import atividade_extensionista.projeto_ambiental.model.Ocorrencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {
    Page<Ocorrencia> findAllByOrderByNomeAsc(Pageable paginacao);
}
