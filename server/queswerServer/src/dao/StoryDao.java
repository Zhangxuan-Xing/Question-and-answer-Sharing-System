package dao;

import java.util.List;

import vo.Story;
import vo.User;

public interface StoryDao extends BaseDao<Story>{
	
	//根据分享的标题信息检索符合条件的分享实体
	Story findStory(String title);
	
	//根据标题信息，模糊检索，返回符合条件的分享信息集
	List<Story> findByTopic(String topic);
	
	//根据关联的用户实体，检索返回符合条件的分享信息集
	List<Story> findByUser(User user);
	
}
