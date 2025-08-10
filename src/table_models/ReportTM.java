/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package table_models;

import domain.Report;
import java.util.List;

/**
 *
 * @author kotar
 */
public class ReportTM extends AbstractTM {

    public ReportTM(List<Report> list) {
        super(list);
        super.columns = new String[]{"ID", "Naziv datoteke"};
    }

    @Override
    public Object getAbstractValueAt(int rowIndex, int columnIndex) {
        if (list == null) {
            return null;
        }
        Report report = (Report) list.get(rowIndex);
        switch (columnIndex) {
            case 0 -> {
                return report.getIdReport();
            }
            case 1 -> {
                return report;
            }
            default ->
                throw new AssertionError();
        }
    }

}
