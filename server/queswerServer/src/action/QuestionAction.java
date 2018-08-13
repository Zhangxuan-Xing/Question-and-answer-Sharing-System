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

import service.AnswerService;
import service.MessageService;
import service.QuestionService;
import service.UserService;
import vo.Answer;
import vo.Message;
import vo.Question;
import vo.User;

import com.opensymphony.xwork2.ActionSupport;

import comparator.MyComparator;
import comparator.MyComparatorByLike;

public class QuestionAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	HttpSession session = request.getSession();

	public Question question;
	public User user;
	public UserService userService;
	public String ques_userName;
	public String is_free;
	public List<Question> questions;
	public AnswerService answerService;
	public MessageService messageService;
	public Message message;

	private QuestionService questionService;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public AnswerService getAnswerService() {
		return answerService;
	}

	public void setAnswerService(AnswerService answerService) {
		this.answerService = answerService;
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public QuestionService getQuestionService() {
		return questionService;
	}

	public void setQuestionService(QuestionService questionService) {
		this.questionService = questionService;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public String getQues_userName() {
		return ques_userName;
	}

	public void setQues_userName(String ques_userName) {
		this.ques_userName = ques_userName;
	}

	public String getIs_free() {
		return is_free;
	}

	public void setIs_free(String is_free) {
		this.is_free = is_free;
	}

	/**
	 * 添加提问记录及信息
	 * 
	 * @return
	 */
	public String addQuest() {
		try {
			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "GET,POST");
			request.setCharacterEncoding("utf-8");

			Date dNow = new Date();
			SimpleDateFormat ft = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
			ques_userName = request.getParameter("ques_userName");
			is_free = request.getParameter("is_free");
			question.setIs_free(Integer.parseInt(is_free));
			User user = userService.findByName(ques_userName);

			question.setCreateTime(ft.format(dNow).toString());
			question.setUser(user);
			questionService.addQuestion(question);

			Question questionTemp = questionService
					.getQuestionByContentAndUser(question.getContent(), user);
			User userTemp = userService
					.findByName(question.getQuesd_username());
			Message message = new Message();
			message.setType(1);
			message.setQuestion(questionTemp.getId());
			message.setFrom_userid(user.getId());
			message.setIsread(1);
			message.setUser(userTemp);
			messageService.addMessage(message);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return SUCCESS;
	}

	/**
	 * 返回已完成回答的 提问 信息集
	 * 
	 * @return
	 * @throws Exception
	 */
	public String listQuestionsByLike() throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");

		JSONArray jso = new JSONArray();
		setQuestions(questionService.getAllQuestionsByTime());
		Answer answer = null;
		for (Question question : questions) {
			JSONObject jsond = new JSONObject();
			answer = answerService.findByQuesId(question);
			if (answer != null) {
				jsond.put("ques_content", question.getContent());
				jsond.put("ques_time", question.getCreateTime().toString());
				jsond.put("ques_is_free", question.getIs_free());
				jsond.put("ques_username", question.getUser().getUsername());
				jsond.put("ques_img", question.getUser().getAvatarUrl());
				jsond.put("ans_time", answer.getCreateTime().toString());
				jsond.put("ans_content", answer.getContent());
				jsond.put("ans_liked", answer.getLikedCount());
				jsond.put("ans_username", answer.getUser().getUsername());
				jsond.put("ans_img", answer.getUser().getAvatarUrl());
				jsond.put("is_answer", 1);
				jso.add(jsond);
			}
		}

		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject jsonObj = null;
		for (int i = 0; i < jso.size(); i++) {
			jsonObj = (JSONObject) jso.get(i);
			list.add(jsonObj);
		}
		Collections.sort(list, new MyComparatorByLike());
		jso.clear();
		for (int i = 0; i < list.size(); i++) {
			jsonObj = list.get(i);
			jso.add(jsonObj);
		}

		System.out.println(jso.toString());

		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.print(jso);
		pw.flush();
		pw.close();
		return SUCCESS;
	}

	/**
	 * 返回符合时间检索的提问信息集
	 * 
	 * @return
	 * @throws Exception
	 */
	public String listQuestionsByTime() throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");

		JSONArray jso = new JSONArray();
		setQuestions(questionService.getAllQuestionsByTime());
		Answer answer = null;
		for (Question question : questions) {
			JSONObject jsond = new JSONObject();
			answer = answerService.findByQuesId(question);
			if (answer != null) {
				jsond.put("ques_content", question.getContent());
				jsond.put("ques_time", question.getCreateTime().toString());
				jsond.put("ques_is_free", question.getIs_free());
				jsond.put("ques_username", question.getUser().getUsername());
				jsond.put("ques_img", question.getUser().getAvatarUrl());
				jsond.put("ans_time", answer.getCreateTime().toString());
				jsond.put("ans_content", answer.getContent());
				jsond.put("ans_liked", answer.getLikedCount());
				jsond.put("ans_username", answer.getUser().getUsername());
				jsond.put("ans_img", answer.getUser().getAvatarUrl());
				jsond.put("is_answer", 1);
				jso.add(jsond);
			}
		}

		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject jsonObj = null;
		for (int i = 0; i < jso.size(); i++) {
			jsonObj = (JSONObject) jso.get(i);
			list.add(jsonObj);
		}
		Collections.sort(list, new MyComparator());
		jso.clear();
		for (int i = 0; i < list.size(); i++) {
			jsonObj = list.get(i);
			jso.add(jsonObj);
		}

		System.out.println(jso.toString());

		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.print(jso);
		pw.flush();
		pw.close();
		return SUCCESS;
	}

	/**
	 * 返回数据库中所有的提问集合
	 * 
	 * @return
	 * @throws Exception
	 */
	public String listQuestions() throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");

		JSONArray jso = new JSONArray();
		setQuestions(questionService.getAllQuestions());
		Answer answer = null;
		for (Question question : questions) {
			JSONObject jsond = new JSONObject();
			answer = answerService.findByQuesId(question);
			if (answer != null) {
				jsond.put("ques_content", question.getContent());
				jsond.put("ques_time", question.getCreateTime().toString());
				jsond.put("ques_is_free", question.getIs_free());
				jsond.put("ques_username", question.getUser().getUsername());
				jsond.put("ques_img", question.getUser().getAvatarUrl());
				jsond.put("ans_time", answer.getCreateTime().toString());
				jsond.put("ans_content", answer.getContent());
				jsond.put("ans_liked", answer.getLikedCount());
				jsond.put("ans_username", answer.getUser().getUsername());
				jsond.put("ans_img", answer.getUser().getAvatarUrl());
				jsond.put("is_answer", 1);
				jso.add(jsond);
			}
		}

		List<JSONObject> list = new ArrayList<JSONObject>();
		JSONObject jsonObj = null;
		for (int i = 0; i < jso.size(); i++) {
			jsonObj = (JSONObject) jso.get(i);
			list.add(jsonObj);
		}
		jso.clear();
		for (int i = list.size() - 1; i >= 0; i--) {
			jsonObj = list.get(i);
			jso.add(jsonObj);
		}

		System.out.println(jso.toString());

		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.print(jso);
		pw.flush();
		pw.close();
		return SUCCESS;
	}

	/**
	 * 返回关联用户的历史提问记录
	 * 
	 * @return
	 * @throws Exception
	 */
	public String MyQuestions() throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");

		JSONArray jso = new JSONArray();
		setQuestions(questionService.getQuestionsByUser(userService
				.findByName(user.getUsername())));
		Answer answer = null;
		for (Question question : questions) {
			JSONObject jsond = new JSONObject();
			answer = answerService.findByQuesId(question);
			if (answer != null) {
				jsond.put("ques_content", question.getContent());
				jsond.put("ques_time", question.getCreateTime().toString());
				jsond.put("ques_is_free", question.getIs_free());
				jsond.put("ques_username", question.getUser().getUsername());
				jsond.put("ques_img", question.getUser().getAvatarUrl());
				jsond.put("ans_time", answer.getCreateTime().toString());
				jsond.put("ans_content", answer.getContent());
				jsond.put("ans_liked", answer.getLikedCount());
				jsond.put("ans_username", answer.getUser().getUsername());
				jsond.put("ans_img", answer.getUser().getAvatarUrl());
				jsond.put("is_answer", 1);
				jso.add(jsond);
			} else {
				jsond.put("ques_content", question.getContent());
				jsond.put("ques_time", question.getCreateTime().toString());
				jsond.put("ques_is_free", question.getIs_free());
				jsond.put("ques_username", question.getUser().getUsername());
				jsond.put("ques_img", question.getUser().getAvatarUrl());
				jsond.put("is_answer", 0);
				jso.add(jsond);
			}
		}
		System.out.println(jso.toString());

		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.print(jso);
		pw.flush();
		pw.close();
		return SUCCESS;
	}

	/**
	 * 返回检索条件下的提问信息集
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchQuestions() throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");

		String topic = request.getParameter("topic");
		JSONArray jso = new JSONArray();
		setQuestions(questionService.getQuestionsByTopic("%" + topic + "%"));
		Answer answer = null;
		for (Question question : questions) {
			JSONObject jsond = new JSONObject();
			answer = answerService.findByQuesId(question);
			if (answer != null) {
				jsond.put("ques_content", question.getContent());
				jsond.put("ques_time", question.getCreateTime().toString());
				jsond.put("ques_is_free", question.getIs_free());
				jsond.put("ques_username", question.getUser().getUsername());
				jsond.put("ques_img", question.getUser().getAvatarUrl());
				jsond.put("ans_time", answer.getCreateTime().toString());
				jsond.put("ans_content", answer.getContent());
				jsond.put("ans_liked", answer.getLikedCount());
				jsond.put("ans_username", answer.getUser().getUsername());
				jsond.put("ans_img", answer.getUser().getAvatarUrl());
				jsond.put("is_answer", 1);
				jso.add(jsond);
			}
		}
		System.out.println(jso.toString());

		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.print(jso);
		pw.flush();
		pw.close();
		return SUCCESS;
	}

	/**
	 * 返回向关联用户提问的信息内容集合
	 * 
	 * @return
	 * @throws Exception
	 */
	public String findQuesToMe() throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");
		JSONObject jsond = new JSONObject();

		Question questionTemp = questionService.getQuestionById(question
				.getId());
		int isanswer = 0;
		Answer answer = answerService.findByQuesId(question);
		if (answer != null) {
			isanswer = 1;
		}
		jsond.put("ques_isanswer", isanswer);
		jsond.put("ques_content", questionTemp.getContent());
		jsond.put("ques_time", questionTemp.getCreateTime().toString());
		jsond.put("ques_is_free", questionTemp.getIs_free());
		jsond.put("ques_username", questionTemp.getUser().getUsername());
		jsond.put("ques_img", questionTemp.getUser().getAvatarUrl());

		response.setContentType("text/html;charset=utf-8");
		PrintWriter pw = response.getWriter();
		pw.print(jsond);
		pw.flush();
		pw.close();
		return SUCCESS;
	}

	/**
	 * 在关联用户的历史记录中，模糊检索，返回符合条件的提问集
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchMyQuestions() throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET,POST");
		request.setCharacterEncoding("utf-8");

		String topic = request.getParameter("topic");
		JSONArray jso = new JSONArray();
		setQuestions(questionService.getQuestionsByTopicAndUser("%" + topic
				+ "%", userService.findByName(user.getUsername())));
		Answer answer = null;
		for (Question question : questions) {
			JSONObject jsond = new JSONObject();
			answer = answerService.findByQuesId(question);
			if (answer != null) {
				jsond.put("ques_content", question.getContent());
				jsond.put("ques_time", question.getCreateTime().toString());
				jsond.put("ques_is_free", question.getIs_free());
				jsond.put("ques_username", question.getUser().getUsername());
				jsond.put("ques_img", question.getUser().getAvatarUrl());
				jsond.put("ans_time", answer.getCreateTime().toString());
				jsond.put("ans_content", answer.getContent());
				jsond.put("ans_liked", answer.getLikedCount());
				jsond.put("ans_username", answer.getUser().getUsername());
				jsond.put("ans_img", answer.getUser().getAvatarUrl());
				jso.add(jsond);
			}
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
