package service;

import java.util.List;

import vo.User;
import dao.AnswerDao;
import dao.MessageDao;
import dao.QuestionDao;
import dao.UserDao;

public class UserService {
	
	private UserDao userDao;
	
	private AnswerDao answerDao;
	
	private QuestionDao questionDao;
	
	private MessageDao messageDao;

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

	public MessageDao getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(MessageDao messageDao) {
		this.messageDao = messageDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void addUser(User user){
		userDao.save(user);
	}

	public void updateUser(User user){
		userDao.update(user);
	}
	
	public User  findByName(String name){
		return userDao.findByName(name);
	}
	
	public User  findById(int userid){
		return userDao.findByUserid(userid);
	}
	
	public  List<User>  findAllByIsFamous(int isfamous){
		return userDao.findByIsFamous(isfamous);
	}
	
	public  List<User>  findLikedByUserid(int userid){
		return userDao.findLikedByUserid(userid);
	}
	
	public  List<User>  findBySearch(String name){
		return userDao.findBySearch(name);
	}
	
	public User  findByImgUrl(String url){
		return userDao.findByImgUrl(url);
	}
	
}
