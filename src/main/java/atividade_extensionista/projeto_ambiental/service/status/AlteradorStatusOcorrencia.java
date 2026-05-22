package atividade_extensionista.projeto_ambiental.service.status;

import org.springframework.stereotype.Component;
import projeto_ambiental.atividade_extensionista.model.Ocorrencia;
import projeto_ambiental.atividade_extensionista.model.StatusOcorrencia;

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
