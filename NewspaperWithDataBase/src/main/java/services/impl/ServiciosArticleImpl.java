package services.impl;

import model.Article;

import java.util.List;

public interface ServiciosArticleImpl {
    List<Article> getArticleList();

    boolean addArticle(Article article);

    List<Integer> getAllIdNewspaper();

    List<Article> getArticleListByType(int type);

    boolean isArticleIdInList(int id);
}
