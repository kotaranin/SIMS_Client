/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package table_models;

import domain.Report;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author kotar
 */
public class ReportTableModel extends AbstractTableModel {

    private final List<Report> reports;
    private final String[] columns;

    public ReportTableModel(List<Report> reports) {
        this.reports = reports;
        this.columns = new String[]{"ID", "Naziv datoteke"};
    }

    @Override
    public int getRowCount() {
        return (reports == null) ? 0 : reports.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (reports == null) {
            return null;
        }
        Report report = reports.get(rowIndex);
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

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

}
