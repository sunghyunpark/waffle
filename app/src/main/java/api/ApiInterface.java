package api;

import api.response.CafeResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by SungHyun on 2017-09-12.
 */

public interface ApiInterface {

    /**
     * Tab1 Cafe Info 불러오기
     * @param tag -> cafe_list_from_user_location
     * @param uid
     * @return
     */
    @GET("cafe/cafe_info.php")
    Call<CafeResponse> GetCafeListFromMyLocation(@Query("tag") String tag, @Query("uid") String uid);
}
