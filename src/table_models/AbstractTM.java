/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package table_models;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author kotar
 */
public abstract class AbstractTM<T> extends AbstractTableModel {

    protected List<T> list;
    protected String[] columns;

    public AbstractTM(List<T> list) {
        this.list = list;
    }

    @Override
    public int getRowCount() {
        return (list == null) ? 0 : list.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return getAbstractValueAt(rowIndex, columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    public List<T> getList() {
        return list;
    }

    public void insert(T parameter) {
        list.add(parameter);
        fireTableDataChanged();
    }

    public void update(T parameter) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(parameter)) {
                list.set(i, parameter);
                fireTableDataChanged();
                return;
            }
        }
    }

    public void delete(T parameter) {
        list.remove(parameter);
        fireTableDataChanged();
    }

    public abstract Object getAbstractValueAt(int rowIndex, int columnIndex);

}
