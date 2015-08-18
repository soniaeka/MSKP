package apps.info.mskp.model;

/**
 * Created by ACER on 13/08/2015.
 */
public class ReferalItem {

    private int id;
    private String id_member;
    private String nama;
    private String alamat;

    public ReferalItem(){

    }

    public void setId_member(String id_member) {
        this.id_member = id_member;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getId_member() {
        return id_member;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
