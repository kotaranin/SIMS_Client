/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import communication.Communication;
import domain.ExamPeriod;
import enums.Mode;
import static enums.Mode.INSERT;
import static enums.Mode.UPDATE;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import view.InsertExamPeriodForm;

/**
 *
 * @author kotar
 */
public class InsertExamPeriodController {

    private final InsertExamPeriodForm insertExamPeriodForm;
    private final ExamPeriod examPeriod;
    private final Mode mode;
    private final Communication communication;

    public InsertExamPeriodController(InsertExamPeriodForm insertExamPeriodForm, ExamPeriod examPeriod, Mode mode) {
        this.insertExamPeriodForm = insertExamPeriodForm;
        this.examPeriod = examPeriod;
        this.mode = mode;
        this.communication = Communication.getInstance();
        addActionListeners();
    }

    public void openInsertExamPeriodForm() {
        if (mode == Mode.UPDATE) {
            insertExamPeriodForm.getTxtName().setText(examPeriod.getName());
            insertExamPeriodForm.getTxtStartDate().setText(examPeriod.getStartDate().toString());
            insertExamPeriodForm.getTxtEndDate().setText(examPeriod.getEndDate().toString());
            insertExamPeriodForm.setTitle("Azuriraj ispitni rok");
        } else {
            insertExamPeriodForm.setTitle("Unesi ispitni rok");
        }
        insertExamPeriodForm.setLocationRelativeTo(null);
        insertExamPeriodForm.setVisible(true);
    }

    private void addActionListeners() {
        insertExamPeriodForm.insertExamPeriodAddActionListener((ActionEvent e) -> {
            String name = insertExamPeriodForm.getTxtName().getText();
            LocalDate startdDate = LocalDate.parse(insertExamPeriodForm.getTxtStartDate().getText());
            LocalDate endDate = LocalDate.parse(insertExamPeriodForm.getTxtEndDate().getText());
            switch (mode) {
                case INSERT -> {
                    try {
                        ExamPeriod ep = new ExamPeriod(null, name, startdDate, endDate);
                        communication.insertExamPeriod(ep);
                        JOptionPane.showMessageDialog(insertExamPeriodForm, "Sistem je zapamtio ispitni rok", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                        closeInsertExamPeriodForm();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(insertExamPeriodForm, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                    }
                }
                case UPDATE -> {
                    try {
                        examPeriod.setName(name);
                        examPeriod.setStartDate(startdDate);
                        examPeriod.setEndDate(endDate);
                        communication.updateExamPeriod(examPeriod);
                        JOptionPane.showMessageDialog(insertExamPeriodForm, "Sistem je zapamtio ispitni rok", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                        closeInsertExamPeriodForm();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(insertExamPeriodForm, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
                    }
                }
                default ->
                    throw new AssertionError();
            }
        });
    }

    private void closeInsertExamPeriodForm() {
        insertExamPeriodForm.dispose();
    }

}
