/**
 * @author wangqi
 *2013-7-30 下午9:36:21
 */
package ouc.sei.adapter;

import java.text.ParseException;
import java.util.List;

import ouc.sei.entity.Comment;
import ouc.sei.entity.User;
import ouc.sei.weibo.R;
import ouc.sei.weibo.UserInfoActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;

/**
 * TODO 下午9:36:21
 */
public class CommentMyAdapter extends BaseAdapter {
	private List<Comment> list;
	private Context mContext;
	private View.OnClickListener faceClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			User localUser = (User) v.getTag();
			Intent localIntent = new Intent();
			localIntent.setClass(CommentMyAdapter.this.mContext,
					UserInfoActivity.class);
			Bundle localBundle = new Bundle();
			localBundle.putSerializable("user", localUser);
			localIntent.putExtras(localBundle);
			CommentMyAdapter.this.mContext.startActivity(localIntent);
		}
	};

	public CommentMyAdapter(List<Comment> paramList, Context paramContext) {
		this.list = paramList;
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
			paramView = View.inflate(this.mContext, R.layout.comment_my_view,
					null);
			localViewHolder.face_image_view = ((ImageView) paramView
					.findViewById(R.id.tweet_listitem_userface));
			localViewHolder.username_text_view = ((TextView) paramView
					.findViewById(R.id.tweet_listitem_username));
			localViewHolder.content_text_view = ((TextView) paramView
					.findViewById(R.id.tweet_listitem_content));
			localViewHolder.date_text_view = ((TextView) paramView
					.findViewById(R.id.tweet_listitem_date));
			localViewHolder.v_icon = ((ImageView) paramView
					.findViewById(R.id.v_icon));
			localViewHolder.comment_gender = ((ImageView) paramView
					.findViewById(R.id.tweet_comment_gender));
			// localViewHolder.real_name =
			// ((TextView)paramView.findViewById(2131296259));
			localViewHolder.to_topic = ((TextView) paramView
					.findViewById(R.id.tweet_root_content));
			paramView.setTag(localViewHolder);
		}
		Comment comment = (Comment) this.list.get(paramInt);
		((AQuery) new AQuery(this.mContext).id(localViewHolder.face_image_view))
				.image(comment.getUser().getProfileImageUrl());
		localViewHolder.face_image_view.setOnClickListener(faceClickListener);
		localViewHolder.face_image_view.setTag(comment.getUser());
		if (comment.getUser().getName().length() > 10)
			localViewHolder.username_text_view.setText(comment.getUser()
					.getName().substring(0, 10));
		else
			localViewHolder.username_text_view.setText(comment.getUser()
					.getName());
		localViewHolder.content_text_view.setText(comment.getText());
		try {
			localViewHolder.date_text_view.setText(comment
					.getCreatedAtDif(comment.getCreatedAt()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (comment.getReply_comment() != null)
			localViewHolder.to_topic.setText("回复我的评论："
					+ comment.getReply_comment().getText());
		else{
			localViewHolder.to_topic.setText("回复我的评论："
					+ comment.getStatus().getText());
		}
		if (comment.getUser().isVerified() == false) {
			localViewHolder.v_icon.setVisibility(View.GONE);
		} else {
			localViewHolder.v_icon.setVisibility(View.VISIBLE);
		}
		if (comment.getUser().getGender().equals("f"))
			localViewHolder.comment_gender
					.setBackgroundResource(R.drawable.userinfo_icon_female);
		else
			localViewHolder.comment_gender
					.setBackgroundResource(R.drawable.userinfo_icon_male);
		return paramView;
	}

	private class ViewHolder {
		TextView content_text_view;
		TextView date_text_view;
		ImageView face_image_view;
		// TextView real_name;
		TextView to_topic;
		TextView username_text_view;
		ImageView v_icon;
		ImageView comment_gender;

		private ViewHolder() {
		}
	}
}
