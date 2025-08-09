/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import communication.Communication;
import coordinator.Coordinator;
import domain.Country;
import enums.Mode;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import table_models.CountryTableModel;
import view.CountriesPanel;

/**
 *
 * @author kotar
 */
public class CountriesPanelController {

    private final CountriesPanel countriesPanel;
    private final Communication communication;
    private final Coordinator coordinator;

    public CountriesPanelController(CountriesPanel countriesPanel) {
        this.countriesPanel = countriesPanel;
        this.communication = Communication.getInstance();
        this.coordinator = Coordinator.getInstance();
        addActionListeners();
    }

    public void fillCountries(List<Country> countries) {
        countriesPanel.getTblCountry().setModel(new CountryTableModel(countries));
    }

    private void addActionListeners() {
        countriesPanel.getTblCountry().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean selected = countriesPanel.getTblCountry().getSelectedRow() != -1;
                countriesPanel.getBtnDeleteCountry().setEnabled(selected);
                countriesPanel.getBtnUpdateCountry().setEnabled(selected);
            }
        });
        countriesPanel.getTxtName().getDocument().addDocumentListener(new DocumentListener() {
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

            private void search() {
                try {
                    String name = countriesPanel.getTxtName().getText();
                    fillCountries(communication.searchCountries(" WHERE LOWER(name) LIKE LOWER('%" + name + "%')"));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(countriesPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        countriesPanel.deleteCountryAddActionListener((ActionEvent e) -> {
            try {
                int row = countriesPanel.getTblCountry().getSelectedRow();
                Country country = (Country) ((CountryTableModel) countriesPanel.getTblCountry().getModel()).getValueAt(row, 1);
                communication.deleteCountry(country);
                JOptionPane.showMessageDialog(countriesPanel, "Sistem je obrisao drzavu.", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                fillCountries(communication.getAllCountries());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(countriesPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
        countriesPanel.insertCountryAddActionListener((ActionEvent e) -> {
            try {
                coordinator.openInsertCountriesForm(null, Mode.INSERT);
                fillCountries(communication.getAllCountries());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(countriesPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
        countriesPanel.updateCountryAddActionListener((ActionEvent e) -> {
            try {
                int row = countriesPanel.getTblCountry().getSelectedRow();
                Country country = (Country) ((CountryTableModel) countriesPanel.getTblCountry().getModel()).getValueAt(row, 1);
                coordinator.openInsertCountriesForm(country, Mode.UPDATE);
                fillCountries(communication.getAllCountries());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(countriesPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}
