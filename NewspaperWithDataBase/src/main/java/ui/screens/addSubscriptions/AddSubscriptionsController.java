package ui.screens.addSubscriptions;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Newspaper;
import model.Subscription;
import ui.screens.common.BasePantallaController;

import java.time.LocalDate;

public class AddSubscriptionsController extends BasePantallaController {
    private final AddSubscriptionsViewModel addSubscriptionsViewModel;
    @FXML
    private TableView<Newspaper> tablaNewspaper;
    @FXML
    private TableColumn<Newspaper, String> id;
    @FXML
    private TableColumn<Newspaper, Double> nameNewspaper;
    @FXML
    private TableView<Subscription> tablaSubscriptions;
    @FXML
    private TableColumn<Subscription, LocalDate> signingDate;
    @FXML
    private TableColumn<Subscription, Integer> idNewspaper;
    @FXML
    private TableColumn<Newspaper, LocalDate> dateNewspaper;

    private int idReader;


    @Inject
    public AddSubscriptionsController(AddSubscriptionsViewModel addSubscriptionsViewModel) {
        this.addSubscriptionsViewModel = addSubscriptionsViewModel;
    }

    public void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameNewspaper.setCellValueFactory(new PropertyValueFactory<>("name_newspaper"));
        dateNewspaper.setCellValueFactory(new PropertyValueFactory<>("release_date"));
        idNewspaper.setCellValueFactory(new PropertyValueFactory<>("idNewspaper"));
        signingDate.setCellValueFactory(new PropertyValueFactory<>("signingDate"));
        tablaNewspaper.setItems(addSubscriptionsViewModel.getNewspaperList());
        addSubscriptionsViewModel.getState().addListener((observableValue, oldState, newState) -> {
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
        addSubscriptionsViewModel.getSubscriptionList(idReader);
    }
    @FXML
    private void add(){
        Newspaper newspaper = tablaNewspaper.getSelectionModel().getSelectedItem();
        if (newspaper != null) {
            Subscription subscription = new Subscription(idReader, newspaper.getId(), LocalDate.now(),null);
            addSubscriptionsViewModel.addSubscription(subscription,idReader);
        }else
            this.getPrincipalController().sacarAlertError("Any newspaper selected");

    }

}
