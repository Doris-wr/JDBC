package 数据库操作;
import org.junit.Test;
import 工具类.JDBCUtils;
import java.lang.reflect.Field;
import java.sql.*;

/**
 * @Author:wangrui
 * @Date:2020/3/15 20:10
 */
/*
 * 功能描述:针对于Cuetomers表的查询操作
 * @return
 */
public class CustomerForQuery {
    @Test
    public void testQuery1() {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            con = JDBCUtils.getConnection();
            String sql = "select id,named,email,birth from customers where id=?";
            ps = con.prepareStatement(sql);
            ps.setObject(1, 20);
            //执行，并返回结果集
            resultSet = ps.executeQuery();
            //处理结果集
            if (resultSet.next()) {//next()：判断结果集的下一跳是否有数据，如果有数据返回true，并指针下移；如果返回false，指针不会下移，
                //获取当前数据的各个字段值
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                Date birth = resultSet.getDate(4);
                //方式一
                Object[] data = new Object[]{id, name, email, birth};
                //方式二：将数封装为一个对象（推荐）
                Customer customer = new Customer(id, name, email, birth);
                System.out.println(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            JDBCUtils.closeResourse(con, ps, resultSet);
        }
    }

    /*
     * 功能描述:针对于Customers表的通用的查询操作
     * @return void
     */
    public Customer queryForCustomers(String sql, Object... args){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = JDBCUtils.getConnection();
            ps = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            rs = ps.executeQuery();
            //获取结果集的元数据，ResultSetMetaData（）
            ResultSetMetaData rsmd = rs.getMetaData();
            //通过ResultSetMetaDate获取结果集的列数
            int columnCount = rsmd.getColumnCount();
            if (rs.next()) {
                Customer cust = new Customer();
                //处理结果集一行数据中的每一个列
                for (int i = 0; i < columnCount; i++) {
                    //获取列值
                    Object columValue = rs.getObject(i + 1);
                    //获取每个列的列名
                    String columnName = rsmd.getColumnName(i + 1);
                    //给cust对象指定的columnName属性，赋值为columValue；通过反射
                    Field field = Customer.class.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(cust, columValue);
                }
                  return cust;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResourse(con,ps,rs);
            return null;
        }
    }
    @Test
    public void testQueryForCustomers(){
           String sql="select id,named,birth,email from customers where id=?";
           Customer customer=queryForCustomers(sql,20);
           System.out.println(customer);

           sql="select name,email,from customers where name=?";
           Customer customer1 = queryForCustomers(sql, "zhangchenguang");
           System.out.println(customer1);
    }
}
