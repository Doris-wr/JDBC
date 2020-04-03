package dao;

import 数据库操作.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @Author:wangrui
 * @Date:2020/3/18 17:15
 */
public class CustomerDAOImpl extends BaseDAO implements CustomerDAO{
    @Override
    public void insert(Connection con, Customer customer) {
        String sql="insert into customers(name,email,birth)values(?,?,?)";
          update(con,sql, customer.getName(), customer.getEmail(), customer.getBirth());
    }

    @Override
    public void deleteById(Connection con, int id) {
        String sql="delete from customers where id=?";
        update(con,sql,id);
    }

    @Override
    public void update(Connection con, Customer cust) {
        String sql="update customers set name=?,email=?,birth=?,where id=?";
        update(con,sql,cust.getName(),cust.getEmail(),cust.getBirth(),cust.getId());
    }

    @Override
    public Customer getCustomerById(Connection con, int id) {
        String sql="select id,name,email,birth from customers where id=?";
        Customer customer = getInstance(con, Customer.class, sql, id);
        return customer;
    }

    @Override
    public List<Customer> getAll(Connection con) {
       String sql="select id,name,email,birth from customers";
       List<Customer>list=getForList(con,Customer.class,sql);
       return list;
    }

    @Override
    public Long getCount(Connection con) {
        String sql="select count(*) from customers";
        return getValue(con,sql);
    }

    @Override
    public Date getMaxBirth(Connection con) {
        String sql="select max(birth) from customers";
        return getValue(con,sql);
    }
}
