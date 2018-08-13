var app = getApp();
var util = require('../../utils/util.js');

var app = getApp()
Page({
  /**
   * 页面的初始数据
   */
  data: {
   isFree: '￥1.00', //默认付费人民币一元
   is_free: 0,
   ischange:0,
   ques: '<-我问',// 页面内容设置
   quesq: 'TA答->                                ',// 页面内容设置
   famImg:null,//名人头像
   famName:null,//名人姓名
   userInfo: {},//用户信息
   hiddenToast: true
  },
  /**
   * 登录加载数据
   */
  onLoad: function (e) {
    var that = this;
    that.setData({
      famImg: e.img,
      famName: e.name,
      userInfo: app.globalData.userInfo
    })
    console.log(e)
  },
  
  bindFormSubmit: function (e) {
    var that = this;
    wx.getUserInfo({
      success: function (res) {
        wx.request({
          url: 'https://stupidant.cn/queswerServer/addQuest',
          data: {
             ques_userName: res.userInfo.nickName,
             is_free: that.data.is_free,
            'question.content': e.detail.value.question,
            'question.quesd_username': that.data.famName,
            'question.is_free': that.data.is_free,
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
      }
    })
  },
  /**
   * 免费与付费之间切换（支持重复操作）
   */
  checkChange: function (e) {
    var that = this;
    if (that.data.ischange % 2 == 0) {
      that.setData({
        isFree: '￥0.00',
        is_free: 1,
        ischange: that.data.ischange + 1
      })
    } else {
      that.setData({
        isFree: '￥1.00',
        is_free: 0,
        ischange: that.data.ischange + 1
      })
    }
  },
  toastHidden: function () {
    wx.switchTab({
      url: '../mine/mine',
    })
  },

})
