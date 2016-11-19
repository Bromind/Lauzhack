package lauzhack.client;

import org.json.*;


public class Message {

	public static final int MAX_LENGTH = 20;
	
	private char message[];
	private Color colors[];
	private String src;
	private String dest;
	
	public Message(char message[], Color colors[], String src, String dest) {
		this.message = message;
		this.colors = colors;
		this.src = src;
		this.dest = dest;
	}
	
	public Message(String json_string) {
		try{
		JSONObject parser = new JSONObject(json_string);
		
		JSONArray messageArray = parser.getJSONArray("message");
		JSONArray colorsArray = parser.getJSONArray("colors");

		this.message = new char[20];
		this.colors = new Color[20];
		
		for(int i = 0; i < messageArray.length(); i++) {
			String s = messageArray.getString(i);
			this.message[i] = s.charAt(0);
		}
		
		for(int i = 0; i < colorsArray.length(); i++) {
			JSONObject color = colorsArray.getJSONObject(i);
			this.colors[i] = new Color(color.getInt("red"), color.getInt("green"), color.getInt("blue"));
		}
		
		this.src = parser.getString("source");
		this.dest = parser.getString("dest");
		this.colors = new Color[0];
		} catch (JSONException e) {
			System.err.println("U noob 3");
			System.err.println(e.toString());
		}
	}
	
	public String toJSON() {
		JSONObject obj = new JSONObject();
		try{
			obj.put("source", src);
			obj.put("dest", dest);
		} catch (JSONException e) {
			System.err.println("U noob");
			System.err.println(e.toString());
		}
		
		JSONArray json_colors = new JSONArray();
		JSONArray json_message = new JSONArray();
		
		for(int i = 0; i < MAX_LENGTH; i++) {
			if(i < message.length) {
				json_message.put(message[i]);
				if(i < colors.length) {
					json_colors.put(colors[i].toJSONArray());	
				}
			}
		}
		
		try {
			obj.put("message", message);
			obj.put("colors", colors);
		} catch (JSONException e) {
			System.err.println("U noob 2");
			System.err.println(e.toString());
		}
		
		return obj.toString();
	}
	
	@Override
	public String toString() {
		return "Source: " + src + "\nDest: " + dest + "\n" + message[0] + message[1];
	}
	
	public String getDest(){
		return this.dest;
	}
	
}

