/**
 * @author wangqi
 *2013-7-30 上午11:21:41
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
 * TODO 上午11:21:41
 */
public class CommentAdapter extends BaseAdapter {
	private List<Comment> list;
	private Context mContext;
	private View.OnClickListener faceClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			User localUser = (User) v.getTag();
			Intent localIntent = new Intent();
			localIntent.setClass(CommentAdapter.this.mContext,
					UserInfoActivity.class);
			Bundle localBundle = new Bundle();
			localBundle.putSerializable("user", localUser);
			localIntent.putExtras(localBundle);
			CommentAdapter.this.mContext.startActivity(localIntent);
		}
	};

	public CommentAdapter(List<Comment> paramList, Context paramContext) {
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
			paramView = View.inflate(this.mContext, R.layout.comment_list_item,
					null);
			localViewHolder.face_image_view = ((ImageView) paramView
					.findViewById(R.id.tweet_listitem_userface));
			localViewHolder.username_text_view = ((TextView) paramView
					.findViewById(R.id.tweet_listitem_username));
			localViewHolder.v_icon = ((ImageView) paramView
					.findViewById(R.id.v_icon));
			localViewHolder.comment_gender = ((ImageView) paramView
					.findViewById(R.id.tweet_commentmy_gender));
			localViewHolder.date_text_view = ((TextView) paramView
					.findViewById(R.id.tweet_listitem_date));
			localViewHolder.content_text_view = ((TextView) paramView
					.findViewById(R.id.tweet_listitem_content));
			// localViewHolder.content_image_view =
			// ((ImageView)paramView.findViewById(2131296262));
			localViewHolder.from_text_view = ((TextView) paramView
					.findViewById(R.id.tweet_listitem_client));
			// localViewHolder.real_name =
			// ((TextView)paramView.findViewById(2131296259));
			paramView.setTag(localViewHolder);
		}
		Comment localComment = (Comment) this.list.get(paramInt);
		((AQuery) new AQuery(this.mContext).id(localViewHolder.face_image_view))
				.image(localComment.getUser().getProfileImageUrl());
		localViewHolder.face_image_view.setOnClickListener(faceClickListener);
		localViewHolder.face_image_view.setTag(localComment.getUser());
		if (localComment.getUser().getName().length() >= 10)
			localViewHolder.username_text_view.setText(localComment.getUser()
					.getName().substring(0, 10));
		else localViewHolder.username_text_view.setText(localComment.getUser()
				.getName());
		localViewHolder.content_text_view.setText(localComment.getText());
		/*
		 * if (localComment.getReply_comment() == null &&
		 * localComment.getRetweeted_status() == null)
		 * localViewHolder.content_text_view.setText(localComment.getText());
		 * else if (localComment.getReply_comment() != null) { str =
		 * localComment.getText() + "//@" +
		 * localComment.getReply_comment().getUser().getScreenName() + ":" +
		 * localComment.getReply_comment().getText();
		 * localViewHolder.content_text_view.setText(str); } else if
		 * (localComment.getRetweeted_status() != null) { str =
		 * localComment.getText() + "//@" +
		 * localComment.getRetweeted_status().getUser() .getScreenName() + ":" +
		 * localComment.getRetweeted_status().getText();
		 * localViewHolder.content_text_view.setText(str); } else { str =
		 * localComment.getText() + "//@" +
		 * localComment.getReply_comment().getUser().getScreenName() + ":" +
		 * localComment.getReply_comment().getText() + "//@" +
		 * localComment.getRetweeted_status().getUser() .getScreenName() + ":" +
		 * localComment.getRetweeted_status().getText();
		 * localViewHolder.content_text_view.setText(str); }
		 */
		localViewHolder.from_text_view.setText(localComment.getSource());
		try {
			localViewHolder.date_text_view.setText(localComment
					.getCreatedAtDif(localComment.getCreatedAt()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (localComment.getUser().isVerified() == false) {
			localViewHolder.v_icon.setVisibility(View.GONE);
			// localViewHolder.real_name.setVisibility(View.GONE);
		} else {
			localViewHolder.v_icon.setVisibility(View.VISIBLE);
			// localViewHolder.real_name.setVisibility(0);
			// localViewHolder.real_name.setText("(" +
			// localComment.getUser().getValidate_true_name() + ")");
		}
		if (localComment.getUser().getGender().equals("f"))
			localViewHolder.comment_gender
					.setBackgroundResource(R.drawable.userinfo_icon_female);
		else
			localViewHolder.comment_gender
					.setBackgroundResource(R.drawable.userinfo_icon_male);
		return paramView;
	}

	private class ViewHolder {
		// ImageView content_image_view;
		ImageView comment_gender;
		TextView content_text_view;
		TextView date_text_view;
		ImageView face_image_view;
		TextView from_text_view;
		// TextView real_name;
		TextView username_text_view;
		ImageView v_icon;

		private ViewHolder() {
		}
	}
}