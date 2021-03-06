package ru.akirakozov.sd.refactoring;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.akirakozov.sd.refactoring.database.Database;
import ru.akirakozov.sd.refactoring.database.ProductDatabase;
import ru.akirakozov.sd.refactoring.domain.Product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.Mockito.when;


public class DatabaseProductsTestBase {
    private static final String DEFAULT_DATABASE_CONNECTION_STRING = "jdbc:sqlite:test.db";
    protected final Database<Product> database;

    protected StringWriter writer = new StringWriter();
    @Mock
    protected HttpServletResponse response;
    @Mock
    protected HttpServletRequest request;

    public DatabaseProductsTestBase() {
        this(DEFAULT_DATABASE_CONNECTION_STRING);
    }

    public DatabaseProductsTestBase(String connectionString) {
        this.database = new ProductDatabase(connectionString);
    }

    @Before
    public void setUpMocks() throws IOException {
        MockitoAnnotations.openMocks(this);
        when(response.getWriter()).thenReturn(new PrintWriter(writer));
    }

    @Before
    public void createTable() {
        database.createIfNotExist();
    }

    @After
    public void dropTable() {
        database.dropIfExist();
    }

    protected void stripAndCheck(String result, String actual) {
        Assert.assertEquals(actual.strip(), result.strip());
    }
}
