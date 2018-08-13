var app = getApp();
var util = require('../../utils/util.js');
Page({
  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {},//用户信息
    hiddenToast: true//吐司内容是否隐藏
  },

/**
   * 登录加载操作
   */
  onLoad: function (options) {
    var that = this;
    that.setData({
      userInfo: app.globalData.userInfo
    })
  },
  /**
   * 提交内容
   */
  bindSubmit: function () {
    var that = this;
    that.setData({
      hiddenToast: false
    })
  },
  /**
   * 完成动作后的吐司变化与页面跳转
   */
  toastHidden: function () {
    var that = this;
    that.setData({
      hiddenToast: true
    })
    wx: wx.switchTab({
      url: '../mine/mine',
    })
  }
})