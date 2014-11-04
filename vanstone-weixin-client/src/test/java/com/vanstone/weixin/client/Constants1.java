/**
 * 
 */
package com.vanstone.weixin.client;

import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.vanstone.centralserver.common.MyAssert;


/**
 * 平台系统常量
 * @author shipeng
 */
public class Constants1 {
	
	/** 社区URL选择器*/
	public static final Pattern WEIXIN_PROCESS_URL_PATTERN = Pattern.compile("http://weixin-service.c-cap.com.cn/weixinprocess/(.+).action");
	
	/** 消息体中的COMMUNITYID参数*/
	public static final String COMMUNITY_ID_PARAM_IN_MSG = "COMMUNITYID";
	
	/** DWZ div container id值*/
	public static final String DWZ_CONTAINER_HTML_ID = "container";
	
	/** 默认系统任务池数量 */
	public static final int DEFAULT_SYS_TASK_THREAD_NUM = 100;
	
	/** 项目公司默认最高权限账户名后缀*/
	public static final String PROJECT_COMPANY_DEFAULT_ACCOUNT_NAME = "_admin";
	
	/** 平台系统日志索引名称*/
	public static final String ES_PLATFORM_SYSTEM_LOG_INDEX_NAME = "pdp.syslog.index";
	
	/** 平台系统日志Mapping名称*/
	public static final String ES_PLATFORM_SYSTEM_LOG_MAPPING_NAME = "pdp.syslog.mapping";
	
	/** 默认管理员名称*/
	public static final String DEFAULT_ADMIN_NAME = "admin";
	
	/** 默认管理员密码*/
	public static final String DEFAULT_ADMIN_PASSWORD = "admin";
	
	/** 权限中默认session名称*/
	public static final String AUTH_ADMIN_SESSION_NAME = "pdp.auth.session.admin";
	
	/** 登录页面*/
	public static final String AUTH_ADMIN_LOGIN_PAGE = "login";
	
	/** 页面后缀*/
	public static final String PAGE_SUFFIX = "jhtml";
	
	/** 登出页面*/
	public static final String AUTH_ADMIN_LOGOUT_PAGE = "logout";
	
	/** 公共区域对象缓冲前缀*/
	public static final String COMMON_REGION_CACHE_KEY_REPFIX = "sys_region_";
	
	/** 公共区域列表缓冲前缀*/
	public static final String COMMON_REGIONLIST_CACHE_KEY_PREFIX = "sys_regionlist_";
	
	/** 缓冲省份中间值*/
	public static final String COMMON_REGION_PROVINCE_OBJECT_NAME = "province_";
	
	/** 缓冲城市中间值*/
	public static final String COMMON_REGION_CITY_OBJECT_NAME = "city_";
	
	/** 公共区域全部Key*/
	public static final String COMMON_REGION_CACHE_ALL = COMMON_REGION_CACHE_KEY_REPFIX + "*";
	
	
	
	
	
	/** 物业微信服务号*/
	//public static final String WEIXIN_SERVICE_NUM = "ecointel.pdp.weixin.servicesnum";
	public static final String WEIXIN_SERVICE_NUM = "ecointel";
	
	public static final String SYS_CHARSET_STRING = "utf-8";
	
	public static final Charset SYS_CHARSET = Charset.forName(SYS_CHARSET_STRING);
	
	/** 默认验证码失效时间，小时*/
	public static final int DEFAULT_VALIDATECODE_EXPIRE_HOUR = 2;
	
	
	public static final String INNODEV_PDP_MAIL_GROUP = "innodev.pdp.mail.group";
	
	/**
	 * 邮件主机配置信息
	 * @author shipeng
	 */
	public static enum MailConf implements BaseEnum<String> {
		
		Smtp("smtp主机地址","smtp"),
		SmtpPort("smtp端口号","port"),
		FromEmailAddress("发送邮件地址","fromEmailAddress"),
		EmailUsername("发送用户名","emailUsername"),
		EmailUserPwd("发送密码","emailUserPwd"),
		;
		private String desc;
		private String code;
		
		private MailConf(String desc,String code) {
			this.desc = desc;
			this.code = code;
		}
		@Override
		public String getCode() {
			return this.code;
		}
		@Override
		public String getDesc() {
			return this.desc;
		}
	}
	
	/**
	 * 邮件模板
	 * @author shipeng
	 */
	public static enum MailTemplate implements BaseEnum<String> {
		
