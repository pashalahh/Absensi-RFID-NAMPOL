/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

/**
 *
 * @author LENOVO
 */
public class logabsensi {
    private int idLog;
    private String uidRfid;
    private String waktu;
    private String tanggal;
    private String nama;
    private String status;

    public logabsensi() {
    }

    public logabsensi(int idLog, String uidRfid, String waktu, String tanggal, String nama, String status) {
        this.idLog = idLog;
        this.uidRfid = uidRfid;
        this.waktu = waktu;
        this.tanggal = tanggal;
        this.nama = nama;
        this.status = status;
    }

    @Override
    public String toString() {
        return "logabsensi{" +
                "idLog=" + idLog +
                ", uidRfid=" + uidRfid +
                ", waktu=" + waktu +
                ", tanggal=" + tanggal +
                ", nama=" + nama +
                ", status=" + status +
                '}';
    }

    // Getter & Setter
    public int getIdLog() {
        return idLog;
    }

    public void setIdLog(int idLog) {
        this.idLog = idLog;
    }

    public String getUidRfid() {
        return uidRfid;
    }

    public void setUidRfid(String uidRfid) {
        this.uidRfid = uidRfid;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
