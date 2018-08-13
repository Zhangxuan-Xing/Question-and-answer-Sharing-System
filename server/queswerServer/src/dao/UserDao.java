package dao;

import java.util.List;

import vo.User;

public interface UserDao extends BaseDao<User>{
	
	//根据姓名信息，检索返回符合条件的用户
	User findByName(String name);
	
	//根据用户头像Url，检索返回符合条件的用户集
	User findByImgUrl(String imgUrl);
	
	//根据是否为名人的标签，检索返回符合条件的用户集
	List<User> findByIsFamous(int isfamous);
	
	//根据关联的用户实体ID，检索ta所关注的用户集
	List<User> findLikedByUserid(int userid);
	
	//根据用户ID，检索返回用户实体
	User findByUserid(int userid);
	
	//根据姓名，模糊检索，返回符合条件的用户集
	List<User> findBySearch(String name);

}
