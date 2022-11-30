package ui.screens.deleteSubscriptions;

import lombok.Data;
import model.Subscription;

import java.util.List;
@Data
public class DeleteSubscriptionsState {
    private final List<Subscription> newspaperList;
    private final String error;
}
