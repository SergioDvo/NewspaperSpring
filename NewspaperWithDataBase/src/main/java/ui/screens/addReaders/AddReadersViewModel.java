package ui.screens.addReaders;

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

import java.time.LocalDate;
import java.util.List;

public class AddReadersViewModel {

    private final ServicesReaders servicesReaders;

    private final ServicesArticle servicesArticle;
    private final ObservableList<Reader> readers;
    private final ObjectProperty<AddReadersState> state;

    @Inject
    public AddReadersViewModel(ServicesReaders servicesReaders, ServicesArticle servicesArticle) {
        this.servicesReaders = servicesReaders;
        this.servicesArticle = servicesArticle;
        readers= FXCollections.observableArrayList();
        state = new SimpleObjectProperty<>(new AddReadersState(null, null));
    }
    public ReadOnlyObjectProperty<AddReadersState> getState() {
        return state;
    }

    public ObservableList<Reader> getReaderList() {
        readers.addAll(servicesReaders.getReadersList().get());
        return readers;
    }
    public List<Article> getArticleList(){
        return servicesArticle.getArticleList();
    }
    public void addReader(String name, LocalDate dateOfBirth){
        Reader r = new Reader(0,name, dateOfBirth);
        if (servicesReaders.addReader(r) > 0){
            readers.add(r);
            state.setValue(new AddReadersState(readers, null));
        }else {
            state.setValue(new AddReadersState(null, "Not added"));
        }
    }
}
