/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import communication.Communication;
import domain.Country;
import enums.Mode;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import view.InsertCountryForm;

/**
 *
 * @author kotar
 */
public class InsertCountryController {

    private final InsertCountryForm insertCountryForm;
    private final Communication communication;
    private final Mode mode;
    private final Country country;

    public InsertCountryController(InsertCountryForm insertCountryForm, Country country, Mode mode) {
        this.insertCountryForm = insertCountryForm;
        this.communication = Communication.getInstance();
        this.country = country;
        this.mode = mode;
        addActionListeners();
    }

    public void openInsertCountryForm() {
        if (mode == Mode.UPDATE) {
            insertCountryForm.setTitle("Azuriraj drzavu");
            insertCountryForm.getTxtCountryName().setText(country.getName());
        } else {
            insertCountryForm.setTitle("Unesi drzavu");
        }
        insertCountryForm.setLocationRelativeTo(insertCountryForm.getParent());
        insertCountryForm.setVisible(true);
    }

    public void closeInsertCountryForm() {
        insertCountryForm.dispose();
    }

    private void addActionListeners() {
        insertCountryForm.saveAddActionListener((ActionEvent e) -> {
            switch (mode) {
                case INSERT -> {
                    try {
                        Country c = new Country(null, insertCountryForm.getTxtCountryName().getText());
                        communication.insertCountry(c);
                        JOptionPane.showMessageDialog(insertCountryForm, "Sistem je zapamtio drzavu.", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                        closeInsertCountryForm();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(insertCountryForm, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                    }
                }
                case UPDATE -> {
                    try {
                        country.setName(insertCountryForm.getTxtCountryName().getText());
                        System.out.println(country);
                        communication.updateCountry(country);
                        JOptionPane.showMessageDialog(insertCountryForm, "Sistem je zapamtio drzavu.", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                        closeInsertCountryForm();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(insertCountryForm, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                    }
                }

                default ->
                    throw new AssertionError();
            }
        });
    }

}
