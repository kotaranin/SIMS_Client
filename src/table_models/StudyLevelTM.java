/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package table_models;

import domain.StudyLevel;
import java.util.List;

/**
 *
 * @author Uros
 */
public class StudyLevelTM extends AbstractTM {

    public StudyLevelTM(List<StudyLevel> list) {
        super(list);
        super.columns = new String[] {"ID", "Naziv"};
    }

    @Override
    public Object getAbstractValueAt(int rowIndex, int columnIndex) {
        if (list == null)
            return null;
        StudyLevel studyLevel = (StudyLevel) list.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return studyLevel.getIdStudyLevel();
            case 1:
                return studyLevel;
            default:
                throw new AssertionError();
        }
    }
    
}
