package com.holy.factory.model.api.account;

/**
 * @author Hailin
 * @time 2018/4/27 09:56
 * @function
 */
public class RegisterModel {
    private String account;
    private String password;
    private String name;
    private String pushId;

    public RegisterModel(String account, String passwd, String name) {
        this(account, passwd, name, null);
    }

    public RegisterModel(String account, String password, String name, String pushID) {
        this.account = account;
        this.password = password;
        this.name = name;
        this.pushId = pushID;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RegisterModel{" +
                "account='" + account + '\'' +
                ", passwd='" + password + '\'' +
                ", name='" + name + '\'' +
                ", pushID='" + pushId + '\'' +
                '}';
    }
}
