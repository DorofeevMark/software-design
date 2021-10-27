package ru.akirakozov.sd.refactoring;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.akirakozov.sd.refactoring.domain.Product;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.when;

public class AddProductsServletTest extends DatabaseProductsTestBase {
    private AddProductServlet servlet;

    @Before
    public void setUp() {
        servlet = new AddProductServlet(database);
    }

    @Test(expected = Exception.class)
    public void emptyParamsTest() throws IOException {
        servlet.doGet(request, response);
    }

    @Test
    public void simpleTest() throws IOException {
        when(request.getParameter("name")).thenReturn("test1");
        when(request.getParameter("price")).thenReturn("10");
        servlet.doGet(request, response);
        stripAndCheck("OK", writer.toString());
        List<Product> result = database.selectAll();
        Assert.assertEquals(1, result.size());
        Assert.assertEquals("test1", result.get(0).getName());
        Assert.assertEquals(10, result.get(0).getPrice());
    }
}
