/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.panel;

import communication.Communication;
import domain.StudentOfficer;
import java.util.List;
import view.panels.StudentOfficerPanel;

/**
 *
 * @author kotar
 */
public class StudentOfficerPanelController {

    private final StudentOfficerPanel studentOfficerPanel;
    private final Communication communication;

    public StudentOfficerPanelController(StudentOfficerPanel studentOfficerPanel) {
        this.studentOfficerPanel = studentOfficerPanel;
        this.communication = Communication.getInstance();
        addActionListeners();
    }
    
    public void fillStudentOfficers(List<StudentOfficer> studentOfficers) {
        
    }

    private void addActionListeners() {
        
    }

}
