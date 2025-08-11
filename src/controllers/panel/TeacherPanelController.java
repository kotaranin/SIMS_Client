/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.panel;

import communication.Communication;
import coordinator.Coordinator;
import domain.Teacher;
import enums.Mode;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import table_models.TeacherTM;
import view.panels.TeacherPanel;

/**
 *
 * @author kotar
 */
public class TeacherPanelController {

    private final TeacherPanel teacherPanel;
    private final Communication communication;
    private final Coordinator coordinator;

    public TeacherPanelController(TeacherPanel teacherPanel) {
        this.teacherPanel = teacherPanel;
        this.communication = Communication.getInstance();
        this.coordinator = Coordinator.getInstance();
        addActionListeners();
    }

    public void fillTeachers(List<Teacher> teachers) {
        teacherPanel.getTblTeachers().setModel(new TeacherTM(teachers));
    }

    private void addActionListeners() {
        teacherPanel.getTblTeachers().getSelectionModel().addListSelectionListener((e) -> {
            if (!e.getValueIsAdjusting()) {
                boolean selected = teacherPanel.getTblTeachers().getSelectedRow() != -1;
                teacherPanel.getBtnDelete().setEnabled(selected);
                teacherPanel.getBtnUpdate().setEnabled(selected);
            }
        });
        teacherPanel.getTxtFirstName().getDocument().addDocumentListener(new DocumentListener() {
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
        teacherPanel.getTxtLastName().getDocument().addDocumentListener(new DocumentListener() {
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
        teacherPanel.deleteAddActionListener((ActionEvent e) -> {
            try {
                int row = teacherPanel.getTblTeachers().getSelectedRow();
                Teacher teacher = (Teacher) ((TeacherTM) teacherPanel.getTblTeachers().getModel()).getValueAt(row, 1);
                communication.deleteTeacher(teacher);
                JOptionPane.showMessageDialog(teacherPanel, "Sistem je obrisao nastavnika", "Obavestenje", JOptionPane.INFORMATION_MESSAGE);
                fillTeachers(communication.getAllTeachers());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(teacherPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
        teacherPanel.insertAddActionListener((ActionEvent e) -> {
            try {
                coordinator.openInsertTeacherForm(null, Mode.INSERT);
                fillTeachers(communication.getAllTeachers());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(teacherPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
        teacherPanel.updateAddActionListener((ActionEvent e) -> {
            try {
                int row = teacherPanel.getTblTeachers().getSelectedRow();
                Teacher teacher = (Teacher) ((TeacherTM) teacherPanel.getTblTeachers().getModel()).getValueAt(row, 1);
                coordinator.openInsertTeacherForm(teacher, Mode.UPDATE);
                fillTeachers(communication.getAllTeachers());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(teacherPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void search() {
        try {
            String firstName = teacherPanel.getTxtFirstName().getText();
            String lastName = teacherPanel.getTxtLastName().getText();
            List<Teacher> teachers = communication.searchTeachers(" WHERE LOWER(first_name) LIKE LOWER('%" + firstName + "%') AND LOWER(last_name) LIKE LOWER('%" + lastName + "%')");
            fillTeachers(teachers);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(teacherPanel, ex.getMessage(), "Greska", JOptionPane.ERROR_MESSAGE);
        }
    }
;

}
