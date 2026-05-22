package atividade_extensionista.projeto_ambiental.infra.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidoException extends RuntimeException {

    private HttpStatus status;

    public InvalidoException(String mensagem) {
        super(mensagem);
    }

    public InvalidoException(String mensagem, HttpStatus status) {
        super(mensagem);
        this.status = status;
    }

    public InvalidoException(HttpStatus status) {
        this.status = status;
    }
}
