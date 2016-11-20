package lauzhack.client;

public class CommunicationWrapper {

	NetworkInterface ne;
	PushingWorker pw;
	public CommunicationWrapper(PushingWorker pw, NetworkInterface ne){
		this.ne = ne;
		this.pw = pw;
	}
	
	public void sendMessage(String message, String dest) {
		Color[] colors = { new Color(100, 0, 0) };
		ne.sendMessage(message, colors, dest);
	}
	
	public void displayNext(){
		pw.displayNextMessage();
	}
}
