/**
 * @author wangqi
 *2013-7-26 下午9:11:01
 */
package ouc.sei.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * TODO 下午9:11:01
 */
public class Topic implements Serializable {

	/**
	 * 微博
	 */
	private static final long serialVersionUID = 1L;
	private String idstr;// 字符串形式的微博ID
	private String created_at;// 创建时间
	private long id;// 微博ID
	private String text;// 微博信息内容
	private String source;// 微博来源
//	private boolean favorited;// 是否已收藏
	private long in_reply_to_status_id;// 回复ID
	private long in_reply_to_user_id;// 回复人UID
	private String in_reply_to_screen_name;// 回复人昵称
	private String bMiddlePic;// 中等尺寸图片地址
	private String originalPic;
	private String thumbnail_pic = null;// 缩略图地址
	private int reposts_count;// 转发数
	private int comments_count;// 评论数
	private Comment reply_comment;
	/**
	 * @return the reply_comment
	 */
	public Comment getReply_comment() {
		return reply_comment;
	}

	/**
	 * @param reply_comment the reply_comment to set
	 */
	public void setReply_comment(Comment reply_comment) {
		this.reply_comment = reply_comment;
	}

	private Topic retweeted_status = null;
	private User user;// 微博用户

	public Topic getRetweeted_status() {
		return retweeted_status;
	}

	public void setRetweeted_status(Topic retweeted_status) {
		this.retweeted_status = retweeted_status;
	}

	public String getIdstr() {
		return idstr;
	}

	public void setIdstr(String idstr) {
		this.idstr = idstr;
	}

	public String getCreatedAt() {
		return created_at;
	}

	public String getCreatedAtDif(String str) throws ParseException {
		return ConvertTime(str);
	}

