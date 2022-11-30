package dao;

import JDBC.DBConnectionPool;
import dao.querysConstant.SQLQueries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Article;
import model.ReadArticles;
import model.Reader;
import model.Subscription;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DaoReadArticle {

    private final DBConnectionPool pool;

    @Inject
    public DaoReadArticle(DBConnectionPool pool) {
        this.pool = pool;
    }

    public Either<String, List<ReadArticles>> getAll() {
        List<ReadArticles> readers = new ArrayList<>();
        // Read the XML document from the file
        try (Connection con = pool.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_readarticles_QUERY);
            List<ReadArticles> readerList = readRS(readers, rs);
            return Either.right(readerList);

        } catch (SQLException ex) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, ex);
            return Either.left("Error reading the database");
        }

    }

    private List<ReadArticles> readRS(List<ReadArticles> readers, ResultSet rs) {
        try {
            while (rs.next()) {
                int id_article = rs.getInt("id_article");
                int id_readers = rs.getInt("id_reader");
                ReadArticles reader = new ReadArticles(id_article, id_readers);
                readers.add(reader);
            }
            return readers;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int saveReadArticle(Reader reader, Article article, Integer rating) {

        try (Connection con = pool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.INSERT_READ_ARTICLE)) {
            preparedStatement.setInt(1, article.getId());
            preparedStatement.setInt(2, reader.getId());
            preparedStatement.setInt(3, rating);
            preparedStatement.executeUpdate();
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(DaoReadArticle.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }

    }
}
