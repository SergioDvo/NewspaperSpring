package ui.screens.addNewspaper;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Newspaper;
import model.Query1;
import ui.screens.common.BasePantallaController;

import java.time.LocalDate;

public class AddNewspaperController extends BasePantallaController {

    private final AddNewspaperViewModel addNewspaperViewModel;
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
    public AddNewspaperController(AddNewspaperViewModel addNewspaperViewModel) {
        this.addNewspaperViewModel = addNewspaperViewModel;
    }

    public void initialize() {
        tablaNewspaper.setItems(addNewspaperViewModel.getNewspaperList());
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameNewspaper.setCellValueFactory(new PropertyValueFactory<>("name_newspaper"));
        dateNewspaper.setCellValueFactory(new PropertyValueFactory<>("release_date"));
        addNewspaperViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.getError() != null) {
                this.getPrincipalController().sacarAlertError(newState.getError());
            }
            if (newState.isSeleccionOK()) {
                this.getPrincipalController().sacarAlertOkay("Se realizo con exitooo");
            }
        });
    }
    public void add(){
        if (idNameNewspaper != null && datePicker != null) {
            Newspaper newspaper = new Newspaper(0,idNameNewspaper.getText(),datePicker.getValue());
            addNewspaperViewModel.addNewspaper(newspaper);
        }else
            addNewspaperViewModel.addNewspaper(null);
    }
}
