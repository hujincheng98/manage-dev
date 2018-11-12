package com.skynet.rimp.common.utils.userUtil;

import org.apache.shiro.SecurityUtils;
import com.skynet.platform.common.authentication.UserToken;

public class UserUtil {
	
	/**
	 * 获取USER_ID
	 * @return
	 */
    public static String getUserId(){
    	UserToken token = (UserToken)SecurityUtils.getSubject().getPrincipal();
    	return token.getUser().getUserId();
    }
    /**
     * 获取ORG_ID
     * @return
     */
    public static String getOrgId(){
    	UserToken token = (UserToken)SecurityUtils.getSubject().getPrincipal();
    	return token.getUser().getOrgId();
    }
    /**
     * 获取ORG_ID对应的编码AUTH_CODE
     * @return
     */
    public static String getAuthCode(){
    	UserToken token = (UserToken)SecurityUtils.getSubject().getPrincipal();
    	return token.getUser().getOrg().getAuthCode();
    }
}
