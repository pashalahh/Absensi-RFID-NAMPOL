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
}