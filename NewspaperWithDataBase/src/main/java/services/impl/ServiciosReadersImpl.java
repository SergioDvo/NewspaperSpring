package services.impl;

import io.vavr.control.Either;
import model.Article;
import model.Newspaper;
import model.Reader;

import java.util.List;

public interface ServiciosReadersImpl {
    Either<String, List<Reader>> getReadersList();

    List<Reader> getReadersListByArticleType(int type);

    boolean addReaderArticle(Reader reader, Article article, int rating);

    void deleteReader(Reader reader);

    int addReader(Reader r);

    List<Reader> getReadersListByNewspaper(Newspaper newspaper);
}
