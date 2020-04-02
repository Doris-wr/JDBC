package 数据库操作;

import org.junit.Test;
import 工具类.JDBCUtils;
import 数据库连接.ConnectionTest;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * @Author:wangrui
 * @Date:2020/3/15 11:15
 */
//使用Preparestatement来替换Statement，实现对数据表的曾删改查
public class UpdateTest {
    //向cuetomer表中添加一条记录
    @Test
    public void testInsert() {
        //1.读取配置文件中的四个基本信息
        Connection con = null;
        PreparedStatement ps = null;
        try {
            InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
            Properties pros = new Properties();
            pros.load(is);
            String user = pros.getProperty("user");
            String password = pros.getProperty("password");
            String url = pros.getProperty("url");
            String driverClass = pros.getProperty("driverClass");
            //2.加载驱动
            Class.forName(driverClass);
            //3.获取连接
            con = DriverManager.getConnection(url, user, password);
            //System.out.println(con);
            //4.预编译sql语句，返回PrepareStatement的实例
            String sql = "insert into customers(named,email,birth)values(?,?,?)";
            ps = con.prepareStatement(sql);
            //5.填充占位符
            ps.setString(1, "jiangchen");
            ps.setString(2, "jiangchen@gmail.com");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = sdf.parse("1990-12-29");
            ps.setDate(3, new Date(date.getTime()));
            //6.执行操作
            ps.execute();
        } catch (IOException | ClassNotFoundException | ParseException | SQLException e) {
            e.printStackTrace();
        } finally {
            //7.资源关闭
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //修改customers表的一条记录
    @Test
    public void testUpdate() {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            //1.获取数据库连接
            con = JDBCUtils.getConnection();
            //2.预编译sql语句，返回PrepareStatement的实例
            String sql = "update customers set named=? where id=?";
            ps = con.prepareStatement(sql);
            //3.填充占位符
            ps.setObject(1, "liangchaowei");
            ps.setObject(2, 19);
            //4.执行
            ps.execute();
            //5.资源的关闭
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResourse(con, ps);
        }
    }

    //通用的增删改操作
    public void update(String sql, Object... args) {//sql中的占位符应与可变形参成都一致}
        Connection con = null;
        PreparedStatement ps = null;
        try {
            //1.获取数据库的连接
            con = JDBCUtils.getConnection();
            //2.预编译sql语句，返回PrepareStatement的实例
            ps = con.prepareStatement(sql);
            //填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);//小心参数声明错误
            }
            //4.执行
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5.资源的关闭
            JDBCUtils.closeResourse(con, ps);
        }
    }

    @Test
    public void testCommenUpdate(){
        //测试删除
        /*String sql="delete from customers where id=?";
        update(sql,19);*/
        //测试修改
        String sql="update customers set named=? where id=?";
        update(sql,"zhangchenguang","20");
    }
}

