package dao.impl;

import model.Article;

import java.util.List;

public interface DaoArticleImpl {
    List<Article> getAll();

    int save(Article article);

}
