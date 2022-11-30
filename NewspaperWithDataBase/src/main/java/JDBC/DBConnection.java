package JDBC;

import config.ConfigXML;
import config.Configuracion;
import jakarta.inject.Inject;

import java.lang.module.Configuration;
import java.sql.*;

public class DBConnection {
    private ConfigXML config;

    @Inject
    public DBConnection(ConfigXML config) {
        this.config = ConfigXML.getInstance();
    }


    public Connection getConnection(){
        try {
            return DriverManager
                    .getConnection(config.getProperty("url"), config.getProperty("user_name"), config.getProperty("password"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Closes connection
     */
    public void closeConnection(Connection connArg) {
        System.out.println("Releasing all open resources ...");
        try {
            if (connArg != null) {
                connArg.close();
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public void releaseResource(PreparedStatement pstmt) {
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public void releaseResource(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public void releaseResource(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
}
