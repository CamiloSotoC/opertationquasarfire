package camilo.opertationquasarfire.Exceptions;

import lombok.Getter;

@Getter
public class InformationException extends RuntimeException {

    private String code;

    public InformationException(String code, String message) {
        super(message);
        this.code = code;
    }
}
