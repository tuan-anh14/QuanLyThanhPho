package entity;

public class CongVien extends CongTrinh {
    private int congVienId;
    private String ten;
    private String soDiaChi;
    private int duongId;
    private int soKhachMotNgay;
    private double dienTich;
    private String quanLy;

    // Getters and Setters
    public int getCongVienId() {
        return congVienId;
    }

    public void setCongVienId(int congVienId) {
        this.congVienId = congVienId;
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

    public int getSoKhachMotNgay() {
        return soKhachMotNgay;
    }

    public void setSoKhachMotNgay(int soKhachMotNgay) {
        this.soKhachMotNgay = soKhachMotNgay;
    }

    public double getDienTich() {
        return dienTich;
    }

    public void setDienTich(double dienTich) {
        this.dienTich = dienTich;
    }

    public String getQuanLy() {
        return quanLy;
    }

    public void setQuanLy(String quanLy) {
        this.quanLy = quanLy;
    }
}
