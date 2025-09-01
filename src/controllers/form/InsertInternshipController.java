/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.form;

import communication.Communication;
import controllers.panel.ReportPanelController;
import coordinator.Coordinator;
import domain.Company;
import domain.ExamPeriod;
import domain.Internship;
import domain.Report;
import domain.Student;
import domain.Teacher;
import enums.Grade;
import enums.Mode;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.forms.InsertInternshipForm;

/**
 *
 * @author kotar
 */
public class InsertInternshipController {

    private final InsertInternshipForm insertInternshipForm;
    private final Communication communication;
    private final Coordinator coordinator;
    private final Internship internship;
    private final Mode mode;
    private Report report;

    public InsertInternshipController(InsertInternshipForm insertInternshipForm, Internship internship, Mode mode) {
        this.insertInternshipForm = insertInternshipForm;
        this.communication = Communication.getInstance();
        this.coordinator = Coordinator.getInstance();
        this.internship = internship;
        this.mode = mode;
        addActionListeners();
    }

    private void prepareForm() throws Exception {
        List<Student> students = communication.getAllStudents();
        List<Company> companies = communication.getAllCompanies();
        List<Teacher> teachers = communication.getAllTeachers();
        List<ExamPeriod> examPeriods = communication.getAllExamPeriods();
        for (Student student : students) {
            insertInternshipForm.getComboStudent().addItem(student);
        }
        for (Company company : companies) {
            insertInternshipForm.getComboCompany().addItem(company);
        }
        for (Grade value : Grade.values()) {
            insertInternshipForm.getComboGrade().addItem(value);
        }
        for (Teacher teacher : teachers) {
            insertInternshipForm.getComboTeacher().addItem(teacher);
        }
        for (ExamPeriod examPeriod : examPeriods) {
            insertInternshipForm.getComboExamPeriod().addItem(examPeriod);
        }
    }

    public void openInsertInternshipForm() throws Exception {
        prepareForm();
        if (mode == Mode.UPDATE) {
            this.report = internship.getReport();
            insertInternshipForm.getComboStudent().getModel().setSelectedItem(internship.getStudent());
            insertInternshipForm.getComboCompany().getModel().setSelectedItem(internship.getCompany());
            insertInternshipForm.getTxtStartDate().setText(internship.getStartDate().toString());
            insertInternshipForm.getTxtEndDate().setText(internship.getEndDate().toString());
            insertInternshipForm.getLblReport().setText(internship.getReport().toString());
            insertInternshipForm.getTxtDefenseDate().setText(internship.getDefenseDate().toString());
            insertInternshipForm.getComboGrade().getModel().setSelectedItem(internship.getGrade());
            insertInternshipForm.getComboTeacher().getModel().setSelectedItem(internship.getTeacher());
            insertInternshipForm.getComboExamPeriod().getModel().setSelectedItem(internship.getExamPeriod());
            insertInternshipForm.setTitle("Azuriraj strucnu praksu");
        } else {
            insertInternshipForm.setTitle("Kreiraj strucnu praksu");
        }
        insertInternshipForm.setLocationRelativeTo(insertInternshipForm.getParent());
        insertInternshipForm.setVisible(true);
    }

    public void closeInsertInternshipForm() {
        insertInternshipForm.dispose();
    }

    private void addActionListeners() {
        insertInternshipForm.openReportAddActionListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && report != null) {
                    try {
                        File file = File.createTempFile("doc_", "_" + report.getFileName());
                        file.deleteOnExit();
                        FileOutputStream out = new FileOutputStream(file);
                        out.write(report.getFileContent());
                        Desktop.getDesktop().open(file);
                    } catch (IOException ex) {
                        Logger.getLogger(ReportPanelController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        insertInternshipForm.attachReportAddActionListener((ActionEvent e) -> {
            try {
                Report temp = coordinator.openFilePickerForm();
                report.setFileName(temp.getFileName());
                report.setFileContent(temp.getFileContent());
                insertInternshipForm.getLblReport().setText(report.getFileName());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(insertInternshipForm, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
        insertInternshipForm.saveAddActionListener((ActionEvent e) -> {
            Student student = (Student) insertInternshipForm.getComboStudent().getSelectedItem();
            Company company = (Company) insertInternshipForm.getComboCompany().getSelectedItem();
            LocalDate startDate = LocalDate.parse(insertInternshipForm.getTxtStartDate().getText());
            LocalDate endDate = LocalDate.parse(insertInternshipForm.getTxtEndDate().getText());
            LocalDate defenseDate = LocalDate.parse(insertInternshipForm.getTxtDefenseDate().getText());
            Grade grade = (Grade) insertInternshipForm.getComboGrade().getSelectedItem();
            Teacher teacher = (Teacher) insertInternshipForm.getComboTeacher().getSelectedItem();
            ExamPeriod examPeriod = (ExamPeriod) insertInternshipForm.getComboExamPeriod().getSelectedItem();
            switch (mode) {
                case INSERT -> {
                    try {
                        Internship i = new Internship(null, startDate, endDate, defenseDate, grade, teacher, examPeriod, report, coordinator.getStudentOfficer(), company, student);
                        System.out.println("provera " + company.getIdCompany());
                        communication.insertInternship(i);
                        JOptionPane.showMessageDialog(insertInternshipForm, "Sistem je zapamtio strucnu praksu.", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                        closeInsertInternshipForm();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(insertInternshipForm, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                    }
                }
                case UPDATE -> {
                    try {
                        internship.setStudent(student);
                        internship.setCompany(company);
                        internship.setStartDate(startDate);
                        internship.setEndDate(endDate);
                        internship.setReport(report);
                        internship.setDefenseDate(defenseDate);
                        internship.setGrade(grade);
                        internship.setTeacher(teacher);
                        internship.setExamPeriod(examPeriod);
                        communication.updateInternship(internship);
                        JOptionPane.showMessageDialog(insertInternshipForm, "Sistem je zapamtio strucnu praksu.", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                        closeInsertInternshipForm();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(insertInternshipForm, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                    }
                }
                default ->
                    throw new AssertionError();
            }
        });
    }

}
