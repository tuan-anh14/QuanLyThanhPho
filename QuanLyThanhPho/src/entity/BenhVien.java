package entity;

public class BenhVien extends CongTrinh {
    private int benhVienId;
    private String soDiaChi;
    private int duongId;
    private int namThanhLap;
    private int soBacSi;
    private int soBenhNhan;
    private String giamDoc;

    // Getters and Setters
    public int getBenhVienId() {
        return benhVienId;
    }

    public void setBenhVienId(int benhVienId) {
        this.benhVienId = benhVienId;
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

    public int getNamThanhLap() {
        return namThanhLap;
    }

    public void setNamThanhLap(int namThanhLap) {
        this.namThanhLap = namThanhLap;
    }

    public int getSoBacSi() {
        return soBacSi;
    }

    public void setSoBacSi(int soBacSi) {
        this.soBacSi = soBacSi;
    }

    public int getSoBenhNhan() {
        return soBenhNhan;
    }

    public void setSoBenhNhan(int soBenhNhan) {
        this.soBenhNhan = soBenhNhan;
    }

    public String getGiamDoc() {
        return giamDoc;
    }

    public void setGiamDoc(String giamDoc) {
        this.giamDoc = giamDoc;
    }
}
