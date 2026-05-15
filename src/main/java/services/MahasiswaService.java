/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import gui.Mahasiswa;
import dao.GenericDAO;
import objects.mahasiswa;
import com.mongodb.client.model.Filters;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.bson.conversions.Bson;
/**
 *
 * @author LENOVO
 */
public class MahasiswaService {
    
    // Inisialisasi GenericDAO khusus untuk entitas Karyawan
    // Menggunakan koleksi "karyawan" dan referensi Class Karyawan [3]
    private final GenericDAO<mahasiswa> DAO;
    
    public MahasiswaService() {
        this.DAO = new GenericDAO<>("mahasiswa", mahasiswa.class);
    }
    
    /**
     * 1.CREATE: Fungsi untuk menyimpan data karyawan baru ke MongoDB [2], [3]
     *
     * @param mahasiswaBaru
     */
    public void tambahMahasiswa (mahasiswa mahasiswaBaru) {
        DAO.save(mahasiswaBaru); // Memanggil insertOne melalui GenericDAO [3]
    }

    public void tambahMahasiswa(String uidRfid, String nimMahasiswa, String nama, String kelas, String noTelp) {
        mahasiswa mahasiswaBaru = new mahasiswa(uidRfid, nimMahasiswa, nama, kelas, noTelp);
        DAO.save(mahasiswaBaru); // Memanggil insertOne melalui GenericDAO [3]
    }
    
    /**
     * 2. READ (All): Fungsi untuk mengambil semua data karyawan [5], [6]
     */
    public void tampilkanDaftarKaryawan() {
        List<mahasiswa> daftar = DAO.findAll();
        System.out.println("--- Daftar Mahasiswa ---");
        for (mahasiswa m : daftar) {
            System.out.println(m.toString()); // Menggunakan format toString di sumber [7]
        }
    }
    
