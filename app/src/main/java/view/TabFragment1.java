package view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yssh.waffle.AppConfig;
import com.yssh.waffle.R;

import java.util.ArrayList;
import java.util.List;

import api.ApiClient;
import api.ApiInterface;
import api.response.CafeResponse;
import butterknife.BindString;
import butterknife.ButterKnife;
import model.CafeModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabFragment1 extends Fragment {

    View v;
    //리사이클러뷰
    RecyclerAdapter adapter;
    RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<CafeModel> listItems;

    @BindString(R.string.cafe_info_weekdays_time_txt) String cafeInfoWeekdaysTimeStr;
    @BindString(R.string.cafe_info_weekend_time_txt) String cafeInfoWeekendTimeStr;
    @BindString(R.string.network_error_txt) String networkErrorStr;

    public TabFragment1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_tab_fragment1, container, false);

        ButterKnife.bind(this, v);
        //recyclerview 초기화
        listItems = new ArrayList<CafeModel>();
        recyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new RecyclerAdapter(listItems);
        recyclerView.setLayoutManager(linearLayoutManager);

        /* recyclerview 구분선
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(getActivity(),new LinearLayoutManager(getActivity()).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        */

        LoadCafeList();

        return v;
    }

    private void LoadCafeList(){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<CafeResponse> call = apiService.GetCafeListFromMyLocation("cafe_list_from_user_location", "0");
        call.enqueue(new Callback<CafeResponse>() {
            @Override
            public void onResponse(Call<CafeResponse> call, Response<CafeResponse> response) {
                CafeResponse cafeResponse = response.body();
                if(!cafeResponse.isError()){
                    CafeModel cafeModel;
                    int listSize = cafeResponse.getCafeList().size();
                    for (int i=0;i<listSize;i++){
                        cafeModel = new CafeModel();
                        cafeModel.setCafeName(cafeResponse.getCafeList().get(i).getCafeName());
                        cafeModel.setCafeThumbnail(cafeResponse.getCafeList().get(i).getCafeThumbnail());
                        cafeModel.setCafeWeekDaysOpenTime(cafeResponse.getCafeList().get(i).getCafeWeekDaysOpenTime());
                        cafeModel.setCafeWeekDaysCloseTime(cafeResponse.getCafeList().get(i).getCafeWeekDaysCloseTime());
                        cafeModel.setCafeWeekendOpenTime(cafeResponse.getCafeList().get(i).getCafeWeekendOpenTime());
                        cafeModel.setCafeWeekendCloseTime(cafeResponse.getCafeList().get(i).getCafeWeekendCloseTime());
                        cafeModel.setCafeAddress(cafeResponse.getCafeList().get(i).getCafeAddress());
                        cafeModel.setCafePhoneNum(cafeResponse.getCafeList().get(i).getCafePhoneNum());
                        listItems.add(cafeModel);
                    }
                    recyclerView.setAdapter(adapter);
                }else{

                }
                Toast.makeText(getActivity(), cafeResponse.getError_msg(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<CafeResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());
                Toast.makeText(getActivity(), networkErrorStr,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int TYPE_ITEM = 0;
        private static final int TYPE_HEADER = 1;

        List<CafeModel> listItems;

        private RecyclerAdapter(List<CafeModel> listItems) {
            this.listItems = listItems;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_ITEM) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_tab1_item, parent, false);
                return new Cafe_VH(v);
            }else if(viewType == TYPE_HEADER){
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_tab1_header, parent, false);
                return new Header_Vh(v);
            }
            throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
        }

        private CafeModel getItem(int position) {
            return listItems.get(position-1);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            if (holder instanceof Cafe_VH) {
                final CafeModel currentItem = getItem(position);
                final Cafe_VH VHitem = (Cafe_VH)holder;

                VHitem.cafeName.setText(currentItem.getCafeName());

                //Glide Options
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.mipmap.ic_launcher);
                requestOptions.error(R.mipmap.ic_launcher);
                requestOptions.circleCrop();    //circle

                Glide.with(getActivity())
                        .setDefaultRequestOptions(requestOptions)
                        .load(AppConfig.ServerAddress+currentItem.getCafeThumbnail())
                        .into(VHitem.cafeThumbnail);

                VHitem.cafe_weekdays_open_close_time_tv.setText(cafeInfoWeekdaysTimeStr + " " +currentItem.getCafeWeekDaysOpenTime() + " ~ "+currentItem.getCafeWeekDaysCloseTime());
                VHitem.cafe_weekend_open_close_time_tv.setText(cafeInfoWeekendTimeStr + " " + currentItem.getCafeWeekendOpenTime() + " ~ "+currentItem.getCafeWeekendCloseTime());

                VHitem.cafe_address_tv.setText(currentItem.getCafeAddress());
                VHitem.cafe_phone_tv.setText(currentItem.getCafePhoneNum());

            }else if(holder instanceof Header_Vh){

            }
        }

        /**
         * 카페 아이템
         */
        private class Cafe_VH extends RecyclerView.ViewHolder{
            TextView cafeName;
            ImageView cafeThumbnail;
            TextView cafe_weekdays_open_close_time_tv;
            TextView cafe_weekend_open_close_time_tv;
            TextView cafe_address_tv;
            TextView cafe_phone_tv;

            private Cafe_VH(View itemView){
                super(itemView);
                cafeName = (TextView)itemView.findViewById(R.id.cafe_name_txt);
                cafeThumbnail = (ImageView)itemView.findViewById(R.id.cafe_thumb_img);
                cafe_weekdays_open_close_time_tv = (TextView)itemView.findViewById(R.id.weekdays_open_close_time_txt);
                cafe_weekend_open_close_time_tv = (TextView)itemView.findViewById(R.id.weekend_open_close_time_txt);
                cafe_address_tv = (TextView)itemView.findViewById(R.id.cafe_address_txt);
                cafe_phone_tv = (TextView)itemView.findViewById(R.id.cafe_phone_txt);
            }
        }

        /**
         * 상단 헤더
         */
        private class Header_Vh extends RecyclerView.ViewHolder{
            private Header_Vh(View itemView){
                super(itemView);

            }
        }

        private boolean isPositionHeader(int position){
            return position == 0;
        }

        @Override
        public int getItemViewType(int position) {
            if(isPositionHeader(position)){
                return TYPE_HEADER;
            }else{
                return TYPE_ITEM;
            }
        }
        //increasing getItemcount to 1. This will be the row of header.
        @Override
        public int getItemCount() {
            return listItems.size()+1;
        }
    }
}
