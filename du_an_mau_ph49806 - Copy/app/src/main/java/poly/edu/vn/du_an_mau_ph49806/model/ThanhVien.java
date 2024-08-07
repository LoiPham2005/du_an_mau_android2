package poly.edu.vn.du_an_mau_ph49806.model;

public class ThanhVien {
    int maTV;
    String hoTen, namSinh;

    public ThanhVien() {
    }

    public ThanhVien( String hoTen, String namSinh) {
        this.hoTen = hoTen;
        this.namSinh = namSinh;
    }

    public ThanhVien(int maTV, String hoTen, String namSinh) {
        this.maTV = maTV;
        this.hoTen = hoTen;
        this.namSinh = namSinh;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getNamSinh() {
        return namSinh;
    }

    public void setNamSinh(String namSinh) {
        this.namSinh = namSinh;
    }
}
