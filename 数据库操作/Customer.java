package 数据库操作;

import java.sql.Date;

/**
 * @Author:wangrui
 * @Date:2020/3/15 20:43
 */
/*
 * 功能描述:ORM编程思想（Object relation mapping)
 * 表中的一个字段对应一个属性
 * @return
 */
public class Customer {
    int id;
    String name;
    String email;
    Date birth;
    public Customer() {
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public Customer(int id, String name, String email, Date birth) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.birth=birth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Customer{ id="+id+",name="+
                name+",email="+email+",birth="+birth+"}";
    }
}
