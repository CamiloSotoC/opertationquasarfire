package camilo.opertationquasarfire.exceptions;

import lombok.Getter;

@Getter
public class ResquestException extends RuntimeException {

    private final String code;

    public ResquestException(String code) {
        super("There is not enough information from satellites.");
        this.code = code;
    }

    public ResquestException(String code, String message) {
        super(message);
        this.code = code;
    }

}
