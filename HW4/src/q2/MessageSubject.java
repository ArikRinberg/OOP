package q2;

public class MessageSubject extends Subject{
	private Message _message;
	
	public MessageSubject() {
		super();
	}
	
	public void SetMessage(Message msg)
	{
		this._message = msg;
	}
	
	public Message GetMessage()
	{
		return this._message;
	}
}
