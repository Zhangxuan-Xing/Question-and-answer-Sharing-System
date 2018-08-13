package dao.impl;

import java.util.List;

import vo.Message;
import vo.User;
import dao.MessageDao;

public class MessageDaoImpl extends BaseDaoHibernate<Message> implements MessageDao {
	
	/**
	 * 根据用户实体，检索与ta相关的通知信息集
	 * @author xxz
	 */
	@Override
	public List<Message> findByUser(User user) {
		System.out.println("..."+user.getSimpledesc());
		List<Message> messages = find("select e from Message e where e.user = ?0"
				, user);
		return messages;
	}
}
