var util = require('../../utils/util.js')
var app = getApp()
Page({
  data: {
    feed: [],
    topic: null,//话题内容
    feed_length: 0
  },
  onLoad: function () {
    console.log('onLoad')
    var that = this
    //调用应用实例的方法获取全局数据
    this.refresh();
  },
  /**
   * 检索我发布的秘密内容
   */
  search: function () {
    var that = this;
    wx.showNavigationBarLoading()
    wx.request({
      url: 'https://stupidant.cn/queswerServer/searchMyStories',
      data: {
        topic: this.data.topic
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
          url: 'https://stupidant.cn/queswerServer/MyStories',
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
    console.log("---" + $data.url + $data.title)
    wx.navigateTo({
      url: '../detailed/detailed?url=' + $data.url + '&title=' + $data.title + '&name=' + $data.name + '&ismyself=1'
    })
  },
})
