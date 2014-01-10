/**
 * @author wangqi
 *2013-7-25 ����9:08:24
 */
package ouc.sei.app;

import java.util.HashMap;
import java.util.Map;


/**
 * TODO ����9:08:24
 */
public class WeiboParameters {

	public static final String APP_KEY = "2873299902";;
	public static final String APP_SECRET = "e0aa731f8f0d22683ff85c4533b43466";
	public static final String REDIRECT_URL = "http://weibo.com/wang7qi";//微博上填的重定向地址
	public static final Long USER_ID = 1662669180L;
	public static long WEIBO_ID;
	public static final String RESPONSE_TYPE = "response_type";
	public static final String USER_REDIRECT_URL = "redirect_uri";
	public static final String DISPLAY = "display";
	public static final String USER_SCOPE = "scope";
	public static final String PACKAGE_NAME = "packagename";
	public static final String KEY_HASH = "key_hash";
	public static final String TOKEN = "2.00qM5WoBoYE9ID80495863700XfjWp";
	public static final String USERNAME = "username";
	/*post方式请求*/
	public static final String REPLY_URL="https://api.weibo.com/2/comments/reply.json?";//回复一条评论
	public static final String UPDATE_URL="https://api.weibo.com/2/statuses/update.json?";//发送微博
	public static final String REPOST_URL="https://api.weibo.com/2/statuses/repost.json?";//转发微博
	public static final String CREATE_URL="https://api.weibo.com/2/comments/create.json?";//评论一条微博
	/*get方式请求*/
	public static final String Fan_URL="https://api.weibo.com/2/friendships/friends.json?access_token="+TOKEN+"&uid=";//关注列表
	public static final String Follow_URL="https://api.weibo.com/2/friendships/followers.json?access_token="+TOKEN+"&uid=";//粉丝列表
	public static final String FAVWEIBO_URL="https://api.weibo.com/2/favorites.json?access_token="+TOKEN;//获取用户发布的所有微博
	public static final String ALLWEIBO_URL="https://api.weibo.com/2/statuses/user_timeline.json?access_token="+TOKEN;//获取用户发布的所有微博
	public static final String MENTION_URL="https://api.weibo.com/2/statuses/mentions.json?access_token="+TOKEN;//@我的API地址
	public static final String MESSAGE_URL="https://api.weibo.com/2/comments/to_me.json?access_token="+TOKEN;//回复我的地址
	public static final String USER_URL="https://api.weibo.com/2/users/show.json?uid="+USER_ID+"&access_token="+TOKEN;//用户信息
	public static final String HOME_URL = "https://api.weibo.com/2/statuses/friends_timeline.json?access_token="+TOKEN;//首页微博信息
	public static final String COMMENTS_URL = "https://api.weibo.com/2/comments/show.json?access_token="+TOKEN+"&id=";//首页微博回复列表
	public static Map<String, Object> params=new HashMap<String, Object>();

}
