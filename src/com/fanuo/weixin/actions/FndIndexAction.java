package com.fanuo.weixin.actions;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.base.action.BaseAction;

@Controller("fndIndexAction")
@Scope("prototype")
public class FndIndexAction extends BaseAction {
	private static final long serialVersionUID = 2967690464878755571L;

	public String toIndex(){
		return "index";
		
		
		
	}
	
	

}
