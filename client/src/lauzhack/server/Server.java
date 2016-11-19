package lauzhack.server;

import java.io.BufferedInputStream;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;


import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import lauzhack.client.Message;

public class Server{

	public static void main(String[] args) throws Exception {
		System.out.println("start");
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
		server.createContext("/send", new SendHandler());
		server.createContext("/receive", new ReceiveHandler());
		server.setExecutor(null); // creates a default executor
		server.start();
	}

	static class ReceiveHandler implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {

			System.out.println("receive");

			// add the required response header for a PDF file
			Headers h = t.getResponseHeaders();
			h.add("Content-Type", "application/txt");

			byte[] name = new byte[10];
			// add the required response header for a PDF file
			InputStream is = t.getRequestBody();
			int read = is.read(name);
			byte[] name2 = new byte[read];
			for (int i= 0; i< read; i++){
				name2[i]=name[i];
			}
			String str = new String(name2, StandardCharsets.UTF_8);
			System.out.println(str);
			File file = new File ("c:/temp/"+str+".txt");

			byte [] bytearray  = new byte [(int)file.length()];
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			bis.read(bytearray, 0, bytearray.length);

			// ok, we are ready to send the response.
			t.sendResponseHeaders(200, file.length());
			OutputStream os = t.getResponseBody();
			os.write(bytearray,0,bytearray.length);
			os.close();
			PrintWriter writer = new PrintWriter(file);
			writer.print("");
			writer.close();
		}

	}

	static class SendHandler implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {

			System.out.println("send");

			// add the required response header for a PDF file
			Headers h = t.getResponseHeaders();
			h.add("Content-Type", "application/txt");
			String read_byte = new String();
			int read = 0;
			byte[] name = new byte[10];
			// add the required response header for a PDF file
			InputStream is = t.getRequestBody();

			do{
				read = is.read();
				if(read != -1){
					read_byte += (char)read;
				}
			}while(read != -1);
			Message message = new Message(read_byte);	
			String dest = message.getDest();

			//File file = new File ("c:/temp/"+dest+".txt");
			//byte [] bytearray  = new byte [(int)file.length()];
			//FileOutputStream fos = new FileOutputStream(file);
			//BufferedOutputStream bos = new BufferedOutputStream(fos);

			FileWriter fw = new FileWriter("c:/temp/"+dest+".txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			PrintWriter out = new PrintWriter(bw);
			//TODO: be careful we erase!!
			//bos.write(bytearray, 0, bytearray.length);
			out.println(read_byte);
			out.close();
			// ok, we are ready to send the response.
			t.sendResponseHeaders(200, 0);
			OutputStream os = t.getResponseBody();
			//os.write(bytearray,0,bytearray.length);
			os.close();

		}
	}
}
