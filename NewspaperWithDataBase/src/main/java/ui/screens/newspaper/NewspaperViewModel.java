package ui.screens.newspaper;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;
import services.*;

public class NewspaperViewModel {

    private final ServicesNewspaper servicesNewspaper;
    private final ObservableList<Newspaper> newspapers;
    private final ObjectProperty<NewspaperListState> state;

    @Inject
    public NewspaperViewModel(ServicesNewspaper servicesNewspaper) {
        this.servicesNewspaper = servicesNewspaper;
        newspapers= FXCollections.observableArrayList();
        state = new SimpleObjectProperty<>(new NewspaperListState(false, null));
    }
    public ReadOnlyObjectProperty<NewspaperListState> getState() {
        return state;
    }

    public ObservableList<Newspaper> getNewspaperList(){
        newspapers.addAll(servicesNewspaper.getNewspaperList());
        return newspapers;
    }

}
