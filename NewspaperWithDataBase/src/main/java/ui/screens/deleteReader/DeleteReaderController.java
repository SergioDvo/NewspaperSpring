package ui.screens.deleteReader;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Reader;
import ui.screens.common.BasePantallaController;

import java.time.LocalDate;

public class DeleteReaderController extends BasePantallaController {
    private DeleteViewModel deleteViewModel;
    @FXML
    private TableView<Reader> tablaReader;
    @FXML
    private TableColumn<Reader, Integer> id;
    @FXML
    private TableColumn<Reader, String> nameReader;
    @FXML
    private TableColumn<Reader, LocalDate> dateReader;
    @FXML
    private MFXComboBox<Integer> comboBoxType;


    @Inject
    public DeleteReaderController(DeleteViewModel deleteViewModel) {
        this.deleteViewModel = deleteViewModel;
    }

    public void initialize() {
        tablaReader.setItems(deleteViewModel.getReaderList());
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameReader.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateReader.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        deleteViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.getError() != null) {
                this.getPrincipalController().sacarAlertError(newState.getError());
            }
            if (newState.isSeleccionOK()) {
                this.getPrincipalController().sacarAlertOkay("Delete OK");
            }
        });
    }
    public void deleteReader() {

        Reader reader = tablaReader.getSelectionModel().getSelectedItem();
        if (reader != null) {
            deleteViewModel.deleteReader(reader);
        }else{
            this.getPrincipalController().sacarAlertError("Reader not selected");
        }

    }
}
