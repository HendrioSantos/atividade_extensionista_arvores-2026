package atividade_extensionista.projeto_ambiental.components;

import atividade_extensionista.projeto_ambiental.repository.OcorrenciaRepository;

public interface OcorrenciaMetodoExcluir {
    void excluirOcorrencia(Long id, OcorrenciaRepository repository);
    boolean ExclusaoLogica(boolean logico);
}
