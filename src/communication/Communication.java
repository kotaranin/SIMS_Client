/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package communication;

import domain.Report;
import domain.StudentOfficer;
import enums.Operation;
import enums.ResultType;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.*;

/**
 *
 * @author kotar
 */
public class Communication {

    private static Communication instance;
    private Socket socket;
    private Sender sender;
    private Receiver receiver;

    private Communication() {
        try {
            this.socket = new Socket("localhost", 9000);
            this.sender = new Sender(socket);
            this.receiver = new Receiver(socket);
        } catch (IOException ex) {
            Logger.getLogger(Communication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Communication getInstance() {
        if (instance == null) {
            instance = new Communication();
        }
        return instance;
    }

    private Object sendRequest(Request request) throws IOException, ClassNotFoundException, Exception {
        sender.send(request);
        Response response = (Response) receiver.receive();
        if (response.getResultType() == ResultType.SUCCESS) {
            return response.getArgument();
        } else {
            throw response.getException();
        }
    }

    public StudentOfficer logIn(StudentOfficer studentOfficer) throws ClassNotFoundException, Exception {
        return (StudentOfficer) sendRequest(new Request(Operation.LOG_IN, studentOfficer));
    }

    public List<Report> getAllReports() throws ClassNotFoundException, Exception {
        return (List<Report>) sendRequest(new Request(Operation.GET_ALL_REPORTS, null));
    }

    public void deleteReport(Report report) throws ClassNotFoundException, Exception {
        sendRequest(new Request(Operation.DELETE_REPORT, report));
    }

}
