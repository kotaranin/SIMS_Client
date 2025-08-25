/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers.panel;

import communication.Communication;
import view.panels.HelpPanel;

/**
 *
 * @author kotar
 */
public class HelpPanelController {

    private final HelpPanel helpPanel;
    private final Communication communication;

    public HelpPanelController(HelpPanel helpPanel) {
        this.helpPanel = helpPanel;
        this.communication = Communication.getInstance();
    }

}
