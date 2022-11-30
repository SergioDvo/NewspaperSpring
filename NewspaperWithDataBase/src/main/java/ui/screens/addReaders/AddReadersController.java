package ui.screens.addReaders;

import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXTextField;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Reader;
import ui.screens.common.BasePantallaController;

import java.time.LocalDate;

public class AddReadersController extends BasePantallaController {

    private AddReadersViewModel addReadersViewModel;
    @FXML
    private TableView<Reader> tablaReader;
    @FXML
    private TableColumn<Reader, Integer> id;
    @FXML
    private TableColumn<Reader, String> nameReader;
    @FXML
    private TableColumn<Reader, LocalDate> dateReader;
    @FXML
    private MFXTextField name;
    @FXML
    private MFXDatePicker date;


    @Inject
    public AddReadersController(AddReadersViewModel addReadersViewModel) {
        this.addReadersViewModel = addReadersViewModel;
    }

    public void initialize() {
        tablaReader.setItems(addReadersViewModel.getReaderList());
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameReader.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateReader.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        addReadersViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.getError() != null) {
                this.getPrincipalController().sacarAlertError(newState.getError());
            }
            if (newState.getReaderList() != null) {
                this.getPrincipalController().sacarAlertOkay("Done");
            }
        });
    }
    public void addReader(){
        addReadersViewModel.addReader(name.getText(),date.getValue());
    }

}
