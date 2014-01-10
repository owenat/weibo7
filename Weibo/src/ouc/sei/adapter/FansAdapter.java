/**
 * @author wangqi
 *2013-8-1 下午3:16:57
 */
package ouc.sei.adapter;

import java.util.List;

import ouc.sei.entity.User;
import ouc.sei.weibo.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.androidquery.AQuery;

/**
 * TODO 下午3:16:57
 */
public class FansAdapter extends BaseAdapter {
	private List<User> list = null;
	private Context mContext = null;

	public FansAdapter(List<User> paramList, Context paramContext) {
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
			paramView = View.inflate(this.mContext, R.layout.follow_view_item,
					null);
			localViewHolder.background = ((LinearLayout) paramView
					.findViewById(R.id.background));
			localViewHolder.face_image_view = ((ImageView) paramView
					.findViewById(R.id.tweet_listitem_userface));
			localViewHolder.v_icon = ((ImageView) paramView
					.findViewById(R.id.v_icon));
			localViewHolder.tweet_commentmy_gender = ((ImageView) paramView
					.findViewById(R.id.tweet_fan_gender));
			localViewHolder.username_text_view = ((TextView) paramView
					.findViewById(R.id.username));
			localViewHolder.signature_text_view = ((TextView) paramView
					.findViewById(R.id.signature));
			paramView.setTag(localViewHolder);
		}
		User localUser = (User) this.list.get(paramInt);
		((AQuery) new AQuery(this.mContext).id(localViewHolder.face_image_view))
				.image(localUser.getProfileImageUrl());
		if (localUser.getScreenName().length() > 10)
			localViewHolder.username_text_view.setText(localUser
					.getScreenName().substring(0, 10));
		else
			localViewHolder.username_text_view.setText(localUser
					.getScreenName());
		if (localUser.isVerified() == true)
			localViewHolder.v_icon.setVisibility(View.VISIBLE);
		else
			localViewHolder.v_icon.setVisibility(View.GONE);
		if (localUser.getGender().equals("m")) {
			localViewHolder.tweet_commentmy_gender.setVisibility(View.VISIBLE);
			localViewHolder.tweet_commentmy_gender
					.setBackgroundResource(R.drawable.userinfo_icon_male);
		} else if (localUser.getGender().equals("f")) {
			localViewHolder.tweet_commentmy_gender.setVisibility(View.VISIBLE);
			localViewHolder.tweet_commentmy_gender
					.setBackgroundResource(R.drawable.userinfo_icon_female);
		} else
			localViewHolder.tweet_commentmy_gender.setVisibility(View.GONE);
		localViewHolder.signature_text_view.setText(localUser.getDescription());
		if (this.list.size() > 0)
			if (this.list.size() != 1) {
				if (paramInt != 0) {
					if (paramInt != -1 + this.list.size())
						localViewHolder.background
								.setBackgroundResource(R.drawable.setting_middle_bg);
					else
						localViewHolder.background
								.setBackgroundResource(R.drawable.setting_bottom_bg);
				} else
					localViewHolder.background
							.setBackgroundResource(R.drawable.setting_top_bg);
			} else
				localViewHolder.background
						.setBackgroundResource(R.drawable.setting_alone_bg);
		return paramView;
	}

	private class ViewHolder {
		LinearLayout background;
		ImageView face_image_view;
		ImageView v_icon;
		ImageView tweet_commentmy_gender;
		TextView signature_text_view;
		TextView username_text_view;

		private ViewHolder() {
		}
	}
}