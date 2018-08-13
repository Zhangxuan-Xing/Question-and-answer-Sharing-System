package service;

import java.util.List;

import vo.Story;
import vo.User;
import dao.StoryDao;
import dao.UserDao;

public class StoryService {
	
	private StoryDao storyDao;
	
	private UserDao userDao;

	public StoryDao getStoryDao() {
		return storyDao;
	}

	public void setStoryDao(StoryDao storyDao) {
		this.storyDao = storyDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public void addStory(Story story){
		storyDao.save(story);
	}
	
	public void updateStory(Story story){
		storyDao.update(story);
	}
	
	public List<Story> getAllStories(){
		return storyDao.findAll(Story.class);
	}
	
	public Story  findStory(String title){
		return storyDao.findStory(title);
	}
	
	public List<Story> getStoriesByTopic(String topic){
		return storyDao.findByTopic(topic);
	}
	
	public List<Story> getStoriesByUser(User user){
		return storyDao.findByUser(user);
	}
}
