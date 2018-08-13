package dao;

import java.util.List;

import vo.Question;
import vo.User;

public interface QuestionDao extends BaseDao<Question>{
	
	//根据话题信息检索符合条件的问题
	List<Question> findByTopic(String topic);
	
	//根据用户实体和话题信息，检索符合的通知信息集
	List<Question> findByTopicAndUser(String topic,User user);
	
	//根据用户实体，检索与ta相关的问题信息集
	List<Question> findByUser(User user);
	
	//根据创建时间，检索符合的通知信息集
	List<Question> getAllByTime();
	
	//根据问题ID，检索对应的通知实体
    Question findById(int quesid);
    
    //根据用户实体和问题内容，检索符合的通知实体
    Question findByContentAndUser(String content,User user);
    
}
