package com.oliver.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.oliver.constants.ConstantsForNewsItem;
import com.oliver.readers.FinanceReader;


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
		showRequest(request);
		String newsKind = request.getParameter("newskind");
	    System.out.println("request: "+request.getRequestURI()+"+"+request.getParameterMap().toString());
		try {
			if ("focus".equals(newsKind)) {

				handleFocusNewsKind(request, response);

			} else if ("addhot".equals(newsKind)) {// ?newskind=addhot&titleid=12313
				addHotPintToTitle(request, response);

			} else if ("stock".equals(newsKind)) {
				handleStockNewsKind(request, response);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void showRequest(HttpServletRequest request) {
		//10.128.43.78:8080/Spider/test?newskind=stock&show=title&page=0
		System.out.println("newskind: "+request.getParameter("newskind"));
		System.out.println("show: "+request.getParameter("show"));
		System.out.println("page: "+request.getParameter("page"));
		System.out.println("titleid: "+request.getParameter("titleid"));
	}

	private void handleStockNewsKind(HttpServletRequest request,
			HttpServletResponse response) throws IOException, JSONException {
		String show = request.getParameter("show");
		System.out.println("focus news kind...:"+show);
		if("title".equals(show)){
		   responseTitle(request,
				   response,
				   ConstantsForNewsItem.NEWS_ITEM_KIND_STOCK);
		}else if("content".equals(show)){
			int titleId = Integer.valueOf(request.getParameter("titleid"));
			responseContent(request,response,titleId);
		}
	}

	private void addHotPintToTitle(HttpServletRequest request, 
			HttpServletResponse response) {
		int id = Integer.valueOf(request.getParameter("titleid"));
		System.out.println("accept request add hot point on titleId: "+id);
		FinanceReader reader = new FinanceReader();
		reader.addHotPoint(id);
	}

	private void handleFocusNewsKind(HttpServletRequest request,
			HttpServletResponse response) throws IOException, JSONException {
		String show = request.getParameter("show");
		System.out.println("focus news kind...:"+show);
		if("title".equals(show)){
		   responseTitle(request,
				   response,
				   ConstantsForNewsItem.NEWS_ITEM_KIND_FOCUS);
		}else if("content".equals(show)){
			int titleId = Integer.valueOf(request.getParameter("titleid"));
			responseContent(request,response,titleId);
		}
	}

	private void responseContent(HttpServletRequest request, HttpServletResponse response, int titleId) throws IOException {
		FinanceReader reader = new FinanceReader();
		JSONObject jo = reader.getNewsItemContentInJson(titleId);
		response.getOutputStream().write(jo.toString().getBytes());
		response.setContentType("text/json;charset=UTF-8");
		System.out.println("response content on titleId: "+titleId);
		System.out.println("response content on content: "+jo.toString());
	}

	private void responseTitle(HttpServletRequest request, 
			HttpServletResponse response,
			int newsType) throws IOException, JSONException {
		int page = Integer.valueOf(request.getParameter("page"));
		System.out.println("request page: "+page);
		FinanceReader reader = new FinanceReader();
		//JSONArray JSONArr = reader.getNewsItemInJson(page);
		JSONArray JSONArr = reader.getNewsItemInJson(page,newsType);
		response.getOutputStream().write(JSONArr.toString().getBytes());
		response.setContentType("text/json;charset=UTF-8");
		System.out.println(JSONArr);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
