package com.poscodx.mysite.web.mvc.guestbook;

import com.poscodx.web.mvc.Action;
import com.poscodx.web.mvc.ActionFactory;

public class GuestBookFactory implements ActionFactory{

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if("insert".equals(actionName)) {
			action = new InsertAction();		
		} else if("deleteform".equals(actionName)) {
			action = new DeleteFormAction();
		} else if("delete".equals(actionName)){
			action = new DeleteAction();
		} else {
			action = new ListAction();

		}
		return action;
	}

}
