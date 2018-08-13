var util = require('../../utils/util.js')
var app = getApp()
Page({
  data: {
    feed: [],
    navTab: ["筛选排序：", "免费优先", "获赞数"], // 提供排序模式
    currentNavtab: 0, //默认选择的模式index
    isanswer: 0, //是否已经回答
    feed_length: 0
  },
  /**
   * 初次加载页面数据
   */
  onLoad: function () {
    console.log('onLoad')
    var that = this
    //调用应用实例的方法获取全局数据
    this.refresh();
  },
  /**
   * 自主选择标签栏
   */
  switchTab: function (e) {
    var that = this;
    var idx = e.currentTarget.dataset.idx;
    console.log(idx);
    if (idx == 2) {
      wx.request({
        url: 'https://stupidant.cn/queswerServer/listQuestionsByLike',
        data: {},
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
    else if (idx == 1) {
      wx.request({
        url: 'https://stupidant.cn/queswerServer/listQuestionsByTime',
        data: {},
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
    } else if (idx == 0) {
      that.refresh();
    }
    that.setData({
      currentNavtab: e.currentTarget.dataset.idx
    })
  },
  /**
   * 搜索栏，可进行模糊化查询
   */
  search: function () {
    var that = this;
    console.log("..." + this.data.topic)
    wx.request({
      url: 'https://stupidant.cn/queswerServer/searchQuestions',
      data: {
        topic: this.data.topic
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
  },
  searchInput: function (e) {
    this.setData({
      topic: e.detail.value
    })
  },
  /**
   * 用户完成选择后，小程序进行刷新显示
   */
  refresh: function () {
    var that = this;
    wx.request({
      url: 'https://stupidant.cn/queswerServer/listQuestions',
      data: {},
      header: {
        "Content-Type": "applciation/json"
      },
      method: "GET",
      success: function (e) {
        console.log(e)
        that.setData({
          feed: e.data,
          feed_length: e.data.length
        });
        console.log(e);
      },
    })
  },
  /**
   * 跳转页面并传递相关参数
   */
  bindStory: function (e) {
    var $data = e.currentTarget.dataset
    wx.navigateTo({
      url: '../inform/inform?ansname=' + $data.ansname + '&ansimg=' + $data.ansimg + '&anscontent=' + $data.anscontent + '&anstime=' + $data.anstime + '&quesname=' + $data.quesname + '&quesimg=' + $data.quesimg + '&quescontent=' + $data.quescontent + '&ques_is_free=' + $data.ques_is_free + '&is_answer=' + $data.isanswer + '&ans_liked=' + $data.ans_liked
    })
  },
})
