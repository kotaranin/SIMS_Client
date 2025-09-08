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
import static enums.Mode.INSERT;
import static enums.Mode.UPDATE;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import validators.StudentValidator;
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
        insertStudentForm.getComboCity().removeAllItems();
        for (City city : cities) {
            insertStudentForm.getComboCity().addItem(city);
        }
        insertStudentForm.getComboStudyProgram().removeAllItems();
        for (StudyProgram studyProgram : studyPrograms) {
            insertStudentForm.getComboStudyProgram().addItem(studyProgram);
        }
        insertStudentForm.getSpinnerYearOfStudy().setModel(new SpinnerNumberModel(1, 1, 100, 1));
        ((JSpinner.DefaultEditor) insertStudentForm.getSpinnerYearOfStudy().getEditor()).getTextField().setEditable(false);
        insertStudentForm.getLblModule().setVisible(false);
        insertStudentForm.getComboModule().setVisible(false);
        if (mode == Mode.UPDATE) {
            insertStudentForm.getTxtFirstName().setText(student.getFirstName());
            insertStudentForm.getTxtLastName().setText(student.getLastName());
            insertStudentForm.getTxtDateOfBirth().setText(student.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")));
            insertStudentForm.getComboCity().setSelectedItem(student.getCity());
            insertStudentForm.getTxtIndex().setText(student.getIndexNumber());
            insertStudentForm.getSpinnerYearOfStudy().setValue(student.getYearOfStudy());
            insertStudentForm.getComboStudyProgram().setSelectedItem(student.getStudyProgram());
            insertStudentForm.getComboModule().setSelectedItem(student.getModule());
            insertStudentForm.setTitle("Ažuriraj studenta");
        } else {
            insertStudentForm.setTitle("Dodaj studenta");
        }
        insertStudentForm.setLocationRelativeTo(insertStudentForm.getParent());
        insertStudentForm.setVisible(true);
    }

    private void closeInsertStudentForm() {
        insertStudentForm.dispose();
    }

    private void moduleHandler() throws Exception {
        int yearOfStudy = (int) insertStudentForm.getSpinnerYearOfStudy().getValue();
        StudyProgram studyProgram = (StudyProgram) insertStudentForm.getComboStudyProgram().getModel().getSelectedItem();
        boolean showModules = false;
        if (studyProgram.getIdStudyProgram() == 9 && yearOfStudy > 1) {
            showModules = true;
        } else if (studyProgram.getIdStudyProgram() == 8 && yearOfStudy > 2) {
            showModules = true;
        }
        if (showModules) {
            insertStudentForm.getLblModule().setVisible(true);
            insertStudentForm.getComboModule().setVisible(true);
            List<domain.Module> modules = communication.getModules(studyProgram);
            insertStudentForm.getComboModule().removeAllItems();
            for (domain.Module module : modules) {
                insertStudentForm.getComboModule().addItem(module);
            }
        } else {
            insertStudentForm.getLblModule().setVisible(false);
            insertStudentForm.getComboModule().setVisible(false);
            insertStudentForm.getComboModule().removeAllItems();
        }
    }

    private void addActionListeners() {
        insertStudentForm.getSpinnerYearOfStudy().addChangeListener((ChangeEvent e) -> {
            try {
                moduleHandler();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(insertStudentForm, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });
        insertStudentForm.getComboStudyProgram().addActionListener((ActionEvent e) -> {
            try {
                moduleHandler();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(insertStudentForm, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });
        insertStudentForm.saveAddActionListener((ActionEvent e) -> {
            try {
                String firstName = insertStudentForm.getTxtFirstName().getText();
                String lastName = insertStudentForm.getTxtLastName().getText();
                LocalDate dateOfBirth = LocalDate.parse(insertStudentForm.getTxtDateOfBirth().getText(), DateTimeFormatter.ofPattern("dd.MM.yyyy."));
                City city = (City) insertStudentForm.getComboCity().getSelectedItem();
                String index = insertStudentForm.getTxtIndex().getText();
                Integer yearOfStudy = (Integer) insertStudentForm.getSpinnerYearOfStudy().getValue();
                StudyProgram studyProgram = (StudyProgram) insertStudentForm.getComboStudyProgram().getSelectedItem();
                domain.Module module = (domain.Module) insertStudentForm.getComboModule().getSelectedItem();
                switch (mode) {
                    case INSERT -> {
                        Student s = new Student(null, index, firstName, lastName, dateOfBirth, yearOfStudy, city, studyProgram, module);
                        new StudentValidator().checkElementaryContraints(s);
                        communication.insertStudent(s);
                        JOptionPane.showMessageDialog(insertStudentForm, "Sistem je zapamtio studenta.", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
                        closeInsertStudentForm();
                    }
                    case UPDATE -> {
                        student.setFirstName(firstName);
                        student.setLastName(lastName);
                        student.setDateOfBirth(dateOfBirth);
                        student.setCity(city);
                        student.setIndexNumber(index);
                        student.setYearOfStudy(yearOfStudy);
                        student.setStudyProgram(studyProgram);
                        student.setModule(module);
                        new StudentValidator().checkElementaryContraints(student);
                        communication.updateStudent(student);
                        JOptionPane.showMessageDialog(insertStudentForm, "Sistem je zapamtio studenta.", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
                        closeInsertStudentForm();
                    }
                    default ->
                        throw new AssertionError();
                }
            }catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(insertStudentForm, "Datum rođenja mora biti u formatu DD.MM.GGGG.", "Greška", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(insertStudentForm, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
