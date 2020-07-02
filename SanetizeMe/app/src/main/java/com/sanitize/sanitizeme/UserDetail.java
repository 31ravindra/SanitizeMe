package com.sanitize.sanitizeme;

public class UserDetail {
    private String Name;
    private String ContactNumber;
    private String Email;
    private String Address;
    private String VehicleServiceSelected;
    private  String AtShowroom;
    private String ServiceSelected;


    public UserDetail(String name, String contactNumber, String email, String address, String vehicleServiceSelected, String atShowroom, String serviceSelected) {
        Name = name;
        ContactNumber = contactNumber;
        Email = email;
        Address = address;
        VehicleServiceSelected = vehicleServiceSelected;
        AtShowroom = atShowroom;
        ServiceSelected = serviceSelected;
    }
}
