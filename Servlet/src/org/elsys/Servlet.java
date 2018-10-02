package org.elsys;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Servlet
 */
@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HashMap<String, String> queries = new HashMap<String, String>();
    /**
     * Default constructor. 
     */
    public Servlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");

	      PrintWriter out = response.getWriter();
	      String docType =
	         "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
	         
	      out.println(docType +
	         "<html>\n" +
	            "<head></head>\n" +
	            "<body bgcolor = \"#f0f0f0\">\n" +
	               "<form action = \"Servlet\" method = \"POST\">" +
	               "Key: <input type = \"text\" name = \"key\">" +
	               "<br/>" +
	               "Value: <input type = \"text\" name = \"value\">" +
	               "<input type = \"submit\" value = \"Submit\">" +
	               "</form>"
	      );
	      
	      out.println("<table style = \"width:50%\">" + 
	    		  		"<tr>" +
	    		  			"<th> Key </th>" + 
	    		  			"<th> Value </th>" +
	    		  		"</tr>"
	      );
	      for(Map.Entry<String, String> entry : queries.entrySet()) {
	    	  String key = entry.getKey();
	    	  String value = entry.getValue();
	    	  out.println("<tr>" + 
	    			  			"<td>" + key + "</td>" +
	    			  			"<td>" + value + "</td>" +
	    			  	  "</tr>"
	    	  );
	      }
	      
	      out.println("</table> +"
	      		+ "</body>" +
	    		  "</html>");
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String key = request.getParameter("key");
		String value = request.getParameter("value");
		queries.put(key, value);
		
		doGet(request, response);
	}

}
