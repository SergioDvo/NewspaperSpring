package ui.screens.readersList;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Reader;
import model.Type;
import ui.screens.common.BasePantallaController;

import java.time.LocalDate;

public class ReadersListController extends BasePantallaController {

    private ReadersListViewModel readersListViewModel;
    @FXML
    private TableView<Reader> tablaReader;
    @FXML
    private TableColumn<Reader, Integer> id;
    @FXML
    private TableColumn<Reader, String> nameReader;
    @FXML
    private TableColumn<Reader, LocalDate> dateReader;
    @FXML
    private MFXComboBox<Type> comboBoxType;


    @Inject
    public ReadersListController(ReadersListViewModel readersListViewModel) {
        this.readersListViewModel = readersListViewModel;
    }

    public void initialize() {
        tablaReader.setItems(readersListViewModel.getReaderList());
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameReader.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateReader.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        comboBoxType.getItems().addAll(readersListViewModel.getAllTypes());
        readersListViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.getError() != null) {
                this.getPrincipalController().sacarAlertError(newState.getError());
            }
            if (newState.getReaderList() != null) {
                tablaReader.setItems(readersListViewModel.getReaderList());
                this.getPrincipalController().sacarAlertOkay("Se realizo con exitooo");
            }
        });
    }
    public void filterByType(){
        Type type = comboBoxType.getValue();
        readersListViewModel.getReadersListByType(type.getId());
    }
    public void filterByNewspaperDate(){
        readersListViewModel.getReaderListByNewspaperDate();
    }
}
