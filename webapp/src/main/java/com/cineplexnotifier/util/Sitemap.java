package com.cineplexnotifier.util;

import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cineplexnotifier.data.MovieRepository;

@SuppressWarnings("serial")
@WebServlet("/sitemap.xml")
public class Sitemap extends HttpServlet {

	@EJB
	private MovieRepository dao;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//TODO Find or create a convenience method for this
		final String BASE_URL = req.getScheme() + "://" + req.getServerName() + ":" + req.getLocalPort();

		req.setAttribute("baseUrl", BASE_URL);
		req.setAttribute("movies", dao.selectAll());

		RequestDispatcher dispatcher = req.getRequestDispatcher("/sitemap.jsp");
		dispatcher.forward(req, resp);
	}
}
