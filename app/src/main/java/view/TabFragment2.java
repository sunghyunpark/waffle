package view;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    private ArrayList<CafeModel> listItems;
    //viewpager auto-slide
    Thread thread = null;
    Handler handler = null;
    int pageNum=0;	//페이지번호
    @BindView(R.id.viewPager) ViewPager viewPager;
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
        //헤더 부분의 뷰페이저
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
    }

    private void LoadData(){
        listItems = new ArrayList<CafeModel>();
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
                        listItems.add(cafeResponse.getCafeList().get(i));
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

    private class PagerAdapter extends FragmentStatePagerAdapter {

        public PagerAdapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // 해당하는 page의 Fragment를 생성하면서 position과 pircture를 넘겨줌  int position, String cafeThumbnail, String cafeId, String cafeName, String cafeAddress
            return Tab2RecommendCafeFragment.create(position, getPicture(position), listItems.get(position).getCafeId(), listItems.get(position).getCafeName(), listItems.get(position).getCafeAddress());
        }
        //position 값에 따라 picture의 정보를 만듬
        private String getPicture(int position){
            return listItems.get(position).getCafeThumbnail();
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

}
