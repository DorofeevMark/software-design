package ru.akirakozov.sd.refactoring.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Database<T> {
    private final String databaseConnectionString;

    public Database(String databaseConnectionString) {
        this.databaseConnectionString = databaseConnectionString;
    }

    public abstract void createIfNotExist();

    public abstract void dropIfExist();

    public abstract List<T> selectAll();

    public abstract T max();

    public abstract T min();

    public abstract int sum();

    public abstract int count();

    public int insert(T entity) {
        return insert(List.of(entity));
    }

    public int insert(List<T> entities) {
        if (entities.size() == 0) {
            throw new IllegalArgumentException("Illegal entities");
        }
        return doInsert(entities);
    }

    public abstract int doInsert(List<T> entities);


    protected List<List<String>> selectSql(String sql, List<String> fields) {
        List<List<String>> result = new ArrayList<>();
        try (Connection c = DriverManager.getConnection(databaseConnectionString)) {
            try (Statement statement = c.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sql)) {
                    while (resultSet.next()) {
                        List<String> tmp = new ArrayList<>();
                        for (String field : fields) {
                            tmp.add(resultSet.getString(field));
                        }
                        result.add(tmp);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    protected int execSql(String sql) {
        try (Connection c = DriverManager.getConnection(databaseConnectionString)) {
            try (Statement statement = c.createStatement()) {
                return statement.executeUpdate(sql);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    protected int execSqlIntAsResult(String sql) {
        try (Connection c = DriverManager.getConnection(databaseConnectionString)) {
            try (Statement statement = c.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sql)) {
                    if (resultSet.next()) {
                        return resultSet.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
