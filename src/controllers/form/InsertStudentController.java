/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.form;

import communication.Communication;
import domain.City;
import domain.Student;
import domain.StudyProgram;
import enums.Mode;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;
import view.forms.InsertStudentForm;

/**
 *
 * @author kotar
 */
public class InsertStudentController {

    private final InsertStudentForm insertStudentForm;
    private final Communication communication;
    private final Student student;
    private final Mode mode;

    public InsertStudentController(InsertStudentForm insertStudentForm, Student student, Mode mode) {
        this.insertStudentForm = insertStudentForm;
        this.communication = Communication.getInstance();
        this.student = student;
        this.mode = mode;
        addActionListeners();
    }

    public void openInsertStudentForm() throws Exception {
        List<City> cities = communication.getAllCities();
        List<StudyProgram> studyPrograms = communication.getAllStudyPrograms();
        List<domain.Module> modules = communication.getAllModules();
        for (City city : cities) {
            insertStudentForm.getComboCity().addItem(city);
        }
        for (StudyProgram studyProgram : studyPrograms) {
            insertStudentForm.getComboStudyProgram().addItem(studyProgram);
        }
        for (domain.Module module : modules) {
            insertStudentForm.getComboModule().addItem(module);
        }
        if (mode == Mode.UPDATE) {
            insertStudentForm.getTxtFirstName().setText(student.getFirstName());
            insertStudentForm.getTxtLastName().setText(student.getLastName());
            insertStudentForm.getTxtDateOfBirth().setText(student.getDateOfBirth().toString());
            insertStudentForm.getComboCity().setSelectedItem(student.getCity());
            insertStudentForm.getTxtIndex().setText(student.getIndexNumber());
            insertStudentForm.getTxtYearOfStudy().setText(student.getYearOfStudy() + "");
            insertStudentForm.getComboStudyProgram().setSelectedItem(student.getStudyProgram());
            insertStudentForm.getComboModule().setSelectedItem(student.getModule());
            insertStudentForm.setTitle("Azuriraj studenta");
        } else {
            insertStudentForm.setTitle("Kreiraj studenta");
        }
        insertStudentForm.setLocationRelativeTo(insertStudentForm.getParent());
        insertStudentForm.setVisible(true);
    }

    private void closeInsertStudentForm() {
        insertStudentForm.dispose();
    }

    private void addActionListeners() {
        insertStudentForm.saveAddActionListener((ActionEvent e) -> {
            String firstName = insertStudentForm.getTxtFirstName().getText();
            String lastName = insertStudentForm.getTxtLastName().getText();
            LocalDate dateOfBirth = LocalDate.parse(insertStudentForm.getTxtDateOfBirth().getText());
            City city = (City) insertStudentForm.getComboCity().getSelectedItem();
            String index = insertStudentForm.getTxtIndex().getText();
            Integer yearOfStudy = Integer.valueOf(insertStudentForm.getTxtYearOfStudy().getText());
            StudyProgram studyProgram = (StudyProgram) insertStudentForm.getComboStudyProgram().getSelectedItem();
            domain.Module module = (domain.Module) insertStudentForm.getComboModule().getSelectedItem();
            switch (mode) {
                case INSERT -> {
                    try {
                        Student s = new Student(null, index, firstName, lastName, dateOfBirth, yearOfStudy, city, studyProgram, module);
                        System.out.println(dateOfBirth);
                        communication.insertStudent(s);
                        JOptionPane.showMessageDialog(insertStudentForm, "Sistem je zapamtio studenta.", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                        closeInsertStudentForm();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(insertStudentForm, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                    }
                }
                case UPDATE -> {
                    try {
                        student.setFirstName(firstName);
                        student.setLastName(lastName);
                        student.setDateOfBirth(dateOfBirth);
                        student.setCity(city);
                        student.setIndexNumber(index);
                        student.setYearOfStudy(yearOfStudy);
                        student.setStudyProgram(studyProgram);
                        student.setModule(module);
                        communication.updateStudent(student);
                        JOptionPane.showMessageDialog(insertStudentForm, "Sistem je zapamtio studenta.", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                        closeInsertStudentForm();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(insertStudentForm, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                    }
                }

                default ->
                    throw new AssertionError();
            }
        });
    }

}
