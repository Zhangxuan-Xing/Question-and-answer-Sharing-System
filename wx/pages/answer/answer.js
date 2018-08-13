//获取应用实例
var app = getApp();
var util = require('../../utils/util.js');

var app = getApp()
Page({
  /**
   * 页面的初始数据
   */
  data: {
    isFree: '￥1.00',//默认问题价格为1元人民币
    is_free: 0,//问题是否免费
    userInfo: {},
    hiddenToast: true,
    quesInfo: {},//问题信息
    quesid: 1,
  },
  onLoad: function (e) {
    var that = this;
    that.setData({
      userInfo: app.globalData.userInfo,
      quesid: e.quesid //问题ID
    })
    /**
   * 向服务端发出请求
   */
    wx.getUserInfo({
      success: function (res) {
        // console.log('用户信息', res.userInfo)
        wx.request({
          url: 'https://stupidant.cn/queswerServer/findQuesToMe',
          data: {
            'question.id': e.quesid,
          },
          header: {//请求头
            "Content-Type": "applciation/json"
          },
          method: "GET",
          success: function (e) {
            that.setData({
              quesInfo: e.data
            })
            console.log(e)
          },
        })
      }
    })
  },
  /**
   * 确认回复后，向服务器进行处理操作
   */
  bindFormSubmit: function (e) {
    var that = this;
    wx.getUserInfo({
      success: function (res) {
        console.log(that.data.quesid)
        wx.request({
          url: 'https://stupidant.cn/queswerServer/addAnswer',
          data: {
            'answer.content': e.detail.value.answer,
            'question.id': that.data.quesid,
            'user.username': res.userInfo.nickName
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

  toastHidden: function () {
    wx.switchTab({
      url: '../notify/notify',
    })
  },
})
