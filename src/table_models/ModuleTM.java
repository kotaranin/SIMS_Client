/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package table_models;

import java.util.List;
import domain.Module;

/**
 *
 * @author kotar
 */
public class ModuleTM extends AbstractTM {

    public ModuleTM(List list) {
        super(list);
        this.columns = new String[]{"ID", "Naziv"};
    }

    @Override
    public Object getAbstractValueAt(int rowIndex, int columnIndex) {
        if (list == null) {
            return null;
        }
        Module module = (Module) list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return module.getIdModule();
            case 1:
                return module;
            default:
                throw new AssertionError();
        }
    }

}
