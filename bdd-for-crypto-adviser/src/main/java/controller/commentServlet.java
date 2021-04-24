package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.Comment;
import beans.Report;
import dao.CommentDao;

@WebServlet("/creatComment")
public class commentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private CommentDao commentDao;
    
    public void init() {
    	commentDao = new CommentDao();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
    		throws ServletException, IOException{
    	response.getWriter().append("Served at: ").append(request.getContextPath());
    	
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/creatComment.jsp");
    	dispatcher.forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

        String username = request.getParameter("username");
        String report_id = request.getParameter("report_id");
        String content = request.getParameter("content");

        Comment comment = new Comment();
        comment.setUsername(username);
        comment.setReport_id(Integer.parseInt(report_id));
        comment.setContent(content);

        try {
            commentDao.creatComment(comment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("commentdetails.jsp");
    }
}
