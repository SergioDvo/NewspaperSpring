package ui.screens.addNewspaper;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Newspaper;
import services.ServicesNewspaper;
import ui.screens.newspaper.NewspaperListState;

public class AddNewspaperViewModel {

    private final ServicesNewspaper servicesNewspaper;
    private final ObservableList<Newspaper> newspapers;
    private final ObjectProperty<AddNewspaperState> state;

    @Inject
    public AddNewspaperViewModel(ServicesNewspaper servicesNewspaper) {
        this.servicesNewspaper = servicesNewspaper;
        newspapers= FXCollections.observableArrayList();
        state = new SimpleObjectProperty<>(new AddNewspaperState(false, null));
    }
    public ReadOnlyObjectProperty<AddNewspaperState> getState() {
        return state;
    }

    public ObservableList<Newspaper> getNewspaperList(){
        newspapers.addAll(servicesNewspaper.getNewspaperList());
        return newspapers;
    }
    public boolean addNewspaper(Newspaper newspaper){
        if (newspaper ==null){
            state.setValue(new AddNewspaperState(false, "Complete the textfields for add newspaper"));
            return false;
        }
        if (servicesNewspaper.addNewspaper(newspaper)) {
            newspapers.clear();
            newspapers.addAll(servicesNewspaper.getNewspaperList());
            state.setValue(new AddNewspaperState(true, null));
            return true;
        }else {
            state.setValue(new AddNewspaperState(false, "This newspaper is already in the database"));
            return false;
        }
    }
}
