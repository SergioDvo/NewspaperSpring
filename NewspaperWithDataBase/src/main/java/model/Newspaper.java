package model;

import lombok.Data;

import java.time.LocalDate;
import java.util.Objects;

@Data
public class Newspaper {

    private int id;
    private String name_newspaper;
    private LocalDate release_date;

    public Newspaper(){

    }


    public Newspaper(int id, String name_newspaper, LocalDate release_date) {
        this.id = id;
        this.name_newspaper = name_newspaper;
        this.release_date = release_date;
    }
    public Newspaper(String linea) {
        String [] camps = linea.split(";");
        this.id = Integer.parseInt(camps[0]);
        this.name_newspaper = camps[1];
        this.release_date = LocalDate.parse(camps[2]);
    }
    public String convertToLine(){
        return id + ";" + name_newspaper + ";" + release_date+"\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Newspaper newspaper = (Newspaper) o;
        return id == newspaper.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
