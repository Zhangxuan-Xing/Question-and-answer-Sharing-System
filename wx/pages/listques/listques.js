var util = require('../../utils/util.js')
var app = getApp()
Page({
  data: {
    feed: [],
    feed_length: 0
  },
  onLoad: function () {
    console.log('onLoad')
    var that = this
    //调用应用实例的方法获取全局数据
    this.refresh();
  },
  search: function () {
    var that = this;
    wx.getUserInfo({
      success: function (res) {
        wx.request({
          url: 'https://stupidant.cn/queswerServer/searchMyQuestions',
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
  searchInput: function (e) {
    this.setData({
      topic: e.detail.value
    })
  },

  refresh: function () {
    var that = this;
    wx.getUserInfo({
      success: function (res) {
        wx.request({
          url: 'https://stupidant.cn/queswerServer/MyQuestions',
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
      url: '../inform/inform?ansname=' + $data.ansname + '&ansimg=' + $data.ansimg + '&anscontent=' + $data.anscontent + '&anstime=' + $data.anstime + '&quesname=' + $data.quesname + '&quesimg=' + $data.quesimg + '&quescontent=' + $data.quescontent + '&ques_is_free=3' + '&is_answer=' + $data.is_answer + '&ans_liked=' + $data.ans_liked 
    })
  },
})
