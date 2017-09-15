package api;

import api.response.CafeResponse;
import api.response.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by SungHyun on 2017-09-12.
 */

public interface ApiInterface {

    /**
     * Email Register
     * @param tag -> register
     * @param nick_name
     * @param email
     * @param password
     * @param fb_id
     * @param profile_img
     * @param profile_img_thumb
     * @param intro
     * @return
     */
    @FormUrlEncoded
    @POST("login/login.php")
    Call<LoginResponse> EmailRegister(@Field("tag") String tag, @Field("nick_name") String nick_name,
                                      @Field("email") String email, @Field("password") String password,
                                      @Field("fb_id") String fb_id, @Field("profile_img") String profile_img,
                                      @Field("profile_img_thumb") String profile_img_thumb, @Field("intro") String intro);
    /**
     * Tab1 Cafe Info 불러오기
     * @param tag -> cafe_list_from_user_location
     * @param uid
     * @return
     */
    @GET("cafe/cafe_info.php")
    Call<CafeResponse> GetCafeListFromMyLocation(@Query("tag") String tag, @Query("uid") String uid);
}
