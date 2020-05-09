package 数据库事务;

import org.junit.Test;
import 工具类.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author:wangrui
 * @Date:2020/3/17 22:57
 */
/*
 * 功能描述:针对于user_table来说，演示AA用户给BB用户转账100
 * @return
 */
public class  TransactionTest {

    /*
     * 功能描述:未考虑数据库事务情况下的转账操作
     * @return void
     */
    @Test
    public void testUpdate(){
        String sql1="update user_table set balance=balance-100";
        update(sql1,"AA");
        //模拟网络异常
        System.out.println(10 / 0);
        String sql2="uadate user_table set balance=balance+100";
        update(sql2,"BB");
        System.out.println("转账成功");
    }
    //通用的增删改操作——version1
    public int update(String sql, Object... args) {//sql中的占位符应与可变形参成都一致}
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
            return  ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5.资源的关闭
            JDBCUtils.closeResourse(con, ps);
        }
        return 0;
    }
    //******************考虑数据库事务后的转账操作*******************
     //通用的增删改操作——version2
    public int update2(Connection con,String sql, Object... args) {//sql中的占位符应与可变形参成都一致}
        PreparedStatement ps = null;
        try {
            //1.预编译sql语句，返回PrepareStatement的实例
            ps = con.prepareStatement(sql);
            //2填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);//小心参数声明错误
            }
            //3.执行
            return  ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //4.资源的关闭
            JDBCUtils.closeResourse(null, ps);
        }
        return 0;
    }
    @Test
    public void testUpdatewithTX()  {
        Connection con= null;
        try {
            con = JDBCUtils.getConnection();
            //取消数据的自动提交功能
            con.setAutoCommit(false);
            String sql1="update user_table set balance=balance-100";
            update2(con,sql1,"AA");

            String sql2="update user_table set balance=balance+100";
            update2(con,sql2,"BB");
            System.out.println("转账成功");
            //提交数据
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
            //回滚数据
            try {
                con.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
        }
        JDBCUtils.closeResourse(con,null);
    }
}
