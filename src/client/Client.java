/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client;

import coordinator.Coordinator;

/**
 *
 * @author kotar
 */
public class Client {
    public static void main(String[] args) {
        Coordinator.getInstance().openClientForm();
    }
}
