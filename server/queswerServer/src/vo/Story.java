package vo;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * 分享类
 * 采用注解方式进行创建数据库
 * @author xzx
 */
@Entity
@Table(name = "story")
public class Story {
	@Id @Column(name="story_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	//分享信息的标题
	@Column(name="title")
	private String title;
	
	//分享信息的内容
	@Column(name="content")
	private String content;
	
	//分享的时间
	@Column(name="createTime")
	private String createTime;
	
	//分享类型是否免费
	@Column(name="is_free")
	private Integer is_free = 0;
	
	//围观人数
	@Column(name="lookNum")
	private Integer lookNum;
	
	//分享者的头像
	@Column(name="user_avatarUrl")
	private String user_avatarUrl;
	
	//分享者的姓名
	@Column(name="user_username",nullable=false)
	private String user_username;
	
	//分享信息的评论
	@OneToMany(targetEntity=Comment.class, mappedBy="story")
	private Set<Comment> comments = new HashSet<>();
	
	//关联用户
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="user_id",nullable=false)
	private User user;

	public Story() {
		super();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public Integer getIs_free() {
		return is_free;
	}

	public void setIs_free(Integer is_free) {
		this.is_free = is_free;
	}

	public Integer getLookNum() {
		return lookNum;
	}

	public void setLookNum(Integer lookNum) {
		this.lookNum = lookNum;
	}

	public String getUser_avatarUrl() {
		return user_avatarUrl;
	}

	public void setUser_avatarUrl(String user_avatarUrl) {
		this.user_avatarUrl = user_avatarUrl;
	}

	public String getUser_username() {
		return user_username;
	}

	public void setUser_username(String user_username) {
		this.user_username = user_username;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

}
