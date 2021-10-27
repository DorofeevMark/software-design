package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.database.Database;
import ru.akirakozov.sd.refactoring.domain.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet {
    protected final Database<Product> database;

    public BaseServlet(Database<Product> database) {
        this.database = database;
    }

    protected void setResponse(HttpServletResponse response) {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}