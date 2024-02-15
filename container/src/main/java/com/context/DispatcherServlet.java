package com.context;

import com.context.enums.RequestMethod;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "MyServlet", urlPatterns = "/*")
public class DispatcherServlet extends HttpServlet {

    private ContextController contextController;

    public DispatcherServlet(ContextController contextController) {
        this.contextController = contextController;
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) {
        String method = request.getMethod();
        String uri = request.getRequestURI();

        if (method.equals("GET")) {
            contextController.useControllerByMethodAndURI(RequestMethod.GET, uri);
        } else if (method.equals("POST")) {
            contextController.useControllerByMethodAndURI(RequestMethod.POST, uri);
        }
    }
}
