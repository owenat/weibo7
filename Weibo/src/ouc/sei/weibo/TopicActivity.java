/**
 * @author wangqi
 *2013-7-26 下午9:00:22
 */
package ouc.sei.weibo;

import ouc.sei.adapter.TopicListAdapter;
import ouc.sei.entity.Topic;
import ouc.sei.utils.Logger;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.HeaderViewListAdapter;

/**
 * TODO 下午9:00:22
 */
public abstract class TopicActivity extends TopicCommonActivity<Topic> {

	protected AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> paramAdapterView,
				View paramView, int paramInt, long paramLong) {
//			if (paramLong == 0L) {
//				Logger.d("0L");
				Object localObject = (BaseAdapter) ((HeaderViewListAdapter) paramAdapterView
						.getAdapter()).getWrappedAdapter();
				if ((localObject instanceof TopicListAdapter)) {
					Topic localTopic = (Topic) ((BaseAdapter) localObject).getItem(paramInt - 1);
					Logger.v(((TopicListAdapter) localObject).getItem(paramInt - 1)+"'");
					Intent localIntent = new Intent(TopicActivity.this,
							TopicDetailActivity.class);
					localObject = new Bundle();
					((Bundle) localObject).putSerializable("topic", localTopic);
					localIntent.putExtras((Bundle) localObject);
					TopicActivity.this.startActivity(localIntent);
					TopicActivity.this.overridePendingTransition(R.anim.translucent_zoom_in,
							R.anim.translucent_zoom_exit);
//					Logger.d(localTopic.toString());
//				}
			}
		}
	};
	  protected Adapter setAdapter()
	  {
		TopicListAdapter localTopicAdapter = new TopicListAdapter(this.data, this);
	    this.listView.setAdapter(localTopicAdapter);
	    return localTopicAdapter;
	  }

	  protected void setLayout()
	  {
	    setContentView(R.layout.home_view);
	  }

}
