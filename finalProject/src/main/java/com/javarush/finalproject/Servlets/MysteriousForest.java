package com.javarush.finalproject.Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


@WebServlet("/mysteriousForest")

public class MysteriousForest extends HttpServlet {

    public MysteriousForest() {
        super();
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String action = req.getParameter("action");

        if (action == null || action.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Action parameter is missing");
            return;
        }


        if ("WIN".equals(action)) {
            User.getUser().setCountGamesMysteriousForest(User.getUser().getCountGamesMysteriousForest()+1); // Увеличиваем счётчик
            session.setAttribute("countGamesMysteriousForest", User.getUser().getCountGamesMysteriousForest());
            session.setAttribute("stage", "WIN");
            resp.sendRedirect("mysteriousForest");
            return;
        }
        if ("MAIN_MENU".equals(action)) {
            resp.setContentType("application/json");
            resp.getWriter().write("{\"redirect\":\"/start.jsp\"}");
        } else {
            session.setAttribute("stage", action);
            resp.sendRedirect("mysteriousForest");
        }
    }
}
