package ui.screens.deleteNewspaper;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Newspaper;
import ui.screens.common.BasePantallaController;

import java.time.LocalDate;

public class DeleteNewspaperController extends BasePantallaController {
    private DeleteNewspaperViewModel deleteNewspaperViewModel;
    @FXML
    private TableView<Newspaper> tablaNewspaper;
    @FXML
    private TableColumn<Newspaper, String> id;
    @FXML
    private TableColumn<Newspaper, Double> nameNewspaper;

    @FXML
    private TableColumn<Newspaper, LocalDate> dateNewspaper;


    @Inject
    public DeleteNewspaperController(DeleteNewspaperViewModel deleteNewspaperViewModel) {
        this.deleteNewspaperViewModel = deleteNewspaperViewModel;
    }
    public void initialize() {
        tablaNewspaper.setItems(deleteNewspaperViewModel.getNewspaperList());
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameNewspaper.setCellValueFactory(new PropertyValueFactory<>("name_newspaper"));
        dateNewspaper.setCellValueFactory(new PropertyValueFactory<>("release_date"));
        deleteNewspaperViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.getError() != null) {
                this.getPrincipalController().sacarAlertError(newState.getError());
            }
            if (newState.isSeleccionOK()) {
                this.getPrincipalController().sacarAlertOkay("Done it!");
            }
        });
    }

    public void deleteNewspaper() {
        Newspaper newspaper = tablaNewspaper.getSelectionModel().getSelectedItem();
        if (newspaper != null) {
            if (getPrincipalController().askForDelete()){
                deleteNewspaperViewModel.deleteNewspaper(newspaper);
            }else {
                getPrincipalController().sacarAlertError("Okay we cancel the operation");
            }
        } else {
            this.getPrincipalController().sacarAlertError("You didn't select any newspaper");
        }
    }
}
