package entity;

public class TTTM extends CongTrinh {
    private int tttmId;
    private String ten;
    private String soDiaChi;
    private int duongId;
    private int soLuongNhanVien;
    private int soLuongKhachMotNgay;
    private String quanLy;

    // Getters and Setters
    public int getTTTMId() {
        return tttmId;
    }

    public void setTTTMId(int tttmId) {
        this.tttmId = tttmId;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSoDiaChi() {
        return soDiaChi;
    }

    public void setSoDiaChi(String soDiaChi) {
        this.soDiaChi = soDiaChi;
    }

    public int getDuongId() {
        return duongId;
    }

    public void setDuongId(int duongId) {
        this.duongId = duongId;
    }

    public int getSoLuongNhanVien() {
        return soLuongNhanVien;
    }

    public void setSoLuongNhanVien(int soLuongNhanVien) {
        this.soLuongNhanVien = soLuongNhanVien;
    }

    public int getSoLuongKhachMotNgay() {
        return soLuongKhachMotNgay;
    }

    public void setSoLuongKhachMotNgay(int soLuongKhachMotNgay) {
        this.soLuongKhachMotNgay = soLuongKhachMotNgay;
    }

    public String getQuanLy() {
        return quanLy;
    }

    public void setQuanLy(String quanLy) {
        this.quanLy = quanLy;
    }
}
