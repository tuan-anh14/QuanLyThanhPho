package entity;
public class NguoiDan extends Duong {
    private int nguoiDanId;
    private String ho;
    private String ten;
    private String ngaySinh;
    private String gioiTinh;
    private String cccd;
    private int soNha;
    private String ngheNghiep;

    // Getters and Setters
    public int getNguoiDanId() {
        return nguoiDanId;
    }

    public void setNguoiDanId(int nguoiDanId) {
        this.nguoiDanId = nguoiDanId;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public int getSoNha() {
        return soNha;
    }

    public void setSoNha(int soNha) {
        this.soNha = soNha;
    }

    public String getNgheNghiep() {
        return ngheNghiep;
    }

    public void setNgheNghiep(String ngheNghiep) {
        this.ngheNghiep = ngheNghiep;
    }
}