	public void setCreatedAt(String createdAt) {
		this.created_at = createdAt;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSource() {
		// Log.i("source", source.length() + ";" + source);

		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

//	public boolean isFavorited() {
//		return favorited;
//	}
//
//	public void setFavorited(boolean favorited) {
//		this.favorited = favorited;
//	}

	public long getInReplyToStatusId() {
		return in_reply_to_status_id;
	}

	public void setInReplyToStatusId(long inReplyToStatusId) {
		this.in_reply_to_status_id = inReplyToStatusId;
	}

	public long getInReplyToUserId() {
		return in_reply_to_user_id;
	}

	public void setInReplyToUserId(long inReplyToUserId) {
		this.in_reply_to_user_id = inReplyToUserId;
	}

	public String getInReplyToScreenName() {
		return in_reply_to_screen_name;
	}

	public void setInReplyToScreenName(String inReplyToScreenName) {
		this.in_reply_to_screen_name = inReplyToScreenName;
	}

	public String getThumbnailPic() {
		return thumbnail_pic;
	}

	public void setThumbnailPic(String thumbnailPic) {
		this.thumbnail_pic = thumbnailPic;
	}

	public long getRepostsCount() {
		return reposts_count;
	}

	public void setRepostsCount(int repostsCount) {
		this.reposts_count = repostsCount;
	}

	public long getCommentsCount() {
		return comments_count;
	}

	public void setCommentsCount(int commentsCount) {
		this.comments_count = commentsCount;
	}

	public Topic() {

	}

	/**
	 * 从json中分析出一条微博
	 * 
	 * @param jsonObject
	 * @throws JSONException
	 * @throws ParseException
	 */
	public void parseWeiboStatus(JSONObject jsonObject) throws JSONException,
			ParseException {

		this.idstr = jsonObject.getString("idstr");
		this.created_at = jsonObject.getString("created_at");
		this.id = jsonObject.getLong("id");
		this.text = jsonObject.getString("text");
		if(jsonObject.has("source")){
			this.source = jsonObject.getString("source");
			if (this.source.contains("</a>")) {
				this.source = "来自于："
						+ this.source.substring(this.source.indexOf('>') + 1,
								this.source.indexOf("</a>"));
			}
//			this.favorited = jsonObject.getBoolean("favorited");
			this.in_reply_to_screen_name = jsonObject
					.getString("in_reply_to_screen_name");
			/*
			 * if (jsonObject.has("bmiddle_pic")) { this.bMiddlePic =
			 * jsonObject.getString("bmiddle_pic"); } if
			 * (jsonObject.has("original_pic")) { this.originalPic =
			 * jsonObject.getString("bmiddle_pic"); }
			 */
			if (jsonObject.has("thumbnail_pic")) {
				this.thumbnail_pic = jsonObject.getString("thumbnail_pic");
			}
			this.reposts_count = jsonObject.getInt("reposts_count");
			this.comments_count = jsonObject.getInt("comments_count");
			if (jsonObject.has("retweeted_status")) {
				this.retweeted_status = new Topic();
				retweeted_status.parseRetweetedStatus(jsonObject
						.getJSONObject("retweeted_status"));
			}
			if (jsonObject.has("reply_comment")) {
				this.reply_comment = new Comment();
				reply_comment.parseComments(jsonObject.getJSONObject("reply_comment"));
			}
			this.user = new User();
			user.parseWeiboUser(jsonObject.getJSONObject("user"));
		}
		
		
	}

	/**
	 * 从json中分析出转发的信息
	 * 
	 * @param jsonObject
	 * @throws JSONException
	 * @throws ParseException
	 */
	public void parseRetweetedStatus(JSONObject jsonObject)
			throws JSONException, ParseException {

		this.created_at = getCreatedAtDif(jsonObject.getString("created_at"));
		this.id = jsonObject.getLong("id");
		this.text = jsonObject.getString("text");
		if( jsonObject.getString("source")==null){
			return;
		}
		else{
		this.source = jsonObject.getString("source");
		if (this.source.contains("</a>")) {
			this.source = "来自于："
					+ this.source.substring(this.source.indexOf('>') + 1,
							this.source.indexOf("</a>"));
		}
//		this.favorited = jsonObject.getBoolean("favorited");
		this.in_reply_to_screen_name = jsonObject
				.getString("in_reply_to_screen_name");
		if (jsonObject.has("thumbnail_pic")) {
			this.thumbnail_pic = jsonObject.getString("thumbnail_pic");
		}
		this.user = new User();
		user.parseWeiboUser(jsonObject.getJSONObject("user"));
		}
	}

	/**
	 * 从json中分析出一条微博
	 * 
	 * @param jsonObject
	 * @throws JSONException
	 * @throws ParseException
	 */
	public void parseWeiboStatusWithoutUser(JSONObject jsonObject)
			throws JSONException, ParseException {
		this.idstr = jsonObject.getString("idstr");
		this.created_at = getCreatedAtDif(jsonObject.getString("created_at"));
		this.id = jsonObject.getLong("id");
		this.text = jsonObject.getString("text");
		this.source = jsonObject.getString("source");
		if (this.source.contains("</a>")) {
			this.source = "来自于："
					+ this.source.substring(this.source.indexOf('>') + 1,
							this.source.indexOf("</a>"));
		}
//		this.favorited = jsonObject.getBoolean("favorited");
		this.in_reply_to_screen_name = jsonObject
				.getString("in_reply_to_screen_name");
		/*
		 * if (jsonObject.has("bmiddle_pic")) { this.bMiddlePic =
		 * jsonObject.getString("bmiddle_pic"); } if
		 * (jsonObject.has("original_pic")) { this.originalPic =
		 * jsonObject.getString("bmiddle_pic"); }
		 */
		if (jsonObject.has("thumbnail_pic")) {
			this.thumbnail_pic = jsonObject.getString("thumbnail_pic");
		}
		this.reposts_count = jsonObject.getInt("reposts_count");
		this.comments_count = jsonObject.getInt("comments_count");
	}

	/**
	 * toString 方法 测试
	 */
	public String toString() {
		String str = "微博idstr：" + idstr + "\n" + "创建时间：" + created_at + "\n"
				+ "微博ID：" + id + "\n" + "内容：" + text + "\n" + "微博来源：" + source
				+ "\n"  + "\n" + "回复ID："
				+ in_reply_to_status_id + "\n" + "回复人UID："
				+ in_reply_to_user_id + "\n" + "回复人昵称："
				+ in_reply_to_screen_name + "\n" + "\n" + "\n" + "转发数："
				+ reposts_count + "\n" + "评论数：" + comments_count + "\n"
				+ bMiddlePic + originalPic;
		return str;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private String ConvertTime(String time) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(
				"EEE MMM dd HH:mm:ss Z yyyy", Locale.US);
		Date date = sdf.parse(time);
		Date now = new Date();
		long rlt = now.getTime() - date.getTime();
		long days = rlt / (1000 * 60 * 60 * 24);
		long hours = rlt % (1000 * 60 * 60 * 24) / (1000 * 60 * 60);
		long minutes = (rlt % (1000 * 60 * 60)) / (1000 * 60);
		long seconds = (rlt % (1000 * 60)) / 1000;
		if (days > 0) {
			return days + "天前";
		} else if (hours > 0) {
			return hours + "小时前";
		} else if (minutes > 0) {
			return minutes + "分钟前";
		} else {
			return seconds + "秒前";
		}
	}

	/**
	 * @return the bMiddlePic
	 */
	public String getbMiddlePic() {
		return thumbnail_pic.replace("thumbnail", "bmiddle");
	}

	/**
	 * @param bMiddlePic
	 *            the bMiddlePic to set
	 */
	public void setbMiddlePic(String bMiddlePic) {
		this.bMiddlePic = bMiddlePic;
	}

	/**
	 * @return the originalPic
	 */
	public String getOriginalPic() {
		return thumbnail_pic.replace("thumbnail", "large");

	}

	/**
	 * @param originalPic
	 *            the originalPic to set
	 */
	public void setOriginalPic(String originalPic) {
		this.originalPic = originalPic;
	}

	/**
	 * 
	 * 作用： 处理时间，以便于显示
	 * 
	 * @param time
	 * @return
	 */
	/*
	 * private String parseTime(String time) { String[] strArr =
	 * time.split(" "); int month = 11; if (strArr[1].equals("Jan")) { month =
	 * 0; } else if (strArr[1].equals("Feb")) { month = 1; } else if
	 * (strArr[1].equals("Mar")) { month = 2; } else if
	 * (strArr[1].equals("Apr")) { month = 3; } else if
	 * (strArr[1].equals("May")) { month = 4; } else if
	 * (strArr[1].equals("Jun")) { month = 5; } else if
	 * (strArr[1].equals("Jul")) { month = 6; } else if
	 * (strArr[1].equals("Aug")) { month = 7; } else if
	 * (strArr[1].equals("Sept")) { month = 8; } else if
	 * (strArr[1].equals("Oct")) { month = 9; } else if
	 * (strArr[1].equals("Nov")) { month = 10; }
	 * 
	 * int year = Integer.parseInt(strArr[5]); int day =
	 * Integer.parseInt(strArr[2]); int hour =
	 * Integer.parseInt(strArr[3].substring(0, 2)); int minute =
	 * Integer.parseInt(strArr[3].substring(3, 5)); int second =
	 * Integer.parseInt(strArr[3].substring(6, 8)); Date date = new
	 * GregorianCalendar(year, month, day, hour, minute, second).getTime(); Date
	 * currentDate = Calendar.getInstance().getTime(); long timePlus =
	 * currentDate.getTime() - date.getTime(); int minutes = Math.abs((int)
	 * (timePlus / 1000)); if (minutes < 60) { return "发布于" + minutes + "秒前"; }
	 * else if (minutes < 60 * 60) { return "发布于" + (currentDate.getMinutes() -
	 * minute) + "分钟前"; } else if (minutes < 24 * 60 * 60) { return "发布于" +
	 * (currentDate.getHours() - hour) + "小时前"; } else if (minutes < 30 * 24 *
	 * 60 * 60) { return "发布于" + (currentDate.getDay() - day) + "天前"; } else if
	 * (minutes < 12 * 30 * 24 * 60 * 60) { return "发布于" +
	 * (currentDate.getMonth() - month) + "月前"; } else { return "发布于" +
	 * (currentDate.getYear() - year) + "年前"; } }
	 */
}
