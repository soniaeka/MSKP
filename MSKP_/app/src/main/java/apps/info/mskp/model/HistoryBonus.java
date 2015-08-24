package apps.info.mskp.model;

/**
 * Created by Bustomi Raharjo on 14/08/2015.
 */
public class HistoryBonus {

    private String id;
    private String tanggal;
    private String jumlah;
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ID\t:\t"+id+
                "\nJumlah\t:\t"+jumlah+
                "\nTanggal\t:\t"+tanggal+
                "\nStatus\t:\t"+status;
    }
}
