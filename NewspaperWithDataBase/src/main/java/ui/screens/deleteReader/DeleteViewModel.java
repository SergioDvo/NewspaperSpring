package ui.screens.deleteReader;

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

import java.util.List;

public class DeleteViewModel {
    private final ServicesReaders servicesReaders;

    private final ServicesArticle servicesArticle;
    private final ObservableList<Reader> readers;
    private final ObjectProperty<DeleteReaderState> state;

    @Inject
    public DeleteViewModel(ServicesReaders servicesReaders, ServicesArticle servicesArticle) {
        this.servicesReaders = servicesReaders;
        this.servicesArticle = servicesArticle;
        readers= FXCollections.observableArrayList();
        state = new SimpleObjectProperty<>(new DeleteReaderState(false, null));
    }
    public ReadOnlyObjectProperty<DeleteReaderState> getState() {
        return state;
    }

    public ObservableList<Reader> getReaderList() {
        readers.addAll(servicesReaders.getReadersList().get());
        return readers;
    }
    public List<Article> getArticleList(){
        return servicesArticle.getArticleList();
    }

    public void deleteReader(Reader reader){
        servicesReaders.deleteReader(reader);
        if (!servicesReaders.getReadersList().get().isEmpty()){
            readers.clear();
            readers.addAll(servicesReaders.getReadersList().get());
            state.setValue(new DeleteReaderState(true, null));
        }else{
            state.setValue(new DeleteReaderState(false, "Error reading xml"));
        }
    }
}
