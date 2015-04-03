package com.oliver.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.oliver.readers.FinanceCEReader;


/**
 * Servlet implementation class test
 */
@WebServlet("/test")
public class MyTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public MyTest() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		String newsKind = request.getParameter("newskind");
		if("focus".equals(newsKind)){
			handleFocusNewsKind(request,response);
		}
		
	}

	private void handleFocusNewsKind(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String show = request.getParameter("show");
		if("title".equals(show)){
		   responseTitle(request,response);
		}else if("content".equals(show)){
			int titleId = Integer.valueOf(request.getParameter("titleid"));
			responseContent(request,response,titleId);
		}
	}

	private void responseContent(HttpServletRequest request, HttpServletResponse response, int titleId) throws IOException {
		FinanceCEReader reader = new FinanceCEReader();
		JSONObject jo = reader.getNewsItemContentInJson(titleId);
		response.getOutputStream().write(jo.toString().getBytes());
		response.setContentType("text/json;charset=UTF-8");
	}

	private void responseTitle(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int page = Integer.valueOf(request.getParameter("page"));
		FinanceCEReader reader = new FinanceCEReader();
		JSONArray JSONArr = reader.getNewsItemInJson(page);
		response.getOutputStream().write(JSONArr.toString().getBytes());
		response.setContentType("text/json;charset=UTF-8");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
