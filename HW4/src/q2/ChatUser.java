package q2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.text.AttributeSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class ChatUser implements Observer
{
	String _username;
	Chat _chat;
	JFrame _newFrame = new JFrame(_username); 
	JPanel _userChatPanel;
	JPanel _southPanel;
	JTextField  _messageBox;
	JButton _sendButton;
	JTextPane _chatBox;
	String _MyLastMassage;

	public ChatUser(String username, Chat chat)
	{
		_username = username;
		_chat = chat;
	    _userChatPanel = new JPanel();
	    _userChatPanel.setLayout(new BorderLayout());
	
	    _southPanel = new JPanel();
	    _southPanel.setBackground(Color.BLUE);
	    _southPanel.setLayout(new GridBagLayout());
	
	    _messageBox = new JTextField(30);
	    _messageBox.requestFocusInWindow();
	
	    _sendButton = new JButton("Send Message");
	    _sendButton.addActionListener(new sendMessageButtonListener());
	
	    _chatBox = new JTextPane();
	    _chatBox.setEditable(false);
	    _chatBox.setFont(new Font("Serif", Font.PLAIN, 15));
	    //_chatBox.setLineWrap(true);
	
	    _userChatPanel.add(new JScrollPane(_chatBox), BorderLayout.CENTER);
	
	    GridBagConstraints left = new GridBagConstraints();
	    left.anchor = GridBagConstraints.LINE_START;
	    left.fill = GridBagConstraints.HORIZONTAL;
	    left.weightx = 512.0D;
	    left.weighty = 1.0D;
	
	    GridBagConstraints right = new GridBagConstraints();
	    right.insets = new Insets(0, 10, 0, 0);
	    right.anchor = GridBagConstraints.LINE_END;
	    right.fill = GridBagConstraints.NONE;
	    right.weightx = 1.0D;
	    right.weighty = 1.0D;
	
	    _southPanel.add(_messageBox, left);
	    _southPanel.add(_sendButton, right);
	
	    _userChatPanel.add(BorderLayout.SOUTH, _southPanel);
	
	    _newFrame.add(_userChatPanel);
	    _newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    _newFrame.setSize(470, 300);
	    _newFrame.setVisible(true);
	}
	
	public void update(Subject s)
	{
		String Massage = _chat.getLastMassage();
		if(Massage.equals(_MyLastMassage))
		{
			appendToPane(_chatBox, Massage, Color.green);
			_MyLastMassage = "";
		}
		else
		{
			appendToPane(_chatBox, Massage, Color.black);
		}
	}
	
    private void appendToPane(JTextPane tp, String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
        
        int len = tp.getText().length();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);

        tp.setText( tp.getText() + msg + "\n");
    }
	
	class sendMessageButtonListener implements ActionListener 
	{
	    public void actionPerformed(ActionEvent event)
	    {
	        if (_messageBox.getText().length() > 0) 
	        {
	        	_MyLastMassage = "<" + _username + ">:  " + _messageBox.getText();
	            _messageBox.setText("");
	            _chat.addMassage(new String(_MyLastMassage));
	        }
	        _messageBox.requestFocusInWindow();
	    }
	}

	
}