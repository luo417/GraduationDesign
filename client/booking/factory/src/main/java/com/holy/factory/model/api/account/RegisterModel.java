package com.holy.factory.model.api.account;

/**
 * @author Hailin
 * @time 2018/4/27 09:56
 * @function
 */
public class RegisterModel {
    private String account;
    private String passwd;
    private String name;
    private String pushID;

    public RegisterModel(String account, String passwd, String name) {
        this(account, passwd, name, null);
    }

    public RegisterModel(String account, String passwd, String name, String pushID) {
        this.account = account;
        this.passwd = passwd;
        this.name = name;
        this.pushID = pushID;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPushID() {
        return pushID;
    }

    public void setPushID(String pushID) {
        this.pushID = pushID;
    }

    @Override
    public String toString() {
        return "RegisterModel{" +
                "account='" + account + '\'' +
                ", passwd='" + passwd + '\'' +
                ", name='" + name + '\'' +
                ", pushID='" + pushID + '\'' +
                '}';
    }
}
