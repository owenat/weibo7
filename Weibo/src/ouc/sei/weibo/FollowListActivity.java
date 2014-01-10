/**
 * @author wangqi
 *2013-8-1 下午3:08:53
 */
package ouc.sei.weibo;

import ouc.sei.adapter.FansAdapter;
import ouc.sei.app.WeiboParameters;
import ouc.sei.entity.User;
import ouc.sei.utils.Logger;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * TODO
 * 下午3:08:53
 */
public class FollowListActivity extends UserActivity {
	private ImageView back_btn;
	private ImageView refresh_btn;
	private TextView title_text;

	public void init() {
		refresh_btn = ((ImageView) findViewById(R.id.refresh_btn));
		refresh_btn.setVisibility(View.VISIBLE);
		back_btn = ((ImageView) findViewById(R.id.back_btn));
		back_btn.setVisibility(View.VISIBLE);
		title_text = ((TextView) findViewById(R.id.head_title));
		title_text.setText(R.string.focus);
		
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
		this.url = WeiboParameters.Follow_URL+this.getIntent().getExtras().getString("user");
		Logger.v(url);
		this.listView.setOnItemClickListener(this.itemClickListener);
	}

	@Override
	protected Adapter setAdapter() {
		FansAdapter localTopicAdapter = new FansAdapter(this.data, this);
		this.listView.setAdapter(localTopicAdapter);
		return localTopicAdapter;
	}

	@Override
	protected void setLayout() {
		// TODO Auto-generated method stub
		setContentView(R.layout.home_view);
	}

	protected AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> paramAdapterView,
				View paramView, int paramInt, long paramLong) {
			Object localObject = (BaseAdapter) ((HeaderViewListAdapter) paramAdapterView
					.getAdapter()).getWrappedAdapter();
			if ((localObject instanceof FansAdapter)) {
				User localTopic = (User) ((BaseAdapter) localObject)
						.getItem(paramInt - 1);
				Logger.v(((FansAdapter) localObject).getItem(paramInt - 1)
						+ "'");
				Intent localIntent = new Intent(FollowListActivity.this,
						UserInfoActivity.class);
				localObject = new Bundle();
				((Bundle) localObject).putSerializable("user", localTopic);
				localIntent.putExtras((Bundle) localObject);
				FollowListActivity.this.startActivity(localIntent);
				FollowListActivity.this.overridePendingTransition(
						R.anim.translucent_zoom_in,
						R.anim.translucent_zoom_exit);

			}
		}
	};

}