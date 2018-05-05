package com.holy.factory.model.api.user;

/**
 * 用户更新信息所使用的Model
 *
 * @author hailin
 * @version 1.0.0
 */
public class UserUpdateModel {
    private String name;
    private String portrait;
    private int sex;

    public UserUpdateModel(String name, String portrait, int sex) {
        this.name = name;
        this.portrait = portrait;
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "UserUpdateModel{" +
                "name='" + name + '\'' +
                ", portrait='" + portrait + '\'' +
                ", sex=" + sex +
                '}';
    }
}
