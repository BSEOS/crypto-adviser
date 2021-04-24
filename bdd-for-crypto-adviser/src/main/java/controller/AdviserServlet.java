package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Adviser;
import dao.AdviserDao;

@WebServlet("/registerAdviser")
public class AdviserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AdviserDao adviserDao;
    
    public void init() {
    	adviserDao = new AdviserDao();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException{
    	response.getWriter().append("Served at: ").append(request.getContextPath());
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/adviserregister.jsp");
    	dispatcher.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        String username = request.getParameter("username");
        String full_name = request.getParameter("full_name");
        String password = request.getParameter("password");

        Adviser adviser = new Adviser();
        adviser.setUsername(username);
        adviser.setFull_name(full_name);
        adviser.setPassword(password);

        try {
            adviserDao.registerAdviser(adviser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("adviserdetails.jsp");
    }
}
