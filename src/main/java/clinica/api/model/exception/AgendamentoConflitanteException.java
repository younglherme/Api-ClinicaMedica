package clinica.api.model.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AgendamentoConflitanteException extends RuntimeException {

    public AgendamentoConflitanteException(String mensagem) {
        super(mensagem);
    }

    public AgendamentoConflitanteException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
