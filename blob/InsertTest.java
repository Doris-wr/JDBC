package blob;

import org.junit.Test;
import 工具类.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @Author:wangrui
 * @Date:2020/3/17 21:34
 * 使用PrepareStatement实现批量的数据的操作
 * update delete 本身就具有批量操作的效果；
 *此时的批量操作，主要指的是批量插入，使用PrepareStatement如何实现更高效的批量插入？
 题目：向goos表中插入20000条数据
 方式一：使用statement
 Connection conn=JDBCUtils.getConnection();
 Statement st=conn.createStatement();
 for(int i=0;i<20000;i++){
    String sql="insert into gooos<name) valus('name_"+i+"')";
    st.execute(sql);
}


 */

public class InsertTest {
    //批量插入的方式二，使用PrepareStatement
    @Test
    public void testInsert2() {
        Connection con= null;
        PreparedStatement ps= null;
        try {
            long start=System.currentTimeMillis();
            con = JDBCUtils.getConnection();
            String sql="insert into goods(name)values(?)";
            ps = con.prepareStatement(sql);
            for(int i=1;i<20000;i++){
                ps.setObject(1,"name_"+i);
                ps.execute();
            }
            long end=System.currentTimeMillis();
            System.out.println("花费的时间为："+(end-start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        JDBCUtils.closeResourse(con,ps);
    }
    //批量插入的方式三：
    //1.addBatch(),executeBatch(),clearBatch()
    @Test
    public void testInsert3() {
        Connection con= null;
        PreparedStatement ps= null;
        try {
            long start=System.currentTimeMillis();
            con = JDBCUtils.getConnection();
            String sql="insert into goods(name)values(?)";
            ps = con.prepareStatement(sql);
            for(int i=1;i<20000;i++){
                ps.setObject(1,"name_"+i);
                //1.,"攒"sql
                ps.addBatch();
                if(i%500==0){
                    //2.执行batch
                    ps.executeBatch();
                    //清空batch
                    ps.clearBatch();
                }
            }
            long end=System.currentTimeMillis();
            System.out.println("花费的时间为："+(end-start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        JDBCUtils.closeResourse(con,ps);
    }
    //批量插入的方式四:设置不允许自动提交数据
    @Test
    public void testInsert4() {
        Connection con= null;
        PreparedStatement ps= null;
        try {
            long start=System.currentTimeMillis();
            con = JDBCUtils.getConnection();
            //设置不允许自动提交数据
            con.setAutoCommit(false);
            String sql="insert into goods(name)values(?)";
            ps = con.prepareStatement(sql);
            for(int i=1;i<20000;i++){
                ps.setObject(1,"name_"+i);
                //1.,"攒"sql
                ps.addBatch();
                if(i%500==0){
                    //2.执行batch
                    ps.executeBatch();
                    //清空batch
                    ps.clearBatch();
                }
            }
            //提交数据
            con.commit();
            long end=System.currentTimeMillis();
            System.out.println("花费的时间为："+(end-start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        JDBCUtils.closeResourse(con,ps);
    }
}
