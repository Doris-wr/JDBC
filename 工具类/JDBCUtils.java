package 工具类;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @Author:wangrui
 * @Date:2020/3/15 13:28
 */
/*
 * 功能描述:操作数据路的工具类
 * @return
 */
public class JDBCUtils {
    /*
     * 功能描述:获取数据库连接
     * @return java.sql.Connection
     */
    public static Connection getConnection() throws   Exception {
        //1.读取配置文件中的四个基本信息
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties pros = new Properties();
        pros.load(is);
        String user=pros.getProperty("user");
        String password=pros.getProperty("password");
        String url=pros.getProperty("url");
        String driverClass=pros.getProperty("driverClass");
        //2.加载驱动
        Class.forName(driverClass);
        //3.获取连接
        Connection con=DriverManager.getConnection(url,user,password);
        return con;
    }
    /*
     * 功能描述:关闭资源(连接和statement的操作）
     * @return void
     */
    public static void closeResourse(Connection con,Statement ps){
         if(ps!=null){
             try {
                 ps.close();
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         }
         if(con!=null){
             try {
                 con.close();
             } catch (SQLException e) {
                 e.printStackTrace();
             }
         }
    }
    //关闭资源
    public static void closeResourse(Connection con,Statement ps,ResultSet rs){
        if(ps!=null){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(con!=null){
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try{
            if(rs!=null){
                rs.close();
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
