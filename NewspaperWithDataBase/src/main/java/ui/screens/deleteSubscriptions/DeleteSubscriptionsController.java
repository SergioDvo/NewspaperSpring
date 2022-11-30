package ui.screens.deleteSubscriptions;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Subscription;
import ui.screens.common.BasePantallaController;

import java.time.LocalDate;

public class DeleteSubscriptionsController extends BasePantallaController {
    private final DeleteSubscriptionsViewModel deleteSubscriptionsViewModel;

    @FXML
    private TableView<Subscription> tablaSubscriptions;
    @FXML
    private TableColumn<Subscription, LocalDate> signingDate;
    @FXML
    private TableColumn<Subscription, Integer> idNewspaper;
    private int idReader;


    @Inject
    public DeleteSubscriptionsController(DeleteSubscriptionsViewModel deleteSubscriptionsViewModel) {
        this.deleteSubscriptionsViewModel = deleteSubscriptionsViewModel;
    }

    public void initialize() {
        idNewspaper.setCellValueFactory(new PropertyValueFactory<>("idNewspaper"));
        signingDate.setCellValueFactory(new PropertyValueFactory<>("signingDate"));
        deleteSubscriptionsViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.getError() != null) {
                this.getPrincipalController().sacarAlertError(newState.getError());
            }
            if (newState.getNewspaperList() != null) {
                tablaSubscriptions.getItems().setAll(newState.getNewspaperList());
            }
        });
    }
    public void principalCargado(){
        idReader = this.getPrincipalController().getIdReader();
        deleteSubscriptionsViewModel.getSubscriptionList(idReader);
    }
    @FXML
    private void delete(){
        Subscription subscription = tablaSubscriptions.getSelectionModel().getSelectedItem();
        if (subscription != null) {
            deleteSubscriptionsViewModel.cancelSubscription(subscription,idReader);
            deleteSubscriptionsViewModel.getSubscriptionList(idReader);
        }else
            this.getPrincipalController().sacarAlertError("Any newspaper selected");

    }
}
