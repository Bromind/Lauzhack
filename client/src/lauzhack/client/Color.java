package lauzhack.client;

import org.json.JSONArray;


public class Color {
	private int red;
	private int green;
	private int blue;
	
	public Color(int red, int green, int blue){
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public int getRed() {
		return red;
	}

	public int getGreen() {
		return green;
	}

	public int getBlue() {
		return blue;
	}
	
	public JSONArray toJSONArray() {
		JSONArray json = new JSONArray();
		json.put(red);
		json.put(green);
		json.put(blue);
		
		return json;
	}
	
	public String toString(){
		return "(" + red + ";" + green + ";" + blue + ")";  
	}
}
