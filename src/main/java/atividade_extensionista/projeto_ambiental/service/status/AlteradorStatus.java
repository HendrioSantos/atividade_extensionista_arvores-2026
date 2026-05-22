package atividade_extensionista.projeto_ambiental.service.status;


import atividade_extensionista.projeto_ambiental.model.Ocorrencia;
import atividade_extensionista.projeto_ambiental.model.StatusOcorrencia;

public interface AlteradorStatus {
    void alterarStatus(Ocorrencia ocorrencia, StatusOcorrencia status);
}
