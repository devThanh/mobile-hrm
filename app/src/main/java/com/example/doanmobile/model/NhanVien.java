package com.example.doanmobile.model;

import java.io.Serializable;
import java.util.Arrays;

public class NhanVien implements Serializable {
    public int ma;
    public String hoten, phongban, sdt, email;
    public byte[] anh;

    public NhanVien(int ma, String hoten, String phongban, String sdt, String email, byte[] anh) {
        this.ma = ma;
        this.hoten = hoten;
        this.phongban = phongban;
        this.sdt = sdt;
        this.email = email;
        this.anh = anh;
    }

    public NhanVien(int ma, String hoten, String phongban, byte[] anh) {
        this.ma = ma;
        this.hoten = hoten;
        this.phongban = phongban;
        this.anh = anh;
    }

    public NhanVien() {
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getPhongban() {
        return phongban;
    }

    public void setPhongban(String phongban) {
        this.phongban = phongban;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getAnh() {
        return anh;
    }

    public void setAnh(byte[] anh) {
        this.anh = anh;
    }

    @Override
    public String toString() {
        return
                "MÃ: " + ma + '\n'+
                "HỌ TÊN: '" + hoten + '\n' +
                "PHÒNG BAN: " + phongban + '\n' +
                "SĐT: " + sdt + '\n' +
                "EMAIL: " + email + '\'';
    }
}
