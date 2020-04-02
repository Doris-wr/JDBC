package 数据库操作;
import org.junit.Test;
import 工具类.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:wangrui
 * @Date:2020/3/17 14:47
 * 描述：实现针对不同表的通用的查询操作
 */
public class QueryTest {
    /*
     * 功能描述:针对于不同表的通用的查询操作，返回表中的一条记录
     * @return T
     */
      public <T> T getInstance(Class<T>clazz,String sql,Object...args){
          Connection con=null;
          PreparedStatement ps=null;
          ResultSet rs=null;
          try{
              con=JDBCUtils.getConnection();
              ps=con.prepareStatement(sql);
              for(int i=0;i<args.length;i++){
                  ps.setObject(i+1,args[i]);
              }
              rs=ps.executeQuery();
              //获取结果集的元数据：ResultSetMetaData
              ResultSetMetaData rsmd=rs.getMetaData();
              //通过resultSetMetaData获取结果集中的列数
              int columbCount=rsmd.getColumnCount();
              //创建集合对象
              ArrayList<T> list=new ArrayList<T>();
              if(rs.next()){
                  T t=clazz.newInstance();
                  //处理结果集的每一行数据中的每一列
                  for(int i=0;i<columbCount;i++){
                      Object colimValue=rs.getObject(i+1);
                      //获取列值
                      Object columValue=rs.getObject(i+1);
                      //获取每个列的列名
                      //String columnName=rsmd.getColumnName(i+1);
                      String columnLabel=rsmd.getColumnLabel(i+1);

                      //给t对象指定的columnName属性，赋值为columValue，通过反射
                      Field field=clazz.getDeclaredField(columnLabel);
                      field.setAccessible(true);
                      field.set(t,colimValue);
                  }
                  return t;
              }
          } catch (Exception e) {
              e.printStackTrace();
          }finally{
              JDBCUtils.closeResourse(con,ps,rs);
          }
          return null;
      }
    @Test
    public void testGetInstance(){
        String sql="select id,named name,email from customers where id=?";
        Customer customer=getInstance(Customer.class,sql,1);
        System.out.println(customer);


    }
      public <T> List<T> getForList(Class<T> clazz,String sql,Object...args){
          Connection con=null;
          PreparedStatement ps=null;
          ResultSet rs=null;
          try{
              con=JDBCUtils.getConnection();
              ps=con.prepareStatement(sql); String sql1="select order_id orderId,order_name orderName from orderd where order_id=?";
              Order order=getInstance(Order.class,sql1,1);
              System.out.println(order);
              for(int i=0;i<args.length;i++){
                  ps.setObject(i+1,args[i]);
              }
              rs=ps.executeQuery();
              //获取结果集的元数据：ResultSetMetaData
              ResultSetMetaData rsmd=rs.getMetaData();
              //通过resultSetMetaData获取结果集中的列数
              int columbCount=rsmd.getColumnCount();
              ArrayList<T> list=new ArrayList<T>();
              while(rs.next()){
                  T t=clazz.newInstance();
                  //处理结果集的每一行数据中的每一列;给t对象指定的属性赋值
                  for(int i=0;i<columbCount;i++){
                      Object colimValue=rs.getObject(i+1);
                      //获取列值
                      Object columValue=rs.getObject(i+1);
                      //获取每个列的列名
                      //String columnName=rsmd.getColumnName(i+1);
                      String columnLabel=rsmd.getColumnLabel(i+1);

                      //给t对象指定的columnName属性，赋值为columValue，通过反射
                      Field field=clazz.getDeclaredField(columnLabel);
                      field.setAccessible(true);
                      field.set(t,colimValue);
                  }
                  list.add(t);
              }
              return list;
          } catch (Exception e) {
              e.printStackTrace();
          }finally{
              JDBCUtils.closeResourse(con,ps,rs);
          }
          return null;
      }
      @Test
    public void testGetForList(){
          String sql="select id,named name,email from customers where id<?";
          List<Customer>list=getForList(Customer.class,sql,3);
          list.forEach(System.out::println);
          System.out.println("**************************");
          String sql1="select order_id orderId,order_name orderName from orderd where order_id<?";
          List<Order>orderList=getForList(Order.class,sql1,3);
          orderList.forEach(System.out::println);
      }
}
