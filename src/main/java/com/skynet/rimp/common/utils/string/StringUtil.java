package com.skynet.rimp.common.utils.string;

public class StringUtil {

	/**
	 * 找指定字符出现的次数
	 * @param src
	 * @param find
	 * @return
	 */
	public static int getOccur(String src, String find) {
		int o = 0;
		int index = -1;
		while ((index = src.indexOf(find, index)) > -1) {
			++index;
			++o;
		}
		return o;
	}
	
	/**
	 * 判断一个字符是否是中文
	 * @param src
	 * @param find
	 * @return
	 */
    public static boolean isChinese(char c) {
		      return c >= 0x4E00 &&  c <= 0x9FA5;// 根据字节码判断
		}
    
    /**
	 *  判断一个字符串是否含有中文
	 * @param src
	 * @param find
	 * @return
	 */
	public static boolean isChinese(String str) {
		    if (str == null) return false;
		    for (char c : str.toCharArray()) {
		        if (isChinese(c)) return true;// 有一个中文字符就返回
		    }
		    return false;
		}
	/**
	 *  屏蔽身份证号
	 * @param src
	 * @param find
	 * @return
	 */
	public static String screenIdentityCard(String idCard) {
	    String identityCard = "";
	    identityCard = idCard.replace(idCard.substring(6, 14), "********");
	    return identityCard;
	}
	/**
	 *  屏蔽电话号码
	 * @param src
	 * @param find
	 * @return
	 */
	public static String screenTelephone(String telephone) {
	    String tel = "";
	    tel = telephone.substring(0, 3)+"****"+telephone.substring(7);
	    return tel;
	    }

}
