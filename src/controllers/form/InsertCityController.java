/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.form;

import domain.City;
import enums.Mode;
import java.awt.event.ActionEvent;
import table_models.CityTM;
import view.forms.InsertCityForm;
import view.forms.InsertCountryForm;

/**
 *
 * @author kotar
 */
public class InsertCityController {

    private final InsertCityForm insertCityForm;
    private final City city;
    private final Mode mode;

    public InsertCityController(InsertCityForm insertCityForm, City city, Mode mode) {
        this.insertCityForm = insertCityForm;
        this.city = city;
        this.mode = mode;
        addActionListeners();
    }

    public void openInsertCityForm() {
        if (mode == Mode.UPDATE) {
            insertCityForm.setTitle("Ažuriraj grad");
            insertCityForm.getTxtCityName().setText(city.getName());
        } else {
            insertCityForm.setTitle("Dodaj grad");
        }
        insertCityForm.setLocationRelativeTo(insertCityForm.getParent());
        insertCityForm.setVisible(true);
    }

    private void closeInsertCityForm() {
        insertCityForm.dispose();
    }

    private void addActionListeners() {
        insertCityForm.saveAddActionListener((ActionEvent e) -> {
            String name = insertCityForm.getTxtCityName().getText();
            switch (mode) {
                case INSERT -> {
                    City c = new City(null, name, null);
                    ((CityTM) ((InsertCountryForm) insertCityForm.getParent()).getTblCity().getModel()).insert(c);
                    closeInsertCityForm();
                }
                case UPDATE -> {
                    city.setName(name);
                    closeInsertCityForm();
                }
                default ->
                    throw new AssertionError();
            }
        });
    }

}
