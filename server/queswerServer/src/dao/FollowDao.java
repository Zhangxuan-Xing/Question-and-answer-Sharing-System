package dao;

import vo.Follow;

public interface FollowDao  extends BaseDao<Follow>{
	
	//根据用户的ID，进行取关操作
	void deleteFollow(Integer followed_userid, Integer follow_userid);
	
	//根据用户的ID，获取关注实体的相关信息
	Follow findFollow(Integer followed_userid, Integer follow_userid);
}
