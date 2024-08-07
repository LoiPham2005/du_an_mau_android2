package poly.edu.vn.du_an_mau_ph49806.model;

public class LoaiSach {
    int maLoai;
    String tenLoai;

    public LoaiSach() {
    }

    public LoaiSach(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
}
