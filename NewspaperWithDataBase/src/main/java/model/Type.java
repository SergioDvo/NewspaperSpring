package model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Type {

    private int id;
    private String description;

    public Type() {
    }

    public Type(String linea) {
        String [] camps = linea.split(";");
        this.id = Integer.parseInt(camps[0]);
        this.description = camps[1];
    }


    @Override
    public String toString() {
        return id +
                " is " + description;
    }
}
