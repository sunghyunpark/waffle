package view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yssh.waffle.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import util.CropView;

public class ImageViewerFragment extends Fragment {

    private int position;
    private String  imgPath;
    @BindView(R.id.img_iv) CropView img_iv;
    View v;

    public static ImageViewerFragment create(int position, String imgPath) {
        ImageViewerFragment fragment = new ImageViewerFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        args.putString("imgPath", imgPath);

        fragment.setArguments(args);
        return fragment;
    }
    public ImageViewerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("position");
        imgPath = getArguments().getString("imgPath");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = (ViewGroup)inflater.inflate(R.layout.fragment_image_viewer,container, false);
        ButterKnife.bind(this, v);

        //Glide Options
        /*
        RequestOptions requestOptions = new RequestOptions();

        Glide.with(getActivity())
                .setDefaultRequestOptions(requestOptions)
                .load(AppConfig.ServerAddress+imgPath)
                .into(img_iv);
*/
        Toast.makeText(getActivity(), imgPath,Toast.LENGTH_SHORT).show();
        return v;
    }

}
