package view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yssh.waffle.AppConfig;
import com.yssh.waffle.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Grid 형식으로 사진들 보여주는 화면
 * ArrayList<String>으로 받아온다.
 * cafeName(Title) 도 받아옴
 */
public class PhotoSelectActivity extends AppCompatActivity {

    private ArrayList<String> photoList;
    private String titleStr;

    RecyclerAdapter adapter;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.title_txt) TextView title_tv;
    @BindView(R.id.back_btn) ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_select);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        photoList = (ArrayList<String>) intent.getSerializableExtra("photoList");
        titleStr = intent.getExtras().getString("cafeName");

        init();
    }

    private void init(){
        title_tv.setText(titleStr);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        GridLayoutManager lLayout = new GridLayoutManager(getApplicationContext(),4);
        recyclerView.setLayoutManager(lLayout);

        adapter = new RecyclerAdapter(photoList);
        recyclerView.setAdapter(adapter);
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private static final int TYPE_ITEM = 1;
        ArrayList<String> listItems;

        public RecyclerAdapter(ArrayList<String> listItems) {
            this.listItems = listItems;
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_ITEM) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_photo_select_item, parent, false);
                return new VHitem(v);
            }
            throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
        }

        private String getItem(int position) {
            return listItems.get(position);
        }


        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            if (holder instanceof VHitem)//아이템(게시물)
            {
                final VHitem VHitem = (VHitem)holder;

                //Glide Options
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.centerCrop();

                Glide.with(getApplicationContext())
                        .setDefaultRequestOptions(requestOptions)
                        .load(AppConfig.ServerAddress+getItem(position))
                        .into(VHitem.thumb_img_iv);
                VHitem.thumb_img_iv.setLayoutParams(setSize());

            }
        }
        class VHitem extends RecyclerView.ViewHolder{

            ImageView thumb_img_iv;

            public VHitem(View itemView){
                super(itemView);
                thumb_img_iv = (ImageView)itemView.findViewById(R.id.thumb_img);
            }
        }
        private RelativeLayout.LayoutParams setSize(){
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(AppConfig.DISPLAY_WIDTH/4,
                    AppConfig.DISPLAY_WIDTH/4);
            return params;
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
