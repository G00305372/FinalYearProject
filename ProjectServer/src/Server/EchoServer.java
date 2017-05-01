package Server;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Created by Stephen Molloy on 23/02/2017.
 */
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;

import net.miginfocom.swing.MigLayout;

public class EchoServer {
	JFrame frameEquip;
	
	
	ArrayList<String> equipment = new ArrayList<String>();
	ArrayList<String> users = new ArrayList<String>();
	
    public static void main(String[] args) throws Exception {
    	
    	new EchoServer();
        
    }
    
    public EchoServer(){
    	javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {

            	Equip();
            }
        });
    }
    private void Equip(){
    	frameEquip = new JFrame("Login");
        frameEquip.setSize(300,200);
        frameEquip.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel Panel = new JPanel(new MigLayout("center"));
       
        JButton Equipment = new JButton("ADD EQUIPMENT");
        JButton Employees = new JButton("    ADD USER   ");
        
       
        Equipment.setFont(new Font("Calibri", Font.ITALIC, 14));
        Employees.setFont(new Font("Calibri", Font.ITALIC, 14));

        
        Panel.add(Employees,"span,center,wrap");
        Panel.add(new JLabel(),"span,center,wrap");
        Panel.add(Equipment,"span,center,wrap");

        frameEquip.add(Panel);
        frameEquip.setVisible(true);
        
        Equipment.addActionListener(new ActionListener() {
    		
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			// TODO Auto-generated method stub
    			EquipLocate();
    			frameEquip.setVisible(false);

    		}
    	});
        Employees.addActionListener(new ActionListener() {
    		
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			// TODO Auto-generated method stub
    			CrewLocate();
    			frameEquip.setVisible(false);
    			
    		}
    	});
        
        Connect();
    }
    private void EquipLocate(){
    	JFrame frame3 = new JFrame("Login");
        frame3.setSize(300,250);
        frame3.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel Panel = new JPanel(new MigLayout("center"));
        JLabel Ename = new JLabel("Equipment:");
        JTextField username = new JTextField(15);
        JLabel pass = new JLabel("ID Number");
        JTextField id = new JTextField(15);
        JButton confirm = new JButton("ADD EQUIPMENT");
        JButton back = new JButton("HOME");

        
        Ename.setFont(new Font("Calibri", Font.ITALIC, 16));
        username.setFont(new Font("Calibri", Font.PLAIN, 16));
        pass.setFont(new Font("Calibri", Font.ITALIC, 16));
        id.setFont(new Font("Calibri", Font.PLAIN, 16));
        confirm.setFont(new Font("Calibri", Font.ITALIC, 14));
        back.setFont(new Font("Calibri", Font.ITALIC, 14));

        
        Panel.add(Ename,"span,center,wrap");
        Panel.add(username,"span,center,wrap");
        Panel.add(pass,"span,center,wrap");
        Panel.add(id,"span,center,wrap");
        Panel.add(confirm,"span,center,wrap");
        Panel.add(back,"span,center,wrap");


        frame3.add(Panel);
        frame3.setVisible(true);
        
        confirm.addActionListener(new ActionListener() {
    		
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			// TODO Auto-generated method stub
    			equipment.add(username.getText()+" "+id.getText()); 
    			database(username.getText().toString(),"53.5183593" ,"-28.89693");
    			System.out.println("equipment: "+equipment);
    			id.setText("");
    			username.setText("");
    		}
    	});
        back.addActionListener(new ActionListener() {
    		
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			// TODO Auto-generated method stub
    			frame3.setVisible(false);
    			frameEquip.setVisible(true);
    			username.setText("");
    			id.setText("");
    		}
    	});
        
    }
    private void CrewLocate(){
    	JFrame frame3 = new JFrame("Login");
        frame3.setSize(300,250);
        frame3.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel Panel = new JPanel(new MigLayout("center"));
        JLabel user = new JLabel("Username:");
        JTextField username = new JTextField(15);
        JLabel pass = new JLabel("Password:");
        JPasswordField Password = new JPasswordField(15);
        JButton confirm = new JButton("ADD USER");
        JButton back = new JButton("HOME");
        user.setFont(new Font("Calibri", Font.ITALIC, 16));
        username.setFont(new Font("Calibri", Font.PLAIN, 16));
        pass.setFont(new Font("Calibri", Font.ITALIC, 16));
        Password.setFont(new Font("Calibri", Font.PLAIN, 16));
        confirm.setFont(new Font("Calibri", Font.ITALIC, 14));
        back.setFont(new Font("Calibri", Font.ITALIC, 14));

        
        Panel.add(user,"span,center,wrap");
        Panel.add(username,"span,center,wrap");
        Panel.add(pass,"span,center,wrap");
        Panel.add(Password,"span,center,wrap");
        Panel.add(confirm,"span,center,wrap");
        Panel.add(back,"span,center,wrap");

        frame3.add(Panel);
        frame3.setVisible(true);
        
        confirm.addActionListener(new ActionListener() {
    		
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			// TODO Auto-generated method stub
    			users.add(username.getText()+" "+Password.getText()); 
    			
    			System.out.println("Strings are: "+users);
    			Password.setText("");
    			username.setText("");
    		}
    	});
        back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frameEquip.setVisible(true);
				frame3.setVisible(false);
				username.setText("");
    			Password.setText("");
			}
		});
    }


    public void Connect() {
    	(new conn()).execute();
    	
    }

    class conn extends SwingWorker<Void, String> {
        @Override
        protected Void doInBackground() throws Exception {
        	ServerSocket m_ServerSocket = new ServerSocket(8080);
            int id = 0;
            while (true) {
                Socket Socket = m_ServerSocket.accept();
                ConnectionThread cliThread = new ConnectionThread(Socket,id++);
                cliThread.start();
            }
        }
    }





