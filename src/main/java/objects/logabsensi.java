/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author LENOVO
 */
public class logabsensi {
    private int idLog;
    private String uidRfid;
    private LocalDateTime waktu;
    private LocalDate tanggal;
    private String nama;
    private String status;

    public logabsensi() {
    }

    public logabsensi(int idLog, String uidRfid, LocalDateTime waktu, LocalDate tanggal, String nama, String status) {
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

    public LocalDateTime getWaktu() {
        return waktu;
    }

    public void setWaktu(LocalDateTime waktu) {
        this.waktu = waktu;
    }

    public LocalDate getTanggal() {
        return tanggal;
    }

    public void setTanggal(LocalDate tanggal) {
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
