package poly.edu.vn.du_an_mau_ph49806.model;

public class Books {
    int maPhieu;
    String thanhVien;
    String tenSach;
    int tienThue;
    String tinhTrang;
    String ngayThue;

    public Books() {
    }

    public Books(String thanhVien, String tenSach, int tienThue, String tinhTrang, String ngayThue) {
        this.thanhVien = thanhVien;
        this.tenSach = tenSach;
        this.tienThue = tienThue;
        this.tinhTrang = tinhTrang;
        this.ngayThue = ngayThue;
    }

    public Books(int maPhieu, String thanhVien, String tenSach, int tienThue, String tinhTrang, String ngayThue) {
        this.maPhieu = maPhieu;
        this.thanhVien = thanhVien;
        this.tenSach = tenSach;
        this.tienThue = tienThue;
        this.tinhTrang = tinhTrang;
        this.ngayThue = ngayThue;
    }

    public int getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(int maPhieu) {
        this.maPhieu = maPhieu;
    }

    public String getThanhVien() {
        return thanhVien;
    }

    public void setThanhVien(String thanhVien) {
        this.thanhVien = thanhVien;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getTienThue() {
        return tienThue;
    }

    public void setTienThue(int tienThue) {
        this.tienThue = tienThue;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getNgayThue() {
        return ngayThue;
    }

    public void setNgayThue(String ngayThue) {
        this.ngayThue = ngayThue;
    }
}
