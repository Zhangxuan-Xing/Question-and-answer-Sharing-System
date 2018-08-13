package action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import service.CommentService;
import service.StoryService;
import service.UserService;
import vo.Comment;
import vo.Story;
import vo.User;

import com.opensymphony.xwork2.ActionSupport;

public class CommentAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	HttpSession session = request.getSession();
	JSONObject json = null;

	private CommentService commentService;
	private UserService userService;
	private StoryService storyService;
	private Comment comment;
	private User user;
	private Story story;
	private List<Comment> comments;

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public CommentService getCommentService() {
		return commentService;
	}

	public void setCommentService(CommentService commentService) {
		this.commentService = commentService;
	}

	public UserService getUserService() {
		return userService;
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

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Story getStory() {
		return story;
	}

	public void setStory(Story story) {
		this.story = story;
	}

	/**
	 * 添加评论
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addComment() throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");

		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");

		User userTemp = userService.findByName(user.getUsername());
		System.out.println("1 " + user.getUsername());
		Story storyTemp = storyService.findStory(story.getTitle());
		System.out.println("2 " + comment.getContent());
		System.out.println("3 " + story.getTitle());
		comment.setCreateTime(ft.format(dNow));
		comment.setStory(storyTemp);
		comment.setUser(userTemp);
		commentService.addComment(comment);

		return SUCCESS;
	}

	/**
	 * 返回评论集合信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String listComments() throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");
		JSONArray jsons = new JSONArray();
		System.out.println("进入评论查询……");
		System.out.println("story-title:" + story.getTitle());
		Story storyTemp = storyService.findStory(story.getTitle());
		setComments(commentService.listComments(storyTemp));
		for (Comment comment : comments) {
			json = new JSONObject();
			json.put("content", comment.getContent());
			json.put("time", comment.getCreateTime());
			json.put("likeNum", comment.getLikeNum());
			json.put("user_img", comment.getUser().getAvatarUrl());
			json.put("user_name", comment.getUser().getUsername());
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
