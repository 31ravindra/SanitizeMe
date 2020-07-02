package com.sanitize.sanitizeme;



import java.io.Serializable;

public class Details implements Serializable {
    private String ShowroomContact;
    private String ShowroomAddress;


    public String getShowroomContact() {
        return ShowroomContact;
    }

    public void setShowroomContact(String showroomContact) {
        ShowroomContact = showroomContact;
    }

    public String getShowroomAddress() {
        return ShowroomAddress;
    }

    public void setShowroomAddress(String showroomAddress) {
        ShowroomAddress = showroomAddress;
    }

    public Details(String showroomContact, String showroomAddress) {
        ShowroomContact = showroomContact;
        ShowroomAddress = showroomAddress;
    }

}
