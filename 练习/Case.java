import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.*;

public class Case {
    private final static String URL =
            "jdbc:mysql://127.0.0.1/my_jdbc_db";
    private final static String USER = "root";
    private final static String PASSWORD = "";

    static {
        // db.properties
    }
    public static DataSource getDataSource(){
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(URL);
        dataSource.setUser(USER);
        dataSource.setPassword(PASSWORD);

        return dataSource;
    }
    //建表
    public static void createTable(){
        DataSource dataSource = getDataSource();
        Connection connection = null;
        Statement stmt = null;

        try {
            // 获取连接
            connection = dataSource.getConnection();

            // 建表语句
            String sql = "create table student(" +
                    "name varchar(20) not null," +
                    "sn int primary key," +
                    "height double(5,2)" +
                    ")";

            System.out.println("sql = " + sql);

            stmt = connection.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null){
                    stmt.close();
                }

                if (connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void insertIntoTable(Student stu){
        DataSource dataSource = getDataSource();
        Connection connection = null;
        PreparedStatement stmt = null;

        try {
            // 获取连接
            connection = dataSource.getConnection();

            // 建表语句                                占位符
            String sql = "insert into student values(?,?,?)";
            System.out.println("sql = " + sql);

            stmt = connection.prepareStatement(sql);
            stmt.setString(1,stu.getName());
            stmt.setInt(2,stu.getSn());
            stmt.setInt(3,stu.getHeight());
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null){
                    stmt.close();
                }

                if (connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void selectData(int sn){
        DataSource dataSource = getDataSource();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs=null;
        try {
            // 获取连接
            connection = dataSource.getConnection();

            // 建表语句
            String sql = "select* from student where sn=?";
            System.out.println("sql = " + sql);

            stmt = connection.prepareStatement(sql);
            stmt.setInt(1,sn);
            rs=stmt.executeQuery();
            while(rs.next()){
                System.out.println("name"+
                        rs.getString("name")+
                        ",sn="+rs.getInt("sn")+
                        ",heigth="+
                        rs.getInt("heigth"));
            }
            stmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs!=null){
                    rs.close();
                }
                if (stmt != null){
                    stmt.close();
                }

                if (connection != null){
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static class Student{
        private String name;
        private int sn;
        private int height;
        public String getName() {
            return name;
        }

        public int getSn() {
            return sn;
        }

        public void setSn(int sn) {
            this.sn = sn;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getHeight() {
            return height;

        }

        public void setHeight(int height) {
            this.height = height;
        }
    }
    public static void main(String[] args) {
      //  getDataSource();
        //createTable();
        Student stu=new Student();
        stu.setHeight(188);
        stu.setName("lily");
        stu.setSn(2019013);
        selectData(20190001);
    }
}
