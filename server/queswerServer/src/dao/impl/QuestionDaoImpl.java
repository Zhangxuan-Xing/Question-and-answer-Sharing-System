package dao.impl;

import java.util.List;

import vo.Question;
import vo.User;
import dao.QuestionDao;

public class QuestionDaoImpl extends BaseDaoHibernate<Question> implements QuestionDao{

	/**
	 * 根据话题信息检索符合条件的问题
	 * @author xxz
	 */
	@Override
	public List<Question> findByTopic(String topic) {
		List<Question> questions = find("select e from Question e where e.content like ?0"
				, topic);
		return questions;
	}
	
	/**
	 * 根据用户实体，检索与ta相关的问题信息集
	 * @author xxz
	 */
	@Override
	public List<Question> findByUser(User user) {
		List<Question> questions = find("select e from Question e where e.user =?0"
				, user);
		return questions;
	}
	
	/**
	 * 根据用户实体和话题信息，检索符合的通知信息集
	 * @author xxz
	 */
	@Override
	public List<Question> findByTopicAndUser(String topic, User user) {
		List<Question> questions = find("select e from Question e where e.content like ?0 and e.user =?1"
				, topic,user);
		return questions;
	}
	
	/**
	 * 根据问题ID，检索对应的通知实体
	 * @author xxz
	 */
	@Override
	public Question findById(int quesid) {
		List<Question> questions = find("select e from Question e where e.id = ?0"
				, quesid);
			if (questions!= null && questions.size() >= 1)
			{
				return questions.get(0);
			}
			return null;
	}
	
	/**
	 * 根据用户实体和问题内容，检索符合的通知实体
	 * @author xxz
	 */
	@Override
	public Question findByContentAndUser(String content, User user) {
		List<Question> questions = find("select e from Question e where e.content = ?0  and e.user = ?1"
				, content,user);
			if (questions!= null && questions.size() >= 1)
			{
				return questions.get(0);
			}
			return null;
	}
	
	/**
	 * 根据创建时间，检索符合的通知信息集
	 * @author xxz
	 */
	@Override
	public List<Question> getAllByTime() {
		List<Question> questions = find("from Question order by is_free desc");
		return questions;
	}

}
