/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import objects.dosen;
import dao.GenericDAO;
import PanelAdmin.DashboardAdmin; // Halaman tujuan
import PanelAdmin.Login;
import util.Security;
import com.mongodb.client.model.Filters;
import java.awt.Frame;
import javax.swing.JOptionPane;
import java.time.LocalDateTime;
import util.Security;

/**
 *
 * @author LENOVO
 */
public class AuthService {
    // Inisialisasi DAO untuk koleksi "users" [8]
    private final GenericDAO<dosen> userDAO = new GenericDAO<>("dosen", dosen.class);

    /**
     * Melakukan proses login dengan memvalidasi kredensial (Sub-CPMK 4) [5].
     *
     * @param username
     * @param plainPassword
     * @param loginPage
     */
    public void login(String username, String plainPassword, Login loginPage) {
        // 1. Mengubah password input menjadi hash SHA-256 untuk keamanan [2]
        String hashedInput = Security.getHash(plainPassword, Security.SHA_256);

        // 2. Mencari user di database berdasarkan username DAN password hash [7, 9]
        dosen user = userDAO.findOne(Filters.and(
                Filters.eq("username", username),
                Filters.eq("password", hashedInput)
        ));

        // 3. Validasi hasil pencarian
        if (user != null) {
            // Update waktu login terakhir
            user.setLastLogin(LocalDateTime.now());
            userDAO.update(Filters.eq("username", username), user);

            // KUNCI UTAMA: Menyimpan data objek dosen yang berhasil login ke variabel static global di class Login
            PanelAdmin.Login.dosenAktif = user;

            // Berhasil: Masuk ke Halaman Admin
            JOptionPane.showMessageDialog(null, "Selamat Datang, " + user.getNama());
            DashboardAdmin admPage = new DashboardAdmin();
            admPage.setLocationRelativeTo(null); 
            admPage.setVisible(true);
            admPage.setExtendedState(Frame.MAXIMIZED_BOTH); 
            loginPage.setVisible(false); 
        } else {
            // Gagal: Notifikasi Error
            JOptionPane.showMessageDialog(null,
                    "Username atau Password Salah!",
                    "Login Gagal",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metode untuk menambahkan user/admin baru ke database. Implementasi sesuai
     * target SPRINT 3 untuk pengamanan kredensial [2].
     *
     * @param fullname Nama lengkap user
     * @param username Username untuk login
     * @param plainPassword Password mentah (akan di-hash otomatis)
     */
    public void registerUser(String nipDosen, String nama, String username, String plainPassword) {
        // 1. Proses Hashing: Mengamankan password mentah menggunakan SHA-256 [1]
        String hashedPassword = Security.getHash(plainPassword, Security.SHA_256);

        // 2. Instansiasi Objek: Membuat objek User baru dengan password yang sudah di-hash
        // lastLogin disetel null karena user baru belum pernah masuk sistem
        dosen newUser = new dosen(nipDosen,nama, username, hashedPassword, null);

        // 3. Operasi Create: Menyimpan dokumen user ke koleksi MongoDB melalui GenericDAO [3], [4]
        try {
            userDAO.save(newUser); // Memanggil insertOne melalui GenericDAO [5]
        } catch (Exception e) {
            // Standar Debugging: Mengidentifikasi error log secara mandiri [6]
            JOptionPane.showMessageDialog(null, "Gagal mendaftarkan user: " + e.getMessage());
        }
    }

}
