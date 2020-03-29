package com.spitter.orm.domain;

import java.io.Serializable;

/**
 * @author Tyler Yin
 */
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    private String no;
    private String name;
    private String manufacture;
    private Integer num;
    private String createDate;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacture() {
        return manufacture;
    }

    public void setManufacture(String manufacture) {
        this.manufacture = manufacture;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
