package ui.screens.deleteNewspaper;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Newspaper;
import services.ServicesNewspaper;

public class DeleteNewspaperViewModel {

    private final ServicesNewspaper servicesNewspaper;
    private final ObservableList<Newspaper> newspapers;
    private final ObjectProperty<DeleteNewspaperState> state;

    @Inject
    public DeleteNewspaperViewModel(ServicesNewspaper servicesNewspaper) {
        this.servicesNewspaper = servicesNewspaper;
        newspapers= FXCollections.observableArrayList();
        state = new SimpleObjectProperty<>(new DeleteNewspaperState(false, null));
    }
    public ReadOnlyObjectProperty<DeleteNewspaperState> getState() {
        return state;
    }

    public ObservableList<Newspaper> getNewspaperList(){
        newspapers.addAll(servicesNewspaper.getNewspaperList());
        return newspapers;
    }

    public void deleteNewspaper(Newspaper newspaper){
        if (servicesNewspaper.deleteNewspaper(newspaper)) {
            state.set(new DeleteNewspaperState(true, null));
            newspapers.clear();
            newspapers.addAll(servicesNewspaper.getNewspaperList());
        }else {
            state.set(new DeleteNewspaperState(false, "This newspaper is not in the database"));
        }

    }
}
