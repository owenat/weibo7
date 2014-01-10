/**
 * @author wangqi
 *2013-8-1 下午2:52:17
 */
package ouc.sei.weibo;

import ouc.sei.app.WeiboParameters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * TODO
 * 下午2:52:17
 */
public class MyTopicActivity extends TopicActivity {
	private ImageView back_btn;
	private ImageView refresh_btn;
	private TextView title_text;

	public void init() {
		refresh_btn = ((ImageView) findViewById(R.id.refresh_btn));
		refresh_btn.setVisibility(View.VISIBLE);
		back_btn = ((ImageView) findViewById(R.id.back_btn));
		back_btn.setVisibility(View.VISIBLE);
		title_text = ((TextView) findViewById(R.id.head_title));
		title_text.setText(R.string.mtab_me);
	}
	public void back(View v) {
		 finish();
	}

	public void refresh(View v) {
		this.listView.clickRefresh();
		refresh_List();
	}

	@Override
	protected void _onCreate() {
		init();
		this.url=WeiboParameters.ALLWEIBO_URL;
		  this.listView.setOnItemClickListener(this.itemClickListener);
	}

	@Override
	protected void addParams(WeiboParameters paramWeiboParameters) {
		// TODO Auto-generated method stub

	}
}

