package com.powernode.servlets;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 动力节点
 * @version 1.0
 * @className WelcomeServlet
 * @since 1.0
 **/
@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print("<h3>welcome to study servlet!</h3>");
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<String> names = new ArrayList<>();
        try {
            //1.注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");
            //2.获取连接
            String url = "jdbc:mysql://47.93.41.203:3306/oa";
            String username = "root";
            String password = "@Yuhaoxuan1";
            conn = DriverManager.getConnection(url, username, password);
            //3.获取预编译的数据库操作对象
            String sql = "select name from user";
            ps = conn.prepareStatement(sql);
            //4.执行SQL语句
            rs = ps.executeQuery();
            //5.处理查询结果集
            while(rs.next()){
                String name = rs.getString("name");
                names.add(name);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //6.释放资源
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        out.print("<ul>");
        for(String name:names){
            out.print("<li>");
            out.print(name);
            out.print("</li>");
        }
        out.print("</ul>");
    }
}
