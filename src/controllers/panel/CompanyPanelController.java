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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
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

    public CompanyPanelController(CompanyPanel companyPanel) {
        this.companyPanel = companyPanel;
        this.communication = Communication.getInstance();
        this.coordinator = Coordinator.getInstance();
        addActionListeners();
    }

    public void preparePanel() throws Exception {
        List<City> cities = communication.getAllCities();
        List<Company> companies = communication.getAllCompanies();
        for (City city : cities) {
            companyPanel.getComboCity().addItem(city);
        }
        fillCompanies(companies);
    }

    private void fillCompanies(List<Company> companies) {
        companyPanel.getTblCompany().setModel(new CompanyTM(companies));
    }

    private void addActionListeners() {
        companyPanel.getTblCompany().getSelectionModel().addListSelectionListener((e) -> {
            if (!e.getValueIsAdjusting()) {
                boolean selected = companyPanel.getTblCompany().getSelectedRow() != -1;
                companyPanel.getBtnUpdateCompany().setEnabled(selected);
            }
        });
        companyPanel.insertCompanyAddActionListener((ActionEvent e) -> {
            try {
                coordinator.openInsertCompanyForm(null, Mode.INSERT);
                fillCompanies(communication.getAllCompanies());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(companyPanel, ex.getMessage(), "Greska", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        companyPanel.updateCompanyAddActionListener((ActionEvent e) -> {
            try {
                int row = companyPanel.getTblCompany().getSelectedRow();
                Company company = (Company) ((CompanyTM) companyPanel.getTblCompany().getModel()).getValueAt(row, 1);
                coordinator.openInsertCompanyForm(company, Mode.UPDATE);
                fillCompanies(communication.getAllCompanies());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(companyPanel, ex.getMessage(), "Greska", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
    }

}
