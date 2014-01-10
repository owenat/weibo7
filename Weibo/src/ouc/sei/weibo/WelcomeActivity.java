package ouc.sei.weibo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
/**
 *
 */
public class WelcomeActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.welcome);
        AlphaAnimation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(2000);
        
        ImageView img_logo = (ImageView)findViewById(R.id.welcome_iv);
        img_logo.setAnimation(animation);
        
        animation.setAnimationListener(new AnimationListener(){

			public void onAnimationEnd(Animation animation)
			{

				 Intent intent = new Intent(WelcomeActivity.this, TabHostActivity.class);
			     startActivity(intent);
			     WelcomeActivity.this.finish();
			}

			public void onAnimationRepeat(Animation animation)
			{
				// TODO Auto-generated method stub
				
			}

			public void onAnimationStart(Animation animation)
			{
				// TODO Auto-generated method stub
				
			}});
    }
}