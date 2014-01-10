package ouc.sei.weibo;

import ouc.sei.app.WeiboApp;
import ouc.sei.utils.TaskFeedback;
import android.app.AlertDialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

public class TabHostActivity extends TabActivity implements
		OnCheckedChangeListener {
	private RadioGroup mainTab;
	private TabHost mTabHost;
	private Intent HomeIntent;
	private Intent InfoIntent;
	private Intent MeIntent;
	private Intent MessageIntent;

	private final static String TAB_TAG_HOME = "tab_tag_home";
	private final static String TAB_TAG_INFO = "tab_tag_info";
	private final static String TAB_TAG_ME = "tab_tag_me";
	private final static String TAB_TAG_MESSEGE = "tab_tag_message";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tabhost_view);
		WeiboApp.addActivity(this);
		mainTab = (RadioGroup) findViewById(R.id.main_tab);
		mainTab.setOnCheckedChangeListener(this);
		prepareIntent();
		setupIntent();

	}
	private void prepareIntent() {
		HomeIntent = new Intent(this, HomeActivity.class);
		InfoIntent = new Intent(this, MessageActivity.class);
		MeIntent = new Intent(this, UserInfoActivity.class);
		MessageIntent = new Intent(this, AtMeActivity.class);
		Bundle localBundle = new Bundle();
		localBundle.putSerializable("user", null);
		MeIntent.putExtras(localBundle);

	}
	private void setupIntent() {
		this.mTabHost = getTabHost();
		TabHost localTabHost = this.mTabHost;
		localTabHost.addTab(buildTabSpec(TAB_TAG_HOME, R.string.mtab_home,
				HomeIntent));
		localTabHost.addTab(buildTabSpec(TAB_TAG_INFO,
				R.string.mtab_info,  InfoIntent));
		localTabHost.addTab(buildTabSpec(TAB_TAG_ME, R.string.mtab_me,
				 MeIntent));
		localTabHost.addTab(buildTabSpec(TAB_TAG_MESSEGE, R.string.mtab_message,
				 MessageIntent));

	}
	private TabHost.TabSpec buildTabSpec(String tag, int resLabel, 
			final Intent intent) {
		return this.mTabHost
				.newTabSpec(tag)
				.setIndicator(getString(resLabel)
						)
				.setContent(intent);
	}
	public void confirmExit(){
		AlertDialog.Builder dialog=new AlertDialog.Builder(this);
		dialog.setMessage("确认退出微博？");
		dialog.setPositiveButton("是的", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				WeiboApp app =new WeiboApp();
				app.onTerminate();
			}
		});
		dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		dialog.show();
	}

	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.radio_button0:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_HOME);
			break;
		case R.id.radio_button1:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_INFO);
			break;
		case R.id.radio_button2:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_ME);
			break;
		case R.id.radio_button3:
			this.mTabHost.setCurrentTabByTag(TAB_TAG_MESSEGE);
			break;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);	
		return true;
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId())
        {

        case R.id.menu_exit:
        	confirmExit();
            break;
        case R.id.menu_update:
        	Handler handler=new Handler();
        	TaskFeedback.getInstance(TabHostActivity.this).start("查看更新中。。");
        	handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					TaskFeedback.getInstance(TabHostActivity.this).success("已经是最新版本!");
				}
			}, 2000);
            break;
        }
        return true;
    }

}
