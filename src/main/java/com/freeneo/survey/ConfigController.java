package com.freeneo.survey;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.freeneo.survey.domain.ConfigItem;
import com.freeneo.survey.mapper.ConfigMapper;

@Controller
@RequestMapping(value="/config")
public class ConfigController {

	private static final Logger logger = LoggerFactory.getLogger(ConfigController.class);
	
	@Autowired
	ConfigMapper configMapper;
	
	@RequestMapping(method=RequestMethod.GET)
	public String config(Model model){

		ConfigItem organization = configMapper.select("organization");
		ConfigItem phone = configMapper.select("phone");
		ConfigItem fax = configMapper.select("fax");
		ConfigItem address = configMapper.select("address");
		ConfigItem logo_image = configMapper.select("logo_image");
		ConfigItem domain = configMapper.select("domain");

		model.addAttribute("organization", ( organization != null ? organization.getValue() : "" ) );
		model.addAttribute("phone", ( phone != null ? phone.getValue() : "" ) );
		model.addAttribute("fax", ( fax != null ? fax.getValue() : "" ) );
		model.addAttribute("address", ( address != null ? address.getValue() : "" ) );
		model.addAttribute("logo_image", ( logo_image != null ? logo_image.getValue() : "" ) );
		model.addAttribute("domain", ( domain != null ? domain.getValue() : "" ) );

		model.addAttribute("pageTitle", "환경설정");
		
		return "config";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String insert(
			HttpServletRequest request,
			Model model){
		
		String[] keys = {
			"organization", 
			"phone", 
			"fax",
			"address",
			"logo_image",
			"domain"
		};
		
		for(String key : keys){
			
			String value = (String) request.getParameter(key);
			
			logger.debug(key + " = {}", value);
			if(value == null){
				continue;
			}
			
			ConfigItem configItem = configMapper.select(key);
			
			if( configItem == null){
				configMapper.insert(new ConfigItem(key, value));
			}else{
				configMapper.update(new ConfigItem(key, value));
			}
			
			
			
			
		}
		
		return "redirect:/config";
	}
	
}
