package ui.screens.articles;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Article;
import model.Newspaper;
import model.Query1;
import model.Type;
import services.ServicesArticle;
import services.ServicesNewspaper;
import services.ServicesReaders;
import services.ServicesType;

import java.util.List;

public class ArticlesViewModel {
    private final ServicesArticle servicesArticle;

    private final ServicesNewspaper servicesNewspaper;
    private final ServicesReaders servicesReaders;
    private final ObservableList<Article> articles;
    private final ObjectProperty<ArticlesState> state;
    private final ObservableList<Newspaper> newspapers;
    private final ServicesType servicesType;

    @Inject
    public ArticlesViewModel(ServicesArticle servicesArticle,ServicesNewspaper servicesNewspaper ,ServicesType servicesType,ServicesReaders servicesReaders) {
        this.servicesArticle = servicesArticle;
        this.servicesType = servicesType;
        this.servicesReaders = servicesReaders;
        this.servicesNewspaper = servicesNewspaper;
        articles = FXCollections.observableArrayList();
        newspapers = FXCollections.observableArrayList();
        state = new SimpleObjectProperty<>(new ArticlesState(false, null));
    }
    public ReadOnlyObjectProperty<ArticlesState> getState() {
        return state;
    }

    public ObservableList<Article> getArticleList(){
        articles.addAll(servicesArticle.getArticleList());
        return articles;
    }
    public ObservableList<Article> articleList(){
        return articles;
    }
    public void getArticleListByType(int type){
        articles.clear();
        articles.addAll(servicesArticle.getArticleListByType(type));
    }
    public List<Type> getTypeList(){
        return servicesType.getAll();
    }
    public int counterReaders(int idArticle){
        return servicesReaders.counterReaders(idArticle);
    }
    public List<Type> getAllTypes(){
        return servicesType.getAll();
    }
    public void query4(int idNewspaper){
        articles.clear();
        articles.addAll(servicesArticle.query4(idNewspaper));
    }
    public ObservableList<Newspaper> getNewspaperList(){
        newspapers.addAll(servicesNewspaper.getNewspaperList());
        return newspapers;
    }
    public ObservableList<Query1> showArticlesQuery() {
        return FXCollections.observableArrayList(servicesArticle.getArticlesQuery().get());
    }
}
