/**
 * @author wangqi
 *2013-7-26 下午8:31:12
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

import ouc.sei.app.WeiboApp;
import ouc.sei.app.WeiboParameters;
import ouc.sei.entity.Topic;
import ouc.sei.utils.Logger;
import ouc.sei.utils.TaskFeedback;
import ouc.sei.widgets.MyListView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.AbsListView.OnScrollListener;

/**
 * TODO 下午8:31:12
 * @param <T>
 */
public abstract class TopicCommonActivity<T> extends Activity {
	private int visibleLastIndex = 0; // 最后的可视项索引
	private int visibleItemCount;
	private View footerView;
	private ProgressBar moreFooterProgressBar;
	public ArrayList<T> data=new ArrayList<T>();
	private BaseAdapter adapter;
	MyListView listView;
	String url ;
	Topic topic;
	int count = 10;

	protected abstract void _onCreate();

	protected abstract void addParams(WeiboParameters paramWeiboParameters);
    
	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setLayout();
		WeiboApp.addActivity(this);
		footerView = (View) View.inflate(this, R.layout.list_footer, null);
		moreFooterProgressBar = (ProgressBar) footerView
				.findViewById(R.id.moreFooterProgressBar);
		listView = (MyListView) findViewById(R.id.weibolist);
		_onCreate();
		refresh_List();
		adapter = (BaseAdapter) setAdapter();
		listView.addFooterView(footerView);
		this.listView.clickRefresh();
		listView.setOnScrollListener(new OnScrollListener() {
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				int itemsLastIndex = adapter.getCount() - 1; // 数据集最后一项的索引
				int lastIndex = itemsLastIndex + 2; // 加上底部的loadMoreView项
				if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
						&& visibleLastIndex == lastIndex) {
					// 如果是自动加载,可以在这里放置异步加载数据的代码
					moreFooterProgressBar.setVisibility(View.VISIBLE);
					new AQuery(TopicCommonActivity.this).ajax(url, JSONObject.class,
							new AjaxCallback<JSONObject>() {
								@SuppressWarnings("unchecked")
								@Override
								public void callback(String url,
										JSONObject object, AjaxStatus status) {
									// TODO Auto-generated method stub
									super.callback(url, object, status);
									Logger.v("start connecting");
									if (status.getCode() == 200) {
										try {
											Logger.v("count=" + count);
											JSONArray jsonArray = (JSONArray) object
													.get("statuses");
											if (count < jsonArray.length()) {
												for (int i = count; i < count + 5; ++i) {
													Logger.v("array="+jsonArray.length());
													JSONObject o = (JSONObject) jsonArray
															.get(i);
													topic = new Topic();
													topic.parseWeiboStatus(o);
													data.add( (T) topic);
													System.out.println(topic
															.toString());
												}
												count += 5;
											}
											else{
												TaskFeedback.getInstance(TopicCommonActivity.this).failed("没有微博了");
											}
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
											
										} catch (ParseException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										} finally {
											Logger.v("onRefreshComplete");
											adapter.notifyDataSetChanged();
											listView.onRefreshComplete();
											listView.setSelection(visibleLastIndex
													- visibleItemCount + 1); // 设置选中项
											moreFooterProgressBar
													.setVisibility(View.INVISIBLE);
										}
									} else {
										TaskFeedback.getInstance(
												TopicCommonActivity.this).failed(
												"网络连接错误！");
										adapter.notifyDataSetChanged();
										listView.onRefreshComplete();
										listView.setSelection(visibleLastIndex
												- visibleItemCount + 1); // 设置选中项
										moreFooterProgressBar
												.setVisibility(View.INVISIBLE);
									}
								}
							});

				}

			}

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				listView.firstItemIndex = firstVisibleItem;
				TopicCommonActivity.this.visibleItemCount = visibleItemCount;
				visibleLastIndex = firstVisibleItem + visibleItemCount - 1;

			}
		});

		listView.setonRefreshListener(new MyListView.OnRefreshListener() {
			public void onRefresh() {
				refresh_List();
			}
		});

	}

	public void refresh_List() {
		new AQuery(TopicCommonActivity.this).ajax(url, JSONObject.class,
				new AjaxCallback<JSONObject>() {
					@SuppressWarnings("unchecked")
					@Override
					public void callback(String url, JSONObject object,
							AjaxStatus status) {
						// TODO Auto-generated method stub
						super.callback(url, object, status);
						Logger.v("start connecting");
						if (status.getCode() == 200) {
							try {
								JSONArray jsonArray = (JSONArray) object
										.get("statuses");
								for (int i = 0; i < 10; ++i) {
									JSONObject o = (JSONObject) jsonArray
											.get(i);
									topic = new Topic();
									topic.parseWeiboStatus(o);
									data.add( (T) topic);
									System.out.println(topic.toString());
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} finally {
								Logger.v("onRefreshComplete");
								// adapter=(BaseAdapter) setAdapter();
								adapter.notifyDataSetChanged();
								listView.onRefreshComplete();
							}
						} else {
							TaskFeedback.getInstance(TopicCommonActivity.this)
									.failed("refresh_List_网络连接错误！");
							adapter.notifyDataSetChanged();
							listView.onRefreshComplete();
						}
					}
				});
	}

	protected abstract Adapter setAdapter();

	protected abstract void setLayout();

}
