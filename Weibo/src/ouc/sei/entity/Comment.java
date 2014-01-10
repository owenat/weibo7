/**
 * @author wangqi
 *2013-7-30 上午11:09:20
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
 * TODO 上午11:09:20
 */
public class Comment implements Serializable {
	/**
	 * 评论信息
	 */
	private static final long serialVersionUID = 1L;
	private String idstr;// 字符串形式的微博ID
	private String created_at;// 创建时间
	private long id;// 微博ID
	private String text;// 评论信息内容
	private String source;// 评论来源
	private Comment reply_comment = null;
	private User user;
	private Topic retweeted_status = null;
	private Topic status = null;

	/**
	 * @return the status
	 */
	public Topic getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Topic status) {
		this.status = status;
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

	public Comment() {

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
		this.source = jsonObject.getString("source");
		if (this.source.contains("</a>")) {
			this.source = "来自于："
					+ this.source.substring(this.source.indexOf('>') + 1,
							this.source.indexOf("</a>"));
		}
		 if (jsonObject.has("status")) {
        	 this.status=new Topic();
        	 status.parseRetweetedStatus(jsonObject.getJSONObject("status"));
         }
		 if (jsonObject.has("retweeted_status")) {
        	 this.retweeted_status=new Topic();
         retweeted_status.parseRetweetedStatus(jsonObject.getJSONObject("retweeted_status"));
         }
		this.user = new User();
		user.parseWeiboUser(jsonObject.getJSONObject("user"));
		if (jsonObject.has("reply_comment")) {
			this.reply_comment = new Comment();
			reply_comment.parseComments(jsonObject.getJSONObject("reply_comment"));
		}
	}

	/**
	 * 从json中分析出转发的信息
	 * 
	 * @param jsonObject
	 * @throws JSONException
	 * @throws ParseException
	 */
	public void parseComments(JSONObject jsonObject) throws JSONException,
			ParseException {

		this.created_at = getCreatedAtDif(jsonObject.getString("created_at"));
		this.id = jsonObject.getLong("id");
		this.text = jsonObject.getString("text");
	/*	if (this.source.contains("</a>")) {
			this.source = "来自于："
					+ this.source.substring(this.source.indexOf('>') + 1,
							this.source.indexOf("</a>"));
		}*/
		this.user = new User();
		user.parseWeiboUser(jsonObject.getJSONObject("user"));
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
		if (this.source.contains("</a>")) {
			this.source = "来自于："
					+ this.source.substring(this.source.indexOf('>') + 1,
							this.source.indexOf("</a>"));
		}
		this.created_at = getCreatedAtDif(jsonObject.getString("created_at"));
		this.id = jsonObject.getLong("id");
		this.text = jsonObject.getString("text");
	}

	/**
	 * toString 方法 测试
	 */
	public String toString() {
		String str = "微博idstr：" + idstr + "\n" + "创建时间：" + created_at + "\n"
				+ "微博ID：" + id + "\n" + "内容：" + text + "\n" + "微博来源："
				+ user.toString();
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

	public Comment getReply_comment() {
		return reply_comment;
	}

	/**
	 * @param reply_comment
	 *            the reply_comment to set
	 */
	public void setReply_comment(Comment reply_comment) {
		this.reply_comment = reply_comment;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the retweeted_status
	 */
	public Topic getRetweeted_status() {
		return retweeted_status;
	}

	/**
	 * @param retweeted_status the retweeted_status to set
	 */
	public void setRetweeted_status(Topic retweeted_status) {
		this.retweeted_status = retweeted_status;
	}


}
