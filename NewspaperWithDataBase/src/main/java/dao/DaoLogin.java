package dao;

import JDBC.DBConnectionPool;
import config.Configuracion;
import dao.querysConstant.SQLQueries;
import jakarta.inject.Inject;

import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoLogin implements dao.impl.DaoClientesImpl {

    private Configuracion configuracion;

    private final DBConnectionPool pool;

    @Inject
    public DaoLogin(Configuracion configuracion, DBConnectionPool pool) {
        this.configuracion = Configuracion.getInstance();
        this.pool = pool;
    }

    @Override
    public List<String> getAll() {
        String username  = String.valueOf((Paths.get(configuracion.getUsername())));
        String password = String.valueOf((Paths.get(configuracion.getPassword())));
        return new ArrayList<>(List.of(username,password));
    }
    public int getReader(String username,String password){
        try (Connection con = pool.getConnection();
             PreparedStatement preparedStatement = con.prepareStatement(SQLQueries.SELECT_READERS_FROM_LOGIN, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()){
                return rs.getInt(1);
            }else {
                return -17;
            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
