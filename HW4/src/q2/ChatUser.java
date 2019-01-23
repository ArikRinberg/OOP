package q2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.text.DefaultStyledDocument;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ChatUser implements Observer
{
	
	String _username;
	Chat _chat;
	JFrame _newFrame;
	JPanel _userChatPanel;
	JPanel _southPanel;
	JPanel _northPanel;
	JTextField  _messageBox;
	JButton _sendButton;
	JButton _toggleBold;
	boolean _isBoldText;
	JButton _toggleItalic;
	boolean _isItalicText;
	StyledDocument _doc;
	JTextPane _chatBox;
	
	ArrayList<Message> _messages;
	
	public ChatUser(String username, Chat chat)
	{
		_username = username;
		_newFrame = new JFrame(_username);
		_newFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		_chat = chat;
	    _userChatPanel = new JPanel();
	    _userChatPanel.setLayout(new BorderLayout());
	
	    _southPanel = new JPanel();
	    _southPanel.setBackground(Color.BLUE);
	    _southPanel.setLayout(new GridBagLayout());
	
	    _northPanel = new JPanel();
	    _northPanel.setBackground(Color.DARK_GRAY);
	    _northPanel.setLayout(new GridBagLayout());
	    
	    _messageBox = new JTextField(30);
	    _messageBox.requestFocusInWindow();
	
	    _sendButton = new JButton("Send Message");
	    _sendButton.addActionListener(new sendMessageButtonListener());
	    
	    _toggleBold = new JButton("B");
	    _toggleBold.addActionListener(new toggleBoldListener());
	    _isBoldText = false;
	    
	    _toggleItalic = new JButton("I");
	    _toggleItalic.addActionListener(new toggleItalicListener());
	    _isItalicText = false;

	    _doc = new DefaultStyledDocument();
	    _chatBox = new JTextPane(_doc);
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
	
	    _northPanel.add(_toggleBold, left);
	    _northPanel.add(_toggleItalic, left);
	    
	    _southPanel.add(_messageBox, left);
	    _southPanel.add(_sendButton, right);
	
	    _userChatPanel.add(BorderLayout.SOUTH, _southPanel);
	    _userChatPanel.add(BorderLayout.NORTH, _northPanel);
	
	    _newFrame.add(_userChatPanel);
	    _newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    _newFrame.setSize(470, 300);
	    _newFrame.setVisible(true);
	    
	    _messages = new ArrayList<Message>();
	}
	
	public void update(Subject s)
	{
		MessageSubject msgSubject = (MessageSubject)s;
		_messages.add(msgSubject.GetMessage());
		updatePane();
	}
	
	private void updatePane()
	{
    	SimpleAttributeSet set = new SimpleAttributeSet();
    	StyleConstants.setBold(set, _isBoldText);
    	StyleConstants.setItalic(set, _isItalicText);
    	String text = "";
    	for (Message m : _messages)
    	{
    		text += m.GetDisplayMessage() + "\n";
    	}
    	_chatBox.setText(text);
    	
		int caret = 0;
		for (Message m : _messages)
		{
			int length = m.GetDisplayMessage().length();
			if (m.GetUser().equals(_username))
			{
		        StyleConstants.setForeground(set, Color.green);
		        _doc.setCharacterAttributes(caret, length, set, true);
			}
			else
			{
		        StyleConstants.setForeground(set, Color.black);
		        _doc.setCharacterAttributes(caret, length, set, true);
			}
			caret += length + 1;
		}
	}
	
	class sendMessageButtonListener implements ActionListener 
	{
	    public void actionPerformed(ActionEvent event)
	    {
	        if (_messageBox.getText().length() > 0) 
	        {
	            _chat.sendMessage(_username, new String(_messageBox.getText()));
	            _messageBox.setText("");
	        }
	        _messageBox.requestFocusInWindow();
	    }
	}
	
	class toggleBoldListener implements ActionListener
	{
	    public void actionPerformed(ActionEvent event)
	    {
	    	_isBoldText = !_isBoldText;
	    	updatePane();
	    }
	}
	
	class toggleItalicListener implements ActionListener
	{
	    public void actionPerformed(ActionEvent event)
	    {
	    	_isItalicText = !_isItalicText;
	    	updatePane();
	    }
	}

}