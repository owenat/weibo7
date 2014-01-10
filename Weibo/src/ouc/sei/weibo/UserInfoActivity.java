/**
 * @author wangqi
 *2013-7-23 ����3:26:57
 */
package ouc.sei.weibo;

import org.json.JSONException;
import org.json.JSONObject;

import ouc.sei.app.WeiboApp;
import ouc.sei.app.WeiboParameters;
import ouc.sei.entity.User;
import ouc.sei.utils.TaskFeedback;
import ouc.sei.widgets.LoadingDialog;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

/**
 * TODO wangqi 3:26:57
 */
public class UserInfoActivity extends Activity {
	private TextView title_text;
	// private ImageView write_btn;
	private ImageView refresh_btn;
	private String url = WeiboParameters.USER_URL;
	private User user;
	private TextView districtText;
	private TextView fansText;
	private TextView favoritesText;
	private TextView followersText;
	private ImageView genderImage;
	private TextView siganatureText;
	private TextView topicsText;
	private ImageView userFaceImage;
	private TextView usernameText;
	private LoadingDialog load;
	private OnClickListener l = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String str = (String) v.getTag();
			str = str.replace("/50/", "/180/");
			System.out.println("+++++++++++++++++" + str);
			Intent localIntent = new Intent();
			localIntent.setClass(UserInfoActivity.this, ImageActivity.class);
			Bundle localBundle = new Bundle();
			localBundle.putString("imageurl", str);
			localIntent.putExtras(localBundle);
			UserInfoActivity.this.startActivity(localIntent);
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.user_info);
		WeiboApp.addActivity(this);
		init();
		getDate();
	}

	public void getDate() {
		this.user = ((User) getIntent().getExtras().getSerializable("user"));
		if (this.user == null) {
			load = new LoadingDialog(this);
			load.show();
			findViewById(R.id.user_info_add).setVisibility(View.GONE);
			get_user_info();
		} else
			display_user_info();

	}

	public void refresh(View v) {
		getDate();
	}

	public void init() {
		// write_btn = ((ImageView) findViewById(R.id.write_btn));
		// write_btn.setVisibility(View.VISIBLE);
		refresh_btn = ((ImageView) findViewById(R.id.refresh_btn));
		refresh_btn.setVisibility(View.VISIBLE);
		title_text = ((TextView) findViewById(R.id.head_title));
		title_text.setText(R.string.userinfo);
		this.userFaceImage = ((ImageView) findViewById(R.id.user_info_userface));
		this.usernameText = ((TextView) findViewById(R.id.user_info_username));
		this.genderImage = ((ImageView) findViewById(R.id.user_info_gender));
		this.siganatureText = ((TextView) findViewById(R.id.user_info_siganature));
		this.topicsText = ((TextView) findViewById(R.id.user_info_topics));
		this.favoritesText = ((TextView) findViewById(R.id.user_info_favorites));
		this.followersText = ((TextView) findViewById(R.id.user_info_followers));
		this.fansText = ((TextView) findViewById(R.id.user_info_fans));
		this.districtText = ((TextView) findViewById(R.id.user_info_from));
		// this.realname = ((TextView)findViewById(2131296329));

	}

	public void getMyTopics(View v) {
		Intent intent = new Intent();
		intent.setClass(UserInfoActivity.this, MyTopicActivity.class);
		startActivity(intent);
	}

	public void getFavorite(View v) {
		Intent intent = new Intent();
		intent.setClass(UserInfoActivity.this, MyFavActivity.class);
		startActivity(intent);
	}

	public void getFans(View v) {
		Intent intent = new Intent();
		Bundle bundle=new Bundle();
		bundle.putString("user", String.valueOf(user.getUid()));
		intent.putExtras(bundle);
		intent.setClass(UserInfoActivity.this, FansListActivity.class);
		startActivity(intent);
	}

	public void getFollow(View v) {
		Intent intent = new Intent();
		intent.setClass(UserInfoActivity.this, FollowListActivity.class);
		Bundle bundle=new Bundle();
		bundle.putString("user", String.valueOf(user.getUid()));
		intent.putExtras(bundle);
		startActivity(intent);
	}

	private void display_user_info() {
		((AQuery) new AQuery(this).id(this.userFaceImage)).image(this.user
				.getProfileImageUrl());
		this.userFaceImage.setTag(this.user.getProfileImageUrl());
		this.userFaceImage.setOnClickListener(l);
		this.usernameText.setText(this.user.getName());
		if (this.user.getGender().equals("f"))
			this.genderImage
					.setBackgroundResource(R.drawable.userinfo_icon_female);
		else
			this.genderImage
					.setBackgroundResource(R.drawable.userinfo_icon_male);
		this.topicsText.setText(String.valueOf(this.user.getStatusesCount()));
		this.favoritesText.setText(String.valueOf(this.user
				.getFavouritesCount()));
		this.followersText.setText(String.valueOf(this.user.getFriendsCount()));
		this.fansText.setText(String.valueOf(this.user.getFollowersCount()));
		fansText.setSingleLine(true);
		if (this.user.getDescription() != null)
			this.siganatureText.setText(this.user.getDescription());
		else
			this.siganatureText.setText("未添加简介");
		this.districtText.setText(this.user.getLocation());
	}

	private void get_user_info() {
		new AQuery(UserInfoActivity.this).ajax(url, JSONObject.class,
				new AjaxCallback<JSONObject>() {
					@Override
					public void callback(String url, JSONObject object,
							AjaxStatus status) {
						// TODO Auto-generated method stub
						super.callback(url, object, status);
						if (status.getCode() != 200) {
							TaskFeedback.getInstance(UserInfoActivity.this)
									.failed("user_info_网络错误！");
							load.dismiss();
						} else {
							try {
								user = new User();
								load.dismiss();
								user.parseWeiboUser(object);
								display_user_info();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								load.dismiss();
								System.out.println("error");
							}
						}

					}
				});
	}
}