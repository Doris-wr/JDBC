package dao;

import jdk.nashorn.internal.scripts.JD;
import org.junit.Test;
import 工具类.JDBCUtils;
import 数据库操作.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @Author:wangrui
 * @Date:2020/3/18 22:23
 */
public class CustomerDAOImpleTest {
    private CustomerDAOImpl dao=new CustomerDAOImpl();
    @Test
    public void testInsert(){
        Connection con= null;
        try {
            con = JDBCUtils.getConnection();
            Customer cust=new Customer(1,"yuxiaofei","xiaofe@qq.com",new Date(23948388785L));
            dao.insert(con,cust);
            System.out.println("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResourse(con,null);
        }
    }
    @Test
    public void testDeleteById(){
        Connection con= null;
        try {
            con = JDBCUtils.getConnection();
            dao.deleteById(con,13);
            System.out.println("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResourse(con,null);
        }
    }
    @Test
    public void testUpdateConnectionCustomer(){
        Connection con= null;
        try {
            con = JDBCUtils.getConnection();
            Customer cust=new Customer(18,"贝多芬","beiduofen@126.con",new Date(13475637853L));
            dao.update(con,cust);
            System.out.println("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        JDBCUtils.closeResourse(con,null);
    }
    @Test
    public void testGetCustomerById(){
        Connection con= null;
        try {
            con = JDBCUtils.getConnection();
            Customer cust=dao.getCustomerById(con,19);
            System.out.println(cust);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        JDBCUtils.closeResourse(con,null);
    }
    @Test
    public void testGetAll(){
        Connection con= null;
        try {
            con = JDBCUtils.getConnection();
            List<Customer>list=dao.getAll(con);
            list.forEach(System.out::println);
            System.out.println("");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        JDBCUtils.closeResourse(con,null);
    }
    @Test
    public void testGetCount(){
        Connection con= null;
        try {
            con = JDBCUtils.getConnection();
            Long count=dao.getCount(con);
            System.out.println("表中的记录数为："+count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        JDBCUtils.closeResourse(con,null);
    }
    @Test
    public void testGetMaxBirth(){
        Connection con= null;
        try {
            con = JDBCUtils.getConnection();
            Date maxbirth=dao.getMaxBirth(con);
            System.out.println("最大的生日为："+maxbirth);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        JDBCUtils.closeResourse(con,null);
    }
}
