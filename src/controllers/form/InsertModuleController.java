/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.form;

import coordinator.Coordinator;
import domain.Module;
import enums.Mode;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import validators.ModuleValidator;
import view.forms.InsertModuleForm;

/**
 *
 * @author kotar
 */
public class InsertModuleController {

    private final InsertModuleForm insertModuleForm;
    private final domain.Module module;
    private final Mode mode;
    private final Coordinator coordinator;

    public InsertModuleController(InsertModuleForm insertModuleForm, Module module, Mode mode) {
        this.insertModuleForm = insertModuleForm;
        this.module = module;
        this.mode = mode;
        this.coordinator = Coordinator.getInstance();
        addActionListeners();
    }

    public void openInsertModuleForm() {
        if (mode == Mode.UPDATE) {
            insertModuleForm.getTxtModuleName().setText(module.getName());
            insertModuleForm.setTitle("Ažuriraj modul");
        } else {
            insertModuleForm.setTitle("Dodaj modul");
        }
        insertModuleForm.setLocationRelativeTo(insertModuleForm.getParent());
        insertModuleForm.setVisible(true);
    }

    private void closeInsertModuleForm() {
        insertModuleForm.dispose();
    }

    private void addActionListeners() {
        insertModuleForm.saveAddActionListener((ActionEvent e) -> {
            try {
                String name = insertModuleForm.getTxtModuleName().getText();
                if (mode == Mode.INSERT) {
                    domain.Module m = new Module(null, name, null);
                    new ModuleValidator().checkElementaryContraints(m);
                    coordinator.getInsertStudyProgramController().insert(m);
                } else {
                    module.setName(name);
                    new ModuleValidator().checkElementaryContraints(module);
                    coordinator.getInsertStudyProgramController().update(module);
                }
                closeInsertModuleForm();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(insertModuleForm, ex.getMessage(), "Greška", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}
