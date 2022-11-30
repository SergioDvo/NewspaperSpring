package model;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@XmlRootElement(name = "readArticle")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReadArticles {

    private int id;
    @XmlElement(name = "id_reader")
    private int idReader;
    @XmlElement(name = "id_article")
    private int idArticle;

    private int rating;

    public ReadArticles(int idReader, int idArticle) {
        this.idReader = idReader;
        this.idArticle = idArticle;
    }
}
