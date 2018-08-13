var util = require('../../utils/util.js')
var app = getApp()
Page({
  data: {
    feed: [], //服务器返回的回答内容信息集
    feed_length: 0
  },
  onLoad: function () {
    console.log('onLoad')
    var that = this
    //调用应用实例的方法获取全局数据
    this.refresh();
  },
  /**
   * 检索有关自己的回答内容
   */
  search: function () {
    var that = this;
    wx.getUserInfo({
      success: function (res) {
        wx.request({
          url: 'https://stupidant.cn/queswerServer/searchMyAnswers',
          data: {
            topic: that.data.topic,
            'user.username': res.userInfo.nickName
          },
          header: {//请求头
            "Content-Type": "applciation/json"
          },
          method: "GET",
          success: function (e) {
            that.setData({
              feed: e.data,
              feed_length: e.data.length
            });
            console.log(e);
          },
        })
      }
    })
  },
  /**
   * 内容获取
   */
  searchInput: function (e) {
    this.setData({
      topic: e.detail.value
    })
  },
  /**
   * 页面刷新，重新请求，内容赋值
   */
  refresh: function () {
    var that = this;
    wx.getUserInfo({
      success: function (res) {
        wx.request({
          url: 'https://stupidant.cn/queswerServer/MyAnswers',
          data: {
            'user.username': res.userInfo.nickName,
          },
          header: {
            "Content-Type": "applciation/json"
          },
          method: "GET",
          success: function (e) {
            that.setData({
              feed: e.data,
              feed_length: e.data.length
            });
            console.log(e);
          },
        })
      }
    })
  },
  bindStory: function (e) {
    var $data = e.currentTarget.dataset
    wx.navigateTo({
      url: '../inform/inform?ansname=' + $data.ansname + '&ansimg=' + $data.ansimg + '&anscontent=' + $data.anscontent + '&anstime=' + $data.anstime + '&quesname=' + $data.quesname + '&quesimg=' + $data.quesimg + '&quescontent=' + $data.quescontent + '&ques_is_free=4' + '&is_answer=' + $data.is_answer + '&ans_liked=' + $data.ans_liked
    })
  },
})
