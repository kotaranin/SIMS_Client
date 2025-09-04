/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package table_models;

import domain.Internship;
import java.util.List;

/**
 *
 * @author kotar
 */
public class InternshipTM extends AbstractTM {

    public InternshipTM(List list) {
        super(list);
        this.columns = new String[]{"ID", "Student", "Broj indeksa", "Kompanija", "Pocetak", "Kraj", "Dnevnik", "Odbrana", "Ocena", "Nastavnik", "Ispitni rok", "Evidentirao"};
    }

    @Override
    public Object getAbstractValueAt(int rowIndex, int columnIndex) {
        if (list == null) {
            return null;
        }
        Internship internship = (Internship) list.get(rowIndex);
        switch (columnIndex) {
            case 0 -> {
                return internship.getIdInternship();
            }
            case 1 -> {
                return internship;
            }
            case 2 -> {
                return internship.getStudent().getIndexNumber();
            }
            case 3 -> {
                return internship.getCompany();
            }
            case 4 -> {
                return internship.getStartDate();
            }
            case 5 -> {
                return internship.getEndDate();
            }
            case 6 -> {
                return internship.getReport();
            }
            case 7 -> {
                return internship.getDefenseDate();
            }
            case 8 -> {
                return internship.getGrade();
            }
            case 9-> {
                return internship.getTeacher();
            }
            case 10 -> {
                return internship.getExamPeriod();
            }
            case 11 -> {
                return internship.getStudentOfficer();
            }
            default ->
                throw new AssertionError();
        }
    }

}
