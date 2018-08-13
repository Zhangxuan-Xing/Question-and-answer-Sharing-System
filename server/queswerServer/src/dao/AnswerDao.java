package dao;

import java.util.List;

import vo.Answer;
import vo.Question;
import vo.User;

public interface AnswerDao extends BaseDao<Answer>{
	
	//根据问题获取对应的回答
	Answer findByQuesId(Question question);
	
	//根据内容检索对应的回答
	Answer findByContent(String content);
	
	//根据回答ID获取相应信息
	Answer findById(int  ansid);
	
	//根据内容及关联用户检索对应的回答
	Answer findByContentAndUser(String content,User user);
	
	List<Answer> findByUser(User user);
	
}
