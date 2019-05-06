// pages/teamhall/teamapply/teamapply.js
const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    self_description:"",
    openid:"",
    teamid:"",
    teaminfo:[],
    capinfo:[],
    memberinfo:[],
    index:0
  },

  //输入留言
  inputSelfDescrip(e){
    this.setData({
      self_description: e.detail.value
    })
  },

  //提交申请
  submitApply(e){

    var openid = this.data.openid
    var team = this.data.teamid
    var info = this.data.self_description
    console.log(info)
    wx.request({
      url: 'https://www.itreep.club/UNIT/WX_applyServlet', //本地服务器地址
      data: {
        openid: openid,
        team: team,
        info: info
      },
      method: 'GET', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT 
      header: {}, // 设置请求的 header 
      success: function (res) {
        console.log(res)
        if(res.data.msg == "fail"){
          wx.showModal({
            title: '申请失败',
            content: '同一队伍最多只能申请三次哦',
            success:function(res){
              if(res.confirm){
                wx.navigateBack()
              }
            }
          })
        }else{
          wx.showToast({
            title: '申请成功',
            icon: 'success',
            duration: 3000,
            success: function (res) {
              wx.navigateBack()
            }
          })
        }
      }
    });
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    wx.showLoading({
      title: '加载中',
    })
    var that = this
    var openid = wx.getStorageSync("user")
    that.setData({
      openid: openid,
      teamid: options.teamid
    })
    wx.request({
      url: 'https://www.itreep.club/UNIT/WX_getteaminfoServlet', //本地服务器地址
      data: {
        id: options.teamid
      },
      method: 'GET', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT 
      header: {}, // 设置请求的 header 
      success: function (res) {
        var teaminfo = res.data[0]
        var capinfo = res.data[1]
        res.data.splice(0,2)
        that.setData({
          teaminfo: teaminfo,
          capinfo: capinfo,
          memberinfo: res.data,
          index: res.data.length
        })
        wx.hideLoading()
      }
    });
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})