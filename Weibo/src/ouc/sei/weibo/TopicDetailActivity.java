/**
 * @author wangqi
 *2013-7-26 下午9:12:16
 */
package ouc.sei.weibo;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ouc.sei.adapter.CommentAdapter;
import ouc.sei.app.WeiboApp;
import ouc.sei.app.WeiboParameters;
import ouc.sei.entity.Comment;
import ouc.sei.entity.Topic;
import ouc.sei.entity.User;
import ouc.sei.utils.Logger;
import ouc.sei.utils.TaskFeedback;
import ouc.sei.utils.TextUtils;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

/**
 * TODO 下午9:12:16
 */

/**
 * TODO 上午10:20:40
 */
public class TopicDetailActivity extends Activity {
	ImageView face_image_view;
	ImageView refresh_btn;
	ImageView back_btn;
	private int visibleLastIndex = 0; // 最后的可视项索引
	private int visibleItemCount;
	private View footerView;
	private ProgressBar moreFooterProgressBar;
	Button like_btn;
	TextView username_text_view;
	ImageView v_icon;
	TextView date_text_view;
	ImageView content_image_view;
	TextView from_text_view;
	LinearLayout root_topic_bg;
	// WebView tweet_root_webview;
	TextView tweet_root_webview;
	ImageView tweet_root_image;
	TextView comment_count;
	TextView tweet_listitem_rtCount;
	// WebView content_webview;
	TextView content_webview;
	Topic topic;
	AQuery localAQuery;
	String url;
	ListView res_list;
	ProgressBar pb;
	View localView;
	CommentAdapter localCommentAdapter;
	int temp = 0, count = 0;
	List<Comment> comments = new ArrayList<Comment>();

	public void back(View v) {
		finish();
	}

