package view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yssh.waffle.AppConfig;
import com.yssh.waffle.R;

import java.util.ArrayList;

import api.ApiClient;
import api.ApiInterface;
import api.response.CafeResponse;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import model.CafeModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabFragment2 extends Fragment {

    View v;
    //viewpager item count
    private static int VIEWPAGER_PICTURE_COUNT = 3;
    private ArrayList<CafeModel> recommendListItems;
    //grid Adapter
    RecyclerAdapter gridAdapter;
    //viewpager auto-slide
    Thread thread = null;
    Handler handler = null;
    int pageNum=0;	//페이지번호
    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.recyclerView) RecyclerView gridRecyclerView;
    @BindString(R.string.network_error_txt) String networkErrorStr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tab_fragment2, container, false);
        ButterKnife.bind(this, v);

        LoadData();

        // Inflate the layout for this fragment
        return v;
    }

    private void SetUI(){
        /**
         * auto slide viewPager
         */
        PagerAdapter adapter = new PagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(VIEWPAGER_PICTURE_COUNT);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
            }

            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                viewPager.setCurrentItem(position);
                viewPager.setOffscreenPageLimit(3);

                switch (position){

                }
            }
        });
        handler = new Handler(){

            public void handleMessage(android.os.Message msg) {
                if(pageNum==0){
                    viewPager.setCurrentItem(0);
                    pageNum++;
                }else if(pageNum==1){
                    viewPager.setCurrentItem(1);
                    pageNum++;
                }else if(pageNum==2){
                    viewPager.setCurrentItem(2);
                    pageNum=0;
                }

            }
        };

        /**
         * grid recyclerView
         */
        GridLayoutManager lLayout = new GridLayoutManager(getActivity(),2);
        gridRecyclerView.setLayoutManager(lLayout);
        gridAdapter = new RecyclerAdapter(recommendListItems);
        gridRecyclerView.setAdapter(gridAdapter);
        gridRecyclerView.setNestedScrollingEnabled(false);

    }

    private void LoadData(){
        recommendListItems = new ArrayList<CafeModel>();
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<CafeResponse> call = apiService.GetTab2Info("tab2_info");
        call.enqueue(new Callback<CafeResponse>() {
            @Override
            public void onResponse(Call<CafeResponse> call, Response<CafeResponse> response) {
                CafeResponse cafeResponse = response.body();
                if(!cafeResponse.isError()){
                    int listSize = cafeResponse.getCafeList().size();
                    for (int i=0;i<listSize;i++){
                        recommendListItems.add(cafeResponse.getCafeList().get(i));
                    }
                    SetUI();
                    //viewpager 자동슬라이드 쓰레드
                    thread = new Thread(){
                        //run은 jvm이 쓰레드를 채택하면, 해당 쓰레드의 run메서드를 수행한다.
                        public void run() {
                            super.run();
                            while(true){
                                try {
                                    Thread.sleep(2500);
                                    handler.sendEmptyMessage(0);
                                } catch (Exception e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }


                            }
                        }
                    };
                    thread.start();
                }else{
                    Log.d("random", "fail");
                }
                //Toast.makeText(getActivity(), cafeResponse.getError_msg(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<CafeResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());
                Toast.makeText(getActivity(), networkErrorStr,Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * auto slide adapter
     */
    private class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // 해당하는 page의 Fragment를 생성하면서 position과 pircture를 넘겨줌  int position, String cafeThumbnail, String cafeId, String cafeName, String cafeAddress
            return Tab2RecommendCafeFragment.create(position, getPicture(position), recommendListItems.get(position).getCafeId(), recommendListItems.get(position).getCafeName(), recommendListItems.get(position).getCafeAddress());
        }
        //position 값에 따라 picture의 정보를 만듬
        private String getPicture(int position){
            return recommendListItems.get(position).getCafeThumbnail();
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            if (object instanceof Fragment) {
                Fragment fragment = (Fragment) object;
                android.support.v4.app.FragmentManager fm = fragment.getFragmentManager();
                android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
                ft.remove(fragment);
                // this.notifyDataSetChanged();
                ft.commitAllowingStateLoss();
            }
        }
        @Override
        public int getCount() {
            int item_count = VIEWPAGER_PICTURE_COUNT;
            return item_count;
        }
    }

    /**
     * Grid RecyclerView Adapter
     */
    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int TYPE_ITEM = 1;
        ArrayList<CafeModel> listItems;

        public RecyclerAdapter(ArrayList<CafeModel> listItems) {
            for(int i=2;i>=0;i--){
                listItems.remove(i);
            }
            this.listItems = listItems;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_ITEM) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_tab2_recommend_item, parent, false);
                return new CafeVHitem(v);
            }
            throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
        }

        private CafeModel getItem(int position) {
            return listItems.get(position);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            if (holder instanceof CafeVHitem)//아이템(게시물)
            {
                final CafeModel currentItem = getItem(position);
                final CafeVHitem VHitem = (CafeVHitem)holder;

                VHitem.thumbnail_layout.setLayoutParams(setHalfSize());
                VHitem.thumbnail_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), AboutCafeActivity.class);
                        intent.putExtra("isData", "N");
                        intent.putExtra("cafe_id", getItem(position).getCafeId());
                        startActivity(intent);
                    }
                });

                //Glide Options
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.mipmap.not_cafe_img);
                requestOptions.error(R.mipmap.not_cafe_img);

                Glide.with(getActivity())
                        .setDefaultRequestOptions(requestOptions)
                        .load(AppConfig.ServerAddress+currentItem.getCafeThumbnail())
                        .into(VHitem.cafeThumb_iv);

                VHitem.cafeName_tv.setText(currentItem.getCafeName());

            }
        }

        private FrameLayout.LayoutParams setHalfSize(){
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(AppConfig.DISPLAY_WIDTH/2,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            return params;
        }

        class CafeVHitem extends RecyclerView.ViewHolder{

            ImageView cafeThumb_iv;
            TextView cafeName_tv;
            ViewGroup thumbnail_layout;

            public CafeVHitem(View itemView){
                super(itemView);
                cafeThumb_iv = (ImageView)itemView.findViewById(R.id.cafe_thumb_img);
                cafeName_tv = (TextView)itemView.findViewById(R.id.cafe_name_txt);
                thumbnail_layout = (ViewGroup)itemView.findViewById(R.id.thumbnail_layout);
            }
        }
        @Override
        public int getItemViewType(int position) {
            return TYPE_ITEM;
        }
        //increasing getItemcount to 1. This will be the row of header.
        @Override
        public int getItemCount() {
            return listItems.size();
        }
    }

}
