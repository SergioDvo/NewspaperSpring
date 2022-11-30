package dao.impl;

import model.Newspaper;

import java.util.List;

public interface DaoNewspaperImpl {
    List<Newspaper> getAll();

    int save(Newspaper newspaper);

    int delete(Newspaper newspaper);
}
