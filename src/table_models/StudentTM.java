/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package table_models;

import domain.Student;
import java.util.List;

/**
 *
 * @author kotar
 */
public class StudentTM extends AbstractTM {

    public StudentTM(List list) {
        super(list);
        this.columns = new String[]{"ID", "Ime i prezime", "Datum rodjenja", "Grad", "Broj indeksa", "Godina studija", "Studijski program", "Modul"};
    }

    @Override
    public Object getAbstractValueAt(int rowIndex, int columnIndex) {
        if (list == null) {
            return null;
        }
        Student student = (Student) list.get(rowIndex);
        switch (columnIndex) {
            case 0 -> {
                return student.getIdStudent();
            }
            case 1 -> {
                return student;
            }
            case 2 -> {
                return student.getDateOfBirth();
            }
            case 3 -> {
                return student.getCity();
            }
            case 4 -> {
                return student.getIndexNumber();
            }
            case 5 -> {
                return student.getYearOfStudy();
            }
            case 6 -> {
                return student.getStudyProgram();
            }
            case 7 -> {
                return student.getModule();
            }
            default ->
                throw new AssertionError();
        }
    }

}
