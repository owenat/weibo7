/**
 * @author wangqi
 *2013-7-27 上午9:48:00
 */
package ouc.sei.adapter;

import java.text.ParseException;
import java.util.ArrayList;

import ouc.sei.entity.Topic;
import ouc.sei.entity.User;
import ouc.sei.utils.TextUtils;
import ouc.sei.weibo.ImageActivity;
import ouc.sei.weibo.R;
import ouc.sei.weibo.UserInfoActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;

/**
 * TODO 上午9:48:00
 */
public class TopicListAdapter extends BaseAdapter {
	private ArrayList<Topic> list = null;
	private Context mContext = null;
	private View.OnClickListener faceClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			User localUser = (User) v.getTag();
			Intent localIntent = new Intent();
			localIntent.setClass(TopicListAdapter.this.mContext,
					UserInfoActivity.class);
			Bundle localBundle = new Bundle();
			localBundle.putSerializable("user", localUser);
			localIntent.putExtras(localBundle);
			TopicListAdapter.this.mContext.startActivity(localIntent);
		}
	};
	private View.OnClickListener imgClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String str = (String) v.getTag();
			Intent localIntent = new Intent();
			localIntent.setClass(TopicListAdapter.this.mContext,
					ImageActivity.class);
			Bundle localBundle = new Bundle();
			localBundle.putString("imageurl", str);
			localIntent.putExtras(localBundle);
			TopicListAdapter.this.mContext.startActivity(localIntent);
		}
	};

	public TopicListAdapter(ArrayList<Topic> data, Context paramContext) {
		this.list = data;
		this.mContext = paramContext;
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int paramInt) {
		return this.list.get(paramInt);
	}

	public long getItemId(int paramInt) {
		return 0L;
	}

	public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
		ViewHolder localViewHolder;
		if (paramView != null) {
			localViewHolder = (ViewHolder) paramView.getTag();
		} else {
			localViewHolder = new ViewHolder();
			paramView = View.inflate(this.mContext, R.layout.topic_list_item,
					null);
			localViewHolder.face_image_view = ((ImageView) paramView
					.findViewById(R.id.tweet_listitem_userface));
			localViewHolder.username_text_view = ((TextView) paramView
					.findViewById(R.id.tweet_listitem_username));
			localViewHolder.v_icon = ((ImageView) paramView
					.findViewById(R.id.v_icon));
			// localViewHolder.real_name =
			// ((TextView)paramView.findViewById(R.id.user_realname));
			localViewHolder.content_text_view = ((TextView) paramView
					.findViewById(R.id.tweet_listitem_content));
			localViewHolder.content_image_view = ((ImageView) paramView
					.findViewById(R.id.tweet_listitem_image));
			localViewHolder.date_text_view = ((TextView) paramView
					.findViewById(R.id.tweet_listitem_date));
			localViewHolder.from_text_view = ((TextView) paramView
					.findViewById(R.id.tweet_listitem_client));
			localViewHolder.root_topic_bg = ((LinearLayout) paramView
					.findViewById(R.id.root_topic_bg));
			localViewHolder.tweet_root_content = ((TextView) paramView
					.findViewById(R.id.tweet_root_content));
			localViewHolder.tweet_root_image = ((ImageView) paramView
					.findViewById(R.id.tweet_root_image));
			localViewHolder.comment_count = ((TextView) paramView
					.findViewById(R.id.tweet_listitem_commentCount));
			localViewHolder.tweet_listitem_rtCount = ((TextView) paramView
					.findViewById(R.id.tweet_listitem_rtCount));
			paramView.setTag(localViewHolder);
		}
		Topic localTopic = (Topic) this.list.get(paramInt);
		AQuery localAQuery = new AQuery(this.mContext);
		if (localTopic.getSource() == null) {
			localViewHolder.content_text_view.setText(localTopic.getText());
		} else {
			((AQuery) localAQuery.id(localViewHolder.face_image_view)).image(
					localTopic.getUser().getProfileImageUrl(), true, true);
			localViewHolder.face_image_view
					.setOnClickListener(this.faceClickListener);
			localViewHolder.face_image_view.setTag(localTopic.getUser());
			if (localTopic.getUser().getScreenName().length() > 10)
				localViewHolder.username_text_view.setText(localTopic.getUser()
						.getScreenName().substring(0, 10));
			else
				localViewHolder.username_text_view.setText(localTopic.getUser()
						.getScreenName());

			localViewHolder.content_text_view.setText(localTopic.getText());
			localViewHolder.from_text_view.setText(localTopic.getSource());
			try {
				localViewHolder.date_text_view.setText(localTopic
						.getCreatedAtDif(localTopic.getCreatedAt()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			localViewHolder.comment_count.setText(String.valueOf(localTopic
					.getCommentsCount()));
			localViewHolder.tweet_listitem_rtCount.setText(String
					.valueOf(localTopic.getRepostsCount()));
			if (localTopic.getUser().isVerified() != true) {
				localViewHolder.v_icon.setVisibility(View.GONE);
				// localViewHolder.real_name.setVisibility(View.GONE);
			} else {
				localViewHolder.v_icon.setVisibility(View.VISIBLE);
				// localViewHolder.real_name.setVisibility(View.VISIBLE);
				// localViewHolder.real_name.setText("(" +
				// localTopic.getUser().getName() + ")");
			}
			if (TextUtils.isEmpty(localTopic.getThumbnailPic())) {
				localViewHolder.content_image_view.setVisibility(View.GONE);
			} else {
				localViewHolder.content_image_view.setVisibility(View.VISIBLE);
				((AQuery) localAQuery.id(localViewHolder.content_image_view))
						.image(localTopic.getThumbnailPic());
				localViewHolder.content_image_view
						.setOnClickListener(this.imgClickListener);
				localViewHolder.content_image_view.setTag(localTopic
						.getOriginalPic());
			}
			if (localTopic.getRetweeted_status() == null) {
				localViewHolder.root_topic_bg.setVisibility(View.GONE);
			} else {
				localViewHolder.root_topic_bg.setVisibility(View.VISIBLE);
				new String();
				String str;
				if (localTopic.getRetweeted_status().getThumbnailPic() != null) {
					str = "@"
							+ localTopic.getRetweeted_status().getUser()
									.getName() + ":"
							+ localTopic.getRetweeted_status().getText();
					localViewHolder.tweet_root_image
							.setVisibility(View.VISIBLE);
					((AQuery) localAQuery.id(localViewHolder.tweet_root_image))
							.image(localTopic.getRetweeted_status()
									.getThumbnailPic());
					localViewHolder.tweet_root_image
							.setOnClickListener(this.imgClickListener);
					localViewHolder.tweet_root_image.setTag(localTopic
							.getRetweeted_status().getOriginalPic());

					localViewHolder.tweet_root_content.setText(str);
				} else {
					localViewHolder.tweet_root_image.setVisibility(View.GONE);
				}
			}
		}
		return paramView;
	}

	private class ViewHolder {
		TextView comment_count;
		ImageView content_image_view;
		TextView content_text_view;
		TextView date_text_view;
		ImageView face_image_view;
		TextView from_text_view;
		// TextView real_name;
		LinearLayout root_topic_bg;
		TextView tweet_listitem_rtCount;
		TextView tweet_root_content;
		ImageView tweet_root_image;
		TextView username_text_view;
		ImageView v_icon;

		private ViewHolder() {
		}
	}
}
