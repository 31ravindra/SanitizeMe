package com.sanitize.sanitizeme;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserDetialClient {

    @POST("CustomerDetails")
    Call<String> Book(@Body  UserDetail userDetail);

    @POST("Showroomdetails")
    Call<ShowRoomDetail> selectCity(@Body SelectedCity selectedCity);

}
