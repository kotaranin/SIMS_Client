/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.panel;

import communication.Communication;
import coordinator.Coordinator;
import domain.City;
import domain.Country;
import enums.Mode;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import table_models.CityTM;
import table_models.CountryTM;
import view.panels.CountryPanel;

/**
 *
 * @author kotar
 */
public class CountryPanelController {

    private final CountryPanel countryPanel;
    private final Communication communication;
    private final Coordinator coordinator;
    private boolean initialized;

    public CountryPanelController(CountryPanel countriesPanel) {
        this.countryPanel = countriesPanel;
        this.communication = Communication.getInstance();
        this.coordinator = Coordinator.getInstance();
        this.initialized = false;
        addActionListeners();
    }

    public void preparePanel() throws Exception {
        initialized = false;
        countryPanel.getTxtCountryName().setText(null);
        fillCountries(communication.getAllCountries());
        fillCities(null);
        initialized = true;
    }

    private void fillCountries(List<Country> countries) {
        countryPanel.getTblCountry().setModel(new CountryTM(countries));
    }

    private void fillCities(List<City> cities) {
        countryPanel.getTblCity().setModel(new CityTM(cities));
    }

    private void addActionListeners() {
        countryPanel.getTblCountry().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean selected = countryPanel.getTblCountry().getSelectedRow() != -1;
                countryPanel.getBtnUpdateCountry().setEnabled(selected);
            }
        });
        countryPanel.getTblCountry().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    int row = countryPanel.getTblCountry().getSelectedRow();
                    Country country = (Country) ((CountryTM) countryPanel.getTblCountry().getModel()).getValueAt(row, 0);
                    fillCities(country.getCities());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(countryPanel, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
        countryPanel.getTxtCountryName().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (initialized) {
                    search();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (initialized) {
                    search();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }

            private void search() {
                try {
                    String name = countryPanel.getTxtCountryName().getText().trim();
                    Country country = new Country(null, name, null);
                    fillCountries(communication.searchCountries(country));
                    fillCities(null);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(countryPanel, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        countryPanel.insertCountryAddActionListener((ActionEvent e) -> {
            try {
                coordinator.openInsertCountryForm(null, Mode.INSERT);
                preparePanel();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(countryPanel, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });
        countryPanel.updateCountryAddActionListener((ActionEvent e) -> {
            try {
                int row = countryPanel.getTblCountry().getSelectedRow();
                Country country = (Country) ((CountryTM) countryPanel.getTblCountry().getModel()).getValueAt(row, 0);
                coordinator.openInsertCountryForm(country, Mode.UPDATE);
                preparePanel();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(countryPanel, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}
