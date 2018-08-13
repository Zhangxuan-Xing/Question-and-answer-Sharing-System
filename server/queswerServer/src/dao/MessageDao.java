package dao;

import java.util.List;

import vo.Message;
import vo.User;

public interface MessageDao extends BaseDao<Message>{
	
	//根据用户实体，检索与ta相关的通知信息集
	List<Message> findByUser(User user);
	
}
