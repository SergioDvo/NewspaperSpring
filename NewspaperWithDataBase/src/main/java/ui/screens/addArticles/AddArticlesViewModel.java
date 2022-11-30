package ui.screens.addArticles;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Article;
import services.ServicesArticle;

public class AddArticlesViewModel {

    private final ServicesArticle servicesArticle;
    private final ObservableList<Article> articles;
    private final ObjectProperty<AddArticlesState> state;

    @Inject
    public AddArticlesViewModel(ServicesArticle servicesArticle) {
        this.servicesArticle = servicesArticle;
        articles = FXCollections.observableArrayList();
        state = new SimpleObjectProperty<>(new AddArticlesState(false, null));
    }
    public ReadOnlyObjectProperty<AddArticlesState> getState() {
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
    public void addArticle(Article article){
        if (servicesArticle.addArticle(article)) {
            articles.clear();
            articles.addAll(servicesArticle.getArticleList());
            state.setValue(new AddArticlesState(true, null));
        }else {
            state.setValue(new AddArticlesState(false, null));
        }
    }
    public boolean isArticleIdInList(int id){
        return servicesArticle.isArticleIdInList(id);
    }
}
