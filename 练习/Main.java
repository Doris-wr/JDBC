import com.mysql.jdbc.Driver;
        import java.sql.DriverManager;
        import java.sql.*;
//版本一
//mysql(client)   客户端     cmd：  MySQL  -u root -p
//conection      mysql的网络连接语句
//Statement       Mysql语句
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //注册驱动（利用Java语言中的发射）
        Class.forName("com.mysql.jdbc.Driver");
        //通过DriverManager获取连接
        //连接的地址
        //CMD中首先建立库：CREATE DATABASE JAVA11;
        String url="jdbc:mysql://127.0.0.1:3306/java11?useSSL=false";
        String username="root";
        String password="";
        Connection connection= DriverManager.getConnection(url,username,password);
        Statement statement=connection.createStatement();
        //在CMD中建一个表，插入一些数据
        /*CREATE TABLE student（
          id int,
           name varchar(20)
        );
        insert into student(id,neme)values(1,"王蕊");*/
        ResultSet resultSet=statement.executeQuery("SELECT id,neme FROM student");
        //next必须先调用一次
        while(resultSet.next()){
            //列的下标是从1开始的
            int id=resultSet.getInt(1);
            String neme=resultSet.getString(2);
            System.out.printf("%d|%s%n",id,neme);
        }
        System.out.println(connection);
        //关闭ResultSet
        resultSet.close();
        //记得被关闭statement
        statement.close();
        //最后记得关闭连接
        connection.close();
    }
}
