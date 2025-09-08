/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package table_models;

import domain.Internship;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author kotar
 */
public class InternshipTM extends AbstractTM {

    public InternshipTM(List<Internship> list) {
        super(list);
        this.columns = new String[]{"Student", "Broj indeksa", "Kompanija", "Početak", "Kraj", "Dnevnik", "Odbrana", "Ocena", "Nastavnik", "Ispitni rok", "Evidentirao"};
    }

    @Override
    public Object getAbstractValueAt(int rowIndex, int columnIndex) {
        if (list == null) {
            return null;
        }
        Internship internship = (Internship) list.get(rowIndex);
        switch (columnIndex) {
            case 0 -> {
                return internship;
            }
            case 1 -> {
                return internship.getStudent().getIndexNumber();
            }
            case 2 -> {
                return internship.getCompany();
            }
            case 3 -> {
                return internship.getStartDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
            }
            case 4 -> {
                return internship.getEndDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
            }
            case 5 -> {
                return internship.getReport();
            }
            case 6 -> {
                return internship.getDefenseDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."));
            }
            case 7 -> {
                return internship.getGrade();
            }
            case 8 -> {
                return internship.getTeacher();
            }
            case 9 -> {
                return internship.getExamPeriod();
            }
            case 10 -> {
                return internship.getStudentOfficer();
            }
            default ->
                throw new AssertionError();
        }
    }

}
