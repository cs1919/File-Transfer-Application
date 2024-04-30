import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Desktop;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JProgressBar;
import java.awt.TextField;
import java.awt.SystemColor;
import javax.swing.UIManager;

public class welcome {

	private JFrame frame;
	private CardLayout cardLayout;
	private JPasswordField passwordField;
	private JTextField textField;
	private JPanel card1lgn;
	private JPanel card2sgn;
	private JPanel panel;
	private JTextField textField_1;
	private JTextField textField_2;
	private JPanel card3send;
	private CardLayout tnrcdlayout;
	private JPanel panel_1;
	private JPanel rcvpnl;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private static JTextField textField_6;
	private JFileChooser flchooser;
	JTextArea jtarea;
	String stflname;
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					welcome window = new welcome();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public welcome() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setSize(720,720);
		frame.getContentPane().setBackground(SystemColor.activeCaption);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		JLabel lblNewLabel = new JLabel("File Transfer App");
		lblNewLabel.setFont(new Font("Microsoft JhengHei UI", Font.BOLD, 20));
		lblNewLabel.setBounds(264, 26, 174, 52);
		frame.getContentPane().add(lblNewLabel);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(163, 76, 391, 10);
		frame.getContentPane().add(separator);
		
		cardLayout=new CardLayout();
		
		panel = new JPanel();
		panel.setBounds(10, 105, 686, 501);
		frame.getContentPane().add(panel);
		panel.setLayout(cardLayout);
		
		
		
		card1lgn=new JPanel();
		card1lgn.setBackground(SystemColor.textHighlight);
		card1lgn.setLayout(null);
		
		
		