class ConnectionThread extends Thread {
    Socket clientSocket;
    int clientID = -1;
    boolean running = true;
    PrintWriter  out;
    String data="";
    String data2="";
    ConnectionThread(Socket s, int i) {
        clientSocket = s;
        clientID = i;
    }

    public void run() {
        System.out.println("Accepted Client : ID - " + clientID + " : Address - "
                + clientSocket.getInetAddress().getHostName());
        try {
        	

            BufferedReader   in = new BufferedReader(new
                    InputStreamReader(clientSocket.getInputStream()));
               out = new PrintWriter(new
                    OutputStreamWriter(clientSocket.getOutputStream()));
            while (running) {
                String clientCommand = in.readLine();
                System.out.println("Client Says :" + clientCommand);
                if(clientCommand.startsWith("equip")){
                    String loc[] =clientCommand.split("\\s+");
                    updatedatabase(loc[1],loc[2],loc[3]);
                }else if(clientCommand.equals("Admin")){
                	retrieveDatabase();
                	out.println("locations "+data2);
                	out.flush();
                }else if(clientCommand.startsWith("Job ")){
                	String string[] = clientCommand.split("\\s+");
                	Jobdatabase(string[3],string[1],string[2]);
                }else if(clientCommand.startsWith("EMP")){
                    System.out.println("...1...");

                	String str[] = clientCommand.split("\\s+");
                	String user = str[1]+" "+str[2];
                    System.out.println("...2...");

                	if(equipment.contains(user)){
                        System.out.println("...3...");

                		String str2[] = user.split("\\s+");
                		String name = str[1];
                		System.out.println("...4...");
                		retrieveJob(name);
                		System.out.println("...5...");
                	}else if(users.contains(user)){
                		
                		data=" Job-Base 53.5183593 -8.89693";
                		out.println("Job "+data);
                		out.flush();
                	}else{
                		out.println("FALSE");
                		out.flush();
                	}
                	
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void Jobdatabase(String name,String lat, String lng){
        try {
            System.out.println("1");
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            System.out.println("2");
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectlocations","root","");
            String query = "insert into job(name,latitude,longitude)"+"values(?,?,?)";
            PreparedStatement preparedStatement = c.prepareStatement(query);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lat);
            preparedStatement.setString(3,lng);
            preparedStatement.execute();
            System.out.println("3");
            c.close();
            System.out.println("4");
            System.out.println("DONE");
        }catch(Exception e){

        }

    }
    
    public void retrieveDatabase(){
    	
        try {
        	
        	String driver = "com.mysql.jdbc.Driver";
        	
            Class.forName(driver);
           
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectlocations","root","");			
          
            String sql = ("SELECT * FROM locations;");
            
            PreparedStatement st = con.prepareStatement(sql);
			ResultSet rs = st.executeQuery(sql);
			while(rs.next()) { 
			 int id = rs.getInt("EMPLOYEE_ID"); 
			 
			 String str1 = rs.getString("name");
			 String str2 = rs.getString("Latitude");
			 String str3 = rs.getString("Longitude");
			 System.out.println(id+"\t"+str1+"\t"+str2+"\t"+str3);
			 data2=data2 +str1+"\t"+str2+"\t"+str3+" ";
             
			}
			data2 = data2+"\n";
			System.out.println("data2: "+data2+"\n");
			con.close();
        }catch(Exception e){

        }

    }
public void retrieveJob(String name){
    	
        try {
        	System.out.println("retrieve");
        	String driver = "com.mysql.jdbc.Driver";
        	
            Class.forName(driver);
           
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectlocations","root","");			
          
            String sql = "SELECT * FROM job";
            System.out.println("fafasd "+name +"------fdfsf");
            PreparedStatement st = con.prepareStatement(sql);
            //st.setString(1, name);
            System.out.println("fafasd");

			ResultSet rs = st.executeQuery(sql);
            System.out.println("fafasd");

			while(rs.next()) { 
			 int id = rs.getInt("EMPLOYEE_ID"); 
			 
			 String str1 = rs.getString("name");
			 String str2 = rs.getString("Latitude");
			 String str3 = rs.getString("Longitude");
			 System.out.println(id+"\t"+str1+"\t"+str2+"\t"+str3);
			 if(str1.equals(name)){
					System.out.println("ddddddddddddddddd");

				 data=str1+"\t"+str2+"\t"+str3+" ";
			 }
             
			}
			data = data+"\n";
			System.out.println("data: "+data+"\n");
			con.close();
            System.out.println("fafasd");

			if(data.startsWith(name)){
				System.out.println("22222");
				out.println("Job "+data);
        		out.flush();
        		 System.out.println("111111");
        	}else{
         		data=" Job-Base 53.5183593 -8.89693";
         		out.println("Job "+data);
         		out.flush();
        	}
        }catch(Exception e){

        }

    }
}
public void database(String name,String lat, String lng){
    try {
        System.out.println("1");
        String driver = "com.mysql.jdbc.Driver";
        Class.forName(driver);
        System.out.println("2");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectlocations","root","");
        String query = "insert into locations(name,latitude,longitude)"+"values(?,?,?)";
        PreparedStatement preparedStatement = c.prepareStatement(query);
        preparedStatement.setString(1,name);
        preparedStatement.setString(2,lat);
        preparedStatement.setString(3,lng);
        preparedStatement.execute();
        System.out.println("3");
        c.close();
        System.out.println("4");
        System.out.println("DONE");
    }catch(Exception e){

    }

}

public void updatedatabase(String name,String lat, String lng){
    try {
        System.out.println("1"+name+" "+lat+" "+lng);
        String driver = "com.mysql.jdbc.Driver";
        Class.forName(driver);
        System.out.println("2");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectlocations","root","");
        Connection c1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectlocations","root","");
        
        String query = "update locations set latitude = ? where name = ?";
        PreparedStatement preparedStatement = c.prepareStatement(query);
        preparedStatement.setString(1,lat);
        preparedStatement.setString(2,name);
        preparedStatement.executeUpdate();
        System.out.println("3");
        c.close();
        
        
        String query2 = "update locations set longitude = ? where name = ?";
        PreparedStatement preparedStatement2 = c1.prepareStatement(query2);
        preparedStatement2.setString(1,lng);
        preparedStatement2.setString(2,name);
        preparedStatement2.executeUpdate();
        System.out.println("3");
        c1.close();
        
        System.out.println("4");
        System.out.println("DONE");
    }catch(Exception e){

    }

}
public void empdatabase(String name,String lat, String lng,String clkin,String clkout){
    try {
        System.out.println("1");
        String driver = "com.mysql.jdbc.Driver";
        Class.forName(driver);
        System.out.println("2");
        Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/projectlocations","root","");
        String query = "insert into employees(name,latitude,longitude,clkin,clkout)"+"values(?,?,?,?,?)";
        PreparedStatement preparedStatement = c.prepareStatement(query);
        preparedStatement.setString(1,name);
        preparedStatement.setString(2,lat);
        preparedStatement.setString(3,lng);
        preparedStatement.setString(4,clkin);
        preparedStatement.setString(5,clkout);
        preparedStatement.execute();
        System.out.println("3");
        c.close();
        System.out.println("4");
        System.out.println("DONE");
    }catch(Exception e){

    }

}

}
