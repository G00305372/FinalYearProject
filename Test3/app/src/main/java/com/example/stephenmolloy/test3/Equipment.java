package com.example.stephenmolloy.test3;


/**
 * Created by Stephen Molloy on 07/12/2016.
 */
public class Equipment {

    public String model;
    public double equipment_ID;
    public String GPS;

    //Constructor
    public Equipment(String model, double equipment_ID, String GPS)
    {
        this.model = model;
        this.equipment_ID = equipment_ID;
        this.GPS = GPS;
    }

    public String getName(){return model;}
    public void setName(String empname){model = empname;}

    public double getID(){return equipment_ID;}
    public void setID(double ID){equipment_ID = ID; }

    public String getGPS(){return GPS;}
    public void setGPS(String location){GPS = location;}

}

