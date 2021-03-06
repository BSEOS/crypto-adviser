package controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import beans.Trader;
import dao.TraderDao;

@WebServlet("/register-trader")
public class TraderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TraderDao traderDao;

	public void init() {
		traderDao = new TraderDao();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());

		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/traderregister.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String full_name = request.getParameter("full_name");
		String password = request.getParameter("password");

		Trader trader = new Trader();
		trader.setUsername(username);
		trader.setFull_name(full_name);
		trader.setPassword(password);

		try {
			traderDao.registerTrader(trader);
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("traderdetails.jsp");
	}
}