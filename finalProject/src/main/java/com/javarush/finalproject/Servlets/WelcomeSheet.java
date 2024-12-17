package com.javarush.finalproject.Servlets;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/startGameServlet")
public class WelcomeSheet extends HttpServlet {
    public WelcomeSheet() {
        super();
    }

    User user = User.getUser();

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        String userName = req.getParameter("userName");
        if (userName != null && !userName.isEmpty()) {
            session.setAttribute("userName", userName);
            session.setAttribute("countGamesMysteriousForest", user.getCountGamesMysteriousForest());
            resp.sendRedirect("/start.jsp");
        } else {

            String game = req.getParameter("game");
            if (game.equals("1")) {
                //В случае возврата на главный экран, можно было вернуться в игру на тот же момент
                if (session.getAttribute("stage") == null || "WIN".equals(session.getAttribute("stage"))) {
                    session.setAttribute("stage", "START");
                }
                resp.sendRedirect("/mysteriousForest.jsp");
            } else if (game.equals("2")) {
                resp.sendRedirect("/gameDragonHunt.jsp");
            } else if (game.equals("3")) {
                resp.sendRedirect("/dungeonsAndCastles.jsp");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
    @Override
    public void destroy() {
        super.destroy();
    }
}
