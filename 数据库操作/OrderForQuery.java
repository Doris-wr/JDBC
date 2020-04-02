package 数据库操作;

import org.junit.Test;
import 工具类.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.*;

/**
 * @Author:wangrui
 * @Date:2020/3/16 22:12
 * 针对于Order表的通用的查询操作
 * 针对于表的字段名和类的属性名不相同的情况
 * 使用ResultSetMetaData时，需要使用getColumnLabel来替换getColumnName()获取类的别名
 * 说明：如果sql中没有给字段起别名，getCloumnLabel()获取的就是列名
 */

public class OrderForQuery {
    @Test
    //普通的查询
    public void testQuery1(){
        Connection con= null;
        PreparedStatement ps= null;
        ResultSet rs= null;
        try {
            con = JDBCUtils.getConnection();
            String sql="select order_id,order_name,order_date from  orderd where order_id=?";
            ps = con.prepareStatement(sql);
            ps.setObject(1,1);
            rs = ps.executeQuery();
            if(rs.next()){
                int id=(int)rs.getObject(1);
                String name=(String)rs.getObject(2);
                Date date=(Date)rs.getObject(3);
                Order order=new Order(id,name,date);
                System.out.println(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        JDBCUtils.closeResourse(con,ps,rs);
    }
    //针对于order表的通用查询操作
    public Order orderForQuery(String sql,Object...args) {
        Connection con = null;
        PreparedStatement ps= null;
        ResultSet rs= null;
        try {
            con = JDBCUtils.getConnection();
            ps = con.prepareStatement(sql);
            for(int i=0;i<args.length;i++){
                ps.setObject(i+1,args[i]);
            }
            //执行，获取结果集
            rs = ps.executeQuery();
            //获取结果的元数据
            ResultSetMetaData rsmd=rs.getMetaData();
            //获取列数
            int columnCount=rsmd.getColumnCount();
            if(rs.next()){
                Order order=new Order();
                for(int i=0;i<columnCount;i++){
                    //获取每个列的列值：通过ResultSet
                    Object columnValue = rs.getObject(i + 1);
                    //通过ResultSetMetaData
                    //获取列的列名，getColumnName()
                   /* String columnName=rsmd.getColumnName(i+1);*/
                    //获取列的别名 getColumnLabel()
                   String columnLabel=rsmd.getColumnLabel(i+1);
                    //通过反射，将对象指定名columnName的属性 赋值为指定的值columnValue
                    Field field=Order.class.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(order,columnValue);
                }
                return order;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResourse(con,ps,rs);
        }
        return null;
    }
    //测试通用order表查询
    @Test
    public void testOrderForQuery(){
       String sql="select order_id orderId,order_name orderName,order_date orderDate from orderd where order_id=?";
        Order order = orderForQuery(sql, 1);
        System.out.println(order);
    }
}
