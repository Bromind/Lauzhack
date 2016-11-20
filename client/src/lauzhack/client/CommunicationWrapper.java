package lauzhack.client;

public class CommunicationWrapper {

	NetworkInterface ne;
	PushingWorker pw;
	public CommunicationWrapper(PushingWorker pw, NetworkInterface ne){
		this.ne = ne;
		this.pw = pw;
	}
	
	public void sendMessage(String message, String dest, Color[] colors) {
		/*
		if (colors.length >= 1) {
			for (int i = colors.length; i < message.length(); i++) {
				colors[i] = colors[0];
			}
		}
		*/
		ne.sendMessage(message, colors, dest);
	}
	
	public void displayNext(){
		pw.displayNextMessage();
	}
	
	public void newName(String name) {
		ne.setSrc(name);
	}
}
