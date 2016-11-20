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
		try {
			JSONObject parser = new JSONObject(json_string);

			JSONArray colorsArray = parser.getJSONArray("colors");

			String mes = parser.getString("message");
			
			this.message = mes.toCharArray();
			this.colors = new Color[20];

			this.colors = null;
			if (colorsArray.length() > 0) {
				this.colors = new Color[colorsArray.length()];
				for (int i = 0; i < colorsArray.length(); i++) {
					JSONObject color = colorsArray.getJSONObject(i);
					this.colors[i] = new Color(color.getInt("red"), color.getInt("green"), color.getInt("blue"));
				}
			}

			this.src = parser.getString("source");
			this.dest = parser.getString("dest");
		} catch (JSONException e) {
			System.err.println("Error parsing JSON: " + json_string);
			System.err.println(e.toString());
		}
	}
	
	public String toJSON() {
		JSONObject obj = new JSONObject();
		try{
			obj.put("source", src);
			obj.put("dest", dest);
			obj.put("message", this.getMessageAsString());
		} catch (JSONException e) {
			System.err.println("U noob");
			System.err.println(e.toString());
		}
		try {
			obj.put("message", this.getMessageAsString());
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
	
	public char[] getMessage() {
		return message;
	}
	
	public Color[] getColor() {
		return colors;
	}
	
	public String getMessageAsString(){
		StringBuilder sb = new StringBuilder();
		for(char c: message) {
			sb.append(c);
		}
		return sb.toString();
	}
}

