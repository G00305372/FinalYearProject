package com.example.g00305372.projectgps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Login extends AppCompatActivity {
    Socket client;
    PrintWriter out;
    Button enter;
    EditText name,pass;
    String user,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        enter = (Button)findViewById(R.id.button6);
        name =(EditText)findViewById(R.id.editText);
        pass =(EditText)findViewById(R.id.editText2);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user= name.getText().toString();
                password = pass.getText().toString();
                if(user.equals("") || password.equals("")){
                    Toast.makeText(getApplicationContext(), "Please Enter Username and Password", Toast.LENGTH_SHORT).show();
                }else if(user.equals("admin")&& password.equals("admin")){
                    Login.con con = new Login.con();
                    con.start();
                    name.setText("");
                    pass.setText("");

                } else if(user.equals("stephen")&& password.equals("stephen") || user.equals("bob")&& password.equals("bob")
                        || user.equals("paul")&& password.equals("paul") || user.equals("tom")&& password.equals("tom")){
                    Login.conn conn = new Login.conn();
                    conn.start();
                    name.setText("");
                    pass.setText("");
                }

            }
        });
    }

    private class con extends Thread{

        public void run(){
            try {

                //client = new Socket("10.12.20.73", 8080);
                client = new Socket("10.12.13.150", 8080);
                client.setKeepAlive(true);

                out= new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
                out.println("Admin");
                out.flush();
                while(true){
                    BufferedReader in =new BufferedReader(new InputStreamReader(client.getInputStream()));
                    String clientCommand = in.readLine();

                    if(clientCommand.startsWith("locations ")){
                        Intent i = new Intent(Login.this,AdminLogin.class);
                        Bundle b = new Bundle();
                        b.putString("locations",clientCommand);
                        i.putExtras(b);
                        startActivity(i);
                    }

                }
                //out.clos{e();
            }catch(IOException i){

            }
        }
    }

    private class conn extends Thread{

        public void run(){
            try {

                client = new Socket("10.12.13.150", 8080);
                client.setKeepAlive(true);

                out= new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
                out.println("EMP");
                out.flush();
                while(true){
                    BufferedReader in =new BufferedReader(new InputStreamReader(client.getInputStream()));
                    String clientCommand = in.readLine();

                    if(clientCommand.startsWith("Job ")){
                        Bundle b = new Bundle();
                        b.putString("name",user);
                        b.putString("Job",clientCommand);
                        Intent i = new Intent(Login.this,Locations.class);
                        i.putExtras(b);
                        startActivity(i);
                    }

                }
                //out.clos{e();
            }catch(IOException i){

            }
        }
    }

}
