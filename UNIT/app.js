//app.js
App({
  onLaunch: function() {
    // 展示本地存储能力
    var logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    var that = this
    var user = wx.getStorageSync('user') || {};
    var userInfo = wx.getStorageSync('userInfo') || {};
    if (!user.openid || !user.expires_in || (!userInfo.nickName)) {
      wx.login({
        success: function(res) {
          if (res.code) {
            wx.getUserInfo({
              success: function(res) {
                var objz = {};
                objz.avatarUrl = res.userInfo.avatarUrl;
                objz.nickName = res.userInfo.nickName;
                //console.log(objz);
                wx.setStorageSync('userInfo', objz); //存储userInfo
              }
            });
            var d = that.globalData; //这里存储了appid、secret、token串  
            var l = 'https://api.weixin.qq.com/sns/jscode2session?appid=' + d.appid + '&secret=' + d.secret + '&js_code=' + res.code + '&grant_type=authorization_code';
            wx.request({
              url: 'https://www.itreep.club/UNIT/WX_GetOpenidServlet', //本地服务器地址
              data: {
                appid: d.appid,
                secret: d.secret,
                js_code: res.code
              },
              method: 'GET', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT  
              header: {}, // 设置请求的 header  
              success: function(res) {
                d.openid = res.data.openid;
                wx.setStorageSync('user', d.openid); //存储openid  
                console.log(d.openid)
                console.log(res)
              }
            });
          } else {
            console.log('获取用户登录态失败！' + res.errMsg)
          }
        }
      });
    }
    // 获取用户信息
    wx.getSetting({
      success: res => {
        if (res.authSetting['scope.userInfo']) {
          // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
          wx.getUserInfo({
            success: res => {
              // 可以将 res 发送给后台解码出 unionId
              this.globalData.userInfo = res.userInfo

              // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
              // 所以此处加入 callback 以防止这种情况
              if (this.userInfoReadyCallback) {
                this.userInfoReadyCallback(res)
              }
            }
          })
        }
      }
    })
  },
  globalData: {
    appid: 'wx3984ccd575562869', //appid需自己提供，此处的appid我随机编写
    openid:'',
    secret: 'f4a3f115ae443083b75a6c6bcaaae111',
    userInfo: null
  }
})