/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.form;

import communication.Communication;
import domain.City;
import domain.Company;
import enums.Mode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import view.forms.InsertCompanyForm;

/**
 *
 * @author kotar
 */
public class InsertCompanyController {

    private final InsertCompanyForm insertCompanyForm;
    private final Company company;
    private final Mode mode;
    private final Communication communication;

    public InsertCompanyController(InsertCompanyForm insertCompanyForm, Company company, Mode mode) {
        this.insertCompanyForm = insertCompanyForm;
        this.company = company;
        this.mode = mode;
        this.communication = Communication.getInstance();
        addActionListeners();
    }

    public void prepareForm() throws Exception {
        List<City> cities = communication.getAllCities();
        for (City city : cities) {
            insertCompanyForm.getComboCity().addItem(city);
        }
    }

    public void openInsertCompanyForm() throws Exception {
        prepareForm();
        if (mode == Mode.UPDATE) {
            insertCompanyForm.getTxtName().setText(company.getName());
            insertCompanyForm.getTxtAdress().setText(company.getAddress());
            insertCompanyForm.getComboCity().getModel().setSelectedItem(company.getCity());
            insertCompanyForm.setTitle("Azuriraj kompaniju");
        } else {
            insertCompanyForm.setTitle("Kreiraj kompaniju");
        }
        insertCompanyForm.setLocationRelativeTo(insertCompanyForm.getParent());
        insertCompanyForm.setVisible(true);
    }

    private void closeInsertCompanyForm() {
        insertCompanyForm.dispose();
    }

    private void addActionListeners() {
        insertCompanyForm.getTxtName().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        insertCompanyForm.getTxtAdress().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        insertCompanyForm.getComboCity().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });
        insertCompanyForm.saveAddActionListener((ActionEvent e) -> {
            String name = insertCompanyForm.getTxtName().getText();
            String address = insertCompanyForm.getTxtAdress().getText();
            City city = (City) insertCompanyForm.getComboCity().getSelectedItem();
            switch (mode) {
                case INSERT -> {
                    try {
                        Company c = new Company(null, name, address, city);
                        communication.insertCompany(c);
                        JOptionPane.showMessageDialog(insertCompanyForm, "Sistem je zapamtio kompaniju.", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                        closeInsertCompanyForm();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(insertCompanyForm, ex.getMessage(), "Greska", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                case UPDATE -> {
                    try {
                        company.setName(name);
                        company.setAddress(address);
                        company.setCity(city);
                        communication.updateCompany(company);
                        JOptionPane.showMessageDialog(insertCompanyForm, "Sistem je zapamtio kompaniju.", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                        closeInsertCompanyForm();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(insertCompanyForm, ex.getMessage(), "Greska", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                default ->
                    throw new AssertionError();
            }
        });
    }
    
    private void search() {
        
    }

}