		JLabel lblNewLabel_1 = new JLabel("User ID: ");
		lblNewLabel_1.setFont(new Font("Bahnschrift", Font.BOLD, 14));
		lblNewLabel_1.setBounds(203, 85, 63, 35);
		card1lgn.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Password: ");
		lblNewLabel_1_1.setFont(new Font("Bahnschrift", Font.BOLD, 14));
		lblNewLabel_1_1.setBounds(185, 130, 81, 35);
		card1lgn.add(lblNewLabel_1_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(276, 132, 199, 29);
		card1lgn.add(passwordField);
		
		textField = new JTextField();
		textField.setBounds(276, 87, 199, 29);
		card1lgn.add(textField);
		textField.setColumns(10);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Show Password");
		chckbxNewCheckBox.setFont(new Font("Bahnschrift", Font.PLAIN, 14));
		chckbxNewCheckBox.setBounds(276, 167, 127, 27);
		card1lgn.add(chckbxNewCheckBox);
		
		JButton btnNewButton = new JButton("Log In");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String idstr=textField.getText();
				char[] passstr=passwordField.getPassword();
				if(idstr.equals("")||passstr.length==0) {
					JOptionPane.showMessageDialog(card1lgn, "Enter id and password correctly");
				}else {
				int accid=Integer.valueOf(idstr);
				int password=Integer.valueOf(new String(passstr));
				if(db_methods.login(accid, password)) {
					textField.setText("");
					passwordField.setText("");;
					JOptionPane.showMessageDialog(card1lgn, "Login Successfull");
					cardLayout.show(panel, "send_page_card");
				}else {
					JOptionPane.showMessageDialog(card1lgn, "Wrong Password\\Id");
				}
				}
				
			}
		});
		btnNewButton.setFont(new Font("Bahnschrift", Font.BOLD, 14));
		btnNewButton.setBounds(244, 214, 109, 35);
		card1lgn.add(btnNewButton);
		
		JLabel lblNewLabel_1_2 = new JLabel("Don't have an account? ");
		lblNewLabel_1_2.setFont(new Font("Bahnschrift", Font.BOLD, 14));
		lblNewLabel_1_2.setBounds(203, 259, 168, 35);
		card1lgn.add(lblNewLabel_1_2);
		
		JButton btnSignIn = new JButton("Sign In");
		btnSignIn.setFont(new Font("Bahnschrift", Font.BOLD, 14));
		btnSignIn.setBounds(368, 259, 109, 35);
		card1lgn.add(btnSignIn);
		
		JButton btnNewButton_2 = new JButton("Send");
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton_2.setBounds(185, 411, 109, 40);
		card1lgn.add(btnNewButton_2);
		
		JButton btnNewButton_2_1 = new JButton("Receive");
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panel, "send_page_card");
				tnrcdlayout.show(panel_1, "name_286468410705700");
			}
		});
		btnNewButton_2_1.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnNewButton_2_1.setBounds(349, 411, 109, 40);
		card1lgn.add(btnNewButton_2_1);
		
		JLabel lblNewLabel_1_2_1_1 = new JLabel("Send/Receive without an account(Not Recommended)");
		lblNewLabel_1_2_1_1.setFont(new Font("Bahnschrift", Font.BOLD, 14));
		lblNewLabel_1_2_1_1.setBounds(130, 366, 384, 35);
		card1lgn.add(lblNewLabel_1_2_1_1);
		
		card2sgn=new JPanel();
		card2sgn.setBackground(new Color(176, 224, 230));
		card2sgn.setLayout(null);
		
		JLabel lblNewLabel_1_3 = new JLabel("Name:");
		lblNewLabel_1_3.setFont(new Font("Bahnschrift", Font.BOLD, 14));
		lblNewLabel_1_3.setBounds(217, 84, 63, 35);
		card2sgn.add(lblNewLabel_1_3);
		
		JLabel lblNewLabel_1_3_1 = new JLabel("Phone No: ");
		lblNewLabel_1_3_1.setFont(new Font("Bahnschrift", Font.BOLD, 14));
		lblNewLabel_1_3_1.setBounds(193, 120, 79, 35);
		card2sgn.add(lblNewLabel_1_3_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(290, 86, 199, 29);
		card2sgn.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(290, 122, 199, 29);
		card2sgn.add(textField_2);
		
		JButton btnCreateAccount = new JButton("Create Account");
		btnCreateAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String accname=textField_1.getText();
				String phnumber=textField_2.getText();
				String passwordstr=textField_5.getText();
				
				if(accname.equals("")||passwordstr.equals("")) {
					JOptionPane.showMessageDialog(card2sgn, "fill all the values");
				}else {
				int password=Integer.parseInt(passwordstr);
				int accid=db_methods.createAccount(accname,password);
				if(accid>0) {
					JOptionPane.showMessageDialog(card2sgn, "Account Created Successfully");
					textField_1.setText("");
					textField_2.setText("");
					textField_5.setText("");
                    JOptionPane.showMessageDialog(card2sgn,"Your Account id is: "+accid,accname, JOptionPane.INFORMATION_MESSAGE);
					cardLayout.show(panel, "login_page_card");
				}else {
					JOptionPane.showMessageDialog(card2sgn,"Problem in creating account\n account already exists");
				}
			}}
		});
		btnCreateAccount.setFont(new Font("Bahnschrift", Font.BOLD, 14));
		btnCreateAccount.setBounds(217, 208, 164, 35);
		card2sgn.add(btnCreateAccount);
		
		JLabel lblNewLabel_1_3_1_1 = new JLabel("Password:");
		lblNewLabel_1_3_1_1.setFont(new Font("Bahnschrift", Font.BOLD, 14));
		lblNewLabel_1_3_1_1.setBounds(193, 163, 79, 35);
		card2sgn.add(lblNewLabel_1_3_1_1);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(290, 161, 199, 29);
		card2sgn.add(textField_5);
		
		JButton btnLogIn = new JButton("Log In");
		btnLogIn.setFont(new Font("Bahnschrift", Font.BOLD, 14));
		btnLogIn.setBounds(337, 253, 164, 35);
		card2sgn.add(btnLogIn);
		
		JLabel lblNewLabel_1_3_1_1_1 = new JLabel("Already have an account?");
		lblNewLabel_1_3_1_1_1.setFont(new Font("Bahnschrift", Font.BOLD, 14));
		lblNewLabel_1_3_1_1_1.setBounds(152, 253, 187, 35);
		card2sgn.add(lblNewLabel_1_3_1_1_1);
		
		card3send=new JPanel();
		card3send.setBackground(SystemColor.textHighlight);
		card3send.setLayout(null);
		
		JButton btnsendcd3 = new JButton("Send");
		btnsendcd3.setFont(new Font("Bahnschrift", Font.BOLD, 18));
		btnsendcd3.setBounds(10, 158, 109, 35);
		card3send.add(btnsendcd3);
		
		btnsendcd3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tnrcdlayout.show(panel_1, "sndpnl");
			}
		});
		
		
		
		JButton btnReceive = new JButton("Receive");
		btnReceive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tnrcdlayout.show(panel_1,"name_286468410705700");
			}
		});
		btnReceive.setFont(new Font("Bahnschrift", Font.BOLD, 18));
		btnReceive.setBounds(10, 206, 109, 35);
		card3send.add(btnReceive);
		
		tnrcdlayout=new CardLayout();
		
		panel_1 = new JPanel();
		panel_1.setBounds(129, 41, 547, 404);
		card3send.add(panel_1);
		panel_1.setLayout(tnrcdlayout);
		
		JLabel lblNewLabel_2 = new JLabel("Click to send or Receive FIles");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(144, 10, 462, 21);
		card3send.add(lblNewLabel_2);
		
		JButton btnNewButton_3 = new JButton("Home");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panel,"login_page_card");
			}
		});
		btnNewButton_3.setFont(new Font("Bahnschrift", Font.BOLD, 17));
		btnNewButton_3.setBounds(10, 383, 91, 35);
		card3send.add(btnNewButton_3);
		
		
		
		
		addpanels();
		insidepanels();
		
		btnSignIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panel, "signup_page_card");
			}
		});
		
		btnLogIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panel, "login_page_card");
			}
		});
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("HI love");
				cardLayout.show(panel, "send_page_card");
				
			}
		});
		
	}
	
	void insidepanels() {
		JPanel sndpnl=new JPanel();
		sndpnl.setBackground(SystemColor.activeCaption);
		
		
		panel_1.add(sndpnl,"sndpnl");
		sndpnl.setLayout(null);
		
		JButton btnNewButton_1 = new JButton("Select file");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				flchooser=new JFileChooser(FileSystemView.getFileSystemView());
				if(JFileChooser.APPROVE_OPTION==flchooser.showOpenDialog(panel_1)) {
					String filechoosen=flchooser.getSelectedFile().getAbsolutePath();
					textField_3.setText(filechoosen);
				}
				
			}
		});
		btnNewButton_1.setFont(new Font("Bahnschrift", Font.BOLD, 14));
		btnNewButton_1.setBounds(36, 109, 121, 29);
		sndpnl.add(btnNewButton_1);
		
		textField_3 = new JTextField();
		textField_3.setToolTipText("Selected file");
		textField_3.setColumns(10);
		textField_3.setBounds(181, 108, 311, 29);
		sndpnl.add(textField_3);
		
		
		JButton btnSendin = new JButton("Send");
		btnSendin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String filename=textField_3.getText();
				String ipid=textField_4.getText();
				if(ipid.length()<6) {
					System.out.println("Line 368 it might be an id");
					try {
					ipid=db_methods.getIpadd(Integer.valueOf(ipid));
					}
					catch(Exception e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(sndpnl, "Not a valid id or ip address");
					}
				}
				boolean flnmsnt=fle_transfer.sendFilename(ipid, filename);
				if(flnmsnt) {
					System.out.println("Line 402 file name sent scfl");
					boolean rcvrack=fle_transfer.waitForReceiverAck();
					if(rcvrack) {
						System.out.println("receiver accepted file transfer line 405");
						boolean mnbfl=fle_transfer.sendFile(filename, ipid);
						if(mnbfl) {
							JOptionPane.showMessageDialog(sndpnl, "File Sent Successfully");
							textField_3.setText("");
							textField_4.setText("");
						}else {
							System.out.println("line 410 problem in sendfile");
						}
					}else {
						System.out.println("Problem in 411 wait for ack");
					}
				}else {
					System.out.println("line 412 problem sending file name");
					JOptionPane.showMessageDialog(panel, "Wrong Ip address or id");
				}
			}
		});
		btnSendin.setFont(new Font("Bahnschrift", Font.BOLD, 18));
		btnSendin.setBounds(118, 203, 100, 35);
		sndpnl.add(btnSendin);
		
		JLabel lblNewLabel_1 = new JLabel("Receiver ID\\ip: ");
		lblNewLabel_1.setFont(new Font("Bahnschrift", Font.BOLD, 15));
		lblNewLabel_1.setBounds(46, 148, 116, 35);
		sndpnl.add(lblNewLabel_1);
		
		textField_4 = new JTextField();
		textField_4.setToolTipText("Selected file");
		textField_4.setColumns(10);
		textField_4.setBounds(180, 147, 255, 40);
		sndpnl.add(textField_4);
		
		
		
		rcvpnl = new JPanel();
		rcvpnl.setBackground(SystemColor.activeCaption);
		rcvpnl.setLayout(null);
		panel_1.add(rcvpnl, "name_286468410705700");
		
		textField_6 = new JTextField();
		textField_6.setBounds(228, 155, 212, 42);
		rcvpnl.add(textField_6);
		
		JButton btnReceive = new JButton("Receive");
		btnReceive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				stflname=fle_transfer.receiveFilename();
				if(stflname.length()>0) {
					System.out.println("line 285 file name received :"+stflname);
					int opt=JOptionPane.showConfirmDialog(rcvpnl, "you want to receive this file :"+stflname,null, JOptionPane.YES_NO_OPTION );
					if(opt==JOptionPane.YES_OPTION) {
						System.out.println("you accepted this file line 288");
						String filepath="";
						JFileChooser flc=new JFileChooser(FileSystemView.getFileSystemView());
						flc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						if(JFileChooser.APPROVE_OPTION==flc.showOpenDialog(panel_1)) {
							filepath=flc.getSelectedFile().getAbsolutePath();
							textField_3.setText(filepath);
							fle_transfer.sendReceiverAck(true);
							if(fle_transfer.receiveFile(stflname,filepath)) {
								int a=JOptionPane.showConfirmDialog(rcvpnl, "File Received Successfully\n You want to open the file?",null, JOptionPane.YES_NO_OPTION);
								if(a==JOptionPane.YES_OPTION) {
									try {
							            File file = new File(filepath,stflname);
							            Desktop.getDesktop().open(file);
							        } catch (IOException e1) {
							            e1.printStackTrace(); // Handle any IO exceptions
							        }
								}
							}
						}else {
							System.out.println("Select dirs only line 297");
						}
						
					}else {
						System.out.println("You reject line 291");
					}
				}
			}
		});
		btnReceive.setFont(new Font("Bahnschrift", Font.BOLD, 18));
		btnReceive.setBounds(160, 97, 128, 35);
		rcvpnl.add(btnReceive);
		
		JLabel lblNewLabel_3 = new JLabel("Path to Save:");
		lblNewLabel_3.setFont(new Font("Sylfaen", Font.BOLD, 18));
		lblNewLabel_3.setBounds(103, 161, 115, 32);
		rcvpnl.add(lblNewLabel_3);
		
		
		
	}
	
	void addpanels() {
		panel.add(card1lgn,"login_page_card");
		panel.add(card2sgn,"signup_page_card");
		panel.add(card3send,"send_page_card");
		
	}
	static String getdire() {
		return textField_6.getText();
	}
}
