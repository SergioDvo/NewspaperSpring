package ui.screens.deleteSubscriptions;

import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.Subscription;
import services.ServicesNewspaper;
import services.ServicesReaders;

public class DeleteSubscriptionsViewModel {
    private final ServicesNewspaper servicesNewspaper;

    private final ServicesReaders servicesReaders;

    private final ObjectProperty<DeleteSubscriptionsState> state;

    @Inject
    public DeleteSubscriptionsViewModel(ServicesNewspaper servicesNewspaper, ServicesReaders servicesReaders) {
        this.servicesNewspaper = servicesNewspaper;
        this.servicesReaders = servicesReaders;
        state = new SimpleObjectProperty<>(new DeleteSubscriptionsState(null, null));
    }
    public ReadOnlyObjectProperty<DeleteSubscriptionsState> getState() {
        return state;
    }
    public void getSubscriptionList(int idReader){
        if (servicesReaders.getSubscriptionsList(idReader).isRight()) {
            state.set(new DeleteSubscriptionsState(servicesReaders.getSubscriptionsList(idReader).get(), null));
        }else{
            state.set(new DeleteSubscriptionsState(null, servicesReaders.getSubscriptionsList(idReader).getLeft()));
        }
    }
    public void cancelSubscription(Subscription subscription, int idReader){
        if (servicesReaders.cancelSubscription(subscription) > 0) {
            state.set(new DeleteSubscriptionsState(servicesReaders.getSubscriptionsList(idReader).get(), null));
        }else{
            state.set(new DeleteSubscriptionsState(null, "Error adding subscription"));
        }
    }
}
