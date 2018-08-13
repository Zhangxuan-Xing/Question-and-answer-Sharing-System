package action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import service.FollowService;
import service.StoryService;
import service.UserService;
import vo.Story;
import vo.User;

import com.opensymphony.xwork2.ActionSupport;

import comparator.MyComparatorByFree;
import comparator.MyComparatorByLook;

public class StoryAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	HttpSession session = request.getSession();
	JSONObject json = null;

	private StoryService storyService;

	private FollowService followService;

	public UserService userService;

	private List<Story> stories;

	public Story story;
	public User user;

	public FollowService getFollowService() {
		return followService;
	}

	public void setFollowService(FollowService followService) {
		this.followService = followService;
	}

	public List<Story> getStories() {
		return stories;
	}

	public void setStories(List<Story> stories) {
		this.stories = stories;
	}

	public UserService getUserService() {
		return userService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public StoryService getStoryService() {
		return storyService;
	}

	public void setStoryService(StoryService storyService) {
		this.storyService = storyService;
	}

	public Story getStory() {
		return story;
	}

	public void setStory(Story story) {
		this.story = story;
	}

	/**
	 * 添加分享内容
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addStory() throws Exception {
		/* 设置响应头允许ajax跨域访问 */
		response.setHeader("Access-Control-Allow-Origin", "*");
		/* 星号表示所有的异域请求都可以接受， */
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");

		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
		User user = userService.findByName(story.getUser_username());

		story.setCreateTime(ft.format(dNow));
		story.setUser(user);
		story.setLookNum(0);

		storyService.addStory(story);
		return SUCCESS;
	}

	/**
	 * 返回免费的分享内容集
	 * 
	 * @return
	 * @throws Exception
	 */
	public String listStoriesByFree() throws Exception {
		/* 设置响应头允许ajax跨域访问 */
		response.setHeader("Access-Control-Allow-Origin", "*");
		/* 星号表示所有的异域请求都可以接受， */
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");

		JSONArray jsons = new JSONArray();
		setStories(storyService.getAllStories());
		for (Story story : stories) {
			json = new JSONObject();
			json.put("name", story.getUser_username());
			json.put("img", story.getUser_avatarUrl());
			json.put("title", story.getTitle());
			json.put("content", story.getContent());
			json.put("lookNum", story.getLookNum());
			json.put("time", story.getCreateTime());
			json.put("isfree", story.getIs_free());
			jsons.add(json);
		}

		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject jsonObj = null;
		for (int i = 0; i < jsons.size(); i++) {
			jsonObj = (JSONObject) jsons.get(i);
			list.add(jsonObj);
		}
		Collections.sort(list, new MyComparatorByFree());
		jsons.clear();
		for (int i = 0; i < list.size(); i++) {
			jsonObj = list.get(i);
			jsons.add(jsonObj);
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
	 * 返回付费的分享内容集
	 * 
	 * @return
	 * @throws Exception
	 */
	public String listStoriesByLook() throws Exception {
		/* 设置响应头允许ajax跨域访问 */
		response.setHeader("Access-Control-Allow-Origin", "*");
		/* 星号表示所有的异域请求都可以接受， */
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");

		JSONArray jsons = new JSONArray();
		setStories(storyService.getAllStories());
		for (Story story : stories) {
			json = new JSONObject();
			json.put("name", story.getUser_username());
			json.put("img", story.getUser_avatarUrl());
			json.put("title", story.getTitle());
			json.put("content", story.getContent());
			json.put("lookNum", story.getLookNum());
			json.put("time", story.getCreateTime());
			json.put("isfree", story.getIs_free());
			jsons.add(json);
		}

		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject jsonObj = null;
		for (int i = 0; i < jsons.size(); i++) {
			jsonObj = (JSONObject) jsons.get(i);
			list.add(jsonObj);
		}
		Collections.sort(list, new MyComparatorByLook());
		jsons.clear();
		for (int i = 0; i < list.size(); i++) {
			jsonObj = list.get(i);
			jsons.add(jsonObj);
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
	 * 返回数据库中所有的分享内容集
	 * 
	 * @return
	 * @throws Exception
	 */
	public String listStories() throws Exception {
		/* 设置响应头允许ajax跨域访问 */
		response.setHeader("Access-Control-Allow-Origin", "*");
		/* 星号表示所有的异域请求都可以接受， */
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");

		JSONArray jsons = new JSONArray();
		setStories(storyService.getAllStories());
		for (Story story : stories) {
			json = new JSONObject();
			json.put("name", story.getUser_username());
			json.put("img", story.getUser_avatarUrl());
			json.put("title", story.getTitle());
			json.put("content", story.getContent());
			json.put("lookNum", story.getLookNum());
			json.put("time", story.getCreateTime());
			json.put("isfree", story.getIs_free());
			jsons.add(json);
		}

		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject jsonObj = null;
		for (int i = 0; i < jsons.size(); i++) {
			jsonObj = (JSONObject) jsons.get(i);
			list.add(jsonObj);
		}
		jsons.clear();
		for (int i = list.size() - 1; i >= 0; i--) {
			jsonObj = list.get(i);
			jsons.add(jsonObj);
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
	 * 返回关联用户的历史分享记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public String MyStories() throws Exception {
		/* 设置响应头允许ajax跨域访问 */
		response.setHeader("Access-Control-Allow-Origin", "*");
		/* 星号表示所有的异域请求都可以接受， */
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");

		JSONArray jsons = new JSONArray();
		setStories(storyService.getStoriesByUser(userService.findByName(user
				.getUsername())));
		for (Story story : stories) {
			json = new JSONObject();
			json.put("name", story.getUser_username());
			json.put("img", story.getUser_avatarUrl());
			json.put("title", story.getTitle());
			json.put("content", story.getContent());
			json.put("lookNum", story.getLookNum());
			json.put("time", story.getCreateTime());
			json.put("isfree", story.getIs_free());
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
	 * 返回用户所点击的内容栏的具体分享信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String findStory() throws Exception {
		/* 设置响应头允许ajax跨域访问 */
		response.setHeader("Access-Control-Allow-Origin", "*");
		/* 星号表示所有的异域请求都可以接受， */
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");

		System.out.println(story.getTitle());
		Story sto = storyService.findStory(story.getTitle());
		sto.setLookNum(sto.getLookNum() + 1);
		// 对查阅数进行更新
		storyService.updateStory(sto);
		JSONObject jsonn = new JSONObject();

		if (sto.getIs_free() == 0) {
			int account = sto.getUser().getAccount();
			if (account > 1) {
				jsonn.put("isok", 1);
				sto.getUser().setAccount(account - 1);
				userService.updateUser(sto.getUser());
			} else {
				jsonn.put("isok", 0);
			}
		}
		jsonn.put("nameInf", sto.getUser_username());
		jsonn.put("imgInf", sto.getUser_avatarUrl());
		jsonn.put("titleInf", sto.getTitle());
		jsonn.put("isfreeInf", sto.getIs_free());
		jsonn.put("contentInf", sto.getContent());
		jsonn.put("lookNumInf", sto.getLookNum());
		jsonn.put("timeInf", sto.getCreateTime());

		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.print(jsonn);
		pw.flush();
		pw.close();
		return null;
	}

	/**
	 * 模糊检索，返回符合检索条件的分享集
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchStories() throws Exception {
		/* 设置响应头允许ajax跨域访问 */
		response.setHeader("Access-Control-Allow-Origin", "*");
		/* 星号表示所有的异域请求都可以接受， */
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");

		String topic = request.getParameter("topic");
		System.out.println("..." + topic);
		JSONArray jsons = new JSONArray();
		setStories(storyService.getStoriesByTopic("%" + topic + "%"));
		for (Story story : stories) {
			json = new JSONObject();
			json.put("name", story.getUser_username());
			json.put("img", story.getUser_avatarUrl());
			json.put("title", story.getTitle());
			json.put("content", story.getContent());
			json.put("lookNum", story.getLookNum());
			json.put("time", story.getCreateTime());
			json.put("isfree", story.getIs_free());
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
}
