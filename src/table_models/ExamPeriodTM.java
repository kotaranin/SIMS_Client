/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package table_models;

import domain.ExamPeriod;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author kotar
 */
public class ExamPeriodTM extends AbstractTM {

    public ExamPeriodTM(List<ExamPeriod> list) {
        super(list);
        super.columns = new String[]{"Naziv", "Datum početka", "Datum završetka"};
    }

    @Override
    public Object getAbstractValueAt(int rowIndex, int columnIndex) {
        if (list == null)
            return null;
        ExamPeriod examPeriod = (ExamPeriod) list.get(rowIndex);
        switch (columnIndex) {
            case 0 -> {
                return examPeriod;
            }
            case 1 -> {
                return examPeriod.getStartDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
            }
            case 2 -> {
                return examPeriod.getEndDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
            }
            default -> throw new AssertionError();
        }
    }

}
