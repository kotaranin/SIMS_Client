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
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;
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
    private boolean initialized;

    public StudentPanelController(StudentPanel studentPanel) {
        this.studentPanel = studentPanel;
        this.communication = Communication.getInstance();
        this.coordinator = Coordinator.getInstance();
        this.initialized = false;
        addActionListeners();
    }

    public void preparePanel() throws Exception {
        initialized = false;
        List<City> cities = communication.getAllCities();
        List<StudyProgram> studyPrograms = communication.getAllStudyPrograms();
        List<Student> students = communication.getAllStudents();
        List<Object> values = new ArrayList<>();
        studentPanel.getTxtFirstName().setText(null);
        studentPanel.getTxtLastName().setText(null);
        studentPanel.getTxtIndexNumber().setText(null);
        studentPanel.getComboCity().removeAllItems();
        studentPanel.getComboCity().addItem(new City(null, "Svi gradovi", null));
        for (City city : cities) {
            studentPanel.getComboCity().addItem(city);
        }
        studentPanel.getComboStudyProgram().removeAllItems();
        studentPanel.getComboStudyProgram().addItem(new StudyProgram(null, "Svi studijski programi", null, null));
        for (StudyProgram studyProgram : studyPrograms) {
            studentPanel.getComboStudyProgram().addItem(studyProgram);
        }
        fillStudents(students);
        values.add("Sve godine");
        for (int i = 1; i <= 100; i++) {
            values.add(i);
        }
        studentPanel.getSpinnerYearOfStudy().setModel(new SpinnerListModel(values));
        ((JSpinner.DefaultEditor) studentPanel.getSpinnerYearOfStudy().getEditor()).getTextField().setEditable(false);
        initialized = true;
    }

    public void fillStudents(List<Student> students) {
        studentPanel.getTblStudent().setModel(new StudentTM(students));
    }

    private void search() {
        try {
            String firstName = studentPanel.getTxtFirstName().getText().trim();
            String lastName = studentPanel.getTxtLastName().getText().trim();
            City city = (City) studentPanel.getComboCity().getSelectedItem();
            String indexNumber = studentPanel.getTxtIndexNumber().getText().trim();
            Object yearObject = studentPanel.getSpinnerYearOfStudy().getValue();
            Integer yearOfStudy = (yearObject instanceof Integer) ? (Integer) yearObject : null;
            StudyProgram studyProgram = (StudyProgram) studentPanel.getComboStudyProgram().getSelectedItem();
            domain.Module module = (domain.Module) studentPanel.getComboModule().getSelectedItem();
            Student student = new Student(null, indexNumber, firstName, lastName, null, yearOfStudy, city, studyProgram, module);
            fillStudents(communication.searchStudents(student));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(studentPanel, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addActionListeners() {
        studentPanel.getComboStudyProgram().addActionListener((ActionEvent e) -> {
            try {
                StudyProgram studyProgram = (StudyProgram) studentPanel.getComboStudyProgram().getModel().getSelectedItem();
                if (studyProgram != null && studyProgram.getIdStudyProgram() != null) {
                    List<domain.Module> modules = studyProgram.getModules();
                    studentPanel.getComboModule().removeAllItems();
                    studentPanel.getComboModule().addItem(new domain.Module(null, "Svi moduli", null));
                    for (domain.Module module : modules) {
                        studentPanel.getComboModule().addItem(module);
                    }
                } else {
                    studentPanel.getComboModule().removeAllItems();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(studentPanel, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });
        studentPanel.getTblStudent().getSelectionModel().addListSelectionListener((e) -> {
            if (!e.getValueIsAdjusting()) {
                boolean selected = studentPanel.getTblStudent().getSelectedRow() != -1;
                studentPanel.getBtnUpdateStudent().setEnabled(selected);
            }
        });
        studentPanel.insertStudentAddActionListener((ActionEvent e) -> {
            try {
                coordinator.openInsertStudentForm(null, Mode.INSERT);
                preparePanel();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(studentPanel, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });
        studentPanel.updateStudentAddActionListener((ActionEvent e) -> {
            try {
                int row = studentPanel.getTblStudent().getSelectedRow();
                Student student = (Student) ((StudentTM) studentPanel.getTblStudent().getModel()).getValueAt(row, 0);
                coordinator.openInsertStudentForm(student, Mode.UPDATE);
                preparePanel();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(studentPanel, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });
        studentPanel.getTxtFirstName().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (initialized) {
                    search();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (initialized) {
                    search();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        studentPanel.getTxtLastName().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (initialized) {
                    search();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (initialized) {
                    search();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        studentPanel.getComboCity().addActionListener((ActionEvent e) -> {
            if (initialized) {
                search();
            }
        });
        studentPanel.getTxtIndexNumber().getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (initialized) {
                    search();
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (initialized) {
                    search();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
        studentPanel.getSpinnerYearOfStudy().addChangeListener((ChangeEvent e) -> {
            if (initialized) {
                search();
            }
        });
        studentPanel.getComboStudyProgram().addActionListener((ActionEvent e) -> {
            if (initialized) {
                search();
            }
        });
        studentPanel.getComboModule().addActionListener((ActionEvent e) -> {
            if (initialized) {
                search();
            }
        });
    }
}
