package lauzhack.client;

public class client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Color colors[] = {new Color(0, 0, 0)};
		
		Message ms = new Message("test".toCharArray(), colors, "toto", "tata");
		
		System.out.println(ms.toJSON());
		
		Message ms2 = new Message(ms.toJSON());
		System.out.println(ms2);
	}

}
