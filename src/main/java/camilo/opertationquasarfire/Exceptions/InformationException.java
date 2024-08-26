package camilo.opertationquasarfire.exceptions;

import lombok.Getter;

@Getter
public class InformationException extends RuntimeException {

    private String code;

    public InformationException(String code) {
        super("There is not enough information from satellites.");
        this.code = code;
    }

    public InformationException(String code, String message) {
        super(message);
        this.code = code;
    }

}
