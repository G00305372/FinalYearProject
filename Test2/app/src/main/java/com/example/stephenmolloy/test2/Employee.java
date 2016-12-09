package com.example.stephenmolloy.test2;

/**
 * Created by Stephen Molloy on 07/12/2016.
 */
public class Employee
{
    public String name;
    public double employee_ID;
    public String GPS;

    //Constructor
    public Employee(String name, double employee_ID, String GPS)
    {
        this.name = name;
        this.employee_ID = employee_ID;
        this.GPS = GPS;
    }

    public String getName(){return name;}
    public void setName(String empname){name = empname;}

    public double getID(){return employee_ID;}
    public void setID(double ID){employee_ID = ID; }

    public String getGPS(){return GPS;}
    public void setGPS(String location){GPS = location;}

}
