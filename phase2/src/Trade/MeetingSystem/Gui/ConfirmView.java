package Trade.MeetingSystem.Gui;

import javax.swing.*;

public class ConfirmView extends AgreeConfirmView {
    @Override
    void setTextArea(JTextArea questionTextArea) {
        questionTextArea.setText("Do you confirm the current proposal? \n " +
                "Click \"OK\" to agree, click \"Cancel\" to go back to Meeting menu.");
        questionTextArea.setEditable(false);
    }

    @Override
    void setOnOK() {
        getPresenter().performAction();
        JOptionPane.showMessageDialog(null, "Confirm Successfully!\n ");
    }

}