/**
 * Created by Stephen Molloy on 23/02/2017.
 */
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class EchoServer {
    public static void main(String[] args) throws Exception {
        ServerSocket m_ServerSocket = new ServerSocket(8080);
        int id = 0;
        while (true) {
            Socket clientSocket = m_ServerSocket.accept();
            ClientServiceThread cliThread = new ClientServiceThread(clientSocket,
                    id++);
            cliThread.start();
        }
    }

}

class ClientServiceThread extends Thread {
    Socket clientSocket;
    int clientID = -1;
    boolean running = true;

    ClientServiceThread(Socket s, int i) {
        clientSocket = s;
        clientID = i;
    }

    public void run() {
        System.out.println("Accepted Client : ID - " + clientID + " : Address - "
                + clientSocket.getInetAddress().getHostName());
        try {

            BufferedReader   in = new BufferedReader(new
                    InputStreamReader(clientSocket.getInputStream()));
            PrintWriter   out = new PrintWriter(new
                    OutputStreamWriter(clientSocket.getOutputStream()));
            while (running) {
                String clientCommand = in.readLine();
                System.out.println("Client Says :" + clientCommand);
                if (clientCommand.equalsIgnoreCase("quit")) {
                    running = false;
                    System.out.print("Stopping client thread for client : " + clientID);
                } else{
                    String loc[] =clientCommand.split("\\s+");
                   database("BOB",loc[0],loc[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
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
}