    /**
     * 2.READ (All): Fungsi untuk mengambil semua data karyawan [5], [6]
     *
     * @param panelTarget
     * @param key
     */
    public void tampilMahasiswa(JPanel panelTarget, String key) {
        //1. 
        // Menampilkan data berdasarkan request
        // key "null/kosong" = get all data
        // key "filled" = get specific data

        List<mahasiswa> daftarMahasiswa;
        if (key.isEmpty()) {
            //Mengambil data dari database menggunakan GenericDAO
            daftarMahasiswa = DAO.findAll();
        } else {
            //Mengambil data dari database menggunakan GenericDAO
            //berdasarkan kata kunci yang diketik
            daftarMahasiswa = cariMahasiswa(key);
        }
        // 2. Membersihkan panel target utama sebelum memuat data baru
        panelTarget.removeAll();

        // Mengubah layout panel target menjadi BorderLayout
        panelTarget.setLayout(new BorderLayout());
        // Mengatur warna background utama menjadi biru
        panelTarget.setBackground(new Color(68, 114, 196));

        // Membuat panel grid khusus untuk menampung kotak/card
        JPanel gridPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        gridPanel.setOpaque(false); // Transparan agar warna biru panelTarget terlihat
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Memberi jarak dari tepi layar
        
        // 3. Iterasi data dan menambahkannya ke panel grid
        try {
            for (mahasiswa m : daftarMahasiswa) {
                // Membuat panel 'Card' (box orange) untuk 1 karyawan
                // Layout 4 baris 1 kolom agar kolor berisi Nama,ID, Departemen, panel control 
                JPanel cardPanel = new JPanel(new GridLayout(4, 1, 0, 0));
                cardPanel.setBackground(new Color(237, 125, 49)); // Warna background orange

                // Memberikan garis tepi tipis membulat (rounded) dan padding/jarak ke dalam
                cardPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.MAGENTA, 1, true),
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)
                ));

                // Membuat Label Nama & Set warna teks jadi Putih
                JLabel lblNama = new JLabel("Nama: " + m.getNama());
                lblNama.setForeground(Color.WHITE);

                // Membuat Label ID Karyawan & Set warna teks jadi Putih
                JLabel lblNim = new JLabel("NIM Mahasiswa: " + m.getNimMahasiswa());
                lblNim.setForeground(Color.WHITE);

                // Membuat Label Departemen & Set warna teks jadi Putih
                JLabel lblKls = new JLabel("Kelas: " + m.getKelas());
                lblKls.setForeground(Color.WHITE);
                
                JLabel lblTlp = new JLabel("No Telpon: " + m.getNoTelp());
                lblTlp.setForeground(Color.WHITE);

                // Membuat panel kontrol 1 baris 2 kolom, berisi tombol edit dan hapus
                JPanel controlPanel = new JPanel(new GridLayout(1, 2, 20, 15));
                controlPanel.setBackground(new Color(237, 125, 49));

                JButton tombolEdit = new JButton("Edit");
                tombolEdit.setBackground(Color.ORANGE);
                tombolEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
                tombolEdit.addActionListener((ActionEvent e) -> {
                    Mahasiswa.txtUID.setText(m.getUidRfid());
                    Mahasiswa.txtNim.setText(m.getNimMahasiswa());
                    Mahasiswa.txtNim.setEnabled(false); 
                    Mahasiswa.txtNama.setText(m.getNama());
                    Mahasiswa.txtKls.setSelectedItem(m.getKelas());
                    Mahasiswa.txtNoTelp.setText(m.getNoTelp());
                    Mahasiswa.btnUpdate.setEnabled(true);
                    Mahasiswa.btnSave.setEnabled(false); 
                });
                JButton tombolDelete = new JButton("Delete");
                tombolDelete.setBackground(Color.RED);
                tombolDelete.setForeground(Color.WHITE);
                tombolDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
                tombolDelete.addActionListener((ActionEvent e) -> {
                    Object[] options = {"Ya, Hapus", "Batal"};
                    int choice = JOptionPane.showOptionDialog(
                            null, // Parent component
                            "Apakah Anda ingin menyimpan data "+m.getNama()+"?", // Message
                            "Konfirmasi Pengelolaan", // Title
                            JOptionPane.YES_NO_OPTION, // Option type
                            JOptionPane.QUESTION_MESSAGE, // Message type
                            null, // Custom icon (null uses default)
                            options, // The array of custom button text
                            options[0] // Default button focused
                    );

                    switch (choice) {
                        case JOptionPane.YES_OPTION -> hapusMahasiswa(m.getNimMahasiswa());
                        case JOptionPane.NO_OPTION -> System.out.println("User memilih: Batal");
                        default -> {
                        }
                    }
                });

                controlPanel.add(tombolEdit);
                controlPanel.add(tombolDelete);

                // Memasukkan label ke dalam cardPanel (box orange)
                cardPanel.add(lblNama);
                cardPanel.add(lblNim);
                cardPanel.add(lblKls);
                cardPanel.add(lblTlp);
                cardPanel.add(controlPanel);

                // Memasukkan cardPanel utuh ke dalam gridPanel
                gridPanel.add(cardPanel);
            }

            // Memasukkan gridPanel ke bagian ATAS (NORTH) dari panel target.
            panelTarget.add(gridPanel, BorderLayout.NORTH);

            // 4. Me-refresh panel agar perubahan muncul di GUI
            panelTarget.revalidate();
            panelTarget.repaint();
        } catch (Exception e) {
        }
    }
    
    /**
     * 3.READ (One): Mencari satu karyawan spesifik berdasarkan UID RFID [5],
     * [6] Sangat krusial untuk alur Tap Kartu pada Pertemuan 14 [8].
     *
     * @param key
     * @return
     */
    public List<mahasiswa> cariMahasiswa(String key) {
        List<Bson> filters = new ArrayList<>();
        // Get all fields from the Karyawan class
        for (Field field : mahasiswa.class.getDeclaredFields()) {
            // Skip the uidRfid field and non-string fields if necessary
            if (field.getName().equals("uidRfid")) {
                continue;
            }
            filters.add(Filters.regex(field.getName(), key, "i"));
        }
        // Search and return Karyawan objects directly
        List<mahasiswa> results = DAO.findMany(Filters.or(filters));
        return results;
    }
    
    /**
     * 4.UPDATE: Memperbarui data karyawan menggunakan filter Bson [5], [6]
     *
     * @param newK
     */
    public void updateMahasiswa(mahasiswa newm) {
        Bson filter = Filters.eq("nimMahasiswa", newm.getNimMahasiswa());
        mahasiswa m = DAO.findOne(filter);
        if (m != null) {
            DAO.update(filter, newm);
            Mahasiswa.showData("");
            JOptionPane.showMessageDialog(null, "Data berhasil diperbarui!");
        }
    }

    /**
     * 5.DELETE: Menghapus data karyawan dari database [5], [6]
     *
     * @param idK
     */
    public void hapusMahasiswa(String idK) {
        Bson filter = Filters.eq("nimMahasiswa", idK);
        DAO.delete(filter); // Menggunakan deleteOne [6]
        Mahasiswa.showData("");
        JOptionPane.showMessageDialog(null, "Data karyawan berhasil dihapus.");
    }
}
