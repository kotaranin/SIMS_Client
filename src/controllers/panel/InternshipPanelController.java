/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.panel;

import communication.Communication;
import domain.Internship;
import java.util.List;
import view.panels.InternshipPanel;

/**
 *
 * @author kotar
 */
public class InternshipPanelController {

    private final InternshipPanel internshipPanel;
    private final Communication communication;

    public InternshipPanelController(InternshipPanel internshipPanel) {
        this.internshipPanel = internshipPanel;
        this.communication = Communication.getInstance();
        addActionListeners();
    }

    public void fillInternships(List<Internship> internships) {
        
    }

    private void addActionListeners() {
        
    }

}
