/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import dao.GenericDAO;
import java.time.LocalDate;
import objects.logabsensi;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 * @author LENOVO
 */
public class LogAbsensiService {
    private final GenericDAO<logabsensi> logDAO = new GenericDAO<>("log_absensi", logabsensi.class);
    
    public void simpanLog(String hashedUid, LocalDateTime waktu, LocalDate tanggal,String nama, String status) {
        // Membuat objek LogAbsensi sesuai parameter di sumber [6]
        logabsensi log = new logabsensi(
            UUID.randomUUID().toString(), 
            hashedUid,
            LocalDateTime.now(),
            LocalDate.now(),
            nama,
            status
        );
        logDAO.save(log); // Menyimpan ke MongoDB [7]
    }
    
    /**
     * Fungsi untuk otomatis mengisi status "Tidak Hadir" bagi mahasiswa 
     * yang tidak melakukan tapping kartu sama sekali pada hari ini.
     * 
     * @param kodeKelas Filter kelas mahasiswa yang ingin dicek (contoh: "4A", "4C")
     */
    public void setSiswaTidakHadir(String kodeKelas) {
        dao.GenericDAO<objects.mahasiswa> mDAO = new dao.GenericDAO<>("mahasiswa", objects.mahasiswa.class);
        
        // 1. Ambil seluruh data mahasiswa berdasarkan kelas tertentu
        java.util.List<objects.mahasiswa> daftarSiswa = mDAO.findMany(com.mongodb.client.model.Filters.eq("kelas", kodeKelas));
        
        java.time.LocalDate hariIni = java.time.LocalDate.now();
        
        for (objects.mahasiswa m : daftarSiswa) {
            // 2. Gunakan org.bson.conversions.Bson (Koreksi package yang error)
            org.bson.conversions.Bson filterLog = com.mongodb.client.model.Filters.and(
                com.mongodb.client.model.Filters.eq("nama", m.getNama()),
                com.mongodb.client.model.Filters.eq("tanggal", hariIni)
            );
            
            java.util.List<objects.logabsensi> hasilCari = logDAO.findMany(filterLog);
            
            // 3. Jika list hasil cari kosong (size == 0), artinya seharian dia tidak melakukan tap kartu
            if (hasilCari == null || hasilCari.isEmpty()) {
                simpanLog(m.getUidRfid(), java.time.LocalDateTime.now(), hariIni, m.getNama(), "Tidak Hadir");
            }
        }
    }
    public boolean sudahAbsenHariIni(String hashedUid, String status) {
        java.time.LocalDate hariIni = java.time.LocalDate.now();
        
        // Menentukan batasan waktu dari jam 00:00:00 sampai 23:59:59 hari ini
        java.time.LocalDateTime awalHari = hariIni.atStartOfDay();
        java.time.LocalDateTime akhirHari = hariIni.atTime(java.time.LocalTime.MAX);
        
        // KOREKSI TOTAL: Mengubah "hashedUid" menjadi "uidRfid" sesuai dengan kolom di MongoDB Compass Anda
        org.bson.conversions.Bson filter = com.mongodb.client.model.Filters.and(
            com.mongodb.client.model.Filters.eq("uidRfid", hashedUid),
            com.mongodb.client.model.Filters.eq("status", status),
            com.mongodb.client.model.Filters.gte("waktu", awalHari),
            com.mongodb.client.model.Filters.lte("waktu", akhirHari)
        );
        
        java.util.List<logabsensi> hasil = logDAO.findMany(filter);
        return hasil != null && !hasil.isEmpty();
    }

    
    // PERBAIKAN: Menyimpan status aktif sistem absensi secara global di memori harian
    private static String statusSistemAktif = "Masuk"; 

    public static void setStatusSistemAktif(String status) {
        statusSistemAktif = status;
    }

    public static String getStatusSistemAktif() {
        return statusSistemAktif;
    }
    
    // ... (sisa kode simpanLog, setSiswaTidakHadir, dll tetap sama) ...
}



