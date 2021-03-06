package api;

import api.response.CafeEtcInfoResponse;
import api.response.CafeResponse;
import api.response.CommonResponse;
import api.response.LoginResponse;
import api.response.MyCommentResponse;
import api.response.MyFavoriteCntResponse;
import api.response.NaverBlogResponse;
import api.response.RecentAllCommentResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by SungHyun on 2017-09-12.
 */

public interface ApiInterface {

    /**
     * Email Login
     * @param tag -> login
     * @param email
     * @param password
     * @return
     */
    @GET("login/login.php")
    Call<LoginResponse> GetUserDataByLogin(@Query("tag") String tag, @Query("email") String email,
                                           @Query("password") String password);
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
    Call<CafeResponse> GetCafeListFromMyLocation(@Query("tag") String tag, @Query("uid") String uid, @Query("user_latitude") Double user_latitude,
                                                 @Query("user_longitude") Double user_longitude, @Query("last_cafe_id") String last_cafe_id);

    /**
     * About Cafe Etc Info 불러오기
     * @param tag -> cafe_etc_info
     * @param cafe_id
     * @param all_flag -> Y/N
     * @return
     */
    @GET("cafe/cafe_info.php")
    Call<CafeEtcInfoResponse> GetCafeEtcInfo(@Query("tag") String tag, @Query("cafe_id") String cafe_id, @Query("uid") String uid, @Query("all_flag") String all_flag);

    /**
     * Cafe Like
     * @param tag -> like_cafe
     * @param uid
     * @param cafe_id
     * @param state -> like state Y/N
     * @return
     */
    @FormUrlEncoded
    @POST("cafe/cafe_info.php")
    Call<CommonResponse> LikeCafe(@Field("tag") String tag, @Field("uid") String uid, @Field("cafe_id") String cafe_id, @Field("like_state") String state);

    /**
     * Write Cafe Comment
     * @param tag -> write_cafe_comment
     * @param uid
     * @param cafe_id
     * @param comment_text
     * @return
     */
    @FormUrlEncoded
    @POST("cafe/cafe_info.php")
    Call<CommonResponse> WriteCafeComment(@Field("tag") String tag, @Field("uid") String uid, @Field("cafe_id") String cafe_id, @Field("comment_text") String comment_text);

    /**
     * User Profile Image Upload
     * @param tag -> profile
     * @param uid
     * @param loginMethod -> email /
     * @param file
     * @return
     */
    @Multipart
    @POST("upload/upload_img.php")
    Call<CommonResponse> UploadUserProfile(@Part("tag") RequestBody tag, @Part("uid") RequestBody uid,
                                           @Part("login_method") RequestBody loginMethod, @Part MultipartBody.Part file);

    /**
     * Tab4 Like / Comment Count
     * @param tag -> my_favorite_cafe_cnt
     * @param uid
     * @return
     */
    @GET("cafe/my_favorite.php")
    Call<MyFavoriteCntResponse> GetMyFavoriteCnt(@Query("tag") String tag, @Query("uid") String uid);

    /**
     * My Favorite Cafe List
     * @param tag -> my_favorite_cafe
     * @param uid
     * @return
     */
    @GET("cafe/my_favorite.php")
    Call<CafeResponse> GetMyFavoriteCafeList(@Query("tag") String tag, @Query("uid") String uid);

    /**
     * My Comment Cafe List
     * @param tag -> my_comment_cafe
     * @param uid
     * @return
     */
    @GET("cafe/my_favorite.php")
    Call<MyCommentResponse> GetMyCommentCafeList(@Query("tag") String tag, @Query("uid") String uid);

    /**
     * Edit User Profile Info
     * @param tag -> edit_user_info
     * @param uid
     * @param nick_name
     * @param intro
     * @return
     */
    @FormUrlEncoded
    @POST("login/login.php")
    Call<CommonResponse> EditUserProfileInfo(@Field("tag") String tag, @Field("uid") String uid, @Field("nick_name") String nick_name, @Field("intro") String intro);

    /**
     * Get Cafe Info With CafeId
     * @param tag -> about_cafe_info_with_cafe_id
     * @param cafe_id
     * @return
     */
    @GET("cafe/cafe_info.php")
    Call<CafeResponse> GetAboutCafeInfo(@Query("tag") String tag, @Query("cafe_id") String cafe_id);

    /**
     * Tab2 Info
     * @param tag -> tab2_info
     * @param comment_flag -> N / all
     * @return
     */
    @GET("cafe/cafe_info.php")
    Call<CafeResponse> GetTab2Info(@Query("tag") String tag, @Query("comment_flag") String comment_flag);

    /**
     * All Comment
     * @param tag -> recent_all_comment
     * @param comment_flag -> all
     * @param last_comment_id
     * @return
     */
    @GET("cafe/cafe_info.php")
    Call<RecentAllCommentResponse> GetAllCommentList(@Query("tag") String tag, @Query("comment_flag") String comment_flag, @Query("last_comment_id") String last_comment_id);

    /**
     * Naver Blog API
     * @param query
     * @param start
     * @return
     */
    @Headers({
            "X-Naver-Client-Id: 4oMsxTfLyhwRQwRU3bZK",
            "X-Naver-Client-Secret: EZb3BLStu8"
    })
    @GET("v1/search/blog.json")
    Call<NaverBlogResponse> GetNaver(
            @Query("query") String query, @Query("start") int start);


}
