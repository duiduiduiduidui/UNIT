//index.js
//获取应用实例
const app = getApp()

Page({

  data: {
    motto: '欢迎来到UNIT',
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo')
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  signup: function (e){
    wx.navigateTo({
      url: '../signup/signup'
    })
  },
  teamhall: function (e) {
    wx.redirectTo({
      url: '../teamhall/teamhall'
    })
  },
  myinfo: function (e) {
    wx.redirectTo({
      url: '../myinfo/myinfo'
    })
  },
  myteam:function(e){
    wx.redirectTo({
      url: '../myteam/myteam'
    })
  },
onShow: function(){

},
  onLoad: function () {
    wx.showLoading({
      title: '加载中',
    })
    if (app.globalData.userInfo) {
      this.setData({
        userInfo: app.globalData.userInfo,
        hasUserInfo: true
      })
      wx.hideLoading()
    } else if (this.data.canIUse){
      // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
      // 所以此处加入 callback 以防止这种情况
      app.userInfoReadyCallback = res => {
        this.setData({
          userInfo: res.userInfo,
          hasUserInfo: true
        })
        wx.hideLoading()
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
  },

  getUserInfo: function(e) {
    app.globalData.userInfo = e.detail.userInfo
    this.setData({
      userInfo: e.detail.userInfo,
      hasUserInfo: true
    })
    wx.hideLoading()
  }

})
