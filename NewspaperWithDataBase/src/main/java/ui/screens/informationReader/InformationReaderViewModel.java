package ui.screens.informationReader;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Article;
import model.Newspaper;
import model.Reader;
import services.ServicesArticle;
import services.ServicesNewspaper;
import services.ServicesReaders;

import java.util.List;

public class InformationReaderViewModel {
    private final ServicesReaders servicesReaders;

    private final ServicesArticle servicesArticle;

    private final ServicesNewspaper servicesNewspaper;
    private final ObservableList<Reader> readers;

    private final ObservableList<Newspaper> newspapers;
    private final ObjectProperty<InformationReaderState> state;

    @Inject
    public InformationReaderViewModel(ServicesReaders servicesReaders, ServicesArticle servicesArticle, ServicesNewspaper servicesNewspaper) {
        this.servicesReaders = servicesReaders;
        this.servicesArticle = servicesArticle;
        this.servicesNewspaper = servicesNewspaper;
        readers= FXCollections.observableArrayList();
        newspapers= FXCollections.observableArrayList();
        state = new SimpleObjectProperty<>(new InformationReaderState(false, null));
    }
    public ReadOnlyObjectProperty<InformationReaderState> getState() {
        return state;
    }

    public List<Article> getArticleList(){
        return servicesArticle.getArticleList();
    }
    public ObservableList<Newspaper> getNewspaperList(){
        newspapers.addAll(servicesNewspaper.getNewspaperList());
        return newspapers;
    }
    public ObservableList<Reader> getReadersListByNewspaper(Newspaper newspaper) {
        readers.clear();
        readers.addAll(servicesReaders.getReadersListByNewspaper(newspaper));
        return readers;

    }

}
