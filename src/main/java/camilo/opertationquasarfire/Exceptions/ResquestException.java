package camilo.opertationquasarfire.Exceptions;

import lombok.Getter;

@Getter
public class ResquestException extends RuntimeException {

    private String code;

    public ResquestException(String code, String message) {
        super(message);
        this.code = code;
    }

}
