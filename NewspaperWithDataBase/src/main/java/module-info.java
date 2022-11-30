
module NewspaperDataBase {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.yaml;
    requires com.google.gson;
    requires org.apache.logging.log4j;
    requires lombok;
    requires jakarta.xml.bind;
    requires io.vavr;
    requires java.sql;
    requires com.zaxxer.hikari;
    requires spring.jdbc;
    requires jakarta.annotation;
    requires spring.tx;
    requires jakarta.inject;
    requires jakarta.cdi;


    exports model;
    exports ui.main to javafx.graphics;
    exports ui.screens.principal;
    exports ui.screens.common;
    exports config;
    exports dao;
    exports services;
    exports ui.screens.login;
    exports ui.screens.newspaper;
    exports ui.screens.addNewspaper;
    exports ui.screens.articles;
    exports ui.screens.addArticles;
    exports ui.screens.deleteNewspaper;
    exports ui.screens.addReaders;
    exports DI;
    exports ui.screens.addReaderArticles;
    exports ui.screens.deleteReader;
    exports ui.screens.informationReader;
    exports dao.querysConstant;
    exports JDBC;
    exports ui.screens.updateReaders;
    exports ui.screens.deleteSubscriptions;
    exports ui.screens.addSubscriptions;
    exports ui.screens.updateNewspapers;

    opens ui.screens.principal;
    opens ui.main;
    opens model to javafx.base,com.google.gson,javafx.fxml,jakarta.xml.bind;
    opens config;
    opens ui.screens.common;
    opens ui.screens.login;
    opens imagenes;
    opens ui.screens.newspaper;
    opens ui.screens.addNewspaper;
    opens ui.screens.articles;
    opens ui.screens.addArticles;
    opens ui.screens.deleteNewspaper;
    opens ui.screens.addReaders;
    opens ui.screens.addReaderArticles;
    opens ui.screens.deleteReader;
    opens ui.screens.informationReader;
    opens services;
    opens ui.screens.updateReaders;
    opens ui.screens.readersList;
    opens ui.screens.deleteSubscriptions;
    opens ui.screens.addSubscriptions;
    opens ui.screens.updateNewspapers;
}