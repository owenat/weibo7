package ouc.sei.weibo;

import ouc.sei.app.WeiboParameters;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * TODO wangqi 11:34:51
 */
public class HomeActivity extends TopicActivity {

	private ImageView refresh_btn;
	private ImageView write_btn;
//	private ProgressBar loading;
	private TextView title_text;


	public void write(View v) {
		Intent intent = new Intent();
		intent.setClass(HomeActivity.this, PubActivity.class);
		Bundle localBundle = new Bundle();
		localBundle.putInt("flag", 0);//create 发布一条微博
		intent.putExtras(localBundle);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_out_down,
				R.anim.gd_grow_from_bottomleft_to_topright);
	}

	public void refresh(View v) {
	    this.listView.clickRefresh();
	    refresh_List();
	}

	@Override
	protected void addParams(WeiboParameters paramWeiboParameters) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void _onCreate() {
		refresh_btn = ((ImageView) findViewById(R.id.refresh_btn));
		refresh_btn.setVisibility(View.VISIBLE);
		write_btn = ((ImageView) findViewById(R.id.write_btn));
		write_btn.setVisibility(View.VISIBLE);
		title_text = ((TextView) findViewById(R.id.head_title));
		title_text.setText(R.string.mtab_home);	
	    this.listView.setOnItemClickListener(this.itemClickListener);
	    this.url=WeiboParameters.HOME_URL;
	}
}