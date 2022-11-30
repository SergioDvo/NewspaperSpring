package ui.screens.articles;

import io.github.palexdev.materialfx.controls.MFXComboBox;
import jakarta.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Article;
import model.Newspaper;
import model.Query1;
import model.Type;
import ui.screens.common.BasePantallaController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArticlesController extends BasePantallaController {

    private final ArticlesViewModel articlesViewModel;
    @FXML
    private TableView<Article> tableArticle;
    @FXML
    private TableView<Newspaper> tablaNewspaper;
    @FXML
    private TableColumn<Article, Integer> id;
    @FXML
    private TableColumn<Article, String> nameArticle;
    @FXML
    private TableColumn<Article, Integer> idNewspaper;
    @FXML
    private TableColumn<Newspaper, String> idofNewspaper;
    @FXML
    private TableColumn<Newspaper, String> nameNewspaper;
    @FXML
    private TableView<Query1> articlesQueryTable;
    @FXML
    private TableColumn<Query1, String> nameArticleColumn;

    @FXML
    private TableColumn<Query1, Integer> readerCountColumn;

    @FXML
    private TableColumn<Query1, String> articleTypeColumn;
    @FXML
    private TableColumn<Newspaper, LocalDate> dateNewspaper;
    @FXML
    private MFXComboBox<Type> comboBoxType;
    @FXML
    private Label readersCounter;

//TODO AÑADIR LOGIN A AL AÑADIR READER

    @Inject
    public ArticlesController(ArticlesViewModel articlesViewModel) {
        this.articlesViewModel = articlesViewModel;
    }

    public void initialize() {
        tableArticle.setItems(articlesViewModel.getArticleList());
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameArticle.setCellValueFactory(new PropertyValueFactory<>("name_article"));
        idNewspaper.setCellValueFactory(new PropertyValueFactory<>("id_newspaper"));
        comboBoxType.getItems().addAll(articlesViewModel.getAllTypes());
        tablaNewspaper.setItems(articlesViewModel.getNewspaperList());
        idofNewspaper.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameNewspaper.setCellValueFactory(new PropertyValueFactory<>("name_newspaper"));
        dateNewspaper.setCellValueFactory(new PropertyValueFactory<>("release_date"));
        nameArticleColumn.setCellValueFactory(new PropertyValueFactory<>("name_article"));
        readerCountColumn.setCellValueFactory(new PropertyValueFactory<>("count"));
        articleTypeColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        articlesViewModel.getState().addListener((observableValue, oldState, newState) -> {
            if (newState.getError() != null) {
                this.getPrincipalController().sacarAlertError(newState.getError());
            }
            if (newState.isSeleccionOK()) {
                this.getPrincipalController().sacarAlertOkay("Se realizo con exitooo");
            }
        });
    }
    public void filterByType(){
        Type type = comboBoxType.getValue();
        articlesViewModel.getArticleListByType(type.getId());
    }
    public void counterReaders(){
        Article article = tableArticle.getSelectionModel().getSelectedItem();
        if (article != null) {
            readersCounter.setText(String.valueOf(articlesViewModel.counterReaders(article.getId())));
        }else {
            this.getPrincipalController().sacarAlertError("You must select an article");
        }
    }
    @FXML
    private void query4(){
        Newspaper newspaper = tablaNewspaper.getSelectionModel().getSelectedItem();
        if (newspaper != null) {
            articlesViewModel.query4(newspaper.getId());
        }else {
            this.getPrincipalController().sacarAlertError("You must select a newspaper");
        }
    }
    @FXML
    private void query3(){
        articlesQueryTable.setItems(articlesViewModel.showArticlesQuery());
    }

}
