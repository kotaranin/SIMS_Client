/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.form;

import communication.Communication;
import domain.City;
import domain.Company;
import enums.Mode;
import static enums.Mode.INSERT;
import static enums.Mode.UPDATE;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JOptionPane;
import validators.CompanyValidator;
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
            insertCompanyForm.setTitle("Ažuriraj kompaniju");
        } else {
            insertCompanyForm.setTitle("Dodaj kompaniju");
        }
        insertCompanyForm.setLocationRelativeTo(insertCompanyForm.getParent());
        insertCompanyForm.setVisible(true);
    }

    private void closeInsertCompanyForm() {
        insertCompanyForm.dispose();
    }

    private void addActionListeners() {
        insertCompanyForm.saveAddActionListener((ActionEvent e) -> {
            try {
                String name = insertCompanyForm.getTxtName().getText();
                String address = insertCompanyForm.getTxtAdress().getText();
                City city = (City) insertCompanyForm.getComboCity().getSelectedItem();
                switch (mode) {
                    case INSERT -> {
                        Company c = new Company(null, name, address, city);
                        new CompanyValidator().checkElementaryContraints(c);
                        communication.insertCompany(c);
                        JOptionPane.showMessageDialog(insertCompanyForm, "Sistem je zapamtio kompaniju.", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
                        closeInsertCompanyForm();
                    }
                    case UPDATE -> {
                        company.setName(name);
                        company.setAddress(address);
                        company.setCity(city);
                        new CompanyValidator().checkElementaryContraints(company);
                        communication.updateCompany(company);
                        JOptionPane.showMessageDialog(insertCompanyForm, "Sistem je zapamtio kompaniju.", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
                        closeInsertCompanyForm();
                    }
                    default ->
                        throw new AssertionError();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(insertCompanyForm, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
