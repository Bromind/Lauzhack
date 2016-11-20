package lauzhack.gui;


//Using AWT container and component classes
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.*;  // Using AWT event classes and listener interfaces

import java.util.LinkedList;
import java.util.List;

import lauzhack.client.CommunicationWrapper;

import lauzhack.client.Color;
 
// An AWT GUI program inherits from the top-level container java.awt.Frame
public class GUI extends Frame implements ActionListener {
   private Label lab_dest;     // Declare input Label
   private Label lab_message;
   
   private Button but_send;
   private Button but_user_name;
   private Button but_read;
   private Button but_color;

   private TextField text_dest;  // Declare input TextField
   private TextField text_user_name;
   private TextField text_message;
   private TextField text_color;
   
   private Panel pseudo_panel;
   private Panel message_panel;
   private Panel button_panel;
   private CommunicationWrapper cw;
   private Color[] col = {new Color(100,0,0)};
   // Constructor to setup the GUI components and event handlers
   public GUI(CommunicationWrapper cw) {
	  this.cw = cw;
      setLayout(new BorderLayout());
      pseudo_panel = new Panel();
      pseudo_panel.setLayout(new GridLayout());
      message_panel = new Panel();
      message_panel.setLayout(new FlowLayout());
      button_panel = new Panel();
      button_panel.setLayout(new GridLayout());
         // "super" Frame (a Container) sets layout to FlowLayout, which arranges
         // the components from left-to-right, and flow to next row from top-to-bottom.
      but_color = new Button("Red");
      but_color.setActionCommand("Choose Color");
      but_send = new Button("Send");
      but_send.setActionCommand("Send Message");
      but_read = new Button("Read");
      but_read.setActionCommand("Read Message");
      but_user_name = new Button("Choose pseudo");
      but_user_name.setActionCommand("Pseudo");
      lab_dest = new Label("To: ",Label.LEFT); // Construct Label
      message_panel.add(lab_dest);    
      text_dest = new TextField(10);// "super" Frame adds Label
      message_panel.add(text_dest);
      lab_message = new Label("Message: ",Label.LEFT);
      message_panel.add(lab_message);
      text_message = new TextField(20);
      message_panel.add(text_message);
      
      pseudo_panel.add(but_user_name,BorderLayout.WEST);
      text_user_name = new TextField(10);
      text_user_name.setText("user");
      pseudo_panel.add(text_user_name, BorderLayout.CENTER);
      pseudo_panel.add(but_color, BorderLayout.EAST);
      add(pseudo_panel,BorderLayout.NORTH);
     // add(lab_color);
      text_color = new TextField(30);
      //add(text_color);
      
    //  button_panel.add(but_send);
      
      text_dest.addActionListener(this);
      text_message.addActionListener(this);
      text_user_name.addActionListener(this);
      text_color.addActionListener(this);
      but_user_name.addActionListener(new ButtonClickListener());
      but_send.addActionListener( new ButtonClickListener());
      but_read.addActionListener(new ButtonClickListener());
      but_color.addActionListener(new ButtonClickListener());
      button_panel.add(but_read);
      button_panel.add(but_send);
      add(message_panel, BorderLayout.CENTER);
      add(button_panel, BorderLayout.SOUTH);
      
      //tfOutput.setEditable(false);  // read-only
 
      setTitle("Spread the love");  // "super" Frame sets title
      setSize(400, 130);  // "super" Frame sets initial window size
      setVisible(true);   // "super" Frame shows
      addWindowListener( new WindowAdapter() {
          @Override
          public void windowClosing(WindowEvent we) {
              System.exit(0);
          }
      } );
      
   }

   private class ButtonClickListener implements ActionListener{
	   public void actionPerformed(ActionEvent e) {
	   String command = e.getActionCommand();
	   if( command.equals( "Read Message" )) {
		  
		cw.displayNext();
	   }
	   else if( command.equals( "Send Message" ) ) {
		   String mes =text_message.getText();
		   String dest = text_dest.getText();
		  
		   List<Color> lc = new LinkedList<Color>();
		   lc.add(col[0]);
		   cw.sendMessage(mes, dest, lc);
		   text_message.setText("");
	   }
	   else if(command.equals("Pseudo")){
		   cw.newName(text_user_name.getText());
	   	   text_user_name.setEditable(false);
	   	   
	   	   
	   }else if(command.equals("Choose Color")){        
          if( (but_color.getLabel()).equals("Blue")){
        	  but_color.setLabel("Green");
        	  col[0]=new Color(0,100,0);
          }else if((but_color.getLabel()).equals("Green")){
        	  but_color.setLabel("Orange");
        	  col[0]=new Color(100,60,0);
          }else if((but_color.getLabel()).equals("Orange")){
        	  but_color.setLabel("Red");
        	  col[0]=new Color(100,0,0);
          }else if((but_color.getLabel()).equals("Red")){
        	  but_color.setLabel("Blue");
        	  col[0]=new Color(0,0,100);
          }
	   }	
	   }
	   }
  
@Override
public void actionPerformed(ActionEvent e) {
	// TODO Auto-generated method stub
	
}
}