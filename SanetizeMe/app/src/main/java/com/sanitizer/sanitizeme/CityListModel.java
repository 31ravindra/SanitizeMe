package com.sanitizer.sanitizeme;

import java.util.List;

public class CityListModel {

    public List<String> getCityNames() {
        return CityNames;
    }

    public void setCityNames(List<String> cityNames) {
        CityNames = cityNames;
    }

    private List<String> CityNames;

}
