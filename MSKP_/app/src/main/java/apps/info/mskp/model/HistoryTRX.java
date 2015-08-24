package apps.info.mskp.model;

/**
 * Created by Bustomi Raharjo on 12/08/2015.
 */
public class HistoryTRX {

    private String pin;
    private String jenis;
    private String tanggal;
    private String status;

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PIN TRX\t:\t"+pin+
                "\nJenis\t:\t"+jenis+
                "\nTanggal\t:\t"+tanggal+
                "\nStatus\t:\t"+status;
    }
}
