package dao;

import 工具类.JDBCUtils;
import 数据库操作.Order;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * @Author:wangrui
 * @Date:2020/3/18 16:27
 * 封装了针对于数据表的通用的操作
 */
public abstract class BaseDAO {
    //通用的增删改操作——version2
    public int update(Connection con,String sql, Object... args) {//sql中的占位符应与可变形参成都一致}
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
    //通用的查询操操作，用于返回数据表中的一条记录
    public <T> T getInstance(Connection con,Class<T>clazz,String sql,Object...args){
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            ps=con.prepareStatement(sql);
            for(int i=0;i<args.length;i++){
                ps.setObject(i+1,args[i]);
            }
            rs=ps.executeQuery();
            //获取结果集的元数据：ResultSetMetaData
            ResultSetMetaData rsmd=rs.getMetaData();
            //通过resultSetMetaData获取结果集中的列数
            int columnCount=rsmd.getColumnCount();

            if(rs.next()){
                T t=clazz.newInstance();
                //处理结果集的每一行数据中的每一列
                for(int i=0;i<columnCount;i++){
                    //获取列值
                    Object columnValue=rs.getObject(i+1);
                    //获取每个列的列名
                    //String columnName=rsmd.getColumnName(i+1);
                    String columnLabel=rsmd.getColumnLabel(i+1);

                    //给t对象指定的columnName属性，赋值为columValue，通过反射
                    Field field=clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t,columnValue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            JDBCUtils.closeResourse(null,ps,rs);
        }
        return null;
    }
    //通用的查询操操作，用于返回数据表中的多条记录构成的集合
    public <T> List<T> getForList(Connection con,Class<T> clazz, String sql, Object...args){
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            ps=con.prepareStatement(sql); String sql1="select order_id orderId,order_name orderName from orderd where order_id=?";
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
            JDBCUtils.closeResourse(null,ps,rs);
        }
        return null;
    }
    //查询特殊值的通用方法
    public <E> E getValue(Connection con,String sql,Object...args) {
        PreparedStatement ps = null;
        ResultSet rs= null;
        try {
            ps = con.prepareStatement(sql);
            for(int i=0;i<args.length;i++){
                ps.setObject(i+1,args[i]);
            }
            rs = ps.executeQuery();
            if(rs.next()){
                return (E) rs.getObject(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResourse(null,ps,rs);
        }
        return null;
    }
}
