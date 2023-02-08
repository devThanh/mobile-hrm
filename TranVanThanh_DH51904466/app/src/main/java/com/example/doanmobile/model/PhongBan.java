package com.example.doanmobile.model;

public class PhongBan {
    private String MaPB, TenPB;

    public PhongBan(String maPB, String tenPB) {
        MaPB = maPB;
        TenPB = tenPB;
    }

    public PhongBan() {
    }

    public String getMaPB() {
        return MaPB;
    }

    public void setMaPB(String maPB) {
        MaPB = maPB;
    }

    public String getTenPB() {
        return TenPB;
    }

    public void setTenPB(String tenPB) {
        TenPB = tenPB;
    }

    @Override
    public String toString() {
        return
                MaPB + " - " +  TenPB;
    }
}
