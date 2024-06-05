package entity;
public class Main {
    public static void main(String[] args) {
        // Create an instance of Quan
        Quan quan = new Quan();
        quan.setQuanId(1);
        quan.setTenQuan("Quan 1");
        quan.setSoLuongNguoi(100000);
        quan.setSoLuongDuong(50);
        quan.setSoLuongCongTrinh(200);

        // Create an instance of Duong
        Duong duong = new Duong();
        duong.setQuanId(quan.getQuanId());
        duong.setDuongId(1);
        duong.setTenDuong("Duong 1");

        // Create an instance of NguoiDan
        NguoiDan nguoiDan = new NguoiDan();
        nguoiDan.setQuanId(duong.getQuanId());
        nguoiDan.setDuongId(duong.getDuongId());
        nguoiDan.setNguoiDanId(1);
        nguoiDan.setHo("Nguyen");
        nguoiDan.setTen("An");
        nguoiDan.setNgaySinh("01-01-1990");
        nguoiDan.setGioiTinh("Nam");
        nguoiDan.setCccd("123456789");
        nguoiDan.setSoNha(101);
        nguoiDan.setNgheNghiep("Ky Su");

        // Create an instance of Nha
        Nha nha = new Nha();
        nha.setQuanId(nguoiDan.getQuanId());
        nha.setDuongId(nguoiDan.getDuongId());
        nha.setNguoiDanId(nguoiDan.getNguoiDanId());
        nha.setNhaId(1);
        nha.setSonha(101);
        nha.setLoaiNha("Nha Rieng");
        nha.setCongTrinhId(1);

        // Create an instance of TruongHoc
        TruongHoc truongHoc = new TruongHoc();
        truongHoc.setCongTrinhId(1);
        truongHoc.setTruongHocId(1);
        truongHoc.setTen("Truong Tieu Hoc A");
        truongHoc.setSoDiaChi("So 1, Duong 1");
        truongHoc.setDuongId(duong.getDuongId());
        truongHoc.setCapBac("Tieu Hoc");
        truongHoc.setSoHocSinh(500);
        truongHoc.setSoGiangVien(30);
        truongHoc.setHieuTruong("Nguyen Van B");

        // Create an instance of KhachSan
        KhachSan khachSan = new KhachSan();
        khachSan.setCongTrinhId(2);
        khachSan.setKhachSanId(1);
        khachSan.setTen("Khach San A");
        khachSan.setSoDiaChi("So 10, Duong 1");
        khachSan.setDuongId(duong.getDuongId());
        khachSan.setSoPhong(100);
        khachSan.setSoPhongConTrong(20);
        khachSan.setCheckin("14:00");
        khachSan.setCheckout("12:00");
        khachSan.setQuanLy("Tran Van C");

        // Create an instance of BenhVien
        BenhVien benhVien = new BenhVien();
        benhVien.setCongTrinhId(3);
        benhVien.setBenhVienId(1);
        benhVien.setSoDiaChi("So 20, Duong 1");
        benhVien.setDuongId(duong.getDuongId());
        benhVien.setNamThanhLap(2000);
        benhVien.setSoBacSi(50);
        benhVien.setSoBenhNhan(200);
        benhVien.setGiamDoc("Le Thi D");

        // Create an instance of CongVien
        CongVien congVien = new CongVien();
        congVien.setCongTrinhId(4);
        congVien.setCongVienId(1);
        congVien.setTen("Cong Vien A");
        congVien.setSoDiaChi("So 30, Duong 1");
        congVien.setDuongId(duong.getDuongId());
        congVien.setSoKhachMotNgay(1000);
        congVien.setDienTich(2.5);
        congVien.setQuanLy("Nguyen Van E");

        // Create an instance of TTTM
        TTTM tttm = new TTTM();
        tttm.setCongTrinhId(5);
        tttm.setTttmId(1);
        tttm.setTen("TTTM A");
        tttm.setSoDiaChi("So 40, Duong 1");
        tttm.setDuongId(duong.getDuongId());
        tttm.setSoLuongNhanVien(300);
        tttm.setSoLuongKhachMotNgay(5000);
        tttm.setQuanLy("Tran Thi F");

        // Output some details to demonstrate the relationships
        System.out.println("Quan: " + quan.getTenQuan());
        System.out.println("Duong: " + duong.getTenDuong());
        System.out.println("Nguoi Dan: " + nguoiDan.getHo() + " " + nguoiDan.getTen());
        System.out.println("Nha: " + nha.getSonha() + ", Loai nha: " + nha.getLoaiNha());
        System.out.println("Truong Hoc: " + truongHoc.getTen() + ", Hieu truong: " + truongHoc.getHieuTruong());
        System.out.println("Khach San: " + khachSan.getTen() + ", Quan ly: " + khachSan.getQuanLy());
        System.out.println("Benh Vien: " + benhVien.getSoDiaChi() + ", Giam doc: " + benhVien.getGiamDoc());
        System.out.println("Cong Vien: " + congVien.getTen() + ", Quan ly: " + congVien.getQuanLy());
        System.out.println("TTTM: " + tttm.getTen() + ", Quan ly: " + tttm.getQuanLy());
    }
}
