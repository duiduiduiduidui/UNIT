// pages/teamhall/teamhall.js
const app = getApp();
var initdata = function(that) {
  var list = that.data.list
  for (var i = 0; i < list.length; i++) {
    list[i].shows = ""
  }
  that.setData({
    list: list
  })
}
Page({

  /**
   * 页面的初始数据
   */
  data: {
    index: 0,
    multiArray: [
      ['全部', '长期队伍', '短期队伍'],
      ['全部']
    ],
    multiIndex: [0, 0],
    teamtype: "全部",
    teamtype_small: "全部",
    searchid: "",
    delBtnWidth: 185, //删除按钮宽度单位（rpx） 
    // 列表数据
    list: [],
  },

  //更改队伍名称查找
  idSearch(e) {
    this.setData({
      searchid: e.detail.value
    })
    var that = this;
    var resp = [];
    wx.request({
      url: 'https://www.itreep.club/UNIT/WX_getteamServlet', //本地服务器地址
      data: {
        teamtype: this.data.teamtype,
        teamtype_small: this.data.teamtype_small,
        openid: app.globalData.openid,
        info: this.data.searchid
      },
      method: 'GET', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT 
      header: {}, // 设置请求的 header 
      success: function(res) {
        console.log(res.data)
        that.setData({
          list: res.data.reverse(),
          index: res.data.length
        })
      }
    });
  },
  //更改队伍类型查找
  teamTypeChange: function(e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      multiIndex: e.detail.value,
      teamtype: this.data.multiArray[0][e.detail.value[0]],
      teamtype_small: this.data.multiArray[1][e.detail.value[1]]
    })
    var that = this;
    var resp = [];
    wx.request({
      url: 'https://www.itreep.club/UNIT/WX_getteamServlet', //本地服务器地址
      data: {
        teamtype: this.data.teamtype,
        teamtype_small: this.data.teamtype_small,
        openid: app.globalData.openid,
        info: this.data.searchid
      },
      method: 'GET', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT 
      header: {}, // 设置请求的 header 
      success: function(res) {
        console.log(res.data)
        that.setData({
          list: res.data.reverse(),
          index: res.data.length
        })
      }
    });
  },

  teamTypeColumnChange: function(e) {
    if (e.detail.column == 0 && e.detail.value == 0) {
      this.setData({
        multiArray: [
          ['全部', '长期队伍', '短期队伍'],
          ['全部']
        ]
      })
    } else if (e.detail.column == 0 && e.detail.value == 1) {
      this.setData({
        multiArray: [
          ['全部', '长期队伍', '短期队伍'],
          ['全部', '数学建模', '计算机大赛', '商业分析', '大创竞赛']
        ]
      })
    } else if (e.detail.column == 0 && e.detail.value == 2) {
      this.setData({
        multiArray: [
          ['全部', '长期队伍', '短期队伍'],
          ['全部', '约饭', '约球', '约。。']
        ]
      })
    }
  },

  //队伍详情
  teamDetail(e) {
    console.log(e.currentTarget.dataset.id)
    var teamid = e.currentTarget.dataset.id
    wx.navigateTo({
      url: 'teamapply/teamapply?teamid=' + teamid
    })
  },
  //创建队伍
  createTeam: function(e) {
    wx.navigateTo({
      url: 'teamcreate/teamcreate'
    })
  },
  //获取元素自适应后的实际宽度 
  getEleWidth: function(w) {
    var real = 0;
    try {
      var res = wx.getSystemInfoSync().windowWidth;
      //以宽度750px设计稿做宽度的自适应 
      var scale = (750 / 2) / (w / 2);
      // console.log(scale); 
      real = Math.floor(res / scale);
      return real;
    } catch (e) {
      return false;
      // Do something when catch error 
    }
  },
  initEleWidth: function() {
    var delBtnWidth = this.getEleWidth(this.data.delBtnWidth);
    this.setData({
      delBtnWidth: delBtnWidth
    });
  },
  //点击删除按钮事件 
  delItem: function(e) {
    var that = this;
    // 打印出当前选中的index
    console.log(e.currentTarget.dataset.index);
    // 获取到列表数据
    var list = that.data.list;
    // 删除
    list.splice(e.currentTarget.dataset.index, 1);
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    wx.showLoading({
      title: '加载中',
    })
    this.initEleWidth();
    var that = this;
    var resp = [];
    wx.request({
      url: 'https://www.itreep.club/UNIT/WX_getteamServlet', //本地服务器地址
      data: {
        teamtype: "全部",
        teamtype_small: "全部",
        openid: app.globalData.openid,
        info: ""
      },
      method: 'GET', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT 
      header: {}, // 设置请求的 header 
      success: function(res) {
        console.log(res.data)
        that.setData({
          list: res.data.reverse(),
          index: res.data.length
        })
        wx.hideLoading()
      }
    });

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  }

})