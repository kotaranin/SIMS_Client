/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.form;

import communication.Communication;
import coordinator.Coordinator;
import domain.City;
import domain.Country;
import enums.Mode;
import static enums.Mode.INSERT;
import static enums.Mode.UPDATE;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import table_models.CityTM;
import view.forms.InsertCountryForm;

/**
 *
 * @author kotar
 */
public class InsertCountryController {

    private final InsertCountryForm insertCountryForm;
    private final Communication communication;
    private final Coordinator coordinator;
    private final Mode mode;
    private final Country country;

    public InsertCountryController(InsertCountryForm insertCountryForm, Country country, Mode mode) {
        this.insertCountryForm = insertCountryForm;
        this.communication = Communication.getInstance();
        this.coordinator = Coordinator.getInstance();
        this.country = country;
        this.mode = mode;
        addActionListeners();
    }

    private void fillCities(List<City> cities) {
        insertCountryForm.getTblCity().setModel(new CityTM(cities));
    }

    public void openInsertCountryForm() throws Exception {
        if (mode == Mode.UPDATE) {
            insertCountryForm.setTitle("Ažuriraj državu");
            insertCountryForm.getTxtCountryName().setText(country.getName());
            fillCities(communication.getCities(country));
        } else {
            insertCountryForm.setTitle("Dodaj državu");
            fillCities(new LinkedList<>());
        }
        insertCountryForm.setLocationRelativeTo(insertCountryForm.getParent());
        insertCountryForm.setVisible(true);
    }

    public void closeInsertCountryForm() {
        insertCountryForm.dispose();
    }

    private void addActionListeners() {
        insertCountryForm.getTblCity().getSelectionModel().addListSelectionListener((e) -> {
            if (!e.getValueIsAdjusting()) {
                boolean selected = insertCountryForm.getTblCity().getSelectedRow() != -1;
                insertCountryForm.getBtnUpdateCity().setEnabled(selected);
            }
        });
        insertCountryForm.insertCityAddActionListener((ActionEvent e) -> {
            coordinator.openInsertCityForm(null, Mode.INSERT);
            ((CityTM) insertCountryForm.getTblCity().getModel()).fireTableDataChanged();
        });
        insertCountryForm.updateCityAddActionListener((ActionEvent e) -> {
            int row = insertCountryForm.getTblCity().getSelectedRow();
            City city = (City) ((CityTM) insertCountryForm.getTblCity().getModel()).getValueAt(row, 0);
            coordinator.openInsertCityForm(city, Mode.UPDATE);
            ((CityTM) insertCountryForm.getTblCity().getModel()).fireTableDataChanged();
        });
        insertCountryForm.saveAddActionListener((ActionEvent e) -> {
            try {
                String name = insertCountryForm.getTxtCountryName().getText();
                List<City> cities = ((CityTM) insertCountryForm.getTblCity().getModel()).getList();
                switch (mode) {
                    case INSERT -> {
                        Country c = new Country(null, name, cities);
                        communication.insertCountry(c);
                        JOptionPane.showMessageDialog(insertCountryForm, "Sistem je zapamtio državu.", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
                        closeInsertCountryForm();
                    }
                    case UPDATE -> {
                        country.setName(name);
                        country.setCities(cities);
                        communication.updateCountry(country);
                        JOptionPane.showMessageDialog(insertCountryForm, "Sistem je zapamtio državu.", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
                        closeInsertCountryForm();
                    }
                    default ->
                        throw new AssertionError();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(insertCountryForm, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}
