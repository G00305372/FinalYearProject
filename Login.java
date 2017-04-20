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
    EditText name;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        enter = (Button)findViewById(R.id.button);
        name =(EditText)findViewById(R.id.editText);
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user= name.getText().toString();
                if(user.equals("")){
                    Toast.makeText(getApplicationContext(), "Please Enter name", Toast.LENGTH_SHORT).show();
                }else if(user.equals("admin")){
                    Login.con con = new Login.con();
                    con.start();

                } else{
                    Bundle b = new Bundle();
                    b.putString("name",user);
                    Intent i = new Intent(Login.this,Locations.class);
                    i.putExtras(b);
                    startActivity(i);
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

}
