package projeto_ambiental.atividade_extensionista.service.status;

import projeto_ambiental.atividade_extensionista.model.Ocorrencia;
import projeto_ambiental.atividade_extensionista.model.StatusOcorrencia;

public interface AlteradorStatus {
    void alterarStatus(Ocorrencia ocorrencia, StatusOcorrencia status);
}
