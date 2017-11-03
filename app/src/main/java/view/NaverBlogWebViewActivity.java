package view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import com.yssh.waffle.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NaverBlogWebViewActivity extends AppCompatActivity {

    @BindView(R.id.webview) WebView mWebView;
    @BindView(R.id.back_btn) ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_naver_blog_web_view);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        String url = intent.getExtras().getString("url");

        init(url);

    }

    private void init(String url){

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClientClass());
    }

    private class WebViewClientClass extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
            view.loadUrl(url);
            return true;
        }
    }

    @OnClick(R.id.back_btn) void goBack(){
        finish();
    }
}