		;
		private String desc;
		private String code;
		
		@Override
		public String getCode() {
			return this.code;
		}
		@Override
		public String getDesc() {
			return this.desc;
		}
	}
	
	public static final String WEIXIN_SERVER_DOMAIN_NAME = "http://weixin.innodev.com.cn";
	/**
	 * 微信事件定义
	 * @author shipeng
	 */
	public static enum Weixin_Event_Define implements BaseEnum<String> {
		
		CLICK_COMMUNITY_WALL_EVENT("点击社区活动墙事件"),
		CLICK_ABOUTUS_EVENT("点击关于我们事件"),
		CLICK_ACCESSOR_QR_EVENT("点击申请访客二维码事件"),
		CLICK_APPLY_REPAIR_REPORT_EVENT("申请报修单事件")
		;
		
		
		private String desc;
		private Weixin_Event_Define(String desc) {
			this.desc = desc;
		}
		
		@Override
		public String getCode() {
			return this.toString();
		}
		
		@Override
		public String getDesc() {
			return this.desc;
		}
	}
	
	/** 短信配置项*/
	public static final String INNODEV_PDP_SMS_GROUP = "innodev.pdp.sms.group";
	
	/**
	 * 短信配置
	 * @author shipeng
	 */
	public static enum SMS_Conf implements BaseEnum<String> {
		
		VCPSERVER("短信服务器"),
		VCPSVPORT("短信服务器端口"),
		VCPUSERID("短信账号"),
		VCPPASSWD("短信账号密码")
		;
		
		private String desc;
		
		private SMS_Conf(String desc) {
			this.desc = desc;
		}
		
		@Override
		public String getCode() {
			return this.toString();
		}

		@Override
		public String getDesc() {
			return this.desc;
		}
	}
	
	/** 系统模板分组*/
	public static final String INNODEV_PDP_SYSTEMPLATE_GROUP = "innodev.pdp.systemplate.group";
	
	/**
	 * 系统模板DataID
	 * @author shipeng
	 */
	public static enum Global_SysTemplate implements BaseEnum<String> {
		
		//微信端模板
		First_Subscribe_Template("首次关注发送文字信息", true),
		Mobile_Auth_Template("手机认证模板", true),
		Room_Bind_Auth_Template("房间号认证模板", true),
		
		//后台功能模板
		Admin_New_Community_Notice_Mail_Subject_Template("控制台开通社区通知邮件主题模板",false),
		Admin_New_Community_Notice_Mail_Content_Template("控制台开通社区通知邮件模板",true)
		;
		
		private String desc;
		
		private Global_SysTemplate(String desc, boolean textarea) {
			this.desc = desc;
		}
		
		@Override
		public String getCode() {
			return this.toString();
		}
		
		@Override
		public String getDesc() {
			return this.desc;
		}
	}
	
	/** elasticsearch header 插件端口号和插件名称*/
	public static final String ELASTICSEARCH_PORT_AND_HEAD_PLUGIN_NAME = ":9200/_plugin/head";
	
	/** 报修单前缀*/
	public static final String REPAIR_REPORT_NO_PREFIX = "BX_";
	
	/** 报修单默认长度*/
	public static final int REPAIR_REPORT_NO_DEFAULT_LENGTH = 6;
	
	/** 默认密码长度*/
	public static final int DEFAULT_STAFF_PASSWORD_LENGTH = 4;
	
	/** 后台管理默认size大小*/
	public static final int DEFAULT_ADMIN_PAGESIZE = 20;
	
	/** PDP项目ZK锁节点位置*/
	public static final String INNODEV_PDP_ZK_CACHE_LOCK_NODE = "/innodev/pdp/zk/cache/lock";
	
	/** 锁时间*/
	public static final int DEFAULT_LOCK_WAIT_TIME = 1;
	
	/** 系统分隔符*/
	public static final String SYSTEM_SEPARATOR = "/";
	
	
	
	
	
	
	/** =========== 微信处理 ======================*/
	
	
	/** 从URL中解析社区ID
	 * @param servletRequest
	 * @return
	 */
	public static String parseCommunityIdByHttpServletRequest(HttpServletRequest servletRequest) {
		MyAssert.notNull(servletRequest);
		String url = servletRequest.getRequestURL().toString();
		Matcher matcher = WEIXIN_PROCESS_URL_PATTERN.matcher(url);
		if (!matcher.find()) {
			return null;
		}
		return matcher.group(1);
	}
}
