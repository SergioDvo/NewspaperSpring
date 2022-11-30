package ui.screens.updateNewspapers;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Newspaper;
import ui.screens.common.BasePantallaController;

import java.time.LocalDate;

public class UpdateNewspapersController extends BasePantallaController {

    private final UpdateNewspapersViewModel updateNewspapersViewModel;
    @FXML
    private TableView<Newspaper> tablaNewspaper;
    @FXML
    private TextField idNameNewspaper;

    @FXML
    private DatePicker datePicker;
    @FXML
    private TableColumn<Newspaper, String> id;
    @FXML
    private TableColumn<Newspaper, String> nameNewspaper;

    @FXML
    private TableColumn<Newspaper, LocalDate> dateNewspaper;

    @Inject
    public UpdateNewspapersController(UpdateNewspapersViewModel updateNewspapersViewModel) {
        this.updateNewspapersViewModel = updateNewspapersViewModel;
    }

    public void initialize() {
        tablaNewspaper.setItems(updateNewspapersViewModel.getNewspaperList());
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameNewspaper.setCellValueFactory(new PropertyValueFactory<>("name_newspaper"));
        dateNewspaper.setCellValueFactory(new PropertyValueFactory<>("release_date"));
        updateNewspapersViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.getError() != null) {
                this.getPrincipalController().sacarAlertError(newState.getError());
            }
            if (newState.isSeleccionOK()) {
                this.getPrincipalController().sacarAlertOkay("Se realizo con exitooo");
            }
        });
    }
    public void putData(){
        Newspaper newspaper = tablaNewspaper.getSelectionModel().getSelectedItem();
        idNameNewspaper.setText(newspaper.getName_newspaper());
        datePicker.setValue(newspaper.getRelease_date());
    }
    public void update(){
        if (idNameNewspaper != null && datePicker != null) {
            Newspaper newspaperSelected = tablaNewspaper.getSelectionModel().getSelectedItem();
            Newspaper newspaper = new Newspaper(newspaperSelected.getId(),idNameNewspaper.getText(),datePicker.getValue());
            updateNewspapersViewModel.updateNewspaper(newspaper);
        }else
            updateNewspapersViewModel.updateNewspaper(null);
    }
}
