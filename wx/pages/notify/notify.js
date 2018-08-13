var util = require('../../utils/util.js')
Page({
  /**
   * 页面的初始数据
   */
  data: {
    info:[],
    info_length:0
  },
  onLoad: function () {
  },
  onShow: function () {
    var that = this;
    wx.getUserInfo({
      success: function (res) {
        wx.request({
          url: 'https://stupidant.cn/queswerServer/findNotifies',
          data: {
            'user.username': res.userInfo.nickName
          },
          header: {
            "Content-Type": "applciation/json"
          },
          method: "GET",
          success: function (e) {
            that.setData({
              info: e.data,
              info_length: e.data.length
            });
            console.log(e);
          },
        })
      }
    })
  },

  bindNotify: function (e) {
    var $data = e.currentTarget.dataset
    if ($data.type==1){
      wx.navigateTo({
        url: '../answer/answer?quesid=' + $data.quesid
      })
    } else if ($data.type == 2){
      wx.navigateTo({
        url: '../hisAnswer/hisAnswer?ansid=' + $data.ansid
      })
    }
  },
})
