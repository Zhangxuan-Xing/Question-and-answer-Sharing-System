package action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;

import service.AnswerService;
import service.MessageService;
import service.QuestionService;
import service.UserService;
import vo.Answer;
import vo.Message;
import vo.Question;
import vo.User;

import com.opensymphony.xwork2.ActionSupport;

public class AnswerAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	HttpSession session = request.getSession();
	ServletContext application = ServletActionContext.getServletContext();

	private Answer answer;
	private List<Answer> answers;
	private Question question;
	private User user;
	private AnswerService answerService;
	private QuestionService questionService;
	private UserService userService;
	public String answerid;
	private MessageService messageService;

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public String getAnswerid() {
		return answerid;
	}

	public void setAnswerid(String answerid) {
		this.answerid = answerid;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public QuestionService getQuestionService() {
		return questionService;
	}

	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	public AnswerService getAnswerService() {
		return answerService;
	}

	public void setAnswerService(AnswerService answerService) {
		this.answerService = answerService;
	}

	/**
	 * 点赞操作
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addLiked() throws Exception {
		/* 设置响应头允许ajax跨域访问 */
		response.setHeader("Access-Control-Allow-Origin", "*");
		/* 星号表示所有的异域请求都可以接受， */
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		Answer ans = answerService.findByContent(answer.getContent());
		ans.setLikedCount(ans.getLikedCount() + 1);
		answerService.updateAnswer(ans);
		return SUCCESS;
	}

	/**
	 * 添加回答信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addAnswer() throws Exception {
		/* 设置响应头允许ajax跨域访问 */
		response.setHeader("Access-Control-Allow-Origin", "*");
		/* 星号表示所有的异域请求都可以接受， */
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
		answer.setCreateTime(ft.format(dNow));
		answer.setLikedCount(0);
		answer.setQuestion(questionService.getQuestionById(question.getId()));
		answer.setUser(userService.findByName(user.getUsername()));
		answerService.addAnswer(answer);

		Answer answerTemp = answerService.getQuestionByContentAndUser(
				answer.getContent(), answer.getUser());
		Message message = new Message();
		message.setType(2);
		message.setAnswer(answerTemp.getId());
		message.setFrom_userid(answer.getUser().getId());
		message.setIsread(1);
		message.setUser(answer.getUser());
		messageService.addMessage(message);

		return SUCCESS;
	}

	/**
	 * 取消点赞
	 * 
	 * @return
	 * @throws Exception
	 */
	public String deleteLiked() throws Exception {
		/* 设置响应头允许ajax跨域访问 */
		response.setHeader("Access-Control-Allow-Origin", "*");
		/* 星号表示所有的异域请求都可以接受， */
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		Answer ans = answerService.findByContent(answer.getContent());
		ans.setLikedCount(ans.getLikedCount() - 1);
		answerService.updateAnswer(ans);
		return SUCCESS;
	}

	/**
	 * 返回用户围观的答案信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String lookAnswer() throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		JSONObject jsond = new JSONObject();
		int answerid = Integer.parseInt(request.getParameter("answerid"));
		Answer answerTemp = answerService.findByAnswerId(answerid);

		jsond.put("ques_content", answerTemp.getQuestion().getContent());
		jsond.put("ques_img", answerTemp.getQuestion().getUser().getAvatarUrl());
		jsond.put("ques_name", answerTemp.getQuestion().getUser().getUsername());

		jsond.put("ans_content", answerTemp.getContent());
		jsond.put("ans_time", answerTemp.getCreateTime());
		jsond.put("ans_liked", answerTemp.getLikedCount());
		jsond.put("ans_name", answerTemp.getUser().getUsername());
		jsond.put("ans_img", answerTemp.getUser().getAvatarUrl());

		jsond.put("ques_content", answerTemp.getQuestion().getContent());
		jsond.put("ques_is_free", answerTemp.getQuestion().getIs_free());

		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.print(jsond);
		pw.flush();
		pw.close();
		return SUCCESS;
	}

	/**
	 * 用户自己的历史回答集
	 * 
	 * @return
	 * @throws Exception
	 */
	public String MyAnswers() throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");

		JSONArray jso = new JSONArray();
		setAnswers(answerService.getAnswersByUser(userService.findByName(user
				.getUsername())));
		for (Answer answer : answers) {
			JSONObject jsond = new JSONObject();
			jsond.put("ques_content", answer.getQuestion().getContent());
			jsond.put("ques_time", answer.getQuestion().getCreateTime());
			jsond.put("ques_is_free", answer.getQuestion().getIs_free());
			jsond.put("ques_username", answer.getQuestion().getUser()
					.getUsername());
			jsond.put("ques_img", answer.getQuestion().getUser().getAvatarUrl());
			jsond.put("ans_time", answer.getCreateTime());
			jsond.put("ans_content", answer.getContent());
			jsond.put("ans_liked", answer.getLikedCount());
			jsond.put("ans_username", answer.getUser().getUsername());
			jsond.put("ans_img", answer.getUser().getAvatarUrl());
			jsond.put("is_answer", 1);
			jso.add(jsond);
		}
		System.out.println(jso.toString());

		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.print(jso);
		pw.flush();
		pw.close();
		return SUCCESS;
	}

}
