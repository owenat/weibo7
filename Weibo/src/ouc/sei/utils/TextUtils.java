/**
 * @author wangqi
 *2013-7-27 下午3:28:17
 */
package ouc.sei.utils;

/**
 * TODO 下午3:28:17
 */
public class TextUtils {
	public static boolean isEmpty(String paramString) {
		if ((paramString != null) && (!"".equals(paramString.trim())))
			return false;
		else
			return true;
	}
}
