import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
public class Contact {

    public static void createTable() {
        Connection connection = null;
        Statement stmt = null;
        try {
            //1 加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2 获取连接                                               //1227.0.0.1/
            connection = DriverManager.getConnection("jdbc:mysql://localhost/Contact", "root", "");
            //3 创建语句对象
            stmt = connection.createStatement();
            //4 执行
            String sql = "create table contact(name varchar(10),phone varchar(20),addr varchar(20),email varchar(20))";
            stmt.execute(sql);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //5 关闭资源
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //插入数据
    public static void insertIntoTable()  {
        Connection connection=null;
        Statement stmt=null;
        try {
            //1 加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2 获取连接                                               //127.0.0.1/
            connection=DriverManager.getConnection("jdbc:mysql://localhost/Contact","root","");
            //3 创建语句对象
            stmt=connection.createStatement();
            //4 执行
            String sql="insert into contact(name,phone,addr,email)"+"values('王蕊','029455683','西安','123.com')";
            stmt.execute(sql);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            //5 关闭资源
            try{
                if(stmt!=null){
                    stmt.close();
                }
                if(connection!=null){
                    connection.close();
                }
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //更新数据
    public static void update()  {
        Connection connection=null;
        Statement stmt=null;
        try {
            //1 加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2 获取连接                                               //127.0.0.1/
            connection=DriverManager.getConnection("jdbc:mysql://localhost/Contact","root","");
            //3 创建语句对象
            stmt=connection.createStatement();
            //4 执行
            String sql="update contact set phone='123' where name='wangrui'";
            stmt.execute(sql);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            //5 关闭资源
            try{
                if(stmt!=null){
                    stmt.close();
                }
                if(connection!=null){
                    connection.close();
                }
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //删除
    public static void delete()  {
        Connection connection=null;
        Statement stmt=null;
        try {
            //1 加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2 获取连接                                               //127.0.0.1/
            connection=DriverManager.getConnection("jdbc:mysql://localhost/Contact","root","");
            //3 创建语句对象
            stmt=connection.createStatement();
            //4 执行
            String sql="delete from contact where phone='18291840565'";
            stmt.execute(sql);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            //5 关闭资源
            try{
                if(stmt!=null){
                    stmt.close();
                }
                if(connection!=null){
                    connection.close();
                }
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }
    //排序
    public static void sort()  {
        Connection connection=null;
        Statement stmt=null;
        try {
            //1 加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2 获取连接                                               //127.0.0.1/
            connection=DriverManager.getConnection("jdbc:mysql://localhost/Contact","root","");
            //3 创建语句对象
            stmt=connection.createStatement();
            //4 执行
            String sql="";
            stmt.execute(sql);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            //5 关闭资源
            try{
                if(stmt!=null){
                    stmt.close();
                }
                if(connection!=null){
                    connection.close();
                }
            }catch(SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        //建表
       //createTable();
        //添加数据
        insertIntoTable();
        //更新数据
        //update();
        //删除数据
        //delete();

    }
}
