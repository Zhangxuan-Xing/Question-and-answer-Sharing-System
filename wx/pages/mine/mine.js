var app = getApp();
var util = require('../../utils/util.js');

Page({
  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {},
    hasUserInfo: false, //是否已获取用户信息
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    myProfile: [],
    introText: null,
    intro: "请在此处填写您的个人简介",
    fans: 0 //用户粉丝
  },
  //获取用户信息
  myGetUserInfo: function () {
    var that = this;

    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
    } else if (this.data.canIUse) {
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
      }
    } else {
      // 在没有 open-type=getUserInfo 版本的兼容处理
      wx.getUserInfo({
        success: res => {
          app.globalData.userInfo = res.userInfo
          this.setData({
            userInfo: res.userInfo,
            hasUserInfo: true
          })
        }
      })
    }
    console.log(that.data.userInfo)
    /**
   * 如从未登录，进行添加至数据库操作
   */
    wx.request({
      url: 'https://stupidant.cn/queswerServer/addUser',
      data: {
        'user.username': that.data.userInfo.nickName,
        'user.avatarUrl': that.data.userInfo.avatarUrl,
      },
      header: {
        "Content-Type": "applciation/json"
      },
      method: "GET",
      success: function (res) {
        that.setData({
          fans: res.data.fans,
          intro: res.data.intro
        });
      },
    })
  },
  /**
   * 获取用户信息
   */
  getUserInfo: function (e) {
    console.log(e)
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
  },
  /**
   * 找人提问
   */
  toMake: function (e) {
    wx.switchTab({
      url: '../search/search',
    })
  },
  /**
   * 分享秘密
   */
  toShare: function (e) {
    console.log("跳转进入...")
    wx.navigateTo({
      url: '../share/share',
    })
  },
  /**
   * 账户管理
   */
  toMoney: function (e) {
    wx.navigateTo({
      url: '../account/account',
    })
  },
  /**
   * 我的回答记录
   */
  toMyAnswer: function (e) {
    wx.navigateTo({
      url: '../listans/listans',
    })
  },
  /**
   * 充值操作
   */
  toGet: function (e) {
    wx.navigateTo({
      url: '../cash/cash',
    })
  },
  /**
   * 我的提问记录
   */
  toQues: function (e) {
    wx.navigateTo({
      url: '../listques/listques',
    })
  },
  /**
   * 我的分享记录
   */
  toMyShare: function (e) {
    wx.navigateTo({
      url: '../listshares/listshares',
    })
  },
  /**
   * 我的账单记录
   */
  toMyAccount: function (e) {
    wx.navigateTo({
      url: '../listshares/listshares',
    })
  },
  /**
   * 修改个人简介信息
   */
  updateIntro: function (e) {
    var that = this;
    console.log(that.data.introText)
    wx.request({
      url: 'https://stupidant.cn/queswerServer/updateIntro',
      data: {
        'user.username': that.data.userInfo.nickName,
        'intro': that.data.introText
      },
      header: {//请求头
        "Content-Type": "applciation/json"
      },
      method: "GET",
      success: function (res) {
        that.setData({
          intro: res.data.intro
        });
      },
    })
  },
  bindTextAreaBlur: function (e) {
    console.log("1." + e.detail.value)
    this.setData({
      introText: e.detail.value
    })
  },
  onLoad: function () {
    this.myGetUserInfo();
  },
})
