package vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 关注类
 * 采用注解方式进行创建数据库
 * @author xzx
 */
@Entity
@Table(name = "follow")
public class Follow {
	
	@Id @Column(name="follow_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	//主体用户ID
	@Column(name="user_id",nullable=false)
	private Integer user_id;
	
	//关注用户ID
	@Column(name="followed_user_id",nullable=false)
	private Integer followed_user_id;

	public Follow(Integer id, Integer user_id, Integer followed_user_id) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.followed_user_id = followed_user_id;
	}

	public Follow(Integer user_id, Integer followed_user_id) {
		super();
		this.user_id = user_id;
		this.followed_user_id = followed_user_id;
	}

	public Follow() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getFollowed_user_id() {
		return followed_user_id;
	}

	public void setFollowed_user_id(Integer followed_user_id) {
		this.followed_user_id = followed_user_id;
	}
	
}
