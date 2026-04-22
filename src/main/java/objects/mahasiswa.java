/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

/**
 *
 * @author LENOVO
 */
public class mahasiswa {
    private String uidRfid;
    private String nimMahasiswa;
    private String nama;
    private String kelas;
    private String noTelp;
    
    public mahasiswa() {
    }

    public mahasiswa(String uidRfid, String nimMahasiswa, String nama, String kelas, String noTelp) {
        this.uidRfid = uidRfid;
        this.nimMahasiswa = nimMahasiswa;
        this.nama = nama;
        this.kelas = kelas;
        this.noTelp = noTelp;
    }

    @Override
    public String toString() {
        return "mahasiswa{" + 
                "uidRfid=" + uidRfid + 
                ", nimMahasiswa=" + nimMahasiswa + 
                ", nama=" + nama + 
                ", kelas=" + kelas + 
                ", noTelp=" + noTelp + '}';
    }

    public String getUidRfid() {
        return uidRfid;
    }

    public void setUidRfid(String uidRfid) {
        this.uidRfid = uidRfid;
    }

    public String getNimMahasiswa() {
        return nimMahasiswa;
    }

    public void setNimMahasiswa(String nimMahasiswa) {
        this.nimMahasiswa = nimMahasiswa;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }
    
    
    
}
