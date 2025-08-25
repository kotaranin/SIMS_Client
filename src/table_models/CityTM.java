/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package table_models;

import domain.City;
import java.util.List;

/**
 *
 * @author kotar
 */
public class CityTM extends AbstractTM<City> {

    public CityTM(List<City> list) {
        super(list);
        this.columns = new String[]{"ID", "Naziv"};
    }

    @Override
    public Object getAbstractValueAt(int rowIndex, int columnIndex) {
        if (list == null) {
            return null;
        }
        City city = list.get(rowIndex);
        switch (columnIndex) {
            case 0 -> {
                return city.getIdCity();
            }
            case 1 -> {
                return city;
            }
            default ->
                throw new AssertionError();
        }
    }

    public void insertCity(City city) {
        list.add(city);
    }

    public void deleteCity(City city) {
        list.remove(city);
    }

}
