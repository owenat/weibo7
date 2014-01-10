package ouc.sei.weibo;
import ouc.sei.app.WeiboParameters;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**@我的微博
 * TODO wangqi 11:35:13
 */
public class AtMeActivity extends TopicActivity {
	private ImageView refresh_btn;
	private ImageView write_btn;
	private TextView title_text;

	public void init() {
		refresh_btn = ((ImageView) findViewById(R.id.refresh_btn));
		refresh_btn.setVisibility(View.VISIBLE);
		write_btn = ((ImageView) findViewById(R.id.write_btn));
		write_btn.setVisibility(View.VISIBLE);
		title_text = ((TextView) findViewById(R.id.head_title));
		title_text.setText(R.string.mtab_me);
	}

	public void write(View v) {
		Intent intent = new Intent();
		intent.setClass(AtMeActivity.this, PubActivity.class);
		Bundle localBundle = new Bundle();
		localBundle.putInt("flag", 0);//create 发布一条微博
		startActivity(intent);
		overridePendingTransition(R.anim.slide_out_down,
				R.anim.gd_grow_from_bottomleft_to_topright);
	}

	public void refresh(View v) {
		this.listView.clickRefresh();
		refresh_List();
	}

	@Override
	protected void _onCreate() {
		init();
		this.url=WeiboParameters.MENTION_URL;
		  this.listView.setOnItemClickListener(this.itemClickListener);
	}

	@Override
	protected void addParams(WeiboParameters paramWeiboParameters) {
		// TODO Auto-generated method stub

	}

}