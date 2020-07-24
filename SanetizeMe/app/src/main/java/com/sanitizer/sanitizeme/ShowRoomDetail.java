package com.sanitizer.sanitizeme;

import java.util.List;

public class ShowRoomDetail {

    List<Details> Details;

    public List<Details> getDetails() {
        return Details;
    }

    public void setDetails(List<Details> details) {
        Details = details;
    }

    public ShowRoomDetail(List<Details> details) {
        Details = details;
    }
}
