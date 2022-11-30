package dao;

import JDBC.DBConnectionPool;
import config.ConfigXML;
import dao.querysConstant.SQLQueries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.extern.log4j.Log4j2;
import model.Newspaper;
import model.Reader;
import model.Readers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Log4j2
public class DaoReaders implements dao.impl.DaoReadersImpl {

    private final DBConnectionPool pool;

    @Inject
    public DaoReaders(DBConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public Either<String, List<Reader>> getAll() {
        List<Reader> readers = new ArrayList<>();

        try (Connection con = pool.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_readers_QUERY);
            List<Reader> readerList = readRS(readers, rs);
            return Either.right(readerList);

        } catch (SQLException ex) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, ex);
            return Either.left("Error reading the database");
        }

    }

    public Either<String, List<Reader>> getAll(int type) {
        List<Reader> readers = new ArrayList<>();
        try (Connection con = pool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.FILTER_READERS_BY_TYPE)) {
            preparedStatement.setInt(1, type);
            ResultSet rs = preparedStatement.executeQuery();
            List<Reader> readerList = readRS(readers, rs);
            return Either.right(readerList);

        } catch (SQLException ex) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, ex);
            return Either.left("Error reading the database");
        }
    }

    public Either<String, List<Reader>> getAll(Newspaper newspaper) {
        List<Reader> readers = new ArrayList<>();
        try (Connection con = pool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.FILTER_READERS_BY_NEWSPAPER)) {
            preparedStatement.setInt(1, newspaper.getId());
            ResultSet rs = preparedStatement.executeQuery();
            List<Reader> readerList = readRS(readers, rs);
            return Either.right(readerList);

        } catch (SQLException ex) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, ex);
            return Either.left("Error reading the database");
        }
    }

    private List<Reader> readRS(List<Reader> readers, ResultSet rs) {
        try {
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name_reader");
                Date supplierID = rs.getDate("birth_reader");
                LocalDate dateOfBirth = supplierID.toLocalDate();
                Date.valueOf(dateOfBirth);
                if (id > 0) {
                    Reader reader = new Reader(id, name, dateOfBirth);
                    readers.add(reader);
                }
            }
            return readers;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int delete(int id) {
        int rowsAffected;
        Connection con = pool.getConnection();
        try (PreparedStatement preparedStatementDeleteReader = con.prepareStatement(SQLQueries.DELETE_READER);
             PreparedStatement preparedStatementDeleteReaderRatings = con.prepareStatement(SQLQueries.DELETE_READ_ARTICLE);
             PreparedStatement preparedStatementDeleteReaderSubscriptions = con.prepareStatement(SQLQueries.DELETE_SUBSCRIBE)) {

//            con.setAutoCommit(false);
            preparedStatementDeleteReader.setInt(1, id);
            preparedStatementDeleteReader.executeUpdate();

            preparedStatementDeleteReaderRatings.setInt(1, id);
            preparedStatementDeleteReaderRatings.executeUpdate();

            preparedStatementDeleteReaderSubscriptions.setInt(1, id);
            preparedStatementDeleteReaderSubscriptions.executeUpdate();

            con.commit();
            rowsAffected = preparedStatementDeleteReader.executeUpdate();

        } catch (SQLException sqle) {
            try {
                con.rollback();
            } catch (SQLException e) {
                Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, e);
            }

            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, sqle);
            rowsAffected = -1;
        } catch (Exception e) {
            rowsAffected = 0;
        }
        return rowsAffected;
    }

    public int save(Reader r) {
        int rowsAffected = 0;
        try (Connection con = pool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.INSERT_READER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, r.getName());
            preparedStatement.setDate(2, Date.valueOf(r.getDateOfBirth()));

            rowsAffected = preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                r.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            rowsAffected = -1;
        } catch (Exception e) {
            e.printStackTrace();
            rowsAffected = 0;
        }
        return rowsAffected;
    }

    //update reader
    public int update(Reader r) {
        int rowsAffected = 0;
        try (Connection con = pool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.UPDATE_READER)) {
            preparedStatement.setString(1, r.getName());
            preparedStatement.setDate(2, Date.valueOf(r.getDateOfBirth()));
            preparedStatement.setInt(3, r.getId());

            rowsAffected = preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
            rowsAffected = -1;
        } catch (Exception e) {
            e.printStackTrace();
            rowsAffected = 0;
        }
        return rowsAffected;
    }

    @Override
    public Either<String, Boolean> saveReaderList(List<Reader> readerList) {
        JAXBContext context = null;
        Marshaller marshaller = null;
        try {
            context = JAXBContext.newInstance(Readers.class);
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (JAXBException e) {
            return Either.left(e.getMessage());
        }
        Readers readers = new Readers();
        readers.setReader(readerList);
        Path xmlFile = Paths
                .get(ConfigXML.getInstance().getProperty("xmlReadersPath"));
        try {
            marshaller.marshal(readers, Files.newOutputStream(xmlFile));
        } catch (JAXBException | IOException e) {
            return Either.left(e.getMessage());
        }
        return Either.right(true);
    }

    public int counterReadersQuery(int id_article) {
        int count = 0;
        try (Connection con = pool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_count_readers_by_article, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, id_article);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } catch (SQLException ex) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    public List<Reader> counterReadersByNewspaperQuery() {
        List<Reader> readers = new ArrayList<>();
        try (Connection con = pool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_5_READER_NW1_ORDERBY_SIGNING_DATE)) {
            ResultSet rs = preparedStatement.executeQuery();
            int count = 0;
            try {
                while (rs.next() && count < 5) {
                    Reader reader = new Reader();
                    reader.setId(rs.getInt("id"));
                    reader.setName(rs.getString("name_reader"));
                    reader.setDateOfBirth(rs.getDate("birth_reader").toLocalDate());
                    readers.add(reader);
                    count++;
                }
                return readers;
            } catch (SQLException ex) {
                Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, ex);
                return null;
            }
        } catch (SQLException e) {
            Logger.getLogger(DaoReaders.class.getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }

}
