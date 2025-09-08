/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package table_models;

import domain.StudyProgram;
import java.util.List;

/**
 *
 * @author kotar
 */
public class StudyProgramTM extends AbstractTM {

    public StudyProgramTM(List<StudyProgram> list) {
        super(list);
        this.columns = new String[]{"Naziv studijskog programa"};
    }

    @Override
    public Object getAbstractValueAt(int rowIndex, int columnIndex) {
        if (list == null) {
            return null;
        }
        StudyProgram studyProgram = (StudyProgram) list.get(rowIndex);
        switch (columnIndex) {
            case 0 -> {
                return studyProgram;
            }
            default ->
                throw new AssertionError();
        }
    }

}
