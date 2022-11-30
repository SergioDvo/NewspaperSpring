package ui.screens.readersList;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Article;
import model.Reader;
import model.Type;
import services.ServicesArticle;
import services.ServicesReaders;
import services.ServicesType;

import java.util.List;

public class ReadersListViewModel {

    private final ServicesReaders serviciosReaders;

    private final ServicesArticle serviciosArticle;
    private final ServicesType serviciosType;
    private final ObservableList<Reader> readers;
    private final ObjectProperty<ReadersListState> state;

    @Inject
    public ReadersListViewModel(ServicesReaders serviciosReaders,ServicesArticle serviciosArticle,ServicesType serviciosType) {
        this.serviciosReaders=serviciosReaders;
        this.serviciosArticle=serviciosArticle;
        this.serviciosType=serviciosType;
        readers= FXCollections.observableArrayList();
        state = new SimpleObjectProperty<>(new ReadersListState(null, null));
    }
    public ReadOnlyObjectProperty<ReadersListState> getState() {
        return state;
    }

    public ObservableList<Reader> getReaderList() {
        readers.addAll(serviciosReaders.getReadersList().get());
        return readers;
    }
    public List<Article> getArticleList(){
        return serviciosArticle.getArticleList();
    }
    public void getReadersListByType(int type) {
        readers.clear();
        readers.addAll(serviciosReaders.getReadersListByArticleType(type));
    }
    public void getReaderListByNewspaperDate(){
        readers.clear();
        readers.addAll(serviciosReaders.getReadersListByNewspaperDate());
    }
    public List<Type> getAllTypes(){
        return serviciosType.getAll();
    }
}
