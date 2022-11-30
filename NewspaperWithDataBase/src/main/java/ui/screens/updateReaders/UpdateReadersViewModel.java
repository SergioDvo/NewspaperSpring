package ui.screens.updateReaders;

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

public class UpdateReadersViewModel {

    private final ServicesReaders servicesReaders;

    private final ServicesArticle servicesArticle;
    private final ObservableList<Reader> readers;
    private final ObjectProperty<UpdateReadersState> state;

    @Inject
    public UpdateReadersViewModel(ServicesReaders servicesReaders, ServicesArticle servicesArticle) {
        this.servicesReaders = servicesReaders;
        this.servicesArticle = servicesArticle;
        readers= FXCollections.observableArrayList();
        state = new SimpleObjectProperty<>(new UpdateReadersState(null, null));
    }
    public ReadOnlyObjectProperty<UpdateReadersState> getState() {
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
            state.setValue(new UpdateReadersState(readers, null));
        }else {
            state.setValue(new UpdateReadersState(null, "Not added"));
        }
    }
    public void updateReader(String name, LocalDate dateOfBirth, int id,Reader r){
        Reader newReader = new Reader(id,name, dateOfBirth);
        if (servicesReaders.updateReader(r) > 0){
            readers.remove(r);
            readers.add(newReader);
            state.setValue(new UpdateReadersState(readers, null));
        }else {
            state.setValue(new UpdateReadersState(null, "Not updated"));
        }
    }


}
