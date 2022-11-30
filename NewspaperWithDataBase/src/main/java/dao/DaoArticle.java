package dao;

import JDBC.DBConnectionPool;
import config.Configuracion;
import dao.querysConstant.SQLQueries;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import lombok.extern.log4j.Log4j2;
import model.Article;
import model.Query1;
import model.Reader;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Log4j2
public class DaoArticle implements dao.impl.DaoArticleImpl {

    private final DBConnectionPool pool;

    @Inject
    public DaoArticle( DBConnectionPool pool) {
        this.pool = pool;
    }

    @Override
    public List<Article> getAll() {
        JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
        return jtm.query(SQLQueries.SELECT_ALL_ARTICLES, BeanPropertyRowMapper.newInstance(Article.class));
    }
    @Override
    public int save(Article article){
        try {
            SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(pool.getDataSource()).withTableName("article")
                    .usingGeneratedKeyColumns("id");

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("name_article", article.getName_article());
            parameters.put("id_type", article.getId_type());
            parameters.put("id_newspaper", article.getId_newspaper());
            article.setId((int) jdbcInsert.executeAndReturnKey(parameters).longValue());
            return 0;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return -1;
        }
    }
    public Either<Integer, List<Query1>> getArticlesQuery() {
        List<Query1> articles = new ArrayList<>();
        try (Connection con = pool.getConnection();
             Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                     ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = statement.executeQuery(SQLQueries.SELECT_ARTICLE_TYPE_ARTICLE_NAME_AND_READERS);
            articles = readRSQuery(rs);
        } catch (SQLException ex) {
            Logger.getLogger(DaoArticle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return articles.isEmpty() ? Either.left(-1) : Either.right(articles);
    }

    private List<Query1> readRSQuery(ResultSet rs) {
        List<Query1> articles = new ArrayList<>();
        try {
            while (rs.next()) {
                Query1 article = new Query1();
                article.setName_article(rs.getString("name_article"));
                article.setCount(rs.getInt("count(readarticle.id_reader)"));
                article.setDescription(rs.getString("description"));
                articles.add(article);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DaoArticle.class.getName()).log(Level.SEVERE, null, ex);
        }
        return articles;
    }
    public List<Article> query4(int id_newspaper) {
        JdbcTemplate jtm = new JdbcTemplate(pool.getDataSource());
        return jtm.query(SQLQueries.QUERY_4, BeanPropertyRowMapper.newInstance(Article.class), id_newspaper);
    }

}
