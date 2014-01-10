package ouc.sei.weibo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import ouc.sei.adapter.CommentMyAdapter;
import ouc.sei.app.WeiboApp;
import ouc.sei.app.WeiboParameters;
import ouc.sei.entity.Comment;
import ouc.sei.utils.Logger;
import ouc.sei.utils.TaskFeedback;
import ouc.sei.widgets.LoadingDialog;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 评论我的微博 TODO wangqi 11:35:05
 */
public class MessageActivity extends Activity {
	private ImageView refresh_btn;
	private ImageView write_btn;
	private TextView title_text;
	private ListView res_list;
	private List<Comment> comments = new ArrayList<Comment>();
	private CommentMyAdapter localCommentAdapter;
	private String url;
    private int temp=0;
    private AQuery localAQuery;
    private LoadingDialog ld;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.comments_list);
		WeiboApp.addActivity(this);
		this.url = WeiboParameters.MESSAGE_URL;
		init();
		// setData();
		 getComments();
		localCommentAdapter = new CommentMyAdapter(comments, MessageActivity.this);
		res_list.setAdapter(localCommentAdapter);
	}

	public void init() {
		ld=new LoadingDialog(this);
		res_list = (ListView) findViewById(R.id.comments_list);
		res_list.setOnItemClickListener(itemClickListener);
		refresh_btn = ((ImageView) findViewById(R.id.refresh_btn));
		refresh_btn.setVisibility(View.VISIBLE);
		write_btn = ((ImageView) findViewById(R.id.write_btn));
		write_btn.setVisibility(View.VISIBLE);
		title_text = ((TextView) findViewById(R.id.head_title));
		title_text.setText(R.string.mycomment);
	}

	public void write(View v) {
		Intent intent = new Intent();
		intent.setClass(MessageActivity.this, PubActivity.class);
		Bundle localBundle = new Bundle();
		localBundle.putInt("flag", 0);//create 发布一条微博
		startActivity(intent);
		overridePendingTransition(R.anim.slide_out_down,
				R.anim.gd_grow_from_bottomleft_to_topright);
	}
	private void getComments() {
		// TODO Auto-generated method stub
		localAQuery = new AQuery(this);
		ld.show();
		localAQuery.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject object,
					AjaxStatus status) {
				// TODO Auto-generated method stub
				super.callback(url, object, status);
				if (status.getCode() == 200) {
					try {
						JSONArray array = (JSONArray) object.get("comments");
						for (int i = temp; i < array.length(); i++) {
							JSONObject j = (JSONObject) array.get(i);
							Comment comment = new Comment();
							comment.parseWeiboStatus(j);
							comments.add(comment);
						}
						temp=array.length();
						System.out.println("-------------------------------------------"+comments.size());
						localCommentAdapter.notifyDataSetChanged();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						ld.dismiss();
					}

				}else {
					TaskFeedback.getInstance(MessageActivity.this).failed("网络连接错误");
					ld.dismiss();
				}
			}

		});
	}

	public void refresh(View v) {
		 getComments();
	}

	private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
		public void onItemClick(AdapterView<?> paramAdapterView,
				View paramView, int paramInt, long paramLong) {
			Logger.v("++++++++++++++++++++++++++++++++++" + paramInt);
			Comment comment =(Comment) paramAdapterView.getAdapter().getItem(paramInt);
			Intent localIntent = new Intent(MessageActivity.this, PubActivity.class);
		    Bundle localBundle = new Bundle();
		    localBundle.putSerializable("comment",comment );
		    localIntent.putExtras(localBundle);
		    startActivity(localIntent);
		    overridePendingTransition(R.anim.gd_grow_from_bottomleft_to_topright, R.anim.slide_out_down);

		}
	};
}