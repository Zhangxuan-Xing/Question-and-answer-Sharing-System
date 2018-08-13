var app = getApp()
Page({
  data: {
    likeUrl: "../../images/good-filling.png",
    tradeUrl: "../../images/trade.png",
    feed: {},//服务端返回的回答信息集
    isok: 0,
    ischange: 0,
    ansid: 0 //回复ID
  },
  /**
   * 初次加载页面数据
   */
  onLoad: function (e) {
    console.log(e)
    var that = this;
    that.setData({
      userInfo: app.globalData.userInfo,
      ansid: e.ansid
    })
    wx.request({
      url: 'https://stupidant.cn/queswerServer/lookAnswer',
      data: {
        'answerid': that.data.ansid
      },
      header: {
        "Content-Type": "applciation/json"
      },
      method: "GET",
      success: function (e) {
        that.setData({
          feed: e.data
        });
        console.log(e);
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
        header: {
          "Content-Type": "applciation/json"
        },
        method: "GET",
        success: function (e) {
          that.setData({
            hiddenToast: false
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
            hiddenToast: false
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
                //下方为我个人服务器，可能已无法正常请求
                url: 'https://stupidant.cn/queswerServer/updateAccount',
                data: {
                  'user.username': res.userInfo.nickName,
                },
                header: {
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
  /**
   * 添加关注
   */
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
              hiddenToast: false
            })
          },
        })
      }
    })
  },
})
