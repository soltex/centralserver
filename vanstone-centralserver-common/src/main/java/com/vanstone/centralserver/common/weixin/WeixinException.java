/**
 * 
 */
package com.vanstone.centralserver.common.weixin;


/**
 * @author shipeng
 */
public class WeixinException extends Exception {
	
    private static final long serialVersionUID = -8943595785016313782L;
    
    private ErrorCode errorCode;
    
    public WeixinException() {
    	
    }
    
    public WeixinException(ErrorCode errorCode) {
    	this.errorCode = errorCode;
    }
    
    public WeixinException(ErrorCode errorCode, Exception exception) {
    	this(errorCode);
    	exception.printStackTrace();
    }
    
    public ErrorCode getErrorCode() {
		return errorCode;
	}

	public static enum ErrorCode {
    	/**文章数量大于10*/
    	MAX_ARTICLE_ITEM_COUNT_GT_10("文章数量不允许大于10", 100001),
    	
    	/**微信子菜单项大于5*/
    	MAX_SUB_MENU_ITEM_COUNT_GT_5("微信子菜单项大于5",100002),
    	
    	/**微信菜单项大于3*/
    	MAX_MENU_ITEM_COUNT_GT_3("微信菜单项大于3", 100003),
    	
    	/**微信服务器响应错误*/
    	WEIXIN_SERVER_ERROR("微信服务器响应错误",100004),
    	
    	/**微信二维码Ticket失效*/
    	WEIXIN_QC_TICKET_ERROR("微信二维码Ticket失效", 100005),
    	
    	WEIXIN_UPLOAD_IMAGE_GT_MAXSIZE("微信上传图片大小不能超过1M", 100006),
    	
    	WEIXIN_UPLOAD_VOICE_GT_MAXSIZE("微信上传音频大小不能超过2M", 100007),
    	
    	WEIXIN_UPLOAD_VIDEO_GT_MAXSIZE("微信上传视频大小不能超过10M", 100008),
    	
    	WEIXIN_UPLOAD_THUMB_GT_MAXSIZE("微信上传缩略图大小不能超过64K", 100009),
    	
