/**
 * @author wangqi
 *2013-8-1 下午3:04:13
 */
package ouc.sei.weibo;

import java.text.ParseException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

import ouc.sei.adapter.TopicListAdapter;
import ouc.sei.app.WeiboApp;
import ouc.sei.app.WeiboParameters;
import ouc.sei.entity.Topic;
import ouc.sei.utils.TaskFeedback;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * TODO 下午3:04:13
 */
public class MyFavActivity extends Activity {
	private ImageView refresh_btn;
	private ImageView back_btn;
	private TextView title_text;
	private ListView res_list;
	private ArrayList<Topic> topics = new ArrayList<Topic>();
	private TopicListAdapter localCommentAdapter;
	private String url;
    private int temp=0;
    private AQuery localAQuery;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.comments_list);
		WeiboApp.addActivity(this);
		this.url = WeiboParameters.FAVWEIBO_URL;
		init();
		// setData();
		 getComments();
		localCommentAdapter= new TopicListAdapter(topics, MyFavActivity.this);
		res_list.setAdapter(localCommentAdapter);
	}

	public void init() {
		res_list = (ListView) findViewById(R.id.comments_list);
		refresh_btn = ((ImageView) findViewById(R.id.refresh_btn));
		refresh_btn.setVisibility(View.VISIBLE);
		back_btn = ((ImageView) findViewById(R.id.back_btn));
		back_btn.setVisibility(View.VISIBLE);
		title_text = ((TextView) findViewById(R.id.head_title));
		title_text.setText(R.string.favorite);
	}

    public void back(){
    	finish();
    }
	private void getComments() {
		// TODO Auto-generated method stub
		localAQuery = new AQuery(this);
		localAQuery.ajax(url, JSONObject.class, new AjaxCallback<JSONObject>() {
			@Override
			public void callback(String url, JSONObject object,
					AjaxStatus status) {
				// TODO Auto-generated method stub
				super.callback(url, object, status);
				if (status.getCode() == 200) {
					try {
						JSONArray array = (JSONArray) object.get("favorites");
						for (int i = temp; i < array.length(); i++) {
							JSONObject j = (JSONObject) array.get(i);
							JSONObject t= (JSONObject) j.get("status");
							Topic topic = new Topic();
							topic.parseWeiboStatus(t);
							topics.add(topic);
						}
						temp=array.length();
						localCommentAdapter.notifyDataSetChanged();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
					}

				}else {
					TaskFeedback.getInstance(MyFavActivity.this).failed("网络连接错误");
				}
			}

		});
	}

	public void refresh(View v) {

	}
}