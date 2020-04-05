import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {
    public static void createTable()  {
        Connection connection=null;
        Statement stmt=null;
        try {
            //1 加载驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2 获取连接                                               //1227.0.0.1/
            connection=DriverManager.getConnection("jdbc:mysql://localhost/Contact","root","");
            //3 创建语句对象
            stmt=connection.createStatement();
            //4 执行
            String sql="create table contact(name varchar(10),addr varchar(10),phoneNum(20),email(20))";
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
        createTable();
    }
}
