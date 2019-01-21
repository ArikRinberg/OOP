package q2;

public class Message {
	private final String _msg;
	private final String _user;
	
	public Message(String msg, String user)
	{
		_msg = msg;
		_user = user;
	}
	
	public String GetMessage()
	{
		return _msg;
	}
	
	public String GetUser()
	{
		return _user;
	}
	
	public String GetDisplayMessage()
	{
		return "<" + _user + ">:  " + _msg;
	}
}
