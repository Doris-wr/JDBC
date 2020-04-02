package 数据库操作;

import java.sql.Date;

/**
 * @Author:wangrui
 * @Date:2020/3/16 22:04
 */
public class Order {
    private int orderId;
    private String orderName;
    private Date orderDate;

    public Order(int orderId, String orderName, Date orderDate) {
        this.orderId = orderId;
        this.orderName = orderName;
        this.orderDate = orderDate;
    }

    public Order() {
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "Order[ orderId="+orderId+",orderName="+orderName+",orderDate="+orderDate+"]";
    }
}
