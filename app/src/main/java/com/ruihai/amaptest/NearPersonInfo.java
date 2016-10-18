package com.ruihai.amaptest;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 16/10/10.
 */

public class NearPersonInfo implements Serializable{
    @SerializedName("_id")
    public int _id;
    @SerializedName("_location")
    public String _location;
    @SerializedName("_name")
    public int _name;
    @SerializedName("_address")
    public String _address;
    @SerializedName("sex")
    public int sex;
    @SerializedName("nick")
    public String nick;
    @SerializedName("img")
    public String img;
    @SerializedName("account")
    public int account;
    @SerializedName("isAdmin")
    public int isAdmin;
    @SerializedName("_createtime")
    public String _createtime;
    @SerializedName("_updatetime")
    public String _updatetime;
    @SerializedName("_province")
    public String _province;
    @SerializedName("_city")
    public String _city;
    @SerializedName("_district")
    public  String _district;
    @SerializedName("_distance")
    public int _distance;
    @SerializedName("_images")
    public List<NearPersonImage> _images;

    public List<NearPersonImage> get_images() {
        return _images;
    }

    public void set_image(List<NearPersonImage> _images) {
        this._images = _images;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_location() {
        return _location;
    }

    public void set_location(String _location) {
        this._location = _location;
    }

    public int get_name() {
        return _name;
    }

    public void set_name(int _name) {
        this._name = _name;
    }

    public String get_address() {
        return _address;
    }

    public void set_address(String _address) {
        this._address = _address;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getAccount() {
        return account;
    }

    public void setAccount(int account) {
        this.account = account;
    }

   public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

     public String get_createtime() {
        return _createtime;
    }

    public void set_createtime(String _createtime) {
        this._createtime = _createtime;
    }

    public String get_updatetime() {
        return _updatetime;
    }

    public void set_updatetime(String _updatetime) {
        this._updatetime = _updatetime;
    }

    public String get_province() {
        return _province;
    }

    public void set_province(String _province) {
        this._province = _province;
    }

    public String get_city() {
        return _city;
    }

    public void set_city(String _city) {
        this._city = _city;
    }

    public String get_district() {
        return _district;
    }

    public void set_district(String _district) {
        this._district = _district;
    }

    public int get_distance() {
        return _distance;
    }

    public void set_distance(int _distance) {
        this._distance = _distance;
    }


}
