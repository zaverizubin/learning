package com.nexusglobal.rabbit.view;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.stereotype.Component;

import com.nexusglobal.rabbit.shared.MessageDialog;

@Component
public class LoginView extends JPanel {

	//Autowired
	private LoginPresenter presenter;
	private MessageDialog messageDialog;
	
	//UI Components
	private JPanel contentPane;
	private JTextField txtConnection;
	private JButton btnConnect;

	//Global
	private String defaultURI = "amqps://snuwlowc:EgZbCF7EaGCU-hpEaMDDtlLNaD_-Tg-3@shark.rmq.cloudamqp.com/snuwlowc";
	private Runnable onLoginRunnable;
	
	public LoginView(final LoginPresenter presenter, final MessageDialog messageDialog) {
    	this.presenter = presenter;
    	this.messageDialog = messageDialog;
        buildUI();
    }
	
	private void buildUI() {
		initComponents();
		assignEventHandlers();
		this.txtConnection.setText(this.defaultURI);
	}
	
	private void assignEventHandlers() {
		this.btnConnect.addActionListener(evt ->{
			String uri = this.txtConnection.getText();
			if(uri.isEmpty()) {
				this.messageDialog.showErrorMessage(this, "Connection string is required");	
			}
			try {
				this.presenter.connectToServer(uri);
				if(this.onLoginRunnable != null) {
					this.onLoginRunnable.run();
				}
			}catch(Exception ex) {
				this.messageDialog.showErrorMessage(this, "A valid connection string is required");	
			}
			
		});
	}
	
	public void setOnLoginConsumer(final Runnable onLoginRunnable) {
		this.onLoginRunnable = onLoginRunnable;
	}

	public void initComponents() {
		setMinimumSize(new Dimension(1024, 768));
    	setPreferredSize(new Dimension(1024, 768));
    	setSize(new Dimension(1024, 768));
    	
    	JPanel panel = new JPanel();
    	panel.setSize(new Dimension(1024, 768));
    	panel.setMinimumSize(new Dimension(1024, 768));
    	panel.setPreferredSize(new Dimension(1024, 768));
    	
    	JLabel lblConnection = new JLabel("Enter URL to RabbitMQ");
    	lblConnection.setFont(new Font("Tahoma", Font.PLAIN, 16));
    	
    	txtConnection = new JTextField();
    	txtConnection.setColumns(10);
    	
    	btnConnect = new JButton("Connect");
    	GroupLayout gl_panel = new GroupLayout(panel);
    	gl_panel.setHorizontalGroup(
    		gl_panel.createParallelGroup(Alignment.TRAILING)
    			.addGroup(gl_panel.createSequentialGroup()
    				.addGap(251)
    				.addComponent(txtConnection, GroupLayout.PREFERRED_SIZE, 541, GroupLayout.PREFERRED_SIZE)
    				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    			.addGroup(gl_panel.createSequentialGroup()
    				.addGap(443)
    				.addComponent(btnConnect, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
    				.addContainerGap(223, Short.MAX_VALUE))
    			.addGroup(gl_panel.createSequentialGroup()
    				.addContainerGap(425, Short.MAX_VALUE)
    				.addComponent(lblConnection, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE)
    				.addGap(418))
    	);
    	gl_panel.setVerticalGroup(
    		gl_panel.createParallelGroup(Alignment.LEADING)
    			.addGroup(gl_panel.createSequentialGroup()
    				.addGap(216)
    				.addComponent(lblConnection)
    				.addGap(18)
    				.addComponent(txtConnection, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
    				.addGap(31)
    				.addComponent(btnConnect)
    				.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    	);
    	panel.setLayout(gl_panel);
    	
	}

	

	
}
