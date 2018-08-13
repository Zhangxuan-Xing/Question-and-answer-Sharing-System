var util = require('../../utils/util.js')
var app = getApp()
Page({
  data: {
    feed: [],
    navTab: ["筛选排序：", "免费优先", "围观数"], //排序模式
    currentNavtab: 0, //所选模式index
    topic: null, //话题
    feed_length: 0
  },

  onLoad: function () {
    this.refresh();
  },
  onShow: function () {
    this.refresh();
  },

  searchInput: function (e) {
    this.setData({
      topic: e.detail.value
    })
  },
  /**
   * 标签切换
   */
  switchTab: function (e) {
    var that = this;
    var idx = e.currentTarget.dataset.idx;
    console.log(idx);
    if (idx == 2) {
      wx.request({
        url: 'https://stupidant.cn/queswerServer/listStoriesByLook',
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
        url: 'https://stupidant.cn/queswerServer/listStoriesByFree',
        data: {},
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
    } else if (idx == 0) {
      that.refresh();
    }
    //及时切换标签index
    that.setData({
      currentNavtab: e.currentTarget.dataset.idx
    })
  },

  /**
   * 检索返回 分享的内容信息
   */
  search: function () {
    var that = this;
    console.log("..." + this.data.topic)
    wx.request({
      url: 'https://stupidant.cn/queswerServer/searchStories',
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

  refresh: function () {
    var that = this;
    wx.request({
      url: 'https://stupidant.cn/queswerServer/listStories',
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
  },
  /**
   * 点击围观
   */
  bindStory: function (e) {
    var $data = e.currentTarget.dataset
    if ($data.isfree == 0) {
      wx.showModal({
        title: '支付1元ing',
        content: '亲，确认偷看ta的故事吗',
        success: function (res) {
          if (res.confirm) {
            wx.navigateTo({
              url: '../detailed/detailed?url=' + $data.url + '&title=' + $data.title + '&name=' + $data.name
            })
          }
        }
      })
    } else {
      wx.navigateTo({
        url: '../detailed/detailed?url=' + $data.url + '&title=' + $data.title + '&name=' + $data.name + '&ismyself=0'
      })
    }
  },
})
