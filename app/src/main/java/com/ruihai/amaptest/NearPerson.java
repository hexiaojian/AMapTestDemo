package com.ruihai.amaptest;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by apple on 16/10/10.
 */

public class NearPerson implements Serializable{
    @SerializedName("count")
    public int  count;
    @SerializedName("info")
    public String info;
    @SerializedName("infocode")
    public String infocode;
    @SerializedName("status")
    public String status;
    @SerializedName("datas")
    public List<NearPersonInfo> datas;


    public int getCount() {
        return count;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCount(int count) {

        this.count = count;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<NearPersonInfo> getNearPersonInfos() {return datas;}

    public void setNearPersonInfos(List<NearPersonInfo> datas) {
        this.datas = datas;
    }

    @Override
    public String toString() {
        return "NearPerson{" +
                "count=" + count +
                ", info='" + info + '\'' +
                ", infocode='" + infocode + '\'' +
                ", status='" + status + '\'' +
                ", datas=" + datas +
                '}';
    }
}
