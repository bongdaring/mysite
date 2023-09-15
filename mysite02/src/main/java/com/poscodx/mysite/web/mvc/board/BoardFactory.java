package com.poscodx.mysite.web.mvc.board;

import com.poscodx.web.mvc.Action;
import com.poscodx.web.mvc.ActionFactory;


public class BoardFactory implements ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if("write".equals(actionName)) {
			action = new WriteFormAction();		
		} else if("insert".equals(actionName)) {
			action = new WriteAction();
		} else if("select".equals(actionName)){
			action = new SelectBoardAction();
		} else if("commentform".equals(actionName)){
			action = new CommentFormAction();
		} else if("comment".equals(actionName)){
			action = new CommentAction();
		} else if("updateform".equals(actionName)){
			action = new UpdateFormAction();
		} else if("update".equals(actionName)){
			action = new UpdateAction();
		} else if("delete".equals(actionName)){
			action = new DeleteBoardAction();
		} else {
			action = new BoardListAction();

		}
		return action;
	}

}
