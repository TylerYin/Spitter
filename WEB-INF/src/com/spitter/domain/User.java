package com.spitter.domain;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * @author Tyler Yin
 */
@XStreamAlias("message")
public class User {

    @XStreamAsAttribute
    private String name;

    @XStreamAsAttribute
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "name:" + name + ",phone:" + phone;
    }
}