	public void refresh(View v) {
		 getComments();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tweet_topic_list);
		WeiboApp.addActivity(this);
		res_list = (ListView) findViewById(R.id.res_list);
		topic = (Topic) getIntent().getExtras().getSerializable("topic");
		WeiboParameters.WEIBO_ID = topic.getId();
		// init();
		res_list = (ListView) findViewById(R.id.res_list);
		res_list.setOnItemClickListener(itemClickListener);
		addHeader();
		addFooter();
		// setData();
		getComments();
		localCommentAdapter = new CommentAdapter(comments,
				TopicDetailActivity.this);
		res_list.setAdapter(localCommentAdapter);
		res_list.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView arg0, int scrollState) {
				// TODO Auto-generated method stub
				int itemsLastIndex = localCommentAdapter.getCount() - 1; // 数据集最后一项的索引
				int lastIndex = itemsLastIndex + 2; // 加上底部的loadMoreView项
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
						&& visibleLastIndex == lastIndex) {
					moreFooterProgressBar.setVisibility(View.VISIBLE);
					getComments();
				}
			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				TopicDetailActivity.this.visibleItemCount = visibleItemCount;
				visibleLastIndex = firstVisibleItem + visibleItemCount - 1;

			}
		});
	}

	/**
	 * 
	 */
	private void getComments() {
		// TODO Auto-generated method stub
		url = WeiboParameters.COMMENTS_URL + topic.getId();
		System.out.println("+++++++++++++" + url);
//		pb = (ProgressBar) findViewById(R.id.topic_progress);
//		pb.setVisibility(View.VISIBLE);
		localAQuery = new AQuery(this);
		localAQuery.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject object,
					AjaxStatus status) {
				// TODO Auto-generated method stub
				super.callback(url, object, status);
				if (status.getCode() == 200) {
					try {
						JSONArray array = (JSONArray) object.get("comments");
						if (temp < array.length()) {
							if (array.length() > 10) {
								for (int i = temp; i < temp + 10; i++) {
									JSONObject j = (JSONObject) array.get(i);
									Comment comment = new Comment();
									comment.parseWeiboStatus(j);
									comments.add(comment);
								}
								temp += 10;
							} else if (array.length() <= 10) {
								for (int i = temp; i < array.length(); i++) {
									JSONObject j = (JSONObject) array.get(i);
									Comment comment = new Comment();
									comment.parseWeiboStatus(j);
									comments.add(comment);
								}
								temp = array.length()+2;
							}
						} else {
							TaskFeedback.getInstance(TopicDetailActivity.this)
									.failed("没有更多评论了");
						}
						/*
						 * System.out
						 * .println("-------------------------------------------"
						 * + comments.size());
						 */
						localCommentAdapter.notifyDataSetChanged();
//						pb.setVisibility(View.GONE);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						res_list.setSelection(visibleLastIndex
								- visibleItemCount + 1); // 设置选中项
						moreFooterProgressBar.setVisibility(View.INVISIBLE);
//						if (pb.getVisibility() == 0)
//							pb.setVisibility(View.GONE);
					}

				} else {
					res_list.setSelection(visibleLastIndex - visibleItemCount
							+ 1); // 设置选中项
					moreFooterProgressBar.setVisibility(View.INVISIBLE);
					TaskFeedback.getInstance(TopicDetailActivity.this).failed(
							"网络连接错误");
					pb.setVisibility(View.GONE);
				}
			}

		});
	}

	public void addFooter() {
		footerView = (View) View.inflate(this, R.layout.list_footer, null);
		moreFooterProgressBar = (ProgressBar) footerView
				.findViewById(R.id.moreFooterProgressBar);
		res_list.addFooterView(footerView);
	}

	public void addHeader() {
		back_btn = (ImageView) findViewById(R.id.back_btn);
		like_btn = (Button) findViewById(R.id.topic_like);
		back_btn.setVisibility(View.VISIBLE);
		refresh_btn = (ImageView) findViewById(R.id.refresh_btn);
		refresh_btn.setVisibility(View.VISIBLE);
		localView = getLayoutInflater().inflate(R.layout.topic_list_item, null);
		face_image_view = (ImageView) localView
				.findViewById(R.id.tweet_listitem_userface);
		username_text_view = (TextView) localView
				.findViewById(R.id.tweet_listitem_username);
		v_icon = (ImageView) localView.findViewById(R.id.v_icon);
		date_text_view = (TextView) localView
				.findViewById(R.id.tweet_listitem_date);
		// content_webview = (WebView)
		// localView.findViewById(R.id.tweet_listitem_content);
		content_webview = (TextView) localView
				.findViewById(R.id.tweet_listitem_content);
		content_image_view = (ImageView) localView
				.findViewById(R.id.tweet_listitem_image);
		from_text_view = (TextView) localView
				.findViewById(R.id.tweet_listitem_client);
		root_topic_bg = (LinearLayout) localView
				.findViewById(R.id.root_topic_bg);
		// tweet_root_webview = (WebView)
		// localView.findViewById(R.id.tweet_root_content);
		tweet_root_webview = (TextView) localView
				.findViewById(R.id.tweet_root_content);
		tweet_root_image = (ImageView) localView
				.findViewById(R.id.tweet_root_image);
		comment_count = (TextView) localView
				.findViewById(R.id.tweet_listitem_commentCount);
		tweet_listitem_rtCount = (TextView) localView
				.findViewById(R.id.tweet_listitem_rtCount);
		setData();

	}

	public void setData() {
//		Logger.v(topic.toString());
		// setWebView(content_webview, topic.getText());
		content_webview.setText(topic.getText());
		AQuery localAQuery = new AQuery(this);
		localAQuery.id(face_image_view).image(
				topic.getUser().getProfileImageUrl());
		face_image_view.setOnClickListener(this.faceClickListener);
		face_image_view.setTag(topic.getUser());
		username_text_view.setText(topic.getUser().getScreenName());
		from_text_view.setText(topic.getSource());
		try {
			date_text_view.setText(topic.getCreatedAtDif(topic.getCreatedAt()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		comment_count.setText(String.valueOf(topic.getCommentsCount()));
		tweet_listitem_rtCount.setText(String.valueOf(topic.getRepostsCount()));
		if (topic.getUser().isVerified() != true) {
			v_icon.setVisibility(View.GONE);
		} else {
			v_icon.setVisibility(View.VISIBLE);
		}
		if (TextUtils.isEmpty(topic.getThumbnailPic())) {
			content_image_view.setVisibility(View.GONE);
		} else {
			content_image_view.setVisibility(View.VISIBLE);
			localAQuery.id(content_image_view).image(topic.getThumbnailPic());
			content_image_view.setOnClickListener(this.imgClickListener);
			content_image_view.setTag(topic.getOriginalPic());
		}
		if (topic.getRetweeted_status() == null) {
			root_topic_bg.setVisibility(View.GONE);
		} else {
			root_topic_bg.setVisibility(View.VISIBLE);
			new String();
			String str;
			if (topic.getRetweeted_status().getThumbnailPic() != null) {
				str = "@" + topic.getRetweeted_status().getUser().getName()
						+ ":" + topic.getRetweeted_status().getText();
				tweet_root_image.setVisibility(View.VISIBLE);
				((AQuery) localAQuery.id(tweet_root_image)).image(topic
						.getRetweeted_status().getThumbnailPic());
				tweet_root_image.setOnClickListener(this.imgClickListener);
				tweet_root_image.setTag(topic.getRetweeted_status()
						.getOriginalPic());
				// setWebView(tweet_root_webview, str);
				tweet_root_webview.setText(str);
			} else {
				tweet_root_image.setVisibility(View.GONE);
			}
		}
		res_list.addHeaderView(localView);
	}

	private String getWebBody(String paramString) {
		return ("<style>* {font-size:16px;line-height:20px;} p {color:#333;} a {color:#3E62A6;} img {max-width:310px;} img.alignleft {float:left;max-width:120px;margin:0 10px 5px 0;border:1px solid #ccc;background:#fff;padding:2px;} pre {font-size:9pt;line-height:12pt;font-family:Courier New,Arial;border:1px solid #ddd;border-left:5px solid #6CE26C;background:#f6f6f6;padding:5px;} a.tag {font-size:15px;text-decoration:none;background-color:#bbd6f3;border-bottom:2px solid #3E6D8E;border-right:2px solid #7F9FB6;color:#284a7b;margin:2px 2px 2px 0;padding:2px 4px;white-space:nowrap;}</style>" + paramString)
				.replaceAll("(<img[^>]*?)\\s+width\\s*=\\s*\\S+", "$1")
				.replaceAll("(<img[^>]*?)\\s+height\\s*=\\s*\\S+", "$1");
	}

	private View.OnClickListener faceClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			User localUser = (User) v.getTag();
			Intent localIntent = new Intent();
			localIntent.setClass(TopicDetailActivity.this,
					UserInfoActivity.class);
			Bundle localBundle = new Bundle();
			localBundle.putSerializable("user", localUser);
			localIntent.putExtras(localBundle);
			TopicDetailActivity.this.startActivity(localIntent);
		}
	};
	private View.OnClickListener imgClickListener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String str = (String) v.getTag();
			Intent localIntent = new Intent();
			localIntent.setClass(TopicDetailActivity.this, ImageActivity.class);
			Bundle localBundle = new Bundle();
			localBundle.putString("imageurl", str);
			localIntent.putExtras(localBundle);
			TopicDetailActivity.this.startActivity(localIntent);
		}
	};
	private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> paramAdapterView,
				View paramView, int paramInt, long paramLong) {
			Logger.v("++++++++++++++++++++++++++++++++++" + paramInt);
			Comment comment = (Comment) paramAdapterView.getAdapter().getItem(
					paramInt);
			Intent localIntent = new Intent(TopicDetailActivity.this,
					PubActivity.class);
			Bundle localBundle = new Bundle();
			localBundle.putInt("flag", 3);//create 回复一条评论
			localBundle.putSerializable("Topic", topic);
			localBundle.putSerializable("comment", comment);
			localIntent.putExtras(localBundle);
			startActivity(localIntent);
			overridePendingTransition(
					R.anim.gd_grow_from_bottomleft_to_topright,
					R.anim.slide_out_down);

		}
	};

	public void like(View v) {
		if (count % 2 == 0) {
			Drawable drawableM = getResources().getDrawable(
					R.drawable.toolbar_icon_like_highlighted);
			drawableM.setBounds(0, 0, drawableM.getMinimumWidth(),
					drawableM.getMinimumHeight());
			like_btn.setCompoundDrawables(drawableM, null, null, null);
		} else {
			Drawable drawableM = getResources().getDrawable(
					R.drawable.toolbar_icon_like);
			drawableM.setBounds(0, 0, drawableM.getMinimumWidth(),
					drawableM.getMinimumHeight());
			like_btn.setCompoundDrawables(drawableM, null, null, null);
		}
		count++;
	}
	public void retweet(View v){
		Intent localIntent = new Intent(TopicDetailActivity.this,
				PubActivity.class);
		Bundle localBundle = new Bundle();
		localBundle.putInt("flag", 2);//repost 转发一条微博
		localBundle.putSerializable("Topic", topic);
		localIntent.putExtras(localBundle);
		startActivity(localIntent);
		overridePendingTransition(
				R.anim.gd_grow_from_bottomleft_to_topright,
				R.anim.slide_out_down);
	}
	public void comment(View v){
		Intent localIntent = new Intent(TopicDetailActivity.this,
				PubActivity.class);
		Bundle localBundle = new Bundle();
		localBundle.putInt("flag", 1);//create 评论一条微博
		localBundle.putSerializable("Topic", topic);
		localIntent.putExtras(localBundle);
		startActivity(localIntent);
		overridePendingTransition(
				R.anim.gd_grow_from_bottomleft_to_topright,
				R.anim.slide_out_down);
	}

	public void setWebView(WebView view, String str) {
		view.getSettings().setJavaScriptEnabled(false);
		view.getSettings().setSupportZoom(false);
		view.getSettings().setBuiltInZoomControls(false);
		view.getSettings().setDefaultFontSize(12);
		view.loadDataWithBaseURL(null, getWebBody(str), "text/html", "utf-8",
				"");
		// content_webview.setcontent_webviewClient(UIHelper.getcontent_webviewClient());
		view.setBackgroundColor(0);
		// view.setBackgroundResource(R.color.webview_bg);
	}
}
