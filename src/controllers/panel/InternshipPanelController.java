/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.panel;

import communication.Communication;
import coordinator.Coordinator;
import domain.Company;
import domain.ExamPeriod;
import domain.Internship;
import domain.Student;
import domain.Teacher;
import enums.Grade;
import enums.Mode;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JOptionPane;
import table_models.InternshipTM;
import view.panels.InternshipPanel;

/**
 *
 * @author kotar
 */
public class InternshipPanelController {

    private final InternshipPanel internshipPanel;
    private final Communication communication;
    private final Coordinator coordinator;
    private boolean initialized;

    public InternshipPanelController(InternshipPanel internshipPanel) {
        this.internshipPanel = internshipPanel;
        this.communication = Communication.getInstance();
        this.coordinator = Coordinator.getInstance();
        this.initialized = false;
        addActionListeners();
    }

    public void preparePanel() throws Exception {
        initialized = false;
        List<Student> students = communication.getAllStudents();
        List<Company> companies = communication.getAllCompanies();
        List<Teacher> teachers = communication.getAllTeachers();
        List<ExamPeriod> examPeriods = communication.getAllExamPeriods();
        internshipPanel.getComboStudent().removeAllItems();
        internshipPanel.getComboCompany().removeAllItems();
        internshipPanel.getComboGrade().removeAllItems();
        internshipPanel.getComboTeacher().removeAllItems();
        internshipPanel.getComboExamPeriod().removeAllItems();
        internshipPanel.getComboStudent().addItem(new Student(null, null, "Svi", "studenti", null, null, null, null, null));
        for (Student student : students) {
            internshipPanel.getComboStudent().addItem(student);
        }
        internshipPanel.getComboCompany().addItem(new Company(null, "Sve kompanije", null, null));
        for (Company company : companies) {
            internshipPanel.getComboCompany().addItem(company);
        }
        for (Grade value : Grade.values()) {
            internshipPanel.getComboGrade().addItem(value);
        }
        internshipPanel.getComboTeacher().addItem(new Teacher(null, "Svi", "nastavnici"));
        for (Teacher teacher : teachers) {
            internshipPanel.getComboTeacher().addItem(teacher);
        }
        internshipPanel.getComboExamPeriod().addItem(new ExamPeriod(null, "Svi ispitni rokovi", null, null));
        for (ExamPeriod examPeriod : examPeriods) {
            internshipPanel.getComboExamPeriod().addItem(examPeriod);
        }
        fillInternships(communication.getAllInternships());
        initialized = true;
    }

    public void fillInternships(List<Internship> internships) {
        internshipPanel.getTblInternship().setModel(new InternshipTM(internships));
    }

    private void search() {
        try {
            Student student = (Student) internshipPanel.getComboStudent().getModel().getSelectedItem();
            Company company = (Company) internshipPanel.getComboCompany().getModel().getSelectedItem();
            Grade grade = (Grade) internshipPanel.getComboGrade().getModel().getSelectedItem();
            Teacher teacher = (Teacher) internshipPanel.getComboTeacher().getModel().getSelectedItem();
            ExamPeriod examPeriod = (ExamPeriod) internshipPanel.getComboExamPeriod().getModel().getSelectedItem();
            Internship internship = new Internship(null, null, null, null, grade, teacher, examPeriod, null, null, company, student);
            fillInternships(communication.searchInternships(internship));
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(internshipPanel, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addActionListeners() {
        internshipPanel.getTblInternship().getSelectionModel().addListSelectionListener((e) -> {
            if (!e.getValueIsAdjusting()) {
                boolean selected = internshipPanel.getTblInternship().getSelectedRow() != -1;
                internshipPanel.getBtnDeleteInternship().setEnabled(selected);
                internshipPanel.getBtnUpdateInternship().setEnabled(selected);
            }
        });
        internshipPanel.getComboStudent().addActionListener((ActionEvent e) -> {
            if (initialized) {
                search();
            }
        });
        internshipPanel.getComboCompany().addActionListener((ActionEvent e) -> {
            if (initialized) {
                search();
            }
        });
        internshipPanel.getComboGrade().addActionListener((ActionEvent e) -> {
            if (initialized) {
                search();
            }
        });
        internshipPanel.getComboTeacher().addActionListener((ActionEvent e) -> {
            if (initialized) {
                search();
            }
        });
        internshipPanel.getComboExamPeriod().addActionListener((ActionEvent e) -> {
            if (initialized) {
                search();
            }
        });
        internshipPanel.insertInternshipAddActionListener((ActionEvent e) -> {
            try {
                coordinator.openinsertInternshipForm(null, Mode.INSERT);
                preparePanel();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(internshipPanel, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });
        internshipPanel.updateInternshipAddActionListener((ActionEvent e) -> {
            try {
                int row = internshipPanel.getTblInternship().getSelectedRow();
                Internship internship = (Internship) ((InternshipTM) internshipPanel.getTblInternship().getModel()).getValueAt(row, 0);
                coordinator.openinsertInternshipForm(internship, Mode.UPDATE);
                preparePanel();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(internshipPanel, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });
        internshipPanel.deleteInternshipAddActionListener((ActionEvent e) -> {
            try {
                int row = internshipPanel.getTblInternship().getSelectedRow();
                Internship internship = (Internship) ((InternshipTM) internshipPanel.getTblInternship().getModel()).getValueAt(row, 0);
                communication.deleteInternship(internship);
                JOptionPane.showMessageDialog(internshipPanel, "Sistem je obrisao dnevnik prakse.", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(internshipPanel, "Sistem je obrisao stručnu praksu.", "Obaveštenje", JOptionPane.INFORMATION_MESSAGE);
                preparePanel();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(internshipPanel, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}
