package camilo.opertationquasarfire.Models;

import java.util.List;

import lombok.Getter;

@Getter
public class Satellite {

    private String name;
    private Position position;
    private Double distance;
    private List<String> message;

    public Satellite(String name, Position position) {
        this.name = name;
        this.position = position;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }

}
