/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.panel;

import communication.Communication;
import domain.Student;
import java.util.List;
import view.panels.StudentPanel;

/**
 *
 * @author kotar
 */
public class StudentPanelController {

    private final StudentPanel studentPanel;
    private final Communication communication;

    public StudentPanelController(StudentPanel studentPanel) {
        this.studentPanel = studentPanel;
        this.communication = Communication.getInstance();
        addActionListeners();
    }
    
    public void fillStudents(List<Student> students) {
        
    }

    private void addActionListeners() {

    }

}
