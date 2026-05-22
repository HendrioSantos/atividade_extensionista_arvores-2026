package atividade_extensionista.projeto_ambiental.service.status;

import atividade_extensionista.projeto_ambiental.model.Ocorrencia;
import atividade_extensionista.projeto_ambiental.model.StatusOcorrencia;
import org.springframework.stereotype.Component;

@Component
public class AlteradorStatusOcorrencia implements AlteradorStatus{

    @Override
    public void alterarStatus(Ocorrencia ocorrencia, StatusOcorrencia status) {
        if (ocorrencia.getStatusOcorrencia() == StatusOcorrencia.RESOLVIDO){
            throw new IllegalStateException("A ocorrência já foi finalizada");
        }
        ocorrencia.setStatusOcorrencia(status);
    }
}
