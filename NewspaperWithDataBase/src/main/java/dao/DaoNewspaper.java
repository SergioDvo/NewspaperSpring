package dao;

import JDBC.DBConnectionPool;
import config.Configuracion;
import dao.querysConstant.SQLQueries;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.Newspaper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Log4j2
public class DaoNewspaper implements dao.impl.DaoNewspaperImpl {

    private final DBConnectionPool pool;

    @Inject
    public DaoNewspaper( DBConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public List<Newspaper> getAll() {
        JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
        return jtm.query(SQLQueries.SELECT_ALL_NEWSPAPERS, BeanPropertyRowMapper.newInstance(Newspaper.class));
    }

    @Override
    public int save(Newspaper newspaper) {
        try {
            SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(pool.getDataSource()).withTableName("newspaper")
                    .usingGeneratedKeyColumns("id");

            Map<String, Object> parameters = new HashMap<>();

            parameters.put("name_newspaper", newspaper.getName_newspaper());
            parameters.put("release_date", newspaper.getRelease_date());
            newspaper.setId((int) jdbcInsert.executeAndReturnKey(parameters).longValue());
            return 0;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return -1;
        }
    }

    @Override
    public int delete(Newspaper newspaper) {
        int res = -1;
        TransactionDefinition txDef = new DefaultTransactionDefinition();
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(pool.getDataSource());
        TransactionStatus txStatus = transactionManager.getTransaction(txDef);
        try {
            JdbcTemplate jtm = new JdbcTemplate(transactionManager.getDataSource());
            res = jtm.update(SQLQueries.DELETE_ARTICLE_BY_ID_NEWSPAPER, newspaper.getId());
            res = jtm.update(SQLQueries.DELETE_SUBSCRIPTIONS_BY_ID_NEWSPAPER, newspaper.getId());
            res = jtm.update(SQLQueries.DELETE_READ_ARTICLE_BY_IDNEWSPAPER, newspaper.getId());
            res = jtm.update(SQLQueries.DELETE_NEWSPAPER_BY_ID, newspaper.getId());
            transactionManager.commit(txStatus);
        } catch (Exception e) {
            transactionManager.rollback(txStatus);
            return res;
        }
        return res;
    }

    public int update(Newspaper newspaper) {
        try {
            JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
            return jtm.update(SQLQueries.UPDATE_NEWSPAPER, newspaper.getName_newspaper(), newspaper.getRelease_date(), newspaper.getId());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return -1;
        }
    }


}
