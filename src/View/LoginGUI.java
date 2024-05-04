package View;
import java.awt.EventQueue;
import javax.swing.ImageIcon;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import Helper.*;
import Model.Bashekim;
import Model.Doctor;
import Model.Hasta;

public class LoginGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel w_pane;
	private JTextField fldHastaTc;
	private JTextField fldDoctorTc;
	private JPasswordField fldDoctorPass;
	private DBConnection conn = new DBConnection();
	private JPasswordField fld_hastaPass;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginGUI() {
		setResizable(false);
		setTitle("Hastane Yönetim Sistemi");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 400);
		w_pane = new JPanel();
		w_pane.setBackground(Color.WHITE);
		w_pane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(w_pane);
		w_pane.setLayout(null);
		
		JLabel lbl_logo = new JLabel(new ImageIcon(getClass().getResource("medicine.png")));
		lbl_logo.setBounds(187, 11, 100, 55);
		w_pane.add(lbl_logo);
		
		JLabel lblNewLabel = new JLabel("Hastane Yönetim Sistemine Hoşgeldiniz");
		lblNewLabel.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lblNewLabel.setBounds(84, 79, 306, 28);
		w_pane.add(lblNewLabel);
		
		JTabbedPane w_tabpane = new JTabbedPane(JTabbedPane.TOP);
		w_tabpane.setBounds(10, 145, 464, 192);
		w_pane.add(w_tabpane);
		
		JPanel w_hastaLogin = new JPanel();
		w_hastaLogin.setBackground(Color.WHITE);
		w_tabpane.addTab("Hasta Girişi", null, w_hastaLogin, null);
		w_hastaLogin.setLayout(null);
		
		JLabel lblTcNumaraniz = new JLabel("T.C. numaranız :");
		lblTcNumaraniz.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lblTcNumaraniz.setBounds(10, 26, 219, 28);
		w_hastaLogin.add(lblTcNumaraniz);
		
		JLabel lbsifreniz = new JLabel("Şifreniz :");
		lbsifreniz.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lbsifreniz.setBounds(10, 65, 219, 28);
		w_hastaLogin.add(lbsifreniz);
		
		fldHastaTc = new JTextField();
		fldHastaTc.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		fldHastaTc.setBounds(229, 26, 143, 28);
		w_hastaLogin.add(fldHastaTc);
		fldHastaTc.setColumns(10);
		
		JButton btnRegister = new JButton("Kayıt Ol");
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterGUI rGUI = new RegisterGUI();
				rGUI.setVisible(true);
				dispose();
			}
		});
		btnRegister.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		btnRegister.setBounds(10, 106, 194, 47);
		w_hastaLogin.add(btnRegister);
		
		JButton btnHastaLogin = new JButton("Giriş Yap");
		btnHastaLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fldHastaTc.getText().length() == 0 || fld_hastaPass.getText().length() == 0) {
					Helper.showMsg("fill");
				}
				else {
					boolean key = true;
					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM user");
						while(rs.next()) {
							if(fldHastaTc.getText().equals(rs.getString("tcno")) && fld_hastaPass.getText().equals(rs.getString("password"))) {
								if(rs.getString("type").equals("hasta")) {
									Hasta hasta = new Hasta();
									hasta.setId(rs.getInt("id"));
									hasta.setPassword("password");
									hasta.setTcno(rs.getString("tcno"));
									hasta.setName(rs.getString("name"));
									hasta.setType(rs.getString("type"));
									HastaGUI hGUI = new HastaGUI(hasta);// bashekim GUI sini acıyoruz 
									hGUI.setVisible(true); 
									dispose(); // bu GUI yi kapatıyoruz
									key = false;
								}
																								
							}
						}
						
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
					if(key) {
						Helper.showMsg("Böyle bir hasta bulunamadı, lütfen kayıt olunuz !");
					}
				}
			}
		});
		btnHastaLogin.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		btnHastaLogin.setBounds(214, 104, 194, 49);
		w_hastaLogin.add(btnHastaLogin);
		
		fld_hastaPass = new JPasswordField();
		fld_hastaPass.setBounds(229, 65, 143, 28);
		w_hastaLogin.add(fld_hastaPass);
		
		JPanel w_doctorLogin = new JPanel();
		w_doctorLogin.setBackground(Color.WHITE);
		w_tabpane.addTab("Doktor Girişi", null, w_doctorLogin, null);
		w_doctorLogin.setLayout(null);
		
		JLabel lblTcNumaraniz_1 = new JLabel("T.C. numaranız :");
		lblTcNumaraniz_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lblTcNumaraniz_1.setBounds(20, 26, 219, 28);
		w_doctorLogin.add(lblTcNumaraniz_1);
		
		fldDoctorTc = new JTextField();
		fldDoctorTc.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		fldDoctorTc.setColumns(10);
		fldDoctorTc.setBounds(239, 26, 143, 28);
		w_doctorLogin.add(fldDoctorTc);
		
		JLabel lbsifreniz_1 = new JLabel("Şifreniz :");
		lbsifreniz_1.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		lbsifreniz_1.setBounds(20, 65, 219, 28);
		w_doctorLogin.add(lbsifreniz_1);
		
		JButton btnDoctorLogin = new JButton("Giriş Yap");
		btnDoctorLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(fldDoctorTc.getText().length() == 0 || fldDoctorPass.getText().length() == 0) {
					Helper.showMsg("fill");
				}
				else {
					
					try {
						Connection con = conn.connDb();
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery("SELECT * FROM user");
						while(rs.next()) {
							if(fldDoctorTc.getText().equals(rs.getString("tcno")) && fldDoctorPass.getText().equals(rs.getString("password"))) {
								if(rs.getString("type").equals("bashekim")) {
									Bashekim bhekim = new Bashekim();
									bhekim.setId(rs.getInt("id"));
									bhekim.setPassword("password");
									bhekim.setTcno(rs.getString("tcno"));
									bhekim.setName(rs.getString("name"));
									bhekim.setType(rs.getString("type"));
									BashekimGUI bGUI = new BashekimGUI(bhekim);// bashekim GUI sini acıyoruz 
									bGUI.setVisible(true); 
									dispose(); // bu GUI yi kapatıyoruz
								}
								
								if(rs.getString("type").equals("doktor")) {
									Doctor doctor = new Doctor();
									doctor.setId(rs.getInt("id"));
									doctor.setPassword("password");
									doctor.setTcno(rs.getString("tcno"));
									doctor.setName(rs.getString("name"));
									doctor.setType(rs.getString("type"));
									DoctorGUI dGUI = new DoctorGUI(doctor);
									dGUI.setVisible(true);
									dispose();
								}
								
							}
						}
						
					} catch (SQLException e1) {
						
						e1.printStackTrace();
					}
				}
			}
		});
		btnDoctorLogin.setBackground(Color.WHITE);
		btnDoctorLogin.setFont(new Font("Yu Gothic UI Semibold", Font.PLAIN, 17));
		btnDoctorLogin.setBounds(20, 104, 398, 47);
		w_doctorLogin.add(btnDoctorLogin);
		
		fldDoctorPass = new JPasswordField();
		fldDoctorPass.setBounds(239, 65, 143, 28);
		w_doctorLogin.add(fldDoctorPass);
	}
}
