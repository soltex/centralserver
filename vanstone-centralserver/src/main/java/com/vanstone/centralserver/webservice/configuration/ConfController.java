/**
 * 
 */
package com.vanstone.centralserver.webservice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.vanstone.business.ObjectDuplicateException;
import com.vanstone.centralserver.business.sdk.configuration.IConfigurationServiceMgr;
import com.vanstone.centralserver.common.Constants;
import com.vanstone.centralserver.common.WebserviceResponse;
import com.vanstone.centralserver.common.WebserviceResponse.ResponseCode;

/**
 * @author shipeng
 */
@Controller("confController")
@RequestMapping("/conf")
public class ConfController {
	
	@Autowired
	private IConfigurationServiceMgr configurationServiceMgr;
	
	/**
	 * 获取Conf对象
	 * @param groupId
	 * @param dataId
	 * @return
	 */
	@RequestMapping(value="/view/{groupId}/{dataId}", method=RequestMethod.GET)
	@ResponseBody
	public String retrievalConf(String groupId, String dataId) {
		String value = configurationServiceMgr.getValue(groupId, dataId);
		return value;
	}
	
	@RequestMapping(value="/update/{groupId}/{dataId}", method=RequestMethod.POST, produces="application/json")
	@ResponseBody
	public WebserviceResponse updateConf(@PathVariable("groupId") String groupId, @PathVariable("dataId") String dataId, @RequestParam("value")String value) {
		String loadValue = this.configurationServiceMgr.getValue(groupId, dataId);
		if (loadValue == null) {
			return WebserviceResponse.createResponse(ResponseCode.GroupId_DataId_Not_Found, null);
		}
		this.configurationServiceMgr.updateConf(groupId, dataId, value);
		return WebserviceResponse.createResponse(ResponseCode.Success, null);
	}
	
	@RequestMapping(value="/add",method=RequestMethod.POST, produces="application/json") 
	@ResponseBody
	public WebserviceResponse addConf(@RequestParam("groupId")String groupId, @RequestParam("dataId")String dataId, @RequestParam("value")String value) {
		if (groupId == null || "".equals(groupId)) {
			groupId = Constants.DEFAULT_GROUP_NAME;
		}
		if (dataId == null || "".equals(dataId)) {
			return WebserviceResponse.createResponse(ResponseCode.Illegal_Argument_Error);
		}
		try {
			this.configurationServiceMgr.addConf(groupId, dataId, value);
		} catch (ObjectDuplicateException e) {
			e.printStackTrace();
			return WebserviceResponse.createResponse(ResponseCode.GroupId_DataId_Duplicate);
		}
		return WebserviceResponse.createResponse(ResponseCode.Success);
	}
}
