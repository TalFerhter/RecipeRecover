package org.openu.recipeRecover.DAL;

import org.openu.recipeRecover.BL.Recipe;
import org.springframework.data.repository.CrudRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class DBConnection implements CrudRepository<Recipe, Long> {

    public Connection getConnection() throws SQLException {
        String dbURL = "jdbc:postgresql://localhost/RecipeRecover";
        String user = "postgres";
        String pass = "postgres";
        Connection conn = DriverManager.getConnection(dbURL, user, pass);
        return conn;
    }

    public ResultSet executeQuery(Connection connection, String query) throws SQLException {
        return connection.createStatement().executeQuery(query);
    }

    @Override
    public <S extends Recipe> S save(S s) {
        return null;
    }

    @Override
    public <S extends Recipe> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Recipe> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Recipe> findAll() {
        return null;
    }

    @Override
    public Iterable<Recipe> findAllById(Iterable<Long> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Recipe recipe) {

    }

    @Override
    public void deleteAll(Iterable<? extends Recipe> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
