/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

/**
 *
 * @author LENOVO
 */
public class dosen {
    
    private String uidRfid;
    private String nipDosen;
    private String nama;
    private String noTelp;
    
    public dosen (){
    }

    public dosen(String uidRfid, String nipDosen, String nama, String noTelp) {
        this.uidRfid = uidRfid;
        this.nipDosen = nipDosen;
        this.nama = nama;
        this.noTelp = noTelp;
    }

    @Override
    public String toString() {
        return "dosen{" + 
                "uidRfid=" + uidRfid + 
                ", nipDosen=" + nipDosen + 
                ", nama=" + nama + 
                ", noTelp=" + noTelp + '}';
    }

    public String getUidRfid() {
        return uidRfid;
    }

    public void setUidRfid(String uidRfid) {
        this.uidRfid = uidRfid;
    }

    public String getNipDosen() {
        return nipDosen;
    }

    public void setNipDosen(String nipDosen) {
        this.nipDosen = nipDosen;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }
    
    
    
}
