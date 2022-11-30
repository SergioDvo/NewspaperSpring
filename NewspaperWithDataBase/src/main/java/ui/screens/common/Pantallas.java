package ui.screens.common;

public enum Pantallas {

    LOGIN ("/fxml/Login.fxml"),

    PANTALLANEWSPAPER("/fxml/newspaper/newspaperList.fxml"),

    PANTALLAADDNEWSPAPER("/fxml/newspaper/addNewspaper.fxml"),

    PANTALLAARTICLE("/fxml/articles/articles.fxml"),

    PANTALLAADDARTICLE("/fxml/articles/addArticles.fxml"),

    PANTALLADELETENEWSPAPER("/fxml/newspaper/deleteNewspaper.fxml"),

    PANTALLAADDREADER("/fxml/readers/addReaders.fxml"),

    PANTALLAADDREADARTICLE("/fxml/readers/addReaderArticle.fxml"),

    PANTALLADELETEREADER("/fxml/readers/deleteReader.fxml"),

    PANTALLAREADEROFNEWSPAPER("/fxml/readers/informationReaderNewspaper.fxml"),

    PANTALLAUPDATEREADER("/fxml/readers/updateReaders.fxml"),

    PANTALLAREADER("/fxml/readers/readersList.fxml"),

    PANTALLASUBSCRIBENEWSPAPER("/fxml/subscriptions/addSubscriptions.fxml"),

    PANTALLADELETESUBSCRIBENEWSPAPER("/fxml/subscriptions/deleteSubscriptions.fxml"),

    PANTALLAUPDATENEWSPAPER("/fxml/newspaper/updateNewspapers.fxml");



    private String ruta;
    Pantallas(String ruta) {
        this.ruta=ruta;
    }
    public String getRuta(){return ruta;}


}
