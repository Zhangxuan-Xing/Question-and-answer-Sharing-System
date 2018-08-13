package action;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import service.UserService;
import vo.User;

import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport {

	private static final long serialVersionUID = -600925800146269335L;
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	HttpSession session = request.getSession();
	ServletContext application = ServletActionContext.getServletContext();

	private String username = "";
	private String person;
	private String intro;

	private List<User> users;

	private String avatarUrl;

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	private User user;

	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	private Integer following = 0;

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getFollowing() {
		return following;
	}

	public void setFollowing(Integer following) {
		this.following = following;
	}

	/**
	 * 注册并添加用户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		/* 设置响应头允许ajax跨域访问 */
		response.setHeader("Access-Control-Allow-Origin", "*");
		/* 星号表示所有的异域请求都可以接受， */
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		JSONObject json = null;
		User userTemp = userService.findByName(user.getUsername());
		if (userTemp == null) {
			userService.addUser(user);
			user.setAccount(0);
			// 返回值给微信小程序
			Writer out = response.getWriter();
			out.write("收到登录信息");
			System.out.println("未注册，已添加");
			out.flush();
		} else {
			json = new JSONObject();
			json.put("fans", userTemp.getFans());
			json.put("intro", userTemp.getSimpledesc());
			System.out.println("已注册");
			response.setContentType("text/html;charset=utf-8");
			PrintWriter pw = response.getWriter();
			pw.print(json);
			pw.flush();
			pw.close();
		}
		return SUCCESS;
	}

	/**
	 * 更新用户信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateIntro() throws Exception {
		/* 设置响应头允许ajax跨域访问 */
		response.setHeader("Access-Control-Allow-Origin", "*");
		/* 星号表示所有的异域请求都可以接受， */
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		JSONObject json = null;
		String intro = request.getParameter("intro");
		System.out.println("个人简介：" + intro);
		User userTemp = userService.findByName(user.getUsername());
		userTemp.setSimpledesc(intro);
		userService.updateUser(userTemp);
		json = new JSONObject();
		json.put("intro", userTemp.getSimpledesc());
		System.out.println("已注册");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return SUCCESS;
	}

	/**
	 * 返回名人信息集
	 * 
	 * @return
	 * @throws Exception
	 */
	public String listFamous() throws Exception {
		/* 设置响应头允许ajax跨域访问 */
		response.setHeader("Access-Control-Allow-Origin", "*");
		/* 星号表示所有的异域请求都可以接受， */
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		JSONArray jsons = new JSONArray();
		JSONArray js = new JSONArray();
		JSONObject json = null;
		System.out.println("进入查询...");
		setUsers(userService.findAllByIsFamous(1));
		for (User user : users) {
			json = new JSONObject();
			json.put("name", user.getUsername());
			json.put("img", user.getAvatarUrl());
			json.put("content", user.getSimpledesc());
			json.put("isfamous", user.getIsFamous());
			json.put("fans", user.getFans());
			if (user.getIsFamous() == 1) {
				json.put("isfollow", 1);
			} else {
				json.put("isfollow", 0);
			}
			jsons.add(json);
		}
		for (int i = 0; i < 3; i++) {
			Object jsonO = new JSONObject();
			jsonO = jsons.get((int) (Math.random() * (jsons.size() - 1)));
			if (!js.contains(jsonO)) {
				js.add(jsonO);
			} else {
				i--;
			}
		}
		System.out.println(js.toString());

		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.print(js);
		pw.flush();
		pw.close();
		return SUCCESS;
	}

	/**
	 * 返回关联用户所关注的用户集
	 * 
	 * @return
	 * @throws Exception
	 */
	public String listliked() throws Exception {
		/* 设置响应头允许ajax跨域访问 */
		response.setHeader("Access-Control-Allow-Origin", "*");
		/* 星号表示所有的异域请求都可以接受， */
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		JSONArray jsons = new JSONArray();
		JSONObject json = null;
		System.out.println("进入查询...");
		User userTemp = userService.findByName(user.getUsername());

		System.out.println("..." + userTemp.getId());
		setUsers(userService.findLikedByUserid(userTemp.getId()));
		for (User user : users) {
			json = new JSONObject();
			json.put("name", user.getUsername());
			json.put("img", user.getAvatarUrl());
			json.put("content", user.getSimpledesc());
			json.put("isfamous", user.getIsFamous());
			json.put("fans", user.getFans());
			jsons.add(json);
		}
		System.out.println(jsons.toString());

		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.print(jsons);
		pw.flush();
		pw.close();
		return SUCCESS;
	}

	/**
	 * 模糊检索，返回符合条件的用户集
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchPerson() throws Exception {
		/* 设置响应头允许ajax跨域访问 */
		response.setHeader("Access-Control-Allow-Origin", "*");
		/* 星号表示所有的异域请求都可以接受， */
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		String searchName = request.getParameter("person");
		JSONArray jsons = new JSONArray();
		JSONObject json = null;
		System.out.println("进入查询...");

		setUsers(userService.findBySearch("%" + searchName + "%"));
		for (User user : users) {
			json = new JSONObject();
			json.put("name", user.getUsername());
			json.put("img", user.getAvatarUrl());
			json.put("content", user.getSimpledesc());
			json.put("fans", user.getFans());
			json.put("simpleIntro", user.getSimpledesc());
			jsons.add(json);
		}
		System.out.println(jsons.toString());

		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.print(jsons);
		pw.flush();
		pw.close();
		return SUCCESS;
	}

	/**
	 * 返回符合条件的用户实体
	 * 
	 * @return
	 * @throws Exception
	 */
	public String findPerson() throws Exception {
		/* 设置响应头允许ajax跨域访问 */
		response.setHeader("Access-Control-Allow-Origin", "*");
		/* 星号表示所有的异域请求都可以接受， */
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		String searchName = request.getParameter("person");
		JSONObject json = null;
		System.out.println("进入查询...");

		setUser(userService.findByName(searchName));
		json = new JSONObject();
		json.put("name", user.getUsername());
		json.put("img", user.getAvatarUrl());
		json.put("content", user.getSimpledesc());
		json.put("fans", user.getFans());
		json.put("simpleIntro", user.getSimpledesc());

		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.print(json);
		pw.flush();
		pw.close();
		return SUCCESS;
	}

	/**
	 * 更新关联用户的账号余额信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updateAccount() throws Exception {
		/* 设置响应头允许ajax跨域访问 */
		response.setHeader("Access-Control-Allow-Origin", "*");
		/* 星号表示所有的异域请求都可以接受， */
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		JSONObject jsonn = new JSONObject();
		System.out.println(user.getUsername() + "-");
		user = userService.findByName(user.getUsername());
		if (user.getAccount() > 1) {
			System.out.println(user.getUsername() + "---" + user.getAccount());
			user.setAccount(user.getAccount() - 1);
			userService.updateUser(user);
			jsonn.put("isok", 1);
		} else {
			jsonn.put("isok", 0);
		}
		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.print(jsonn);
		pw.flush();
		pw.close();
		return SUCCESS;
	}

}
