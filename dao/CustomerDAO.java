package dao;

import 数据库操作.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @Author:wangrui
 * @Date:2020/3/18 16:45
 */
//此接口用于规定customers表的常用方法
public interface CustomerDAO {
    /*
     * 功能描述:将cust对象添加到数据库中
     * @return void
     */
    void insert(Connection con,Customer customer);
    /*
     * 功能描述:针对指定的id，删除表中的一条记录
     * @return void
     */
    void deleteById(Connection con,int id);
    /*
     * 功能描述:针对内存中的cust对象，取修改表中指定的记录
     * @return void
     */
    void update(Connection con,Customer cust);
    /*
     * 功能描述:针对指定的id查询对应的Customer对象
     * @return void
     */
    Customer getCustomerById(Connection con,int id);
    /*
     * 功能描述:查询表中的所有记录构成的集合
     * @return java.util.List<数据库操作.Customer>
     */
    List<Customer> getAll(Connection con);
    /*
     * 功能描述:返回表中的数据的条目数
     * @return java.lang.Long
     */
    Long getCount(Connection con);
    /*
     * 功能描述:返回表中最大的生日
     * @return java.sql.Date
     */
    Date getMaxBirth(Connection con);
}
