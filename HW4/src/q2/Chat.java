package q2;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Chat
{

    private String _appName = "student chat";
    private JTextField _usernameChooser;
    private JFrame _preFrame;
    private Set<ChatUser> _users =  new HashSet<ChatUser>();
    private Set<String> _userNames = new HashSet<String>();
    private MessageSubject _subject = new MessageSubject(); 

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run() 
            {
                try
                {
                    UIManager.setLookAndFeel(UIManager
                            .getSystemLookAndFeelClassName());
                } 
                catch (Exception e) 
                {
                    e.printStackTrace();
                }
                Chat chat = new Chat();
                chat.preDisplay();
            }
        });
    }
    
    public void preDisplay() 
    {
        //newFrame.setVisible(false);
        _preFrame = new JFrame(_appName);
        _usernameChooser = new JTextField(15);
        JLabel chooseUsernameLabel = new JLabel("Pick a username:");
        JButton enterServer = new JButton("Enter Chat");
        enterServer.addActionListener(new CreateUserButtonListener());
        JPanel prePanel = new JPanel(new GridBagLayout());

        GridBagConstraints preRight = new GridBagConstraints();
        preRight.insets = new Insets(0, 0, 0, 10);
        preRight.anchor = GridBagConstraints.EAST;
        GridBagConstraints preLeft = new GridBagConstraints();
        preLeft.anchor = GridBagConstraints.WEST;
        preLeft.insets = new Insets(0, 10, 0, 10);
        preRight.fill = GridBagConstraints.HORIZONTAL;
        preRight.gridwidth = GridBagConstraints.REMAINDER;

        prePanel.add(chooseUsernameLabel, preLeft);
        prePanel.add(_usernameChooser, preRight);
        _preFrame.add(BorderLayout.CENTER, prePanel);
        _preFrame.add(BorderLayout.SOUTH, enterServer);
        _preFrame.setSize(500, 300);
        _preFrame.setVisible(true);
    }
    
    public void addUser(String userName)
    {
    	if(_userNames.contains(userName))
    	{
    		System.out.println("userName already exists");
    		return;
    	}
    	ChatUser newUser = new ChatUser(userName, this);
    	_users.add(newUser);
    	_userNames.add(userName);
    	_subject.attach(newUser);
    }
    
    public void sendMessage(String user, String msg)
    {
    	_subject.SetMessage(new Message(msg, user));
    	_subject.notifyAllObservers();
    }
    
    class CreateUserButtonListener implements ActionListener 
    {
        public void actionPerformed(ActionEvent event) 
        {
            String username = _usernameChooser.getText();
            if (username.length() > 0) 
            {
            	addUser(username);
            	_usernameChooser.setText("");
            }
        }
    }
}