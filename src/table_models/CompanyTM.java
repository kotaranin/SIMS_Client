/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package table_models;

import domain.Company;
import java.util.List;

/**
 *
 * @author kotar
 */
public class CompanyTM extends AbstractTM {

    public CompanyTM(List<Company> list) {
        super(list);
        this.columns = new String[]{"ID", "Naziv", "Adresa", "Grad"};
    }

    @Override
    public Object getAbstractValueAt(int rowIndex, int columnIndex) {
        if (list == null) {
            return null;
        }
        Company company = (Company) list.get(rowIndex);
        switch (columnIndex) {
            case 0 -> {
                return company.getIdCompany();
            }
            case 1 -> {
                return company;
            }
            case 2 -> {
                return company.getAddress();
            }
            case 3 -> {
                return company.getCity();
            }
            default ->
                throw new AssertionError();
        }
    }

}
