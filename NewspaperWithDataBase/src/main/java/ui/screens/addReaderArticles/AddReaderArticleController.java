package ui.screens.addReaderArticles;

import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Article;
import model.Reader;
import ui.screens.common.BasePantallaController;

import java.time.LocalDate;

public class AddReaderArticleController extends BasePantallaController {
    private AddReaderArticleViewModel addReaderArticleViewModel;
    @FXML
    private TableView<Reader> tablaReader;
    @FXML
    private TableView<Article> tableArticle;
    @FXML
    private TableColumn<Article, Integer> idArticle;
    @FXML
    private TableColumn<Article, String> nameArticle;
    @FXML
    private TableColumn<Article, String> description;
    @FXML
    private TableColumn<Reader, Integer> id;
    @FXML
    private TableColumn<Reader, String> nameReader;
    @FXML
    private TableColumn<Reader, LocalDate> dateReader;
    @FXML
    public ComboBox<Integer> comboBoxRating;
    @FXML
    public TextField txtId;



    @Inject
    public AddReaderArticleController(AddReaderArticleViewModel addReaderArticleViewModel) {
        this.addReaderArticleViewModel = addReaderArticleViewModel;
    }

    public void initialize() {
        tablaReader.setItems(addReaderArticleViewModel.getReaderList());
        idArticle.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameReader.setCellValueFactory(new PropertyValueFactory<>("name"));
        dateReader.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        tableArticle.setItems(addReaderArticleViewModel.getArticleList());
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameArticle.setCellValueFactory(new PropertyValueFactory<>("name_article"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        comboBoxRating.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        addReaderArticleViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.getError() != null) {
                this.getPrincipalController().sacarAlertOkay(newState.getError());
            }
            if (newState.getString() != null) {
                this.getPrincipalController().sacarAlertError(newState.getString());
            }
        });
    }
    public void addReadArticle() {

        Article article = tableArticle.getSelectionModel().getSelectedItem();
        Reader reader = tablaReader.getSelectionModel().getSelectedItem();
        if (article != null && reader != null && comboBoxRating.getValue() != null && txtId.getText() != null) {
            addReaderArticleViewModel.addReaderArticle(article, reader, comboBoxRating.getValue());
        } else {
            this.getPrincipalController().sacarAlertError("No items or rating selected");
        }
    }
}

