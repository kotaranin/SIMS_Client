/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.form;

import coordinator.Coordinator;
import domain.StudyProgram;
import enums.Mode;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JOptionPane;
import table_models.ModuleTM;
import validators.StudyProgramValidator;
import view.forms.InsertStudyProgramForm;

/**
 *
 * @author kotar
 */
public class InsertStudyProgramController {

    private final InsertStudyProgramForm insertStudyProgramForm;
    private final StudyProgram studyProgram;
    private final Mode mode;
    private final Coordinator coordinator;

    public InsertStudyProgramController(InsertStudyProgramForm insertStudyProgramForm, StudyProgram studyProgram, Mode mode) {
        this.insertStudyProgramForm = insertStudyProgramForm;
        this.studyProgram = studyProgram;
        this.mode = mode;
        this.coordinator = Coordinator.getInstance();
        addActionListeners();
    }

    public void fillModules(List<domain.Module> modules) {
        insertStudyProgramForm.getTblModule().setModel(new ModuleTM(modules));
    }

    public void openInsertStudyProgramForm() throws Exception {
        if (mode == Mode.UPDATE) {
            insertStudyProgramForm.getTxtStudyProgramName().setText(studyProgram.getName());
            fillModules(studyProgram.getModules());
            insertStudyProgramForm.setTitle("Ažuriraj studijski program");
        } else {
            fillModules(new LinkedList<>());
            insertStudyProgramForm.setTitle("Dodaj studijski program");
        }
        insertStudyProgramForm.setLocationRelativeTo(insertStudyProgramForm.getParent());
        insertStudyProgramForm.setVisible(true);
    }

    public void closeInsertStudyProgramForm() {
        insertStudyProgramForm.dispose();
    }

    public void insert(domain.Module module) throws Exception {
        List<domain.Module> modules = ((ModuleTM) insertStudyProgramForm.getTblModule().getModel()).getList();
        for (domain.Module m : modules) {
            if (m.getName().equalsIgnoreCase(module.getName())) {
                throw new Exception("Nije moguće uneti dva modula pod istim imenom na jednom studijskom programu.");
            }
        }
        ((ModuleTM) insertStudyProgramForm.getTblModule().getModel()).insert(module);
    }

    public void update(domain.Module module) throws Exception {
        List<domain.Module> modules = ((ModuleTM) insertStudyProgramForm.getTblModule().getModel()).getList();
        for (domain.Module m : modules) {
            if (m != module && m.getName().equalsIgnoreCase(module.getName())) {
                throw new Exception("Nije moguće uneti dva modula pod istim imenom na jednom studijskom programu.");
            }
        }
        ((ModuleTM) insertStudyProgramForm.getTblModule().getModel()).update(module);
    }

    private void addActionListeners() {
        insertStudyProgramForm.getTblModule().getSelectionModel().addListSelectionListener((e) -> {
            if (!e.getValueIsAdjusting()) {
                boolean selected = insertStudyProgramForm.getTblModule().getSelectedRow() != -1;
                insertStudyProgramForm.getBtnUpdateModule().setEnabled(selected);
            }
        });
        insertStudyProgramForm.insertModuleAddActionListener((ActionEvent e) -> {
            try {
                coordinator.openInsertModuleForm(null, Mode.INSERT);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(insertStudyProgramForm, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });
        insertStudyProgramForm.updateModuleAddActionListener((ActionEvent e) -> {
            try {
                int row = insertStudyProgramForm.getTblModule().getSelectedRow();
                domain.Module module = (domain.Module) ((ModuleTM) insertStudyProgramForm.getTblModule().getModel()).getValueAt(row, 0);
                coordinator.openInsertModuleForm(module, Mode.UPDATE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(insertStudyProgramForm, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });
        insertStudyProgramForm.saveAddActionListener((ActionEvent e) -> {
            try {
                String name = insertStudyProgramForm.getTxtStudyProgramName().getText();
                List<domain.Module> modules = ((ModuleTM) insertStudyProgramForm.getTblModule().getModel()).getList();
                if (mode == Mode.INSERT) {
                    StudyProgram sp = new StudyProgram(null, name, null, modules);
                    new StudyProgramValidator().checkElementaryContraints(sp);
                    coordinator.getInsertStudyLevelController().insert(sp);
                } else {
                    studyProgram.setName(name);
                    studyProgram.setModules(modules);
                    new StudyProgramValidator().checkElementaryContraints(studyProgram);
                    coordinator.getInsertStudyLevelController().update(studyProgram);
                }
                closeInsertStudyProgramForm();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(insertStudyProgramForm, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}
