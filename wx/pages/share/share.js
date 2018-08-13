var app = getApp();
var util = require('../../utils/util.js');

var app = getApp()
Page({
  /**
   * 关注用户情况
   */
  data: {
    isFree:'￥1.00',
    is_free:0,
    ischange:0,
    userInfo: {},
    hiddenToast: true
  },
  onLoad: function (e) {
    var that = this;
      that.setData({
        userInfo: app.globalData.userInfo
      })
    console.log(e)
  },
  
  /**
   * 完成分享内容，向服务器发送数据
   */
  bindFormSubmit: function (e) {
    var that = this;
      wx.getUserInfo({
        success: function (res) {
          wx.request({
            url: 'https://stupidant.cn/queswerServer/addStory',
            data: {
              'story.content': e.detail.value.story_share,
              'story.title': e.detail.value.story_title,
              'story.is_free': that.data.is_free,
              'story.user_username': res.userInfo.nickName,
              'story.user_avatarUrl': res.userInfo.avatarUrl,
            },
            header: {//请求头
              "Content-Type": "applciation/json"
            },
            method: "GET",
            success: function (e) {
              that.setData({
                hiddenToast:false
              })
            },
          })
        }
      })
  },

  toastHidden: function () {
    wx.switchTab({
      url: '../mine/mine',
    })
  },
  /**
   * 是否付费进行切换（支持重复操作）
   */
  checkChange: function (e) {
    var that = this;
    if (that.data.ischange%2==0){
      that.setData({
        isFree: '￥0.00',
        is_free: 1,
        ischange: that.data.ischange + 1
      })
    }else{
      that.setData({
        isFree: '￥1.00',
        is_free: 0,
        ischange: that.data.ischange + 1
      })
    }
  },
})
