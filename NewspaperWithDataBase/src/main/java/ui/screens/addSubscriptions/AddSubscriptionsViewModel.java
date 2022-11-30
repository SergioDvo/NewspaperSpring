package ui.screens.addSubscriptions;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Newspaper;
import model.Subscription;
import services.ServicesNewspaper;
import services.ServicesReaders;

public class AddSubscriptionsViewModel {
    private final ServicesNewspaper servicesNewspaper;

    private final ServicesReaders servicesReaders;
    private final ObservableList<Newspaper> newspapers;

    private final ObjectProperty<AddSubscriptionsState> state;

    @Inject
    public AddSubscriptionsViewModel(ServicesNewspaper servicesNewspaper, ServicesReaders servicesReaders) {
        this.servicesNewspaper = servicesNewspaper;
        this.servicesReaders = servicesReaders;
        newspapers= FXCollections.observableArrayList();
        state = new SimpleObjectProperty<>(new AddSubscriptionsState(null, null));
    }
    public ReadOnlyObjectProperty<AddSubscriptionsState> getState() {
        return state;
    }

    public ObservableList<Newspaper> getNewspaperList(){
        newspapers.addAll(servicesNewspaper.getNewspaperList());
        return newspapers;
    }
    public void getSubscriptionList(int idReader){
        if (servicesReaders.getSubscriptionsList(idReader).isRight()) {
            state.set(new AddSubscriptionsState(servicesReaders.getSubscriptionsList(idReader).get(), null));
        }else{
            state.set(new AddSubscriptionsState(null, servicesReaders.getSubscriptionsList(idReader).getLeft()));
        }
    }
    public void addSubscription(Subscription subscription, int idReader){
        if (servicesReaders.addSubscription(subscription) > 0) {
            state.set(new AddSubscriptionsState(servicesReaders.getSubscriptionsList(idReader).get(), null));
        }else{
            state.set(new AddSubscriptionsState(null, "Error adding subscription"));
        }
    }

}
