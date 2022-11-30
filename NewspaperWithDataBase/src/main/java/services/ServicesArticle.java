package services;

import dao.DaoArticle;
import dao.DaoNewspaper;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Article;
import model.Query1;
import model.Reader;

import java.util.ArrayList;
import java.util.List;

public class ServicesArticle implements services.impl.ServiciosArticleImpl {

    private final DaoArticle daoArticle;

    private ServicesType servicesType;

    private DaoNewspaper daoNewspaper;

    @Inject
    public ServicesArticle(DaoArticle daoArticle, ServicesType servicesType, DaoNewspaper daoNewspaper) {
        this.daoArticle = daoArticle;
        this.servicesType = servicesType;
        this.daoNewspaper = daoNewspaper;
    }

    @Override
    public List<Article> getArticleList(){
        return daoArticle.getAll();
    }

    @Override
    public boolean addArticle(Article article){
        List<Integer> allIdsType = servicesType.getAllIds();
        List<Integer> allIdsNewspaper = getAllIdNewspaper();
        if (allIdsType.contains(article.getId_type()) && allIdsNewspaper.contains(article.getId_newspaper())) {
            if (!daoArticle.getAll().contains(article)) {
                daoArticle.save(article);
                return true;
            }else {
                return false;
            }
        } else {
            return false;
        }
    }
    @Override
    public List<Integer> getAllIdNewspaper(){
        List<Integer> allIdsNewspaper = new ArrayList<>();
        for (int i = 0; i < daoNewspaper.getAll().size(); i++) {
            allIdsNewspaper.add(daoNewspaper.getAll().get(i).getId());
        }
        return allIdsNewspaper;
    }
    @Override
    public List<Article> getArticleListByType(int type){
        List<Article> articleList = getArticleList();
        articleList.removeIf(article -> article.getId_type() != type);
        return articleList;
    }
    @Override
    public boolean isArticleIdInList(int id){
        return getArticleList().stream().anyMatch(article -> article.getId() == id);
    }
    public List<Article> query4(int id_newspaper){
        return daoArticle.query4(id_newspaper);
    }
    public Either<Integer, List<Query1>> getArticlesQuery() {
        return daoArticle.getArticlesQuery();
    }

}
