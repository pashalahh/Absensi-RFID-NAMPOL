/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;
/**
 *
 * @author LENOVO
 */
public class coba {

    public static void main(String[] args) {

        AuthService auth = new AuthService();

        auth.registerUser(
                "198273",
                "Pasha",
                "admin",
                "123"
        );

        System.out.println("User berhasil ditambahkan!");
    }

}
    
