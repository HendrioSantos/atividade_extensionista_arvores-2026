package atividade_extensionista.projeto_ambiental.components;

import atividade_extensionista.projeto_ambiental.infra.exception.InvalidoException;
import atividade_extensionista.projeto_ambiental.model.StatusOcorrencia;
import atividade_extensionista.projeto_ambiental.repository.OcorrenciaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class OcorrenciaExclusaoLogica implements OcorrenciaMetodoExcluir {

    @Override
    public void excluirOcorrencia(Long id, OcorrenciaRepository repository) {
        var ocorrencia = repository.findById(id)
                .orElseThrow(() -> new InvalidoException("Não foi possível encontrar a Ocorrencia", HttpStatus.NOT_FOUND));
        ocorrencia.setStatusOcorrencia(StatusOcorrencia.PROCESSADA);
        ocorrencia.setAtivo(false);
        repository.save(ocorrencia);
    }

    @Override
    public boolean ExclusaoLogica(boolean logico) {
        return logico;
    }
}
