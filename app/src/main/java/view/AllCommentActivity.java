package view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import api.ApiClient;
import api.ApiInterface;
import api.response.RecentAllCommentResponse;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import model.RecentCommentModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import util.CommonUtil;

public class AllCommentActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindString(R.string.network_error_txt) String networkErrorStr;
    private ArrayList<RecentCommentModel> recentCommentListitems;
    RecyclerAdapter recyclerAdapter;
    private static final int LOAD_DATA_COUNT = 5;
    private String lastCommentId = "0";
    CommonUtil commonUtil = new CommonUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_comment);

        ButterKnife.bind(this);

        init();
    }

    private void init(){
        recentCommentListitems = new ArrayList<RecentCommentModel>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerAdapter = new RecyclerAdapter(recentCommentListitems);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recyclerAdapter);

        LoadData(lastCommentId);

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                // do something...
                LoadData(lastCommentId);
            }
        });
    }

    private void LoadData(final String last_comment_id){
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<RecentAllCommentResponse> call = apiService.GetAllCommentList("recent_all_comment", "all", last_comment_id);
        call.enqueue(new Callback<RecentAllCommentResponse>() {
            @Override
            public void onResponse(Call<RecentAllCommentResponse> call, Response<RecentAllCommentResponse> response) {
                RecentAllCommentResponse recentAllCommentResponse = response.body();
                if(!recentAllCommentResponse.isError()){
                    int recentCommentSize = recentAllCommentResponse.getRecentCommentList().size();
                    for(int i=0;i<recentCommentSize;i++){
                        recentCommentListitems.add(recentAllCommentResponse.getRecentCommentList().get(i));
                        lastCommentId = recentAllCommentResponse.getRecentCommentList().get(i).getComment_id();
                    }
                    recyclerAdapter.notifyDataSetChanged();

                }else{
                }
            }

            @Override
            public void onFailure(Call<RecentAllCommentResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());
                Toast.makeText(getApplicationContext(), networkErrorStr,Toast.LENGTH_SHORT).show();
            }
        });

    }

    private abstract class EndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener {


        private int previousTotal = 0; // The total number of items in the dataset after the last load
        private boolean loading = true; // True if we are still waiting for the last set of data to load.
        private int visibleThreshold = LOAD_DATA_COUNT; // The minimum amount of items to have below your current scroll position before loading more.
        int firstVisibleItem, visibleItemCount, totalItemCount;

        private int current_page = 1;

        private LinearLayoutManager mLinearLayoutManager;

        public EndlessRecyclerOnScrollListener(LinearLayoutManager linearLayoutManager) {
            this.mLinearLayoutManager = linearLayoutManager;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            visibleItemCount = recyclerView.getChildCount();
            totalItemCount = mLinearLayoutManager.getItemCount();
            firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            if (!loading && (totalItemCount - visibleItemCount)
                    <= (firstVisibleItem + visibleThreshold)) {
                // End has been reached

                // Do something
                current_page++;

                onLoadMore(current_page);

                loading = true;
            }
        }

        public abstract void onLoadMore(int current_page);
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int TYPE_ITEM = 0;
        List<RecentCommentModel> listItems;

        private RecyclerAdapter(List<RecentCommentModel> listItems) {
            this.listItems = listItems;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_ITEM) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_tab2_recent_comment_item, parent, false);
                return new RecyclerAdapter.Comment_VH(v);
            }
            throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
        }

        private RecentCommentModel getItem(int position) {
            return listItems.get(position);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            if (holder instanceof RecyclerAdapter.Comment_VH) {
                final RecentCommentModel currentItem = getItem(position);
                final RecyclerAdapter.Comment_VH VHitem = (RecyclerAdapter.Comment_VH)holder;

                //Glide Options
                RequestOptions requestOptions_user = new RequestOptions();
                requestOptions_user.placeholder(R.mipmap.user_profile_img);
                requestOptions_user.error(R.mipmap.user_profile_img);
                requestOptions_user.circleCrop();    //circle

                Glide.with(getApplicationContext())
                        .setDefaultRequestOptions(requestOptions_user)
                        .load(AppConfig.ServerAddress+currentItem.getProfile_img_thumb())
                        .into(VHitem.userProfile_iv);

                //Glide Options
                RequestOptions requestOptions_cafe = new RequestOptions();
                requestOptions_cafe.placeholder(R.mipmap.not_cafe_img);
                requestOptions_cafe.error(R.mipmap.not_cafe_img);
                requestOptions_cafe.circleCrop();    //circle

                Glide.with(getApplicationContext())
                        .setDefaultRequestOptions(requestOptions_cafe)
                        .load(AppConfig.ServerAddress+currentItem.getCafe_thumbnail())
                        .into(VHitem.cafeProfile_iv);

                VHitem.userNickName_tv.setText(currentItem.getNick_name());
                VHitem.comment_tv.setText(currentItem.getComment_text());
                VHitem.cafeName_tv.setText(currentItem.getCafe_name());

                Date to = null;
                try{
                    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    to = transFormat.parse(currentItem.getCreated_at());
                }catch (ParseException p){
                    p.printStackTrace();
                }
                VHitem.created_at_tv.setText(commonUtil.formatTimeString(to));

                VHitem.comment_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getApplicationContext(), AboutCafeActivity.class);
                        intent.putExtra("isData", "N");
                        intent.putExtra("cafe_id", getItem(position).getCafe_id());
                        startActivity(intent);
                    }
                });
            }
        }

        private class Comment_VH extends RecyclerView.ViewHolder{

            ImageView userProfile_iv;
            TextView userNickName_tv;
            TextView created_at_tv;
            TextView comment_tv;
            ImageView cafeProfile_iv;
            TextView cafeName_tv;
            ViewGroup comment_layout;

            private Comment_VH(View itemView){
                super(itemView);
                userProfile_iv = (ImageView)itemView.findViewById(R.id.user_profile_img);
                userNickName_tv = (TextView)itemView.findViewById(R.id.user_nickname_txt);
                created_at_tv = (TextView)itemView.findViewById(R.id.created_at_txt);
                comment_tv = (TextView)itemView.findViewById(R.id.comment_txt);
                cafeProfile_iv = (ImageView)itemView.findViewById(R.id.cafe_profile_img);
                cafeName_tv = (TextView)itemView.findViewById(R.id.cafe_name_txt);
                comment_layout = (ViewGroup)itemView.findViewById(R.id.comment_layout);
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
