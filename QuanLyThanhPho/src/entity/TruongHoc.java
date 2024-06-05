package entity;

public class TruongHoc extends CongTrinh {
    private int truongHocId;
    private String ten;
    private String soDiaChi;
    private int duongId;
    private String capBac;
    private int soHocSinh;
    private int soGiangVien;
    private String hieuTruong;

    // Getters and Setters
    public int getTruongHocId() {
        return truongHocId;
    }

    public void setTruongHocId(int truongHocId) {
        this.truongHocId = truongHocId;
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

    public String getCapBac() {
        return capBac;
    }

    public void setCapBac(String capBac) {
        this.capBac = capBac;
    }

    public int getSoHocSinh() {
        return soHocSinh;
    }

    public void setSoHocSinh(int soHocSinh) {
        this.soHocSinh = soHocSinh;
    }

    public int getSoGiangVien() {
        return soGiangVien;
    }

    public void setSoGiangVien(int soGiangVien) {
        this.soGiangVien = soGiangVien;
    }

    public String getHieuTruong() {
        return hieuTruong;
    }

    public void setHieuTruong(String hieuTruong) {
        this.hieuTruong = hieuTruong;
    }
}
