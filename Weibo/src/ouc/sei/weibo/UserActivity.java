/**
 * @author wangqi
 *2013-8-1 下午3:32:20
 */
package ouc.sei.weibo;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ouc.sei.app.WeiboApp;
import ouc.sei.entity.User;
import ouc.sei.utils.Logger;
import ouc.sei.utils.TaskFeedback;
import ouc.sei.widgets.MyListView;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;

/**
 * TODO
 * 下午3:32:20
 */
public abstract class UserActivity extends Activity {
	private int visibleLastIndex = 0; // 最后的可视项索引
	private int visibleItemCount;
	private View footerView;
	private ProgressBar moreFooterProgressBar;
	public ArrayList<User> data=new ArrayList<User>();
	private BaseAdapter adapter;
	MyListView listView;
	String url ;
	User user;
	int count = 10;
	protected abstract void _onCreate();
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
					new AQuery(UserActivity.this).ajax(url,JSONObject.class,
							new AjaxCallback<JSONObject>() {
								@Override
								public void callback(String url,
										JSONObject object, AjaxStatus status) {
									// TODO Auto-generated method stub
									super.callback(url, object, status);
									Logger.v("start connecting");
									if (status.getCode() == 200) {
										try {
											JSONArray jsonArray = (JSONArray) object
													.get("users");
											if (count < jsonArray.length()) {
												for (int i = count; i < count + 5; ++i) {
													JSONObject o = (JSONObject) jsonArray
															.get(i);
													user = new User();
													user.parseWeiboUser(o);
													data.add(user);
												}
												count += 5;
											}
											else{
												TaskFeedback.getInstance(UserActivity.this).failed("没有微博了");
											}
										} catch (JSONException e) {
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
												UserActivity.this).failed(
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
				UserActivity.this.visibleItemCount = visibleItemCount;
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
		new AQuery(UserActivity.this).ajax(url,JSONObject.class,
				new AjaxCallback<JSONObject>() {
					@Override
					public void callback(String url, JSONObject object,
							AjaxStatus status) {
						// TODO Auto-generated method stub
						super.callback(url, object, status);
						Logger.v("start connecting");
						if (status.getCode() == 200) {
							try {
								JSONArray jsonArray = (JSONArray) object
										.get("users");
								for (int i = 0; i < 10; ++i) {
									JSONObject o = (JSONObject) jsonArray
											.get(i);
									user = new User();
									user.parseWeiboUser(o);
									data.add(user);
//									System.out.println(user.toString());
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								Logger.v("JSONException");
								e.printStackTrace();
							} finally {
								Logger.v("onRefreshComplete");
								// adapter=(BaseAdapter) setAdapter();
								adapter.notifyDataSetChanged();
								listView.onRefreshComplete();
							}
						} else {
							TaskFeedback.getInstance(UserActivity.this)
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