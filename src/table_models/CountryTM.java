/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package table_models;

import domain.Country;
import java.util.List;

/**
 *
 * @author kotar
 */
public class CountryTM extends AbstractTM {

    public CountryTM(List<Country> list) {
        super(list);
        super.columns = new String[]{"ID", "Drzava"};
    }

    @Override
    public Object getAbstractValueAt(int rowIndex, int columnIndex) {
        if (list == null) {
            return null;
        }
        Country country = (Country) list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return country.getIdCountry();
            case 1:
                return country;
            default:
                throw new AssertionError();
        }
    }

}
