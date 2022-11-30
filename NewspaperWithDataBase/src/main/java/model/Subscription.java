package model;

import DI.LocalDateAdapter;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@XmlRootElement(name = "subscription")
@XmlAccessorType(XmlAccessType.FIELD)
public class Subscription {

    @XmlElement(name = "id_reader")
    private int idReader;
    @XmlElement(name = "id_newspaper")
    private int idNewspaper;

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate signingDate;

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate cancellationDate;

    public Subscription(int idReader,int idNewspaper,LocalDate signingDate,LocalDate cancellationDate){
        this.idReader = idReader;
        this.idNewspaper = idNewspaper;
        this.signingDate = signingDate;
        this.cancellationDate = cancellationDate;
    }
}
