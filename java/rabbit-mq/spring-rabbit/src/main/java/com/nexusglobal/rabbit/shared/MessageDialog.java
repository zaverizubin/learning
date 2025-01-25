package com.nexusglobal.rabbit.shared;

import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageDialog {
	
	public void showInformationMessage(final java.awt.Component component, final String message) {
		JOptionPane.showMessageDialog(component, message, getTranslation("Information"), JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void showSuccessMessage(final java.awt.Component component, final String message) {
		JOptionPane.showMessageDialog(component, message, getTranslation("Success"), JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void showErrorMessage(final java.awt.Component component, final String message) {
		JOptionPane.showMessageDialog(component, message, getTranslation("Error"), JOptionPane.ERROR_MESSAGE);
	}
	
	public void showConfirmMessage(final java.awt.Component component, final String message, final Runnable onOkClick) {
		int result = JOptionPane.showConfirmDialog(component, message, getTranslation("Confirm"), JOptionPane.OK_CANCEL_OPTION);
		if(result == JOptionPane.OK_OPTION && onOkClick != null) {
			onOkClick.run();
		}
	}
	
	public int showConfirmMessage(final java.awt.Component component, final String message) {
		return JOptionPane.showConfirmDialog(component, message, getTranslation("Confirm"), JOptionPane.OK_CANCEL_OPTION);
	}
	
	protected String getTranslation(String message) {
		return message;
	}
}
