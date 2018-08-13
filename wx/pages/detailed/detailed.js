var util = require("../../utils/util")

var app = getApp()
Page({
  data: {
    url: null,//用户头像Url
    title: null,//标题
    dataBack: [],//服务器查询返回的数据集
    userInfo: {},
    myname: null,
    ismyself: 0,//是否为本人内容
    myimg: null,
    hiddenToast: true,
    hiddenAddLike: true,//点赞
    hiddenDeleLike: true,//取消点赞
    ismine: 0,
    isok: 0,
    name: null,
    ischange: 0,
    userMore: {},//服务器查询数据库所得用户信息
    isfollow: 0,//是否关注
    follow: "关注ta",
    istrade: 0,//是否成功转账
    likeUrl: "../../images/good-filling.png",//调用本地图片
    tradeUrl: "../../images/trade.png",
    isShow: false,//控制emoji表情是否显示
    isLoad: true,
    content: "",//内容
    isLoading: true,
    disabled: true,
    cfBg: false,
    _index: 0,
    emojiChar: "☺-😋-😌-😍-😏-😜-😝-😞-😔-😪-😭-😁-😂-😃-😅-😆-👿-😒-😓-😔-😏-😖-😘-😚-😒-😡-😢-😣-😤-😢-😨-😳-😵-😷-😸-😻-😼-😽-😾-😿-🙊-🙋-🙏-✈-🚇-🚃-🚌-🍄-🍅-🍆-🍇-🍈-🍉-🍑-🍒-🍓-🐔-🐶-🐷-👦-👧-👱-👩-👰-👨-👲-👳-💃-💄-💅-💆-💇-🌹-💑-💓-💘-🚲",
    emoji: [
      "60a", "60b", "60c", "60d", "60f",
      "61b", "61d", "61e", "61f",
      "62a", "62c", "62e",
      "602", "603", "605", "606", "608",
      "612", "613", "614", "615", "616", "618", "619", "620", "621", "623", "624", "625", "627", "629", "633", "635", "637",
      "63a", "63b", "63c", "63d", "63e", "63f",
      "64a", "64b", "64f", "681",
      "68a", "68b", "68c",
      "344", "345", "346", "347", "348", "349", "351", "352", "353",
      "414", "415", "416",
      "466", "467", "468", "469", "470", "471", "472", "473",
      "483", "484", "485", "486", "487", "490", "491", "493", "498", "6b4"
    ],
    comments: [],
    emojis: [],//qq-微信原始表情
    alipayEmoji: [],//支付宝表情
  },
  /**
   * 初次加载数据
   */
  onLoad: function (e) {
    var that = this;
    var em = {}, emChar = that.data.emojiChar.split("-");
    var emojis = []
    that.data.emoji.forEach(function (v, i) {
      em = {
        char: emChar[i],
        emoji: "0x1f" + v
      };
      emojis.push(em)
    });
    that.setData({
      emojis: emojis
    })
    /**
   * 本地数据赋值
   */
    that.setData({
      url: e.url,
      title: e.title,
      name: e.name,
      ismyself: e.ismyself,
      userInfo: app.globalData.userInfo,
    })
    /**
   * 获取用户数据信息
   */
    wx.getUserInfo({
      success: function (res) {
        that.setData({
          myname: res.userInfo.nickName,
          myimg: res.userInfo.avatarUrl
        })
        if (that.data.name == res.userInfo.nickName) {
          that.setData({
            ismine: 1
          })
        }
      }
    })
    /**
   * 向服务器发出请求
   */
    wx.request({
      url: 'https://stupidant.cn/queswerServer/findStory',
      data: {
        'story.user_avatarUrl': e.url,
        'story.title': e.title,
      },
      header: {//请求头
        "Content-Type": "applciation/json"
      },
      method: "GET",
      success: function (res) {
        console.log(res.data);
        if (res.data.isok == 0) {
          wx.showToast({
            title: '金币不足！',
            icon: 'loading',
            duration: 2000,
            success: function () {
              setTimeout(function () {
                wx.switchTab({
                  url: '../story/story'
                })
              }, 2000) //设置延迟时间
            }
          })
        } else {
          that.setData({
            dataBack: res.data
          })
          console.log(res);
          wx.request({
            url: 'https://stupidant.cn/queswerServer/findPerson',
            data: {
              person: res.data.nameInf
            },
            header: {
              "Content-Type": "applciation/json"
            },
            method: "GET",
            success: function (e) {
              that.setData({
                userMore: e.data
              });
            },
          })
          wx.request({
            url: 'https://stupidant.cn/queswerServer/isFollow',
            data: {
              hisname: that.data.dataBack.nameInf,
              myname: that.data.userInfo.nickName
            },
            header: {
              "Content-Type": "applciation/json"
            },
            method: "GET",
            success: function (e) {
              console.log(e.data.isfollow)
              that.setData({
                isfollow: e.data.isfollow
              });
            },
          })
        }
      },
    })
    console.log("story_title:" + that.data.title)
    wx.request({
      url: 'https://stupidant.cn/queswerServer/listComments',
      data: {
        'story.title': that.data.title
      },
      header: {
        "Content-Type": "applciation/json"
      },
      method: "GET",
      success: function (e) {
        that.setData({
          comments: e.data
        })
      },
    })
  },
  /**
   * 添加关注
   */
  bindLiked: function (e) {
    var that = this;
    console.log("...." + that.data.dataBack.nameInf)
    wx.getUserInfo({
      success: function (res) {
        wx.request({
          url: 'https://stupidant.cn/queswerServer/addFollow',
          data: {
            'hisname': that.data.dataBack.nameInf,
            'myname': res.userInfo.nickName,
          },
          header: {
            "Content-Type": "applciation/json"
          },
          method: "GET",
          success: function (e) {
            that.setData({
              hiddenToast: false,
              follow: "已关注"
            })
          },
        })
      }
    })
  },
  /**
   * 吐司提醒
   */
  hiddenToast: function () {
    var that = this;
    that.setData({
      hiddenToast: true
    })
  },
  toastAdd: function () {
    var that = this;
    that.setData({
      hiddenAddLike: true
    })
  },
  toastDelete: function () {
    var that = this;
    that.setData({
      hiddenDeleLike: true
    })
  },
  /**
   * 进行点赞操作
   */
  addLike: function (e) {
    var that = this;
    if (that.data.ischange % 2 == 0) {
      that.setData({
        likeUrl: "../../images/good-filling-focus.png",
        ischange: that.data.ischange + 1
      })
      wx.request({
        url: 'https://stupidant.cn/queswerServer/addLiked',
        data: {
          'answer.content': that.data.anscontent
        },
        header: {//请求头
          "Content-Type": "applciation/json"
        },
        method: "GET",
        success: function (e) {
          that.setData({
            hiddenAddLike: false
          })
        },
      })
    } else {
      that.setData({
        likeUrl: "../../images/good-filling.png",
        ischange: that.data.ischange + 1
      })
      wx.request({
        url: 'https://stupidant.cn/queswerServer/deleteLiked',
        data: {
          'answer.content': that.data.anscontent
        },
        header: {
          "Content-Type": "applciation/json"
        },
        method: "GET",
        success: function (e) {
          that.setData({
            hiddenDeleLike: false
          })
        },
      })
    }
  },
  /**
   * 进行打赏
   */
  addtrade: function (e) {
    var that = this;
    wx.showModal({
      title: '我要打赏 ¥1',
      content: '亲，确认打赏ta的分享吗',
      success: function (res) {
        if (res.confirm) {
          wx.getUserInfo({
            success: function (res) {
              wx.request({
                url: 'https://stupidant.cn/queswerServer/updateAccount',
                data: {
                  'user.username': res.userInfo.nickName,
                },
                header: {//请求头
                  "Content-Type": "applciation/json"
                },
                method: "GET",
                success: function (e) {
                  that.setData({
                    isok: e.data.isok,
                    tradeUrl: "../../images/trade_focus.png"
                  })
                  if (e.data.isok == 0) {
                    wx.showToast({
                      title: '金币不足！',
                      icon: 'loading',
                      duration: 2000,
                      success: function () {
                        setTimeout(function () {

                        }, 1000)
                      }
                    })
                  } else {
                    wx.showToast({
                      title: '打赏成功！',
                      icon: 'success',
                      duration: 2000,
                      success: function () {
                        setTimeout(function () {

                        }, 1000)
                      }
                    })
                  }
                },
              })
            }
          })
        } else if (res.cancel) {
        }
      }
    })
  },
  //点击表情显示隐藏表情盒子
  emojiShowHide: function () {
    this.setData({
      isShow: !this.data.isShow,
      isLoad: false,
      cfBg: !this.data.false
    })
  },
  //表情选择
  emojiChoose: function (e) {
    this.setData({
      content: this.data.content + e.currentTarget.dataset.emoji
    })
  },
  //点击emoji背景遮罩隐藏emoji盒子
  cemojiCfBg: function () {
    this.setData({
      isShow: false,
      cfBg: false
    })
  },
  //发送评论
  send: function () {
    var that = this, conArr = [];
    console.log("1. " + that.data.content)
    wx.getUserInfo({
      success: function (res) {
        wx.request({
          url: 'https://stupidant.cn/queswerServer/addComment',
          data: {
            'user.username': res.userInfo.nickName,
            'story.title': that.data.dataBack.titleInf,
            'comment.content': that.data.content
          },
          header: {
            "Content-Type": "applciation/json"
          },
          method: "GET",
          success: function (e) {
            that.setData({
            })
          },
        })
      }
    })
    //此处设置延迟的原因：点发送时 先执行失去文本焦点 再执行的send 事件
    setTimeout(function () {
      if (that.data.content.trim().length > 0) {
        conArr.push({
          user_img: that.data.myimg,
          user_name: that.data.myname,
          time: util.formatTime(new Date()),
          content: that.data.content
        })
        console.log(conArr)
        that.setData({
          comments: that.data.comments.concat(conArr),
          content: "",//清空文本域值
          isShow: false,
          cfBg: false
        })
      } else {
        that.setData({
          content: ""
        })
      }
    }, 100)
  },
  emojiScroll: function (e) {
    console.log(e)
  },
  //文本域失去焦点时 事件处理
  textAreaBlur: function (e) {
    //获取此时文本域值
    console.log(e.detail.value)
    this.setData({
      content: e.detail.value
    })
  },
  //文本域获得焦点事件处理
  textAreaFocus: function () {
    this.setData({
      isShow: false,
      cfBg: false
    })
  },
})
