import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;



public class UserRegistration extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField email;
    private JTextField username;
    private JPasswordField passwordField;
    private JButton registerButton;

    /**
     * Launch
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    UserRegistration frame = new UserRegistration();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame
     */

    public UserRegistration() {

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        setBounds(235, 190, 1014, 597);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblNewUserRegister = new JLabel("New User Register");
        lblNewUserRegister.setFont(new Font("Times New Roman", Font.PLAIN, 42));
        lblNewUserRegister.setBounds(362, 52, 325, 50);
        contentPane.add(lblNewUserRegister);

        username = new JTextField();
        username.setFont(new Font("Tahoma", Font.PLAIN, 32));
        username.setBounds(450, 151, 228, 50);
        contentPane.add(username);
        username.setColumns(10);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblUsername.setBounds(342, 159, 99, 29);
        contentPane.add(lblUsername);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblPassword.setBounds(342, 245, 99, 24);
        contentPane.add(lblPassword);


        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        passwordField.setBounds(450, 235, 228, 50);
        contentPane.add(passwordField);


        JLabel lblEmailAddress = new JLabel("Email\r\n address");
        lblEmailAddress.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblEmailAddress.setBounds(320, 324, 124, 36);
        contentPane.add(lblEmailAddress);


        email = new JTextField();
        email.setFont(new Font("Tahoma", Font.PLAIN, 32));
        email.setBounds(450, 320, 228, 50);
        contentPane.add(email);
        email.setColumns(10);



        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String emailId = email.getText();
                String userName = username.getText();
                String password = passwordField.getText();
                String key = "key5432155555";


                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat_app", "root", "avada");

                    String query = "insert into users (username, password, email) values(  '" + userName + "',aes_encrypt('" + password + "','" + key +  "'),'"+ emailId +  "')";
                    Statement sta = connection.createStatement();
                    int x = 0;
                    try {
                        x = sta.executeUpdate(query);
                        System.out.println(x);
                    }catch (Exception e1){
                        JOptionPane.showMessageDialog(registerButton,
                                "Username already exists");
                        e1.getSuppressed();
                        e1.printStackTrace();
                    }
                    if (x == 1) {
                        JOptionPane.showMessageDialog(registerButton,
                                "Welcome, " + userName + " \nYour account was successfully created");
                    }
                    connection.close();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        registerButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
        registerButton.setBounds(399, 447, 259, 74);
        contentPane.add(registerButton);
    }


}