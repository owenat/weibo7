/**
 * @author wangqi
 *2013-7-23 ����10:43:22
 */
package ouc.sei.weibo;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import ouc.sei.app.WeiboApp;
import ouc.sei.app.WeiboParameters;
import ouc.sei.entity.Comment;
import ouc.sei.entity.Topic;
import ouc.sei.utils.TaskFeedback;
import ouc.sei.widgets.LoadingDialog;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

/**
 * TODO wangqi 10:43:22
 */
public class PubActivity extends Activity {
	private static final String TEXT_NAME = "@请输入用户昵称 ";
	private static final String TEXT_TAG = "#请输入话题#";
	private ImageView back_btn;
	private ImageView send_btn;
	private TextView title_text;
	private EditText pub_content;
	private String str;
	private String url;
	private LoadingDialog load;
	private Map<String, String> params;
	private Topic topic;
	private Comment comment;
	private SharedPreferences weiboPref;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tweet_pub);
		WeiboApp.addActivity(this);
//		weiboPref =PreferenceManager.getDefaultSharedPreferences(this) ;
		weiboPref = WeiboApp.mPref;
//		weiboPref = getSharedPreferences("test",Context.MODE_PRIVATE );
		init();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		str = weiboPref.getString("weibo", null);
		System.out.println("++++" + "onResume=" + str);
		pub_content.setText(str);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		str = pub_content.getText().toString();
	    Editor editor = weiboPref.edit();
	    editor.putString("weibo", str);
	    System.out.println("++++" + "onPause=" + str);
	    editor.commit();
	
	}

	public void init() {
		pub_content = (EditText) findViewById(R.id.tweet_pub_content);
		back_btn = ((ImageView) findViewById(R.id.back_btn));
		back_btn.setVisibility(View.VISIBLE);
		send_btn = ((ImageView) findViewById(R.id.send_btn));
		send_btn.setVisibility(View.VISIBLE);
		title_text = ((TextView) findViewById(R.id.head_title));
		title_text.setText(R.string.pub);
		load = new LoadingDialog(this);
	}

	public void sendTopic(String str, String url, Map<String, String> params) {
		url += str;
		load.setLoadText("微博发送中。。");
		load.show();
		new AQuery(this).ajax(url, params, JSONObject.class,
				new AjaxCallback<JSONObject>() {
					@Override
					public void callback(String url, JSONObject object,
							AjaxStatus status) {
						// TODO Auto-generated method stub
						super.callback(url, object, status);
						if (status.getCode() == 200)
							TaskFeedback.getInstance(PubActivity.this).success(
									"发送成功！");
						else
							TaskFeedback.getInstance(PubActivity.this).failed(
									"发送失败！");
						load.dismiss();
					}

				});
	}

	public void send(View v) {
		str = pub_content.getText().toString();
		int flag = this.getIntent().getExtras().getInt("flag");
		if ((str != null) && (str.trim().length() > 0)) {
			switch (flag) {
			case 0:
				url = WeiboParameters.UPDATE_URL;
				params = new HashMap<String, String>();
				params.put("access_token", WeiboParameters.TOKEN);
				params.put("status", str);
				sendTopic(str, url, params);// 发送微博
				break;
			case 1:
				url = WeiboParameters.CREATE_URL;
				topic = (Topic) this.getIntent().getExtras()
						.getSerializable("Topic");
				params = new HashMap<String, String>();
				params.put("access_token", WeiboParameters.TOKEN);
				params.put("id", String.valueOf(topic.getId()));
				params.put("comment", str);
				sendTopic(str, url, params);// 评论微博
				break;
			case 2:
				url = WeiboParameters.REPOST_URL;
				topic = (Topic) this.getIntent().getExtras()
						.getSerializable("Topic");
				params = new HashMap<String, String>();
				params.put("access_token", WeiboParameters.TOKEN);
				params.put("id", String.valueOf(topic.getId()));
				params.put("status", str);
				sendTopic(str, url, params);// 转发微博
				break;
			default:
				url = WeiboParameters.REPLY_URL;
				topic = (Topic) this.getIntent().getExtras()
						.getSerializable("Topic");
				comment = (Comment) this.getIntent().getExtras()
						.getSerializable("comment");
				params = new HashMap<String, String>();
				params.put("access_token", WeiboParameters.TOKEN);
				params.put("id", String.valueOf(topic.getId()));
				params.put("cid", comment.getIdstr());
				params.put("comment", str);
				sendTopic(str, url, params);// 回复评论
				break;
			}

		} else
			TaskFeedback.getInstance(PubActivity.this).success("请输入微博内容！");
	}

	public void back(View v) {
		finish();
	}

	public void at(View v) {
		insertStr(TEXT_NAME, 1, 1);
	}

	public void trend(View v) {
		insertStr(TEXT_TAG, 1, 2);
	}

	public void emotion(View v) {

	}

	public void camera(View v) {

	}

	private void insertStr(String paramString, int paramInt1, int paramInt2) {
		int i = paramInt1 + this.pub_content.getSelectionStart();
		int j = i + paramString.length() - paramInt2;
		this.pub_content.getText().insert(this.pub_content.getSelectionStart(),
				paramString);
		this.pub_content.setSelection(i, j);
	}
}
