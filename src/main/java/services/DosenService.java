/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import PanelAdmin.DataDosen;
import dao.GenericDAO;
import objects.dosen;
import util.Security;
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
 * @author ADVAN
 */
public class DosenService {
    
    // Inisialisasi GenericDAO khusus untuk entitas Dosen
    private final GenericDAO<dosen> DAO;
    
    public DosenService() {
        this.DAO = new GenericDAO<>("dosen", dosen.class);
    }
    
    /**
     * 1. CREATE: Fungsi untuk menyimpan data dosen baru ke MongoDB
     * 
     * @param d Objek entitas dosen
     */
    public void tambahDosen(dosen d) {
        // Otomatis lakukan hashing SHA-256 pada password mentah sebelum disimpan
        d.setPassword(Security.getHash(d.getPassword(), Security.SHA_256));
        DAO.save(d);
        JOptionPane.showMessageDialog(null, swing.I18nService.get("msg.dsn.success"));
    }
    
    /**
     * 2. READ (All to Console): Debugging atau mencetak daftar dosen ke log sistem
     */
    public void tampilkanDaftarDosen() {
        List<dosen> daftar = DAO.findAll();
        System.out.println("--- Daftar Dosen ---");
        for (dosen d : daftar) {
            System.out.println(d.toString());
        }
    }
    
    /**
     * 2. READ (Dynamic Card Panel): Membuat dynamic container card list ke UI JPanel target
     * 
     * @param panelTarget Komponen jPanel4 dari Dosen.java
     * @param key Kata kunci filter pencarian
     */
    public void tampilDosen(JPanel panelTarget, String key) {
        List<dosen> daftarDosen;
        if (key.isEmpty()) {
            daftarDosen = DAO.findAll();
        } else {
            daftarDosen = cariDosen(key);
        }
        
        // Membersihkan panel kontainer utama sebelum memuat komponen baru
        panelTarget.removeAll();
        panelTarget.setLayout(new BorderLayout());
        
        // Mengatur latar belakang panel sesuai skema UI
        panelTarget.setBackground(new Color(240, 240, 240));

        // Membuat layout grid dengan 3 kolom (menyamping) untuk memajang kartu data
        JPanel gridPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        gridPanel.setOpaque(false);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        try {
            for (dosen d : daftarDosen) {
                // Layout 4 baris, 1 kolom (3 Label Informasi + 1 Control Panel Button)
                JPanel cardPanel = new JPanel(new GridLayout(4, 1, 0, 5));
                cardPanel.setBackground(Color.WHITE);

                // Membuat outline border ungu rounded
                cardPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.MAGENTA, 1, true),
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)
                ));

                JLabel lblNip = new JLabel(swing.I18nService.get("lbl.dsn.nip") + ": " + d.getNipDosen());
                lblNip.setForeground(Color.BLACK);

                JLabel lblNama = new JLabel(swing.I18nService.get("lbl.dsn.nama") + ": " + d.getNama());
                lblNama.setForeground(Color.BLACK);

                JLabel lblUser = new JLabel(swing.I18nService.get("lbl.dsn.username") + ": " + d.getUsername());
                lblUser.setForeground(Color.BLACK);

                // Membuat area tombol aksi aksi Edit & Delete
                JPanel controlPanel = new JPanel(new GridLayout(1, 2, 20, 15));
                controlPanel.setBackground(Color.WHITE);

                JButton tombolEdit = new JButton("Edit");
                tombolEdit.setText(swing.I18nService.get("btn.dsn.edit"));
                tombolEdit.setBackground(Color.ORANGE);
                tombolEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
                tombolEdit.addActionListener((ActionEvent e) -> {
                    // Isi kembali form input static di Dosen.java saat kartu di-klik edit
                    DataDosen.txtNIP.setText(d.getNipDosen());
                    DataDosen.txtNIP.setEnabled(false); // Kunci primary key NIP agar tidak diubah
                    DataDosen.txtNama.setText(d.getNama());
                    DataDosen.txtUsername.setText(d.getUsername());
                    DataDosen.jButton2.setEnabled(true); // Aktifkan tombol Update
                });

                JButton tombolDelete = new JButton("Delete");
                tombolDelete.setText(swing.I18nService.get("btn.dsn.delete"));
                tombolDelete.setBackground(Color.RED);
                tombolDelete.setForeground(Color.WHITE);
                tombolDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
                tombolDelete.addActionListener((ActionEvent e) -> {
                    Object[] options = {"Ya, Hapus", "Batal"};
                    int choice = JOptionPane.showOptionDialog(
                        null,
                        swing.I18nService.get("msg.dsn.delete.confirm") + " " + d.getNama() + "?",
                        swing.I18nService.get("msg.dsn.delete.title"),
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{swing.I18nService.get("btn.yes"), swing.I18nService.get("btn.no")},
                        swing.I18nService.get("btn.yes")
                    );

                    if (choice == JOptionPane.YES_OPTION) {
                        hapusDosen(d.getNipDosen());
                    }
                });

                controlPanel.add(tombolEdit);
                controlPanel.add(tombolDelete);

                // Susun komponen ke dalam kartu dosen
                cardPanel.add(lblNip);
                cardPanel.add(lblNama);
                cardPanel.add(lblUser);
                cardPanel.add(controlPanel);

                // Masukkan kartu utuh ke grid kontainer
                gridPanel.add(cardPanel);
            }

            panelTarget.add(gridPanel, BorderLayout.NORTH);
            
            // Re-render antarmuka grafis
            panelTarget.revalidate();
            panelTarget.repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 3. READ (Filtered Match): Mencari data dosen berdasarkan regex pencarian dinamis
     * 
     * @param key Kata kunci input text
     * @return List data dosen yang cocok
     */
    public List<dosen> cariDosen(String key) {
        List<Bson> filters = new ArrayList<>();
        for (Field field : dosen.class.getDeclaredFields()) {
            if (field.getName().equals("password") || field.getName().equals("lastLogin")) {
                continue; // Lewati field sensitif saat filter pencarian text biasa
            }
            filters.add(Filters.regex(field.getName(), key, "i"));
        }
        return DAO.findMany(Filters.or(filters));
    }
    
    /**
     * 4. UPDATE: Memperbarui data informasi dosen pada dokumen MongoDB
     * 
     * @param newD Objek dosen dengan data baru
     */
    public void updateDosen(dosen newD) {
        Bson filter = Filters.eq("nipDosen", newD.getNipDosen());
        dosen oldD = DAO.findOne(filter);
        if (oldD != null) {
            // Jika password form dikosongkan, gunakan password lama yang sudah terenkripsi di DB
            if (newD.getPassword() == null || newD.getPassword().trim().isEmpty()) {
                newD.setPassword(oldD.getPassword());
            } else {
                // Jika user menginput password baru, lakukan hashing SHA-256 ulang
                newD.setPassword(Security.getHash(newD.getPassword(), Security.SHA_256));
            }
            
            // Pertahankan data lastLogin yang lama
            newD.setLastLogin(oldD.getLastLogin());

            DAO.update(filter, newD);
            DataDosen.showData("");
            JOptionPane.showMessageDialog(null, swing.I18nService.get("msg.dsn.success"));
        }
    }

    /**
     * 5. DELETE: Menghapus data dokumen dosen dari koleksi MongoDB
     * 
     * @param nip NIP dosen yang akan dihapus
     */
    public void hapusDosen(String nip) {
        Bson filter = Filters.eq("nipDosen", nip);
        DAO.delete(filter);
        DataDosen.showData("");
        JOptionPane.showMessageDialog(null, swing.I18nService.get("msg.dsn.success"));
    }
}