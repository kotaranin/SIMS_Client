/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.panel;

import communication.Communication;
import coordinator.Coordinator;
import domain.City;
import domain.Student;
import domain.StudyProgram;
import enums.Mode;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import table_models.StudentTM;
import view.panels.StudentPanel;

/**
 *
 * @author kotar
 */
public class StudentPanelController {

    private final StudentPanel studentPanel;
    private final Communication communication;
    private final Coordinator coordinator;
    private boolean isInitializing;

    public StudentPanelController(StudentPanel studentPanel) {
        this.studentPanel = studentPanel;
        this.communication = Communication.getInstance();
        this.coordinator = Coordinator.getInstance();
        this.isInitializing = true;
        addActionListeners();
    }

    public void preparePanel() throws Exception {
        List<City> cities = communication.getAllCities();
        List<StudyProgram> studyPrograms = communication.getAllStudyPrograms();
        List<domain.Module> modules = communication.getAllModules();
        List<Student> students = communication.getAllStudents();
        for (City city : cities) {
            studentPanel.getComboCity().addItem(city);
        }
        for (StudyProgram studyProgram : studyPrograms) {
            studentPanel.getComboStudyProgram().addItem(studyProgram);
        }
        for (domain.Module module : modules) {
            studentPanel.getComboModule().addItem(module);
        }
        fillStudents(students);
        isInitializing = false;
    }

    public void fillStudents(List<Student> students) {
        studentPanel.getTblStudent().setModel(new StudentTM(students));
    }

    private void addActionListeners() {
        studentPanel.getTblStudent().getSelectionModel().addListSelectionListener((e) -> {
            if (!e.getValueIsAdjusting()) {
                boolean selected = studentPanel.getTblStudent().getSelectedRow() != -1;
                studentPanel.getBtnUpdateStudent().setEnabled(selected);
            }
        });
        studentPanel.insertStudentAddActionListener((ActionEvent e) -> {
            try {
                coordinator.openInsertStudentForm(null, Mode.INSERT);
                fillStudents(communication.getAllStudents());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(studentPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
        studentPanel.updateStudentAddActionListener((ActionEvent e) -> {
            try {
                int row = studentPanel.getTblStudent().getSelectedRow();
                Student student = (Student) ((StudentTM) studentPanel.getTblStudent().getModel()).getValueAt(row, 1);
                coordinator.openInsertStudentForm(student, Mode.UPDATE);
                fillStudents(communication.getAllStudents());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(studentPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
        studentPanel.getTxtFirstName().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        studentPanel.getTxtLastName().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        studentPanel.getTxtDateOfBirth().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        studentPanel.getComboCity().addActionListener((ActionEvent e) -> {
            if (!isInitializing) {
                search();
            }
        });
        studentPanel.getTxtIndexNumber().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        studentPanel.getTxtYearOfStudy().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                search();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        studentPanel.getComboStudyProgram().addActionListener((ActionEvent e) -> {
            if (!isInitializing) {
                search();
            }
        });
        studentPanel.getComboModule().addActionListener((ActionEvent e) -> {
            if (!isInitializing) {
                search();
            }
        });
    }

    public void search() {
        try {
            String firstName = studentPanel.getTxtFirstName().getText();
            String lastName = studentPanel.getTxtLastName().getText();
            String dOB = studentPanel.getTxtDateOfBirth().getText();
            LocalDate dateOfBirth = (dOB != null && !dOB.isEmpty()) ? LocalDate.parse(dOB) : null;
            City city = (City) studentPanel.getComboCity().getSelectedItem();
            String indexNumber = studentPanel.getTxtIndexNumber().getText();
            String yearText = studentPanel.getTxtYearOfStudy().getText().trim();
            Integer yearOfStudy = (yearText != null && !yearText.isEmpty()) ? Integer.valueOf(yearText) : null;
            StudyProgram studyProgram = (StudyProgram) studentPanel.getComboStudyProgram().getSelectedItem();
            domain.Module module = (domain.Module) studentPanel.getComboModule().getSelectedItem();
            Student student = new Student(null, indexNumber, firstName, lastName, dateOfBirth, yearOfStudy, city, studyProgram, module);
            fillStudents(communication.searchStudents(student));
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(studentPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }

}
