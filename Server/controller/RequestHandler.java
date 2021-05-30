package Server.controller;

public interface RequestHandler {
	public void handleRequest();
	public int getType();
	public int getCode();
	public Object getBody();
	public boolean hasMessage();
}
