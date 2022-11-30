package ui.screens.updateNewspapers;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Newspaper;
import services.ServicesNewspaper;
import ui.screens.newspaper.NewspaperListState;

public class UpdateNewspapersViewModel {
    private final ServicesNewspaper servicesNewspaper;
    private final ObservableList<Newspaper> newspapers;
    private final ObjectProperty<NewspaperListState> state;

    @Inject
    public UpdateNewspapersViewModel(ServicesNewspaper servicesNewspaper) {
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
    public void updateNewspaper(Newspaper newspaper){
        if (newspaper ==null){
            state.setValue(new NewspaperListState(false, "Complete the textfields for add newspaper"));
            return;
        }
        if (servicesNewspaper.updateNewspaper(newspaper)) {
            newspapers.clear();
            newspapers.addAll(servicesNewspaper.getNewspaperList());
            state.setValue(new NewspaperListState(true, null));
        }else {
            state.setValue(new NewspaperListState(false, "This newspaper is already in the database"));
        }
    }
}
