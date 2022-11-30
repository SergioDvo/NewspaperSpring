package dao;

import JDBC.DBConnectionPool;
import dao.querysConstant.SQLQueries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import model.Newspaper;
import model.Subscription;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class DaoSubscribe {

    private final DBConnectionPool pool;
    @Inject
    public DaoSubscribe(DBConnectionPool pool) {
        this.pool = pool;
    }

    public Either<String, List<Subscription>> getAll() {
        List<Subscription> readers = new ArrayList<>();
        // Read the XML document from the file
        try (Connection con = pool.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_subscriptions_QUERY);
            List<Subscription> readerList = readRS(readers,rs);
            return Either.right(readerList);

        } catch (SQLException ex) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, ex);
            return Either.left("Error reading the database");
        }

    }
    private List<Subscription> readRS(List<Subscription> readers,ResultSet rs) {
        try {
            while (rs.next()) {
                int id_newspapers = rs.getInt("id_newspaper");
                int id_readers = rs.getInt("id_reader");
                LocalDate signing_date = rs.getDate("signing_date").toLocalDate();
                Subscription reader = new Subscription(id_readers, id_newspapers,signing_date,null);
                readers.add(reader);
            }
            return readers;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    public Either<String,List<Subscription>> getAll(int reader){
        List<Subscription> subscriptions = new ArrayList<>();
        try (Connection con = pool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_SUBSCRIPTIONS_QUERY_BY_ID_READER)) {
            preparedStatement.setInt(1, reader);
            ResultSet rs = preparedStatement.executeQuery();
            List<Subscription> subscriptionList = readRS(subscriptions,rs);
            return Either.right(subscriptionList);

        } catch (SQLException ex) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, ex);
            return Either.left("Error reading the database");
        }
    }
    public int save(Subscription subscription) {
        try (Connection con = pool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.INSERT_SUBSCRIPTIONS_QUERY)) {
            preparedStatement.setInt(1, subscription.getIdNewspaper());
            preparedStatement.setInt(2, subscription.getIdReader());
            preparedStatement.setDate(3, Date.valueOf(subscription.getSigningDate()));
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    public int update(Subscription subscription){
        try (Connection con = pool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.UPDATE_SUBSCRIPTIONS_QUERY)) {
            preparedStatement.setInt(1, subscription.getIdNewspaper());
            preparedStatement.setDate(2, Date.valueOf(subscription.getSigningDate()));
            preparedStatement.setDate(3, Date.valueOf(subscription.getCancellationDate()));
            preparedStatement.setInt(4, subscription.getIdReader());
            preparedStatement.setInt(5, subscription.getIdNewspaper());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