    	/**微信内部错误代码*/
    	WEIXIN_SUCCESS_0("请求成功", 0 ),
    	WEIXIN_ERROR__1("系统繁忙", -1 ),
    	WEIXIN_ERROR_40001("获取access_token时AppSecret错误，或者access_token无效 ",40001),
    	WEIXIN_ERROR_40002("不合法的凭证类型",40002),
    	WEIXIN_ERROR_40003("不合法的OpenID ",40003),
    	WEIXIN_ERROR_40004(" 	不合法的媒体文件类型",40004),
    	WEIXIN_ERROR_40005("	不合法的文件类型",40005),
    	WEIXIN_ERROR_40006(" 	不合法的文件大小",40006),
    	WEIXIN_ERROR_40007(" 	不合法的媒体文件id",40007),
    	WEIXIN_ERROR_40008(" 	不合法的消息类型",40008),
    	WEIXIN_ERROR_40009(" 	不合法的图片文件大小",40009),
    	WEIXIN_ERROR_40010(" 	不合法的语音文件大小",40010),
    	WEIXIN_ERROR_40011(" 	不合法的视频文件大小",40011),
    	WEIXIN_ERROR_40012("	不合法的缩略图文件大小",40012),
    	WEIXIN_ERROR_40013(" 	不合法的APPID",40013),
    	WEIXIN_ERROR_40014(" 	不合法的access_token",40014),
    	WEIXIN_ERROR_40015(" 	不合法的菜单类型",40015),
    	WEIXIN_ERROR_40016(" 	不合法的按钮个数",40016),
    	WEIXIN_ERROR_40017(" 	不合法的按钮个数",40017),
    	WEIXIN_ERROR_40018(" 	不合法的按钮名字长度",40018),
    	WEIXIN_ERROR_40019(" 	不合法的按钮KEY长度",40019),
    	WEIXIN_ERROR_40020(" 	不合法的按钮URL长度",40020),
    	WEIXIN_ERROR_40021(" 	不合法的菜单版本号",40021),
    	WEIXIN_ERROR_40022(" 	不合法的子菜单级数",40022),
    	WEIXIN_ERROR_40023(" 	不合法的子菜单按钮个数",40023),
    	WEIXIN_ERROR_40024(" 	不合法的子菜单按钮类型",40024),
    	WEIXIN_ERROR_40025(" 	不合法的子菜单按钮名字长度",40025),
    	WEIXIN_ERROR_40026(" 	不合法的子菜单按钮KEY长度",40026),
    	WEIXIN_ERROR_40027(" 	不合法的子菜单按钮URL长度",40027),
    	WEIXIN_ERROR_40028(" 	不合法的自定义菜单使用用户",40028),
    	WEIXIN_ERROR_40029(" 	不合法的oauth_code",40029),
    	WEIXIN_ERROR_40030(" 	不合法的refresh_token",40030),
    	WEIXIN_ERROR_40031 ("	不合法的openid列表",40031),
    	WEIXIN_ERROR_40032(" 	不合法的openid列表长度",40032),
    	WEIXIN_ERROR_40033(" 	不合法的请求字符，不能包含\\uxxxx格式的字符",40033),
    	WEIXIN_ERROR_40035(" 	不合法的参数",40035),
    	WEIXIN_ERROR_40038(" 	不合法的请求格式",40038),
    	WEIXIN_ERROR_40039(" 	不合法的URL长度",40039),
    	WEIXIN_ERROR_40050(" 	不合法的分组id",40050),
    	WEIXIN_ERROR_40051(" 	分组名字不合法",40051),
    	WEIXIN_ERROR_41001(" 	缺少access_token参数",41001),
    	WEIXIN_ERROR_41002(" 	缺少appid参数",41002),
    	WEIXIN_ERROR_41003(" 	缺少refresh_token参数",41003),
    	WEIXIN_ERROR_41004(" 	缺少secret参数",41004),
    	WEIXIN_ERROR_41005(" 	缺少多媒体文件数据",41005),
    	WEIXIN_ERROR_41006(" 	缺少media_id参数",41006),
    	WEIXIN_ERROR_41007(" 	缺少子菜单数据",41007),
    	WEIXIN_ERROR_41008(" 	缺少oauth code",41008),
    	WEIXIN_ERROR_41009(" 	缺少openid",41009),
    	WEIXIN_ERROR_42001(" 	access_token超时",42001),
    	WEIXIN_ERROR_42002(" 	refresh_token超时",42002),
    	WEIXIN_ERROR_42003(" 	oauth_code超时",42003),
    	WEIXIN_ERROR_43001(" 	需要GET请求",43001),
    	WEIXIN_ERROR_43002(" 	需要POST请求",43002),
    	WEIXIN_ERROR_43003(" 	需要HTTPS请求",43003),
    	WEIXIN_ERROR_43004(" 	需要接收者关注",43004),
    	WEIXIN_ERROR_43005(" 	需要好友关系",43005),
    	WEIXIN_ERROR_44001(" 	多媒体文件为空",44001),
    	WEIXIN_ERROR_44002(" 	POST的数据包为空",44002),
    	WEIXIN_ERROR_44003(" 	图文消息内容为空",44003),
    	WEIXIN_ERROR_44004(" 	文本消息内容为空",44004),
    	WEIXIN_ERROR_45001(" 	多媒体文件大小超过限制",45001),
    	WEIXIN_ERROR_45002(" 	消息内容超过限制",45002),
    	WEIXIN_ERROR_45003(" 	标题字段超过限制",45003),
    	WEIXIN_ERROR_45004(" 	描述字段超过限制",45004),
    	WEIXIN_ERROR_45005(" 	链接字段超过限制",45005),
    	WEIXIN_ERROR_45006(" 	图片链接字段超过限制",45006),
    	WEIXIN_ERROR_45007(" 	语音播放时间超过限制",45007),
    	WEIXIN_ERROR_45008(" 	图文消息超过限制",45008),
    	WEIXIN_ERROR_45009(" 	接口调用超过限制",45009),
    	WEIXIN_ERROR_45010(" 	创建菜单个数超过限制",45010),
    	WEIXIN_ERROR_45015(" 	回复时间超过限制",45015),
    	WEIXIN_ERROR_45016(" 	系统分组，不允许修改",45016),
    	WEIXIN_ERROR_45017(" 	分组名字过长",45017),
    	WEIXIN_ERROR_45018(" 	分组数量超过上限",45018),
    	WEIXIN_ERROR_45028(" 	has no masssend quota",45028),
    	WEIXIN_ERROR_46001(" 	不存在媒体数据",46001),
    	WEIXIN_ERROR_46002(" 	不存在的菜单版本",46002),
    	WEIXIN_ERROR_46003(" 	不存在的菜单数据",46003),
    	WEIXIN_ERROR_46004(" 	不存在的用户",46004),
    	WEIXIN_ERROR_47001(" 	解析JSON/XML内容错误",47001),
    	WEIXIN_ERROR_48001(" 	api功能未授权",48001),
    	WEIXIN_ERROR_50001(" 	用户未授权该api ",50001),
    	
    	
    	WEIXIN_UNKNOWN_ERROR("微信位置错误，请Debug信息查看",-2);
    	;
    	
    	
    	private String desc;
    	private Integer code;
    	
    	private ErrorCode(String desc,Integer code) {
    		this.desc = desc;
    		this.code = code;
    	}

		public String getDesc() {
			return desc;
		}

		public Integer getCode() {
			return code;
		}
    	
		public static ErrorCode parseErrorCode(Integer code) {
			if (code == null) {
				return null;
			}
			ErrorCode[] errorCodes = ErrorCode.values();
			for (ErrorCode errorCode : errorCodes) {
				if (errorCode.getCode().equals(code)) {
					return errorCode;
				}
			}
			return ErrorCode.WEIXIN_UNKNOWN_ERROR;
		}
    }
}
