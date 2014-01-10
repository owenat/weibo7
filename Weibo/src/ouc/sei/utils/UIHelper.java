/**
 * @author wangqi
 *2013-7-29 下午5:13:36
 */
package ouc.sei.utils;

import ouc.sei.weibo.UserInfoActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * TODO
 * 下午5:13:36
 */
public class UIHelper{
	  public static final String WEB_STYLE = "<style>* {font-size:16px;line-height:20px;} p {color:#333;} a {color:#3E62A6;} img {max-width:310px;} img.alignleft {float:left;max-width:120px;margin:0 10px 5px 0;border:1px solid #ccc;background:#fff;padding:2px;} pre {font-size:9pt;line-height:12pt;font-family:Courier New,Arial;border:1px solid #ddd;border-left:5px solid #6CE26C;background:#f6f6f6;padding:5px;} a.tag {font-size:15px;text-decoration:none;background-color:#bbd6f3;border-bottom:2px solid #3E6D8E;border-right:2px solid #7F9FB6;color:#284a7b;margin:2px 2px 2px 0;padding:2px 4px;white-space:nowrap;}</style>";

	  public static WebViewClient getWebViewClient()
	  {
	    return new WebViewClient()
	    {
	      public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
	      {
//        UIHelper.showUrlRedirect(paramWebView.getContext(), paramString);
	        return true;
	      }
	    };
	  }

	  public static void openBrowser(Context paramContext, String paramString)
	  {
	    try
	    {
	      paramContext.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(paramString)));
	      return;
	    }
	    catch (Exception localException)
	    {
	      while (true)
	        localException.printStackTrace();
	    }
	  }

	  public static void showLinkRedirect(Context paramContext, int paramInt1, int paramInt2, String paramString)
	  {
	    switch (paramInt1)
	    {
	    case 0:
	      openBrowser(paramContext, paramString);
	      break;
	    case 1:
	      showUserCenter(paramContext, paramInt2, paramString);
	      break;
	    }
	  }


/*	  public static void showUrlRedirect(Context paramContext, String paramString)
	  {
//	    URLs localURLs = URLs.parseURL(paramString);
	    Logger.d("**" + paramString + "****");
	    if (localURLs == null)
	      openBrowser(paramContext, paramString);
	    else
	      showLinkRedirect(paramContext, localURLs.getObjType(), localURLs.getObjId(), localURLs.getObjKey());
	  }*/

	  public static void showUserCenter(Context paramContext, int paramInt, String paramString)
	  {
	    Intent localIntent = new Intent(paramContext, UserInfoActivity.class);
	    localIntent.putExtra("his_id", paramInt);
	    localIntent.putExtra("his_name", paramString);
	    paramContext.startActivity(localIntent);
	  }
	}
