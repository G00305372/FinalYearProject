package com.example.stephenmolloy.test3;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by Stephen Molloy on 12/12/2016.
 */
public class Database extends  MapsActivity {
    public static void main(String[] args) {


        try {
            int count = 0;
            Class.forName("com.mysql.jdbc.Driver");  // load the driver
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/database1", "root", "");
            PreparedStatement add = con.prepareStatement("Insert Into details Values (?,?,?,?,?,?)");

        /*add.setDouble(1, Min);
            add.setDouble(3, Max);
            add.setDouble(2, Sum);

    count  = add.executeUpdate();*/


       // MapsActivity C = (MapsActivity)
        //add.setString(1,);
        } catch (Exception E) {
        }
    }
}
