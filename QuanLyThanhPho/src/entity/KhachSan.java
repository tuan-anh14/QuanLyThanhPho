package entity;

public class KhachSan extends CongTrinh {
    private int khachSanId;
    private String ten;
    private String soDiaChi;
    private int duongId;
    private int soPhong;
    private int soPhongConTrong;
    private int checkin;
    private int checkout;
    private String quanLy;

    // Getters and Setters
    public int getKhachSanId() {
        return khachSanId;
    }

    public void setKhachSanId(int khachSanId) {
        this.khachSanId = khachSanId;
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

    public int getSoPhong() {
        return soPhong;
    }

    public void setSoPhong(int soPhong) {
        this.soPhong = soPhong;
    }

    public int getSoPhongConTrong() {
        return soPhongConTrong;
    }

    public void setSoPhongConTrong(int soPhongConTrong) {
        this.soPhongConTrong = soPhongConTrong;
    }

    public int getCheckIn() {
        return checkin;
    }

    public void setCheckIn(int checkin) {
        this.checkin = checkin;
    }

    public int getCheckOut() {
        return checkout;
    }

    public void setCheckOut(int checkout) {
        this.checkout = checkout;
    }

    public String getQuanLy() {
        return quanLy;
    }

    public void setQuanLy(String quanLy) {
        this.quanLy = quanLy;
    }
}
