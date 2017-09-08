package view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.yssh.waffle.R;

import java.util.ArrayList;
import java.util.List;

import model.CafeModel;

public class TabFragment1 extends Fragment {

    View v;
    //리사이클러뷰
    RecyclerAdapter adapter;
    RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<CafeModel> listItems;

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
        CafeModel cafeModel;
        for (int i=1;i<7;i++){
            cafeModel = new CafeModel();
            cafeModel.setCafeName(""+i);
            cafeModel.setCafeThumbnail("http://ustserver.cafe24.com/sh_test/test"+i+".jpg");
            listItems.add(cafeModel);
        }

        recyclerView.setAdapter(adapter);
        return v;
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
                        .load(currentItem.getCafeThumbnail())
                        .into(VHitem.cafeThumbnail);

            }else if(holder instanceof Header_Vh){

            }
        }

        /**
         * 카페 아이템
         */
        private class Cafe_VH extends RecyclerView.ViewHolder{
            TextView cafeName;
            ImageView cafeThumbnail;

            private Cafe_VH(View itemView){
                super(itemView);
                cafeName = (TextView)itemView.findViewById(R.id.cafe_name_txt);
                cafeThumbnail = (ImageView)itemView.findViewById(R.id.cafe_thumb_img);
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
