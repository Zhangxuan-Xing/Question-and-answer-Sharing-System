package service;

import vo.Follow;
import dao.FollowDao;
import dao.UserDao;

public class FollowService {
	
	public FollowDao followDao;
	
	public UserDao userDao;
	
	public FollowDao getFollowDao() {
		return followDao;
	}

	public void setFollowDao(FollowDao followDao) {
		this.followDao = followDao;
	}


	public UserDao getUserDao() {
		return userDao;
	}


	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}


	public void addFollow(Follow follow){
		followDao.save(follow);
	}
	
	public Follow findFollow(Integer followed_id,Integer follow_id){
		return followDao.findFollow(followed_id, follow_id);
	}
	
	public void deleteFollow(Integer followed_userid,Integer follow_userid){
		followDao.deleteFollow(followed_userid, follow_userid);
	}
}
