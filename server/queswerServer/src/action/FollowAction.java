package action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import service.FollowService;
import service.MessageService;
import service.UserService;
import vo.Follow;
import vo.Message;
import vo.User;

import com.opensymphony.xwork2.ActionSupport;

import dao.FollowDao;

public class FollowAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	HttpSession session = request.getSession();
	JSONObject json = null;

	public Follow follow;

	public String hisname;
	public String myname;
	public MessageService messageService;

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public String getHisname() {
		return hisname;
	}

	public void setHisname(String hisname) {
		this.hisname = hisname;
	}

	public String getMyname() {
		return myname;
	}

	public void setMyname(String myname) {
		this.myname = myname;
	}

	public FollowDao followDao;

	public FollowService followService;

	public UserService userService;

	public Follow getFollow() {
		return follow;
	}

	public void setFollow(Follow follow) {
		this.follow = follow;
	}

	public FollowDao getFollowDao() {
		return followDao;
	}

	public void setFollowDao(FollowDao followDao) {
		this.followDao = followDao;
	}

	public FollowService getFollowService() {
		return followService;
	}

	public void setFollowService(FollowService followService) {
		this.followService = followService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * 添加关注
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addFollow() throws Exception {
		/* 设置响应头允许ajax跨域访问 */
		response.setHeader("Access-Control-Allow-Origin", "*");
		/* 星号表示所有的异域请求都可以接受， */
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");

		hisname = request.getParameter("hisname");
		myname = request.getParameter("myname");
		User user_followed = userService.findByName(hisname);
		User user_mine = userService.findByName(myname);
		follow = new Follow(user_mine.getId(), user_followed.getId());
		user_followed.setFans(user_followed.getFans() + 1);
		userService.updateUser(user_followed);
		followService.addFollow(follow);

		Message message = new Message();
		message.setType(3);
		message.setFrom_userid(user_mine.getId());
		message.setIsread(1);
		message.setUser(user_followed);
		messageService.addMessage(message);

		return SUCCESS;
	}

	/**
	 * 取消关注
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteFollow() throws Exception {
		/* 设置响应头允许ajax跨域访问 */
		response.setHeader("Access-Control-Allow-Origin", "*");
		/* 星号表示所有的异域请求都可以接受， */
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");

		hisname = request.getParameter("hisname");
		myname = request.getParameter("myname");
		System.out.println("delete." + hisname + "delete." + myname);
		User user_followed = userService.findByName(hisname);
		User user_mine = userService.findByName(myname);
		followService.deleteFollow(user_followed.getId(), user_mine.getId());
		user_followed.setFans(user_followed.getFans() - 1);
		userService.updateUser(user_followed);

		return SUCCESS;
	}

	/**
	 * 返回 是否关注 信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String isFollow() throws Exception {
		/* 设置响应头允许ajax跨域访问 */
		response.setHeader("Access-Control-Allow-Origin", "*");
		/* 星号表示所有的异域请求都可以接受， */
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");

		hisname = request.getParameter("hisname");
		myname = request.getParameter("myname");
		System.out.println("check." + hisname + "check." + myname);
		User user_followed = userService.findByName(hisname);
		User user_mine = userService.findByName(myname);
		if (followService.findFollow(user_followed.getId(), user_mine.getId()) != null) {
			JSONObject jsonn = new JSONObject();
			jsonn.put("isfollow", 1);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter pw = response.getWriter();
			pw.print(jsonn);
			pw.flush();
			pw.close();
		}

		return SUCCESS;
	}
}
