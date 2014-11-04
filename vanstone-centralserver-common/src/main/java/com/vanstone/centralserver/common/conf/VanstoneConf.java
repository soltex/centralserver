/**
 * 
 */
package com.vanstone.centralserver.common.conf;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vanstone.centralserver.common.MyAssert;

/**
 * 统一的配置管理器
 * @author shipeng
 */
public class VanstoneConf {
	
	private static Logger LOG = LoggerFactory.getLogger(VanstoneConf.class);

	/** 配置文件 */
	private static final String CONF_PATH = "/vanstone-conf.properties";

	/**当前节点下管理的Appnames*/
	private Collection<String> appnames = new ArrayList<String>();
	/**Central Server 地址*/
	private String centralServer;
	/**zk*/
	private String zk;
	/**Connection Timeout In MS*/
	private int zkConnectionTimeoutMS = 2000;
	
	private static class VanstoneConfInstance {
		public static final VanstoneConf instance = new VanstoneConf();
		static {
			try {
				instance.init();
			} catch (Exception e) {
				e.printStackTrace();
				throw new ExceptionInInitializerError(e);
			}
		}
	}
	
	private VanstoneConf() {}

	private void init() throws Exception {
		Properties properties = new Properties();
		InputStream is = VanstoneConf.class.getResourceAsStream(CONF_PATH);
		if (is == null) {
			LOG.warn("Vanstone conf properties not found.");
			return;
		}
		properties.load(is);
		String strappnames = properties.getProperty("weixin.appnames");
//		String weixinTokenserver = properties.getProperty("weixin.tokenserver");
//		if (strappnames == null || "".equals(strappnames) || weixinTokenserver == null || "".equals(weixinTokenserver)) {
//			throw new ExceptionInInitializerError("appnames or weixin token server empty!");
//		}
		if (strappnames != null && !"".equals(strappnames)) {
			String[] arrayAppnames = StringUtils.split(strappnames, ",");
			for (String str : arrayAppnames) {
				this.addAppname(str);
			}
		}
//		this.tokenServer = weixinTokenserver;
//		if (!this.tokenServer.startsWith(Constants.HTTP_PROTOCOLS_URL_PREFIX)) {
//			this.tokenServer = Constants.HTTP_PROTOCOLS_URL_PREFIX + this.tokenServer;
//		}
		
		this.zk = properties.getProperty("zk");
        String strZkConnectionTimeoutMS = properties.getProperty("zk.connectionTimeoutMS");
        if (zk != null && !"".equals(zk) && strZkConnectionTimeoutMS != null && !"".equals(strZkConnectionTimeoutMS)) {
        	zkConnectionTimeoutMS = Integer.parseInt(strZkConnectionTimeoutMS);
        }
        this.centralServer = properties.getProperty("centralserver");
        if (this.centralServer != null && this.centralServer.endsWith("/")) {
        	this.centralServer = this.centralServer.substring(0, this.centralServer.length() - 1);
        }
		LOG.info("Weixin conf initial ok. The info is : " + ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE));
		IOUtils.closeQuietly(is);
	}
	
	/**
	 * 获取Conf实例
	 * 
	 * @return
	 */
	public static VanstoneConf getInstance() {
		return VanstoneConfInstance.instance;
	}
	
	/**
	 * 获取全部Appnames
	 * @return
	 */
	public Collection<String> getAppnames() {
		return appnames;
	}
	
	void addAppname(String appname) {
		MyAssert.hasText(appname);
		this.appnames.add(appname);
	}
	
	void addAppnames(Collection<String> appnameCollection) {
		MyAssert.notEmpty(appnameCollection);
		this.appnames.addAll(appnameCollection);
	}
	
	/**
	 * 是否包含Appname
	 * @param appname
	 * @return
	 */
	public boolean containAppname(String appname) {
		return this.appnames.contains(appname);
	}
	
	public boolean containAppnames() {
		return this.appnames.size() != 0;
	}
	
	/**
	 * 获取ZK地址
	 * @return
	 */
	public String getZk() {
		return zk;
	}

	/**
	 * 获取ZK Connection Timeout MS 
	 * @return
	 */
	public int getZkConnectionTimeoutMS() {
		return zkConnectionTimeoutMS;
	}

	/**
	 * 获取CentralServer 
	 * @return
	 */
	public String getCentralServer() {
		return centralServer;
	}
	
}
