/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package table_models;

import domain.Teacher;
import java.util.List;

/**
 *
 * @author kotar
 */
public class TeacherTM extends AbstractTM {

    public TeacherTM(List<Teacher> list) {
        super(list);
        super.columns = new String[]{"Ime i prezime nastavnika"};
    }

    @Override
    public Object getAbstractValueAt(int rowIndex, int columnIndex) {
        if (list == null) {
            return null;
        }
        Teacher teacher = (Teacher) list.get(rowIndex);
        switch (columnIndex) {
            case 0 -> {
                return teacher;
            }
            default ->
                throw new AssertionError();
        }
    }

}
