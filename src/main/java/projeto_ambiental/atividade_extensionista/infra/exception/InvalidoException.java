package projeto_ambiental.atividade_extensionista.infra.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidoException extends RuntimeException {

    private HttpStatus status;

    public InvalidoException(String mensagem) {
        super(mensagem);
    }

    public InvalidoException(HttpStatus status, String mensagem) {
        super(mensagem);
        this.status = status;
    }

    public InvalidoException(HttpStatus status) {
        this.status = status;
    }
}
