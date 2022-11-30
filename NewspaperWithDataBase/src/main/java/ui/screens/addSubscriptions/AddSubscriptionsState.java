package ui.screens.addSubscriptions;

import lombok.Data;
import model.Subscription;

import java.util.List;
@Data
public class AddSubscriptionsState {
    private final List<Subscription> newspaperList;
    private final String error;
}
