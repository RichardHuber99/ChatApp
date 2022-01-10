import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.awt.EventQueue;
import java.awt.Font;

public class LoginWindow extends JFrame {
    private final ChatClient client;
    private static final long serialVersionUID = 1L;
    JPanel p;

    JLabel loginLabel = new JLabel("Username");
    JTextField loginField = new JTextField();

    JLabel passwordLabel = new JLabel("Password ");
    JPasswordField passwordField = new JPasswordField();

    JButton loginButton = new JButton("Login");
    JButton registerButton = new JButton("Register");


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginWindow frame = new LoginWindow();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public LoginWindow(){
        super("Login");
        this.client = new ChatClient("localhost",8818);
        client.connect();

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(235, 190, 1014, 633);
        setResizable(false);
        p = new JPanel();
        p.setBorder(new EmptyBorder(5, 5, 5, 5));
//        setContentPane(p);
        p.setLayout(null);


        JLabel windowLabel = new JLabel("Chat App Login");
        windowLabel.setFont(new Font("Times New Roman", Font.PLAIN, 42));
        windowLabel.setBounds(362, 52, 325, 50);
        p.add(windowLabel);


        loginLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        loginLabel.setBounds(342, 159, 99, 29);
        p.add(loginLabel);

        loginField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        loginField.setBounds(450, 151, 228, 50);
        p.add(loginField);
        loginField.setColumns(10);

        passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        passwordLabel.setBounds(342, 245, 99, 24);
        p.add(passwordLabel);

        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 32));
        passwordField.setBounds(450, 235, 228, 50);
        p.add(passwordField);

        loginButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
        loginButton.setBounds(399, 344, 259, 74);
        p.add(loginButton);

        registerButton.setFont(new Font("Tahoma", Font.PLAIN, 22));
        registerButton.setBounds(399, 450, 259, 74);
        p.add(registerButton);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doLogin();
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserRegistration.main(null);

            }
        });

        getContentPane().add(p, BorderLayout.CENTER);

//        pack(); //resize window for the components
        setVisible(true);
    }

    private void doLogin()  {
        String login = loginField.getText();
        String password = passwordField.getText();

        try {
            if (client.login(login,password)){
                //bring up the user list
                UserListGUI userListGUI = new UserListGUI(client);
                JFrame frame = new JFrame(login + "'s User List");


                frame.addWindowListener(new WindowAdapter()
                {
                    @Override
                    public void windowClosing(WindowEvent e)
                    {
                        super.windowClosing(e);
                        //Disconnect user upon closing userList GUI
                        try {
                            client.logoff();
                            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }

                    }
                });


                frame.setSize(400,600);
                frame.getContentPane().add(userListGUI, BorderLayout.CENTER);

                frame.setVisible(true);


            }else{
                //show error message
                System.out.println("I enter else");
                JOptionPane.showMessageDialog(this,"Invalid user or password!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
