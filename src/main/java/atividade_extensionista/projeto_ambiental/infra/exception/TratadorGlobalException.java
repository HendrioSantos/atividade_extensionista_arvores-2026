package atividade_extensionista.projeto_ambiental.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class TratadorGlobalException {

    @ExceptionHandler(InvalidoException.class)
    public ResponseEntity<DadosErroSimples> tratarRegraDeNegocio(InvalidoException ex) {
        return ResponseEntity.status(ex.getStatus())
                .body(new DadosErroSimples(ex.getMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<DadosErroValidacao>> tratarErroValidacao(MethodArgumentNotValidException ex) {
        var erros = ex.getFieldErrors().stream()
                .map(DadosErroValidacao::new)
                .toList();
        return ResponseEntity.badRequest().body(erros);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Void> tratar404(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<DadosErroSimples> tratarErro500(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new DadosErroSimples("Erro interno no Servidor " + e.getLocalizedMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<DadosErroSimples> tratarErroLeitura(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body(new DadosErroSimples("Corpo da requisição inválido ou mal formatado: " + ex.getHttpInputMessage(), LocalDateTime.now()));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<DadosErroSimples> tratarParametroAusente(MissingServletRequestParameterException ex) {
        return ResponseEntity.badRequest().body(new DadosErroSimples("Parâmetro obrigatório ausente: " + ex.getParameterName(), LocalDateTime.now()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<DadosErroSimples> tratarErroIntegridade(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new DadosErroSimples("Recurso já cadastrado ou violação de integridade no banco." + ex.getMessage(), LocalDateTime.now()));
    }

}
