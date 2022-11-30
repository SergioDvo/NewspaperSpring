package ui.screens.addReaderArticles;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Article;
import model.Reader;
import services.ServicesArticle;
import services.ServicesReaders;

public class AddReaderArticleViewModel {

    private final ServicesReaders servicesReaders;

    private final ServicesArticle servicesArticle;
    private final ObservableList<Reader> readers;

    private final ObservableList<Article> articles;
    private final ObjectProperty<AddReaderArticleState> state;

    @Inject
    public AddReaderArticleViewModel(ServicesReaders servicesReaders, ServicesArticle servicesArticle) {
        this.servicesReaders = servicesReaders;
        this.servicesArticle = servicesArticle;
        readers = FXCollections.observableArrayList();
        articles = FXCollections.observableArrayList();
        state = new SimpleObjectProperty<>(new AddReaderArticleState(null, null));
    }

    public ReadOnlyObjectProperty<AddReaderArticleState> getState() {
        return state;
    }
    public ObservableList<Article> getArticleList(){
        articles.addAll(servicesArticle.getArticleList());
        return articles;
    }

    public ObservableList<Reader> getReaderList() {
        readers.addAll(servicesReaders.getReadersList().get());
        return readers;
    }
    public void addReaderArticle(Article article,Reader reader,int rating){
        if (servicesReaders.addReaderArticle(reader,article,rating)){
            state.setValue(new AddReaderArticleState(null,"Articule added properly to "+reader.getName()));
        }else
            state.setValue(new AddReaderArticleState(("Error adding article to " +reader.getName()),null));

    }
}

