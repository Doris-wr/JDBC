package blob;

import org.junit.Test;
import 工具类.JDBCUtils;
import 数据库操作.Customer;

import java.io.*;
import java.sql.*;

/**
 * @Author:wangrui
 * @Date:2020/3/17 20:47
 */
public class BlobTest {
    //向数据表中插入Blob类型的字段
    @Test
    public void testInsert() throws Exception {
        Connection con=JDBCUtils.getConnection();
        String sql="insert into customers(named,email,birth,photo)values (?,?,?,?)";
        PreparedStatement ps=con.prepareStatement(sql);
        ps.setObject(1,"liuyifei");
        ps.setObject(2,"liu@qq.com");
        ps.setObject(3,"1988 -12-09");
        FileInputStream is=new FileInputStream(new File("wangrui.jpg"));
        ps.setBlob(4,is);
        ps.execute();
        JDBCUtils.closeResourse(con,ps);
    }
    //查询customers表中Blob类型的字段
    @Test
    public void testQuery() {
        Connection con= null;
        PreparedStatement ps= null;
        ResultSet rs= null;
        InputStream is=null;
        FileOutputStream fos=null;
        try {
            con = JDBCUtils.getConnection();
            String sql="select id,named,email,birth,photo from customers where id=?";
            ps = con.prepareStatement(sql);
            ps.setObject(1,24);
            rs = ps.executeQuery();
            if(rs.next()){
                /*//方式一:以索引查找
                int id= rs.getInt(1);
                String name=rs.getString(2);
                String email=rs.getString(3);
                Date birth=rs.getDate(4);*/
                //方式二：以列的别名查找d
                int id=rs.getInt("id");
                String name=rs.getString("named");
                String email=rs.getString("email");
                Date birth=rs.getDate("birth");
                Customer cust=new Customer(id,name,email,birth);
                System.out.println(cust );
                //将Blob类型的字段下下来，以文件的方式进行保存
                Blob photo = rs.getBlob("photo");
                is=photo.getBinaryStream();
                fos=new FileOutputStream("liuyifei.jpg");
                byte[] buffer=new byte[1024];
                int len;
                while((len=is.read(buffer))!=-1){
                    fos.write(buffer,0,len);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(is!=null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(fos!=null){
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            JDBCUtils.closeResourse(con,ps,rs);
        }
    }
}
