package dao;

import JDBC.DBConnectionPool;
import config.Configuracion;
import dao.querysConstant.SQLQueries;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.Article;
import model.Newspaper;
import model.Type;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
@Log4j2
public class DaoType implements dao.impl.DaoTypeImpl {

    private final DBConnectionPool pool;
    @Inject
    public DaoType(DBConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public List<Type> getAll() {
        JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
        return jtm.query(SQLQueries.SELECT_ALL_TYPE, BeanPropertyRowMapper.newInstance(Type.class));
    }


}
