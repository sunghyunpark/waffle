package view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import api.response.MyCommentResponse;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import util.CommonUtil;

public class MyCommentCafeActivity extends AppCompatActivity {
    //RecyclerView
    RecyclerAdapter adapter;
    private ArrayList<MyCommentResponse.MyComment> commentModelArrayList;
    CommonUtil commonUtil = new CommonUtil();
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.back_btn) ImageButton backBtn;
    @BindString(R.string.network_error_txt) String networkErrorStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comment_cafe);

        ButterKnife.bind(this);

        //recyclerview 초기화
        commentModelArrayList = new ArrayList<MyCommentResponse.MyComment>();
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        adapter = new RecyclerAdapter(commentModelArrayList);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        LoadData();
    }

    private void LoadData(){
        if(commentModelArrayList != null)
            commentModelArrayList.clear();
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<MyCommentResponse> call = apiService.GetMyCommentCafeList("my_comment_cafe", UserModel.getInstance().getUid());
        call.enqueue(new Callback<MyCommentResponse>() {
            @Override
            public void onResponse(Call<MyCommentResponse> call, Response<MyCommentResponse> response) {
                MyCommentResponse myCommentResponse = response.body();
                if(!myCommentResponse.isError()){
                    int listSize = myCommentResponse.getCommentList().size();
                    for(int i=0;i<listSize;i++){
                        commentModelArrayList.add(myCommentResponse.getCommentList().get(i));
                    }
                    adapter.notifyDataSetChanged();

                }else{

                }
                //Toast.makeText(getActivity(), cafeResponse.getError_msg(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MyCommentResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());
                Toast.makeText(getApplicationContext(), networkErrorStr,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int TYPE_ITEM = 0;

        List<MyCommentResponse.MyComment> listItems;

        private RecyclerAdapter(List<MyCommentResponse.MyComment> listItems) {
            this.listItems = listItems;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_ITEM) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_my_comment_item, parent, false);
                return new RecyclerAdapter.Comment_VH(v);
            }
            throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
        }

        private MyCommentResponse.MyComment getItem(int position) {
            return listItems.get(position);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            if (holder instanceof RecyclerAdapter.Comment_VH) {
                final MyCommentResponse.MyComment currentItem = getItem(position);
                final RecyclerAdapter.Comment_VH VHitem = (RecyclerAdapter.Comment_VH)holder;
                //Glide Options
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.mipmap.not_cafe_img);
                requestOptions.error(R.mipmap.not_cafe_img);
                requestOptions.circleCrop();    //circle

                Glide.with(getApplicationContext())
                        .setDefaultRequestOptions(requestOptions)
                        .load(AppConfig.ServerAddress+currentItem.getCafe_thumbnail())
                        .into(VHitem.cafeProfile_iv);

                VHitem.cafeName_tv.setText(currentItem.getCafe_name());

                Glide.with(getApplicationContext())
                        .setDefaultRequestOptions(requestOptions)
                        .load(AppConfig.ServerAddress+currentItem.getProfile_img())
                        .into(VHitem.userProfile_iv);

                VHitem.userName_tv.setText(currentItem.getNick_name());

                Date to = null;
                try{
                    SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    to = transFormat.parse(currentItem.getCreated_at());
                }catch (ParseException p){
                    p.printStackTrace();
                }
                VHitem.updated_tv.setText(commonUtil.formatTimeString(to));

                VHitem.comment_tv.setText(currentItem.getComment_text());

                VHitem.comment_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        /**
                         * CafeModel 정보를 넘겨야하는데 현재 구조상 어려움...
                         * 현재 페이지를 들어올때 카페들 정보들까지 다 가져올껀지 아니면 클릭했을 때 정보를 받아오고 성공하면 AboutCafeActivity로 넘길지 고민이 필요함
                         */
                    }
                });
            }
        }

        /**
         * 리뷰 아이템
         */
        private class Comment_VH extends RecyclerView.ViewHolder{
            ImageView cafeProfile_iv;
            TextView cafeName_tv;
            ImageView userProfile_iv;
            TextView userName_tv;
            TextView updated_tv;
            TextView comment_tv;
            ViewGroup comment_layout;
            private Comment_VH(View itemView){
                super(itemView);
                cafeProfile_iv = (ImageView)itemView.findViewById(R.id.cafe_profile_img);
                cafeName_tv = (TextView)itemView.findViewById(R.id.cafe_name_txt);
                userProfile_iv = (ImageView)itemView.findViewById(R.id.user_profile_img);
                userName_tv = (TextView)itemView.findViewById(R.id.user_name_txt);
                updated_tv = (TextView)itemView.findViewById(R.id.created_at_txt);
                comment_tv = (TextView)itemView.findViewById(R.id.comment_txt);
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

    @OnClick(R.id.back_btn) void goBack(){
        finish();
    }
}
