package action;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import service.MessageService;
import service.UserService;
import vo.Message;
import vo.User;

import com.opensymphony.xwork2.ActionSupport;

public class MessageAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	HttpSession session = request.getSession();
	JSONObject json = null;

	private MessageService messageService;
	private Message message;
	private List<Message> messages;
	private User user;
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	/**
	 * 返回与已登录用户相关的通知集合
	 * 
	 * @return
	 * @throws Exception
	 */
	public String findNotifies() throws Exception {
		/* 设置响应头允许ajax跨域访问 */
		response.setHeader("Access-Control-Allow-Origin", "*");
		/* 星号表示所有的异域请求都可以接受， */
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");

		JSONArray jsons = new JSONArray();
		User userTemp = userService.findByName(user.getUsername());
		setMessages(messageService.findByUser(userTemp));
		for (Message ms : messages) {
			if (ms.getType() == 1) {
				System.out.println("提问");
				userTemp = userService.findById(ms.getFrom_userid());
				json = new JSONObject();
				json.put("img", userTemp.getAvatarUrl());
				json.put("name", userTemp.getUsername());
				json.put("content", "向我提问了     - 点击我回答ta吧");
				json.put("quesid", ms.getQuestion());
				json.put("type", 1);
				json.put("num", ms.getIsread());
				jsons.add(json);
			} else if (ms.getType() == 2) {
				System.out.println("回答");
				userTemp = userService.findById(ms.getFrom_userid());
				json = new JSONObject();
				json.put("img", userTemp.getAvatarUrl());
				json.put("name", userTemp.getUsername());
				json.put("type", 2);
				json.put("content", "回答我了     - 快点击我查看吧");
				json.put("ansid", ms.getAnswer());
				jsons.add(json);
			} else if (ms.getType() == 3) {
				System.out.println("关注");
				userTemp = userService.findById(ms.getFrom_userid());
				json = new JSONObject();
				json.put("img", userTemp.getAvatarUrl());
				json.put("name", userTemp.getUsername());
				json.put("content", "关注我了");
				json.put("type", 3);
				jsons.add(json);
			} else if (ms.getType() == 4) {
				System.out.println("充值");
				json = new JSONObject();
				json.put("img", 0);
				json.put("name", "客服小烜");
				json.put("type", 4);
				json.put("content", " 感谢您的支持！充值已完成！");
				jsons.add(json);
			}
		}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.print(jsons);
		pw.flush();
		pw.close();
		return SUCCESS;
	}
}
