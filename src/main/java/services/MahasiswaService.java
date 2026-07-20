/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

//import gui.Mahasiswa;
import PanelAdmin.DataMahasiswa;
import dao.GenericDAO;
import objects.mahasiswa;
import util.Security;
import util.EncryptionUtils;
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
    public void tambahMahasiswa(mahasiswa m) {

    m.setNimMahasiswa(
        EncryptionUtils.encrypt(m.getNimMahasiswa())
    );

    m.setNoTelp(
        EncryptionUtils.encrypt(m.getNoTelp())
    );

    DAO.save(m);
}

    public void tambahMahasiswa(String uidRfid, String nimMahasiswa, String nama, String kelas, String noTelp) {
        String hashedUID = Security.getHash(uidRfid, Security.SHA_256);
        mahasiswa mahasiswaBaru = new mahasiswa(hashedUID, EncryptionUtils.encrypt(nimMahasiswa), nama, kelas,  EncryptionUtils.encrypt(noTelp));
        DAO.save(mahasiswaBaru); // Memanggil insertOne melalui GenericDAO [3]
    }
    
    /**
     * 2. READ (All): Fungsi untuk mengambil semua data karyawan [5], [6]
     */
    public void tampilkanDaftarMahasiswa() {
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
        List<mahasiswa> daftarMahasiswa;
        if (key.isEmpty()) {
            daftarMahasiswa = DAO.findAll();
        } else {
            daftarMahasiswa = cariMahasiswa(key);
        }
        
        panelTarget.removeAll();
        panelTarget.setLayout(new BorderLayout());
        panelTarget.setBackground(new Color(153, 255, 204));

        JPanel gridPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        gridPanel.setOpaque(false);
        gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        try {
            for (mahasiswa m : daftarMahasiswa) {
                JPanel cardPanel = new JPanel(new GridLayout(5, 1, 0, 5));
                cardPanel.setBackground(Color.WHITE);

                cardPanel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.MAGENTA, 1, true),
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)
                ));

                // Dekripsi data untuk ditampilkan di kartu visual GUI
                String nimDecrypted = "";
                String telpDecrypted = "";
                try {
                    nimDecrypted = EncryptionUtils.decrypt(m.getNimMahasiswa());
                    telpDecrypted = EncryptionUtils.decrypt(m.getNoTelp());
                } catch (Exception e) {
                    // Jika data kosong/corrupt, biarkan kosong
                }

                JLabel lblNama = new JLabel(swing.I18nService.get("lbl.mhs.nama") + ": " + m.getNama());
                lblNama.setForeground(Color.BLACK);

                JLabel lblNim = new JLabel(swing.I18nService.get("lbl.mhs.nim") + ": " + nimDecrypted);
                lblNim.setForeground(Color.BLACK);

                JLabel lblKls = new JLabel(swing.I18nService.get("lbl.mhs.kelas") + ": " + m.getKelas());
                lblKls.setForeground(Color.BLACK);
                
                JLabel lblTlp = new JLabel(swing.I18nService.get("lbl.mhs.notelp") + ": " + telpDecrypted);
                lblTlp.setForeground(Color.BLACK);

                JPanel controlPanel = new JPanel(new GridLayout(1, 2, 20, 15));
                controlPanel.setBackground(Color.WHITE);

                JButton tombolEdit = new JButton("Edit");
                tombolEdit.setText(swing.I18nService.get("btn.mhs.update"));
                tombolEdit.setBackground(Color.ORANGE);
                tombolEdit.setCursor(new Cursor(Cursor.HAND_CURSOR));
                
                // Variabel efektif final untuk action listener
                final String finalNim = nimDecrypted;
                final String finalTelp = telpDecrypted;
                
                tombolEdit.addActionListener((ActionEvent e) -> {
                    DataMahasiswa.txtUID.setText(m.getUidRfid());
                    DataMahasiswa.txtNim.setText(finalNim);
                    DataMahasiswa.txtNim.setEnabled(false); 
                    DataMahasiswa.txtNama.setText(m.getNama());
                    DataMahasiswa.txtKls.setSelectedItem(m.getKelas());
                    DataMahasiswa.txtNoTelp.setText(finalTelp);
                    DataMahasiswa.btnUpdate.setEnabled(true);
                    DataMahasiswa.btnSave.setEnabled(false); 
                });

                JButton tombolDelete = new JButton("Delete");
                tombolDelete.setText("Delete");
                tombolDelete.setBackground(Color.RED);
                tombolDelete.setForeground(Color.WHITE);
                tombolDelete.setCursor(new Cursor(Cursor.HAND_CURSOR));
                tombolDelete.addActionListener((ActionEvent e) -> {
                    Object[] options = {"Ya, Hapus", "Batal"};
                    int choice = JOptionPane.showOptionDialog(
                            null, 
                            swing.I18nService.get("msg.mhs.delete.confirm") + " " + m.getNama() + "?", 
                            swing.I18nService.get("lbl.mhs.title"), 
                            JOptionPane.YES_NO_OPTION, 
                            JOptionPane.QUESTION_MESSAGE, 
                            null, 
                            new Object[]{swing.I18nService.get("btn.yes"), swing.I18nService.get("btn.no")}, 
                            swing.I18nService.get("btn.yes")
                    );

                    if (choice == JOptionPane.YES_OPTION) {
                        // JIKA KOSONG: Cegat langsung dan kirim tanda "KOSONG_TERPAKSA" seperti cara data dosen
                        if (finalNim.isEmpty() || m.getNama() == null || m.getNama().trim().isEmpty()) {
                            hapusMahasiswa("KOSONG_TERPAKSA");
                        } else {
                            // Kirim NIM dalam bentuk plain text biasa, sama persis dengan d.getNipDosen()
                            hapusMahasiswa(finalNim);
                        }
                    }
                });

                controlPanel.add(tombolEdit);
                controlPanel.add(tombolDelete);

                cardPanel.add(lblNama);
                cardPanel.add(lblNim);
                cardPanel.add(cardPanel.add(lblKls));
                cardPanel.add(lblTlp);
                cardPanel.add(controlPanel);

                gridPanel.add(cardPanel);
            }

            panelTarget.add(gridPanel, BorderLayout.NORTH);
            panelTarget.revalidate();
            panelTarget.repaint();
        } catch (Exception e) {
            e.printStackTrace();
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
        String encryptedNim = EncryptionUtils.encrypt(newm.getNimMahasiswa());
        String encryptedNoTelp = EncryptionUtils.encrypt(newm.getNoTelp());

        Bson filter = Filters.eq("nimMahasiswa", encryptedNim);
        mahasiswa m = DAO.findOne(filter);
        if (m != null) {

        // simpan kembali dalam bentuk terenkripsi
        newm.setNimMahasiswa(encryptedNim);
        newm.setNoTelp(encryptedNoTelp);

        DAO.update(filter, newm);

        DataMahasiswa.showData("");
        JOptionPane.showMessageDialog(null, swing.I18nService.get("msg.update.success"));
    }
}

    /**
     * 5.DELETE: Menghapus data karyawan dari database [5], [6]
     *
     * @param idK
     */
    public void hapusMahasiswa(String idK) {
        Bson filter;
        
        // Mengikuti struktur logika DosenService untuk mendeteksi kartu hantu/kosong
        if (idK == null || idK.trim().isEmpty() || idK.equals("KOSONG_TERPAKSA")) {
            filter = Filters.or(
                Filters.eq("nimMahasiswa", ""),
                Filters.eq("nama", ""),
                Filters.exists("nimMahasiswa", false)
            );
        } else {
            // Lakukan enkripsi di sini sebelum menembak database MongoDB
            filter = Filters.eq("nimMahasiswa", EncryptionUtils.encrypt(idK));
        }
        
        DAO.delete(filter); // Menggunakan deleteOne
        DataMahasiswa.showData("");
        JOptionPane.showMessageDialog(null, swing.I18nService.get("msg.delete.success"));
    }
    
    /**
     * Fungsi kustom untuk mencari satu data mahasiswa secara spesifik 
     * berdasarkan nilai hash UID RFID untuk kebutuhan alur menu Absensi.
     * 
     * @param hashedUID Nilai UID RFID yang sudah di-hash SHA-256
     * @return Objek mahasiswa jika ditemukan, atau null jika tidak terdaftar
     */
    public mahasiswa cariMahasiswaSpesifikUID(String uid) {
        return DAO.findOne(com.mongodb.client.model.Filters.eq("uidRfid", uid));
    }
}
