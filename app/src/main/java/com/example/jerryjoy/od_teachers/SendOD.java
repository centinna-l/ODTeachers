package com.example.jerryjoy.od_teachers;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class SendOD {

    public String nam;
    public String regno;
    public String reas;
    public String se;
    public String dt;

    public SendOD(String nam, String regno, String rsn, String se, String dt) {
        this.nam = nam;
        this.regno = regno;
        this.reas = rsn;
        this.se = se;
        this.dt = dt;
    }

    public SendOD() {

    }

    public String getNam() {
        return nam;
    }

    public void setNam(String nam) {
        this.nam = nam;
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno;
    }

    public String getRsn() {
        return reas;
    }

    public void setRsn(String rsn) {
        this.reas = rsn;
    }

    public String getSe() {
        return se;
    }

    public void setSe(String se) {
        this.se = se;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

}
