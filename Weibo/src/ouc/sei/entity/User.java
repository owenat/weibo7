/**
 * @author wangqi
 *2013-7-25 ����9:17:23
 */
package ouc.sei.entity;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * TODO ����9:17:23
 */
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;// 用户的ID
    private String name;// 用户名
    private String screen_name;// 昵称
    private int province;// 省份
    private int city;// 城市
    private String location;// 地理位置
    private String description;// 描述
    private String url;// 微博地址
    private String profileImageUrl;// 头像地址
    private String gender;// 性别
    private int followers_count;// 粉丝数
    private int friends_count;// 关注数
    private int statuses_count;// 微博数
    private int favourites_count;// 收藏数
    private String created_at;// 创建时间
    private boolean verified;// 是否是认证用户
    private String avatar_large ;

    public String getAvatar_large() {
            return avatar_large;
    }

    public void setAvatar_large(String avatar_large) {
            this.avatar_large = avatar_large;
    }

    private Topic topic;

    // private WeiboStatus status;//最近一条微博
    public long getUid() {
            return id;
    }

    public void setUid(long uid) {
            this.id = uid;
    }

    public String getName() {
            return name;
    }

    public void setName(String name) {
            this.name = name;
    }

    public String getScreenName() {
            return screen_name;
    }

    public void setScreenName(String screenName) {
            this.screen_name = screenName;
    }

    public int getProvince() {
            return province;
    }

    public void setProvince(int province) {
            this.province = province;
    }

    public int getCity() {
            return city;
    }

    public void setCity(int city) {
            this.city = city;
    }

    public String getLocation() {
            return location;
    }

    public void setLocation(String location) {
            this.location = location;
    }

    public String getDescription() {
            return description;
    }

    public void setDescription(String description) {
            this.description = description;
    }

    public String getUrl() {
            return url;
    }

    public void setUrl(String url) {
            this.url = url;
    }

    public String getProfileImageUrl() {
            return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
            this.profileImageUrl = profileImageUrl;
    }

    public String getGender() {
            return gender;
    }

    public void setGender(String gender) {
            this.gender = gender;
    }

    public int getFollowersCount() {
            return followers_count;
    }

    public void setFollowersCount(int followersCount) {
            this.followers_count = followersCount;
    }

    public int getFriendsCount() {
            return friends_count;
    }

    public void setFriendsCount(int friendsCount) {
            this.friends_count = friendsCount;
    }

    public int getStatusesCount() {
            return statuses_count;
    }

    public void setStatusesCount(int statusesCount) {
            this.statuses_count = statusesCount;
    }

    public int getFavouritesCount() {
            return favourites_count;
    }

    public void setFavouritesCount(int favouritesCount) {
            this.favourites_count = favouritesCount;
    }

    public String getCreatedAt() {
            return created_at;
    }

    public void setCreatedAt(String createdAt) {
            this.created_at = createdAt;
    }

    public boolean isVerified() {
            return verified;
    }

    public void setVerified(boolean verified) {
            this.verified = verified;
    }
    public User() {
            // TODO Auto-generated constructor stub
    }

    //将微博User类中的所有属性全部解析出来;  可以用到哪个-->显示哪个
    public void parseWeiboUser(JSONObject jsonObject) throws JSONException {
            this.id = jsonObject.getLong("id");
            this.screen_name = jsonObject.getString("screen_name");
            this.name = jsonObject.getString("name");
            this.province = jsonObject.getInt("province");
            this.city = jsonObject.getInt("city");
            this.location = jsonObject.getString("location");
            this.description = jsonObject.getString("description");
            this.url = jsonObject.getString("url");
            this.profileImageUrl = jsonObject.getString("profile_image_url");
            this.gender = jsonObject.getString("gender");
            this.followers_count = jsonObject.getInt("followers_count");
            this.friends_count = jsonObject.getInt("friends_count");
            this.statuses_count = jsonObject.getInt("statuses_count");
            this.favourites_count = jsonObject.getInt("favourites_count");
            this.created_at = jsonObject.getString("created_at");
            this.verified = jsonObject.getBoolean("verified");
            this.avatar_large = jsonObject.getString("avatar_large");
    }

    public String toString() {
            String str = "用户UID：" + id + "\n" + "用户昵称：" + screen_name + "\n"
                            + "好友显示名称：" + name + "\n" + "省份：" + province + "\n" + "城市："
                            + city + "\n" + "位置：" + location + "\n" + "描述：" + description
                            + "\n" + "用户博客地址：" + url + "\n" + "头像地址：" + profileImageUrl
                            + "\n" +  "性别：" + gender + "\n"
                            + "粉丝数：" + followers_count + "\n" + "关注数：" + friends_count + "\n"
                            + "微博数：" + statuses_count + "\n" + "收藏数：" + favourites_count
                            + "\n" + "创建时间：" + created_at + "\n" +  "是否已认证：" + verified + "\n"
                            +  "用户头像地址："        + profileImageUrl + "\n";

            return str;
    }

    public Topic getTopic() {
            return topic;
    }

}