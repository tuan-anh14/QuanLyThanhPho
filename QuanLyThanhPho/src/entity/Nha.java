package entity;

public class Nha extends NguoiDan {
    private int nhaId;
    private int sonha;
    private String loaiNha;
    private int congTrinhId;

    // Getters and Setters
    public int getNhaId() {
        return nhaId;
    }

    public void setNhaId(int nhaId) {
        this.nhaId = nhaId;
    }

    public int getSonha() {
        return sonha;
    }

    public void setSonha(int sonha) {
        this.sonha = sonha;
    }

    public String getLoaiNha() {
        return loaiNha;
    }

    public void setLoaiNha(String loaiNha) {
        this.loaiNha = loaiNha;
    }

    public int getCongTrinhId() {
        return congTrinhId;
    }

    public void setCongTrinhId(int congTrinhId) {
        this.congTrinhId = congTrinhId;
    }
}
