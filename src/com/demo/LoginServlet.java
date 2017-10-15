package com.demo;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name = "Login", urlPatterns = { "/Login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Connection con;
	public void init(){
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con=DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=cherry_db;user=test3;password=test1");
			if(con!=null){
				System.out.println("connected");
			}
		}catch(Exception e){
			e.printStackTrace();
		}	
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	   PrintWriter out=response.getWriter();
	   response.setContentType("text/html"); 
	   String uname=request.getParameter("username");
	   String pword=request.getParameter("password");
	   try{
		   PreparedStatement ps=con.prepareStatement("select * from users where username=? and password=?");
		   ps.setString(1,uname);
		   ps.setString(2,pword );
		   ResultSet rs=ps.executeQuery();
		   if(!rs.next()){
			   response.sendRedirect("user.html");
		   }
		   else{
			   String role=rs.getString(4);
			   if(role.equals("Admin")){
				   response.sendRedirect("Admin.html");
			   }
			   else if(role.equals("Customer")){
				   out.println("<body>Hello!</body>");
			   }
			   else{
				   response.sendRedirect("user.html");
			   }
		   }
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	}
}
