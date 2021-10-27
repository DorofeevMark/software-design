package ru.akirakozov.sd.refactoring;

import org.junit.Before;
import org.junit.Test;
import ru.akirakozov.sd.refactoring.domain.Product;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;

import java.io.*;
import java.util.List;


public class GetProductsServletTest extends DatabaseProductsTestBase {
    private GetProductsServlet servlet;

    @Before
    public void setUp() {
        servlet = new GetProductsServlet(database);
    }

    @Test
    public void emptyTest() throws IOException {
        servlet.doGet(request, response);
        stripAndCheck(writer.toString(), "<html><body>\n</body></html>");
    }

    @Test
    public void nonEmptyTest() throws IOException {
        database.insert(List.of(new Product("test1", 1), new Product("test2", 2)));
        servlet.doGet(request, response);
        stripAndCheck(writer.toString(), "<html><body>\ntest1\t1</br>\ntest2\t2</br>\n</body></html>");
    }
}