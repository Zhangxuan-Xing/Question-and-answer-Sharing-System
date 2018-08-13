package service;

import java.util.List;

import vo.Question;
import vo.User;
import dao.AnswerDao;
import dao.QuestionDao;
import dao.UserDao;

public class QuestionService {
	
	private UserDao userDao;
	
	private QuestionDao questionDao;
	
	private AnswerDao answerDao;

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public AnswerDao getAnswerDao() {
		return answerDao;
	}

	public void setAnswerDao(AnswerDao answerDao) {
		this.answerDao = answerDao;
	}
	
	public QuestionDao getQuestionDao() {
		return questionDao;
	}

	public void setQuestionDao(QuestionDao questionDao) {
		this.questionDao = questionDao;
	}

	public void addQuestion(Question question){
		questionDao.save(question);
	}
	
	public List<Question> getAllQuestions(){
		return questionDao.findAll(Question.class);
	}
	
	public List<Question> getAllQuestionsByTime(){
		return questionDao.findAll(Question.class);
	}
	
	public Question getQuestionById(int id){
		return questionDao.findById(id);
	}
	
	public Question getQuestionByContentAndUser(String content,User user){
		return questionDao.findByContentAndUser(content, user);
	}
	
	
	public List<Question> getQuestionsByTopic(String topic){
		return questionDao.findByTopic(topic);
	}
	
	public List<Question> getQuestionsByTopicAndUser(String topic,User user){
		return questionDao.findByTopicAndUser(topic, user);
	}
	
	public List<Question> getQuestionsByUser(User user){
		return questionDao.findByUser(user);
	}
	
	public List<Question> getQuestionsByTime(){
		return questionDao.getAllByTime();
	}
	
}
