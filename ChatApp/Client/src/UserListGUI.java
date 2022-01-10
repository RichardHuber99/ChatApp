import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class UserListGUI extends JPanel implements UserStatusListener {

    private final ChatClient client;
    private JList<String> userListUI;
    private DefaultListModel<String> userListModel;  //pt useri online <==> UserStatusListener
    DefaultListModel<String> model = new DefaultListModel<>();
    private JList<String> allUserListUI  = new JList<>( model );;

    public UserListGUI(ChatClient client){
        this.client = client;
        this.client.addUserStatusListener(this);   //adaugam clientul in lista userilor online

        userListModel = new DefaultListModel<>();
        userListModel.addElement("Online users:");userListModel.addElement(" ");
        userListUI = new JList<>(userListModel);  //lista userilor online


        setLayout(new BorderLayout());
        add(new JScrollPane(userListUI), BorderLayout.WEST);
        add(new JScrollPane(allUserListUI), BorderLayout.CENTER);
        userListUI.setFont(new Font("Verdana", Font.PLAIN, 18));
        userListUI.setBackground(new Color(100,255,255));
        allUserListUI.setFont(new Font("Verdana", Font.PLAIN, 18));


        model.addElement("All users:");model.addElement(" ");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/chat_app", "root", "avada");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from users");
            while (resultSet.next()) {
                model.addElement(resultSet.getString("username"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        //click pe nume user --> se deschide fereastra de mesaje
        userListUI.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() > 1){
                    String login = userListUI.getSelectedValue();
                    MessageGUI messageGUI = new MessageGUI(client, login);

                    JFrame f = new JFrame("Message: " + login);
                    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    f.setSize(500,500);
                    f.getContentPane().add(messageGUI,BorderLayout.CENTER);
                    f.setVisible(true);
                }
            }
        });
    }

    @Override
    public void online(String login) {
        userListModel.addElement(login);
    }
    @Override
    public void offline(String login) {
        userListModel.removeElement(login);
    }
}
