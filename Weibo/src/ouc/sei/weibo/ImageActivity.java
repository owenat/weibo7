/**
 * @author wangqi
 *2013-7-29 上午9:24:39
 */
package ouc.sei.weibo;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;

import ouc.sei.app.WeiboApp;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

/**
 * TODO
 * 上午9:24:39
 */
public class ImageActivity extends Activity{
	  private ImageView mImage;
	  private ViewSwitcher mViewSwitcher;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_dialog);
		 WeiboApp.addActivity(this);
		    String str = getIntent().getExtras().getString("imageurl");
		    this.mImage = ((ImageView)findViewById(R.id.imagedialog_image));
		    this.mViewSwitcher = ((ViewSwitcher)findViewById(R.id.imagedialog_view_switcher));
		    ((AQuery)new AQuery(this).id(this.mImage)).image(str, true, true, 0, 0, new BitmapAjaxCallback()
		    {
		      protected void callback(String paramString, ImageView paramImageView, Bitmap paramBitmap, AjaxStatus paramAjaxStatus)
		      {
		        super.callback(paramString, paramImageView, paramBitmap, paramAjaxStatus);
		        paramImageView.setImageBitmap(paramBitmap);
		        ImageActivity.this.mViewSwitcher.showNext();
		      }
		    });
		this.mImage.setOnClickListener(new View.OnClickListener()
		    {
		      public void onClick(View paramView)
		      {
				ImageActivity.this.finish();
		      }
		    });
		  }
	}
         

