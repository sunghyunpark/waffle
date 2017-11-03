package view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.yssh.waffle.R;

import java.util.ArrayList;
import java.util.List;

import api.ApiInterface;
import api.NaverApiClient;
import api.response.NaverBlogResponse;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import model.NaverBlogModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabFragment3 extends Fragment {

    View v;
    private ArrayList<NaverBlogModel> listItems;
    private int pageNum = 1;
    RecyclerAdapter adapter;
    private static final int LOAD_DATA_COUNT = 10;

    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindString(R.string.network_error_txt) String networkErrorStr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_tab_fragment3, container, false);

        ButterKnife.bind(this, v);

        init();
        return v;
    }

    private void init(){
        listItems = new ArrayList<NaverBlogModel>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        adapter = new RecyclerAdapter(listItems);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        LoadDataFromNaverAPI();

        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                // do something...
                pageNum += 11;
                LoadDataFromNaverAPI();
            }
        });

    }

    /**
     * Naver OPEN API
     */
    private void LoadDataFromNaverAPI(){
        ApiInterface apiService =
                NaverApiClient.getClient().create(ApiInterface.class);

        Call<NaverBlogResponse> call = apiService.GetNaver("카페", pageNum);
        call.enqueue(new Callback<NaverBlogResponse>() {
            @Override
            public void onResponse(Call<NaverBlogResponse> call, Response<NaverBlogResponse> response) {
                NaverBlogResponse naverBlogResponse = response.body();
                int size = naverBlogResponse.getItems().size();
                for(int i=0;i<size;i++){
                    listItems.add(naverBlogResponse.getItems().get(i));
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NaverBlogResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e("tag", t.toString());
                Toast.makeText(getActivity(), networkErrorStr,Toast.LENGTH_SHORT).show();
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

        List<NaverBlogModel> listItems;

        private RecyclerAdapter(List<NaverBlogModel> listItems) {
            this.listItems = listItems;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == TYPE_ITEM) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_tab3_blog_item, parent, false);
                return new RecyclerAdapter.Blog_VH(v);
            }
            throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
        }

        private NaverBlogModel getItem(int position) {
            return listItems.get(position);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            if (holder instanceof Blog_VH) {
                final NaverBlogModel currentItem = getItem(position);
                final Blog_VH VHitem = (Blog_VH)holder;

                VHitem.blogNum_tv.setText(position+1+"");
                VHitem.blogName_tv.setText(currentItem.getBloggername());
                VHitem.blogPostDate_tv.setText(currentItem.getPostdate());
                VHitem.blogTitle_tv.setText(removeHTMLTAG(currentItem.getTitle()));
                VHitem.blogDescription_tv.setText(removeHTMLTAG(currentItem.getDescription()));

                VHitem.blog_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getActivity(), NaverBlogWebViewActivity.class);
                        intent.putExtra("url", currentItem.getLink());
                        startActivity(intent);
                    }
                });

            }
        }

        private class Blog_VH extends RecyclerView.ViewHolder{

            TextView blogName_tv;
            TextView blogTitle_tv;
            TextView blogNum_tv;
            TextView blogPostDate_tv;
            TextView blogDescription_tv;
            ViewGroup blog_layout;

            private Blog_VH(View itemView){
                super(itemView);
                blogName_tv = (TextView)itemView.findViewById(R.id.blog_name_txt);
                blogTitle_tv = (TextView)itemView.findViewById(R.id.blog_title_txt);
                blogNum_tv = (TextView)itemView.findViewById(R.id.blog_num_txt);
                blogPostDate_tv = (TextView)itemView.findViewById(R.id.post_date_txt);
                blogDescription_tv = (TextView)itemView.findViewById(R.id.description_txt);
                blog_layout = (ViewGroup)itemView.findViewById(R.id.blog_layout);
            }
        }

        /**
         * HTML TAG 제거
         * @param text
         * @return
         */
        private String removeHTMLTAG(String text){
            return text.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
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
