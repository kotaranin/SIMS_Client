/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.panel;

import communication.Communication;
import coordinator.Coordinator;
import domain.City;
import domain.Company;
import enums.Mode;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import table_models.CompanyTM;
import view.panels.CompanyPanel;

/**
 *
 * @author kotar
 */
public class CompanyPanelController {

    private final CompanyPanel companyPanel;
    private final Communication communication;
    private final Coordinator coordinator;
    private boolean initialized;

    public CompanyPanelController(CompanyPanel companyPanel) {
        this.companyPanel = companyPanel;
        this.communication = Communication.getInstance();
        this.coordinator = Coordinator.getInstance();
        this.initialized = false;
        addActionListeners();
    }

    public void preparePanel() throws Exception {
        List<City> cities = communication.getAllCities();
        List<Company> companies = communication.getAllCompanies();
        companyPanel.getComboCity().removeAllItems();
        companyPanel.getComboCity().addItem(new City(null, "Svi gradovi", null));
        for (City city : cities) {
            companyPanel.getComboCity().addItem(city);
        }
        initialized = true;
        fillCompanies(companies);
    }

    private void fillCompanies(List<Company> companies) {
        companyPanel.getTblCompany().setModel(new CompanyTM(companies));
    }

    private void search() {
        try {
            String name = companyPanel.getTxtName().getText().trim();
            String address = companyPanel.getTxtAddress().getText().trim();
            City city = (City) companyPanel.getComboCity().getModel().getSelectedItem();
            Company company = new Company(null, name, address, city);
            fillCompanies(communication.searchCompanies(company));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(companyPanel, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addActionListeners() {
        companyPanel.getTblCompany().getSelectionModel().addListSelectionListener((e) -> {
            if (!e.getValueIsAdjusting()) {
                boolean selected = companyPanel.getTblCompany().getSelectedRow() != -1;
                companyPanel.getBtnUpdateCompany().setEnabled(selected);
            }
        });
        companyPanel.getTxtName().getDocument().addDocumentListener(new DocumentListener() {
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
        companyPanel.getTxtAddress().getDocument().addDocumentListener(new DocumentListener() {
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
        companyPanel.getComboCity().addActionListener((ActionEvent e) -> {
            if (initialized) {
                search();
            }
        });
        companyPanel.insertCompanyAddActionListener((ActionEvent e) -> {
            try {
                coordinator.openInsertCompanyForm(null, Mode.INSERT);
                fillCompanies(communication.getAllCompanies());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(companyPanel, ex.getMessage(), "Greška", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        companyPanel.updateCompanyAddActionListener((ActionEvent e) -> {
            try {
                int row = companyPanel.getTblCompany().getSelectedRow();
                Company company = (Company) ((CompanyTM) companyPanel.getTblCompany().getModel()).getValueAt(row, 0);
                coordinator.openInsertCompanyForm(company, Mode.UPDATE);
                fillCompanies(communication.getAllCompanies());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(companyPanel, ex.getMessage(), "Greška", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

}
