package service;

import java.util.List;

import vo.Message;
import vo.User;
import dao.MessageDao;
import dao.UserDao;

public class MessageService {
	
	private UserDao userDao;
	
	private MessageDao messageDao;
	
	private User user;
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
	
	public  List<Message>  findByUser(User user){
		return messageDao.findByUser(user);
	}
	
	public void addMessage(Message message){
		messageDao.save(message);
	}
	
}
