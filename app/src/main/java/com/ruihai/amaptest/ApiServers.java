package com.ruihai.amaptest;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by apple on 16/10/10.
 */

public interface ApiServers {
    @FormUrlEncoded
    @POST("around")
    Call<NearPerson> sendpost(
            @Field("key") String key,
            @Field("tableid") String tableid,
            @Field("center") String center,
            @Field("radius") String radius
    );

}
