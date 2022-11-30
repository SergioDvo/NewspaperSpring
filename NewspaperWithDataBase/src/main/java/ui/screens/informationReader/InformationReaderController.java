package ui.screens.informationReader;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Newspaper;
import model.Reader;
import ui.screens.common.BasePantallaController;

import java.time.LocalDate;

public class InformationReaderController extends BasePantallaController {

    private InformationReaderViewModel informationReaderViewModel;
    @FXML
    private TableView<Reader> tablaReader;
    @FXML
    private TableColumn<Reader, Integer> id;
    @FXML
    private TableColumn<Reader, String> nameReader;
    @FXML
    private TableColumn<Reader, LocalDate> dateReader;
    @FXML
    private TableView<Newspaper> tablaNewspaper;
    @FXML
    private TableColumn<Newspaper, String> idNewspaper;
    @FXML
    private TableColumn<Newspaper, Double> nameNewspaper;

    @FXML
    private TableColumn<Newspaper, LocalDate> dateNewspaper;

    @Inject
    public InformationReaderController(InformationReaderViewModel informationReaderViewModel) {
        this.informationReaderViewModel = informationReaderViewModel;
    }

    public void initialize() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameReader.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateReader.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        tablaNewspaper.setItems(informationReaderViewModel.getNewspaperList());
        idNewspaper.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameNewspaper.setCellValueFactory(new PropertyValueFactory<>("name_newspaper"));
        dateNewspaper.setCellValueFactory(new PropertyValueFactory<>("release_date"));
        informationReaderViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.getError() != null) {
                this.getPrincipalController().sacarAlertError(newState.getError());
            }
            if (newState.isSeleccionOK()) {
                this.getPrincipalController().sacarAlertOkay("Delete OK");
            }
        });
    }
    public void filterByNewspaper() {

        Newspaper newspaper = tablaNewspaper.getSelectionModel().getSelectedItem();

        if (newspaper != null) {
            tablaReader.setItems(informationReaderViewModel.getReadersListByNewspaper(newspaper));
        } else {
            this.getPrincipalController().sacarAlertError("Newspaper not selected");
        }
    }
}
