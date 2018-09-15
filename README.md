# 基于微信小程序的问答分享系统 【微信小程序 SSH  Java】  

   本系统是基于微信小程序的问答分享系统，采用微信小程序的形式进行开发，后端采用SSH框架进行开发，开发语言为java，用户可以在小程序中进行提问、回答、分享故事、评论、围观以及充值收益等操作，在围观的过程中，用户对于感兴趣的内容可进行打赏与点赞。
   对于本系统中的提问与分享，提供免费与付费两种模式，将知识交互的便捷性和信息娱乐的共享性进行结合；如有第三人进行围观，对于付费内容，需付费等额金币方可查看，金币收益将平分至内容提供者双方账户。
   
   *Tip*：针对现网上小程序主要为PHP和Node.js开发的情况，自己采用Java语言进行开发，希望能给有需要的人一点点力所能及的启示和帮助，也欢迎大家进行批评指正！

### 开发文档

- [官方开发文档](https://mp.weixin.qq.com/debug/wxadoc/dev/)
- [图标致谢 Alibaba国际站图标库](http://iconfont.cn/collections/detail?spm=a313x.7781069.1998910419.de12df413&cid=31)
- [UI致谢 davedavehong](https://github.com/davedavehong/fenda-mock) ：系统的部分页面UI参考了此作者的分享内容，很感谢，另如有侵权，请联系我，本人愿诚恳道歉并立即删除相关内容！

### 系统想法

   近几年，知识付费正在微博、知乎等广大平台成为一种趋势，与分享理念的结合也逐渐成为未来系统的趋势，新模式与新平台的结合，
也会产生新的效果。从单纯的知识付费理念，到付费的娱乐化应用，正成为人们的一种心理诉求和市场趋势。此外，自己对于小程序存有一丝好奇与兴趣，故而尝试
开发此系统。

### 效果预览

- 个人中心  

![个人中心](https://github.com/Zhangxuan-Xing/Question-and-answer-Sharing-System/blob/master/Rendering/Mine.png)

- 问题流动区  

![问题流动区](https://github.com/Zhangxuan-Xing/Question-and-answer-Sharing-System/blob/master/Rendering/quesInfo.png)

- 问答详情  

![问答详情](https://github.com/Zhangxuan-Xing/Question-and-answer-Sharing-System/blob/master/Rendering/quesAndAns.png)

- 分享流动区  

![问答详情](https://github.com/Zhangxuan-Xing/Question-and-answer-Sharing-System/blob/master/Rendering/Look.png)

- 分享详情  
![分享详情](https://github.com/Zhangxuan-Xing/Question-and-answer-Sharing-System/blob/master/Rendering/story.png)

- 搜索用户  
![搜索用户](https://github.com/Zhangxuan-Xing/Question-and-answer-Sharing-System/blob/master/Rendering/person.png)

- 通知栏  
![通知栏](https://github.com/Zhangxuan-Xing/Question-and-answer-Sharing-System/blob/master/Rendering/message.png)

- 分享  
![点赞](https://github.com/Zhangxuan-Xing/Question-and-answer-Sharing-System/blob/master/Rendering/Share.png)

- 提问  
![点赞](https://github.com/Zhangxuan-Xing/Question-and-answer-Sharing-System/blob/master/Rendering/ques.png)

- 点赞  
![点赞](https://github.com/Zhangxuan-Xing/Question-and-answer-Sharing-System/blob/master/Rendering/reward.png)

- 打赏  
![打赏](https://github.com/Zhangxuan-Xing/Question-and-answer-Sharing-System/blob/master/Rendering/give.png)

- 围观  
![围观](https://github.com/Zhangxuan-Xing/Question-and-answer-Sharing-System/blob/master/Rendering/pay.png)

- 历史记录查询  
![围观](https://github.com/Zhangxuan-Xing/Question-and-answer-Sharing-System/blob/master/Rendering/ansHistory.png)

![围观](https://github.com/Zhangxuan-Xing/Question-and-answer-Sharing-System/blob/master/Rendering/shareHistory.png)

![围观](https://github.com/Zhangxuan-Xing/Question-and-answer-Sharing-System/blob/master/Rendering/quesHis.png)





### 数据库设计

- 用户表  
![用户表](https://github.com/Zhangxuan-Xing/Question-and-answer-Sharing-System/blob/master/Rendering/sqlUser.png)

- 问题表  
![问题表](https://github.com/Zhangxuan-Xing/Question-and-answer-Sharing-System/blob/master/Rendering/sqlQues.png)

- 答案表  
![答案表](https://github.com/Zhangxuan-Xing/Question-and-answer-Sharing-System/blob/master/Rendering/sqlAns.png)

- 分享表  
![分享表](https://github.com/Zhangxuan-Xing/Question-and-answer-Sharing-System/blob/master/Rendering/sqlShare.png)

- 通知表  
![通知表](https://github.com/Zhangxuan-Xing/Question-and-answer-Sharing-System/blob/master/Rendering/sqlMess.png)

- 评论表  
![评论表](https://github.com/Zhangxuan-Xing/Question-and-answer-Sharing-System/blob/master/Rendering/sqlCom.png)

- 关注表  
![关注表](https://github.com/Zhangxuan-Xing/Question-and-answer-Sharing-System/blob/master/Rendering/sqlFollow.png)

### 编码注释
   *备注*：本人已对前端与后端的主要代码进行注释，方便大家进行理解与查阅，如下图所示   -->

![注释](https://github.com/Zhangxuan-Xing/Question-and-answer-Sharing-System/blob/master/Rendering/annotation1.png)   

![注释](https://github.com/Zhangxuan-Xing/Question-and-answer-Sharing-System/blob/master/Rendering/annotation2.png)   

![注释](https://github.com/Zhangxuan-Xing/Question-and-answer-Sharing-System/blob/master/Rendering/annotation3.png)
