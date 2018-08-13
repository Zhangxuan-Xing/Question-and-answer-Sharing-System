var app = getApp()
Page({
  /**
   * 页面的初始数据
   * 主要为展示所需数据，许多信息为上个页面所传过来的参数
   */
  data: {
    ansname: "",//回答者姓名
    ansimg: "",//回答者头像
    anscontent: "",//回答内容
    quesimg: "",//提问者头像
    quesname: "",//提问者姓名
    quescontent: "",//提问内容
    anstime: "", //回答时间
    ques_is_free: 1,//是否免费
    is_answer: 0,//是否已回答
    isok: 0,
    ischange: 0,
    userInfo: {},
    userMore: {},
    isfollow: 0,//是否关注
    istrade: 0,//是否打赏
    ans_liked: 0,
    hiddenToast: true,
    hiddenAddLike: true,
    hiddenDeleLike: true,
    likeUrl: "../../images/good-filling.png",
    tradeUrl: "../../images/trade.png",
    follow: "关注ta",
    ismine: 0
  },
  /**
   * 点击相关按钮引发的吐司效果
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
  onLoad: function (e) {
    var that = this;
    that.setData({
      userInfo: app.globalData.userInfo
    })
    console.log(that.data.userInfo)
    if (e.ques_is_free == 0) {
      wx.showModal({
        title: '支付1元ing',
        content: '亲，确认偷看ta的回答吗',
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
                    console.log(e.data.isok + "..*.")
                    that.setData({
                      isok: e.data.isok
                    })
                    if (e.data.isok == 0) {
                      wx.showToast({
                        title: '金币不足！',
                        icon: 'loading',
                        duration: 2000,
                        success: function () {
                          setTimeout(function () {
                            wx.switchTab({
                              url: '../index/index'
                            })
                          }, 2000)
                        }
                      })
                    }
                  },
                })
              }
            })
          } else if (res.cancel) {
            that.setData({
              isok: 0,
            })
          }
        }
      })
    } else {
      that.setData({
        isok: 1
      })
    }
    /**
  * 根据返回内容，重新赋值
  */
    that.setData({
      ansname: e.ansname,
      ansimg: e.ansimg,
      anscontent: e.anscontent,
      quesimg: e.quesimg,
      quesname: e.quesname,
      is_answer: e.is_answer,
      ans_liked: e.ans_liked,
      quescontent: e.quescontent,
      anstime: e.anstime,
      ques_is_free: e.ques_is_free
    })
    if (that.data.ansname == that.data.userInfo.nickName) {
      that.setData({
        ismine: 1
      })
    }
    wx.request({
      url: 'https://stupidant.cn/queswerServer/findPerson',
      data: {
        person: that.data.ansname
      },
      header: {//请求头
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
        hisname: that.data.ansname,
        myname: that.data.userInfo.nickName
      },
      header: {//请求头
        "Content-Type": "applciation/json"
      },
      method: "GET",
      success: function (e) {
        console.log(e)
        that.setData({
          isfollow: e.data.isfollow
        });
      },
    })
  },

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
            ans_liked: that.data.ans_liked + 1,
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
        header: {//请求头
          "Content-Type": "applciation/json"
        },
        method: "GET",
        success: function (e) {
          that.setData({
            ans_liked: that.data.ans_liked - 1,
            hiddenDeleLike: false
          })
        },
      })
    }
  },

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

  bindLiked: function (e) {
    var that = this;
    console.log("...." + that.data.ansname)
    wx.getUserInfo({
      success: function (res) {
        wx.request({
          url: 'https://stupidant.cn/queswerServer/addFollow',
          data: {
            'hisname': that.data.ansname,
            'myname': res.userInfo.nickName,
          },
          header: {//请求头
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
})
