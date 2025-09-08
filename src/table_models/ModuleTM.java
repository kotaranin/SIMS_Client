/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package table_models;

import java.util.List;

/**
 *
 * @author kotar
 */
public class ModuleTM extends AbstractTM {

    public ModuleTM(List<domain.Module> list) {
        super(list);
        this.columns = new String[]{"Naziv modula"};
    }

    @Override
    public Object getAbstractValueAt(int rowIndex, int columnIndex) {
        if (list == null) {
            return null;
        }
        domain.Module module = (domain.Module) list.get(rowIndex);
        switch (columnIndex) {
            case 0 -> {
                return module;
            }
            default ->
                throw new AssertionError();
        }
    }

}
