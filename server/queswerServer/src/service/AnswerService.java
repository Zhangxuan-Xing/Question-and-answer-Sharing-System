package service;

import java.util.List;

import vo.Answer;
import vo.Question;
import vo.User;
import dao.AnswerDao;
import dao.QuestionDao;
import dao.UserDao;

public class AnswerService {
	
	private UserDao userDao;
	
	private Question question;
	
	private QuestionDao questionDao;
	
	private AnswerDao answerDao;

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public QuestionDao getQuestionDao() {
		return questionDao;
	}

	public void setQuestionDao(QuestionDao questionDao) {
		this.questionDao = questionDao;
	}
	
	public AnswerDao getAnswerDao() {
		return answerDao;
	}

	public void setAnswerDao(AnswerDao answerDao) {
		this.answerDao = answerDao;
	}

	public Answer findByQuesId(Question question){
		return answerDao.findByQuesId(question);
	}
	
	public Answer findByAnswerId(Integer ansid){
		return answerDao.findById(ansid);
	}
	
	public Answer findByContent(String content){
		return answerDao.findByContent(content);
	}
	
	public void updateAnswer(Answer answer){
		answerDao.update(answer);
	}
	
	public void addAnswer(Answer answer){
		answerDao.save(answer);
	}
	
	public Answer getQuestionByContentAndUser(String content,User user){
		return answerDao.findByContentAndUser(content, user);
	}
	
	public List<Answer> getAllAnswers(){
		return answerDao.findAll(Answer.class);
	}
	
	public List<Answer> getAnswersByUser(User user){
		return answerDao.findByUser(user);
	}
}
