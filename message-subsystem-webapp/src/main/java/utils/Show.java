package utils;

import dao.Factory;
import dao.Posts;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


public class Show extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            response.setCharacterEncoding("UTF-8");
            response.setContentType( "charset=UTF-8" );
            PrintWriter out = response.getWriter();
                    List<Posts> posts = Factory.getInstance().getPostDAO().getAllPosts();
                    JSONObject jsonToReturn = new JSONObject();
                    jsonToReturn.put( "posts", posts.toString() );
                    out.println( jsonToReturn.toString() );
            } catch (Exception e) {
            System.out.println( e.toString() );
        }
    }
}