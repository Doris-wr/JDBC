import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DriverManager;
import java.util.Scanner;
//13班   版本二
public class Main2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Connection connection = null;
        Statement stmt = null;
        try {
            // 加载数据库驱动
            Class.forName("com.mysql.jdbc.Driver");//加载驱动

            connection = DriverManager.getConnection//建立连接
                    ("jdbc:mysql://localhost:3306/",
                            "root",
                            "123456");
            stmt = connection.createStatement();//创建语句对象
            stmt.execute("create database my_jdbc_db");//执行语句
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e){
            e.printStackTrace();
        } finally {
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
}
