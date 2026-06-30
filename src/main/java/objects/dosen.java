/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objects;

import java.time.LocalDateTime;

/**
 *
 * @author LENOVO
 */
public class dosen {
    
    private String nipDosen;
    private String nama;
    private String username;
    private String password;
    private LocalDateTime lastLogin;
    
    public dosen (){
    }

    public dosen(String nipDosen, String nama, String username, String password, LocalDateTime lastLogin) {
        this.nipDosen = nipDosen;
        this.nama = nama;
        this.username = username;
        this.password= password;
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return "dosen{" + 
                ", nipDosen=" + nipDosen + 
                ", nama=" + nama + 
                ", username=" + username +
                ", password=" + password +'}';
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
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
}

