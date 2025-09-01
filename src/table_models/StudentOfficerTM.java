/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package table_models;

import domain.StudentOfficer;
import java.util.List;

/**
 *
 * @author kotar
 */
public class StudentOfficerTM extends AbstractTM {

    public StudentOfficerTM(List list) {
        super(list);
        this.columns = new String[]{"ID", "Ime", "Prezime", "E-mail", "Nivo studija", "Administrator"};
    }

    @Override
    public Object getAbstractValueAt(int rowIndex, int columnIndex) {
        if (list == null) {
            return null;
        }
        StudentOfficer studentOfficer = (StudentOfficer) list.get(rowIndex);
        switch (columnIndex) {
            case 0 -> {
                return studentOfficer.getIdStudentOfficer();
            }
            case 1 -> {
                return studentOfficer;
            }
            case 2 -> {
                return studentOfficer.getLastName();
            }
            case 3 -> {
                return studentOfficer.getEmail();
            }
            case 4 -> {
                return studentOfficer.getStudyLevel();
            }
            case 5 -> {
                return (studentOfficer.isAdmin()) ? "DA" : "NE";
            }
            default ->
                throw new AssertionError();
        }
    }

}
