package projeto_ambiental.atividade_extensionista.infra.exception;

import org.springframework.validation.FieldError;

public record DadosErroValidacao(
        String campo,
        String mensagem
) {
    public DadosErroValidacao(FieldError error) {
        this(error.getField(), error.getDefaultMessage());
    }
}
