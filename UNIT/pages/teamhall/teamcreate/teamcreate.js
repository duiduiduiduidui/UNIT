// pages/teamhall/teamcreate/teamcreate.js
var util = require('../../../utils/util.js')

Page({

  /**
   * 页面的初始数据
   */
  data: {

    multiArray: [['长期队伍', '短期队伍'], ['数学建模', '商业分析', '大创竞赛']],
    multiIndex: [0, 0],
    tip:"",

    name: "",
    teamtype: "",
    teamtype_small: "",
    captain:"",
    number_max: 0,
    description:"",
    date: "",

  },


  //更改队伍名称
  teamNameInput(e) {
    this.setData({
      name: e.detail.value
    })
  },

  //更改队伍类型  
  teamTypeChange: function (e) {
    console.log('picker发送选择改变，携带值为', e.detail.value)
    this.setData({
      multiIndex: e.detail.value,
      teamtype: this.data.multiArray[0][e.detail.value[0]],
      teamtype_small: this.data.multiArray[1][e.detail.value[1]]
    })
  },

  teamTypeColumnChange: function (e) {
    if (e.detail.column == 0 && e.detail.value == 0) {
      this.setData({
        multiArray: [['长期队伍', '短期队伍'], ['数学建模','计算机大赛', '商业分析', '大创竞赛']]
      })
    } else if (e.detail.column == 0 && e.detail.value == 1) {
      this.setData({
        multiArray: [['长期队伍', '短期队伍'], ['约饭', '约球']]
      })
    }
  },

  //更改队伍人数
  changeTeamScale: function(e) {
    console.log('用户队伍人数发送选择改变，携带值为', e.detail.value)
    this.setData({
      number_max: e.detail.value
    })
  },

  //更改队伍到期日
  changeTeamDate: function(e){
    console.log(e.detail.value)
    this.setData({
      date: e.detail.value
    })
  },

  //输入队伍描述
  teamDescriptionInput(e) {
    this.setData({
      description: e.detail.value
    })
  },

  //提交表单
  submitinfo: function (e) {
    if (this.data.name.length == 0) {
      this.setData({
        tip: '提示：队伍名称不能为空！'
      })
    } else if(this.data.teamtype.length == 0 || this.data.teamtype_small.length == 0){
      this.setData({
        tip: '提示：请选择队伍类型！'
      })
    } else{
      var name = this.data.name
      var teamtype = this.data.teamtype
      var teamtype_small = this.data.teamtype_small
      var openid = wx.getStorageSync("user")
      var number_max = this.data.number_max
      var description = this.data.description
      var date = this.data.date
      wx.request({
        url: 'https://www.itreep.club/UNIT/WX_storeteaminfoServlet', //本地服务器地址
        data: {
          name: name,
          teamtype: teamtype,
          teamtype_small: teamtype_small,
          captain: openid,
          number_max: number_max,
          description: description,
          date: date,
        },
        method: 'GET', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT 
        header: {}, // 设置请求的 header 
        success: function (res) {
          console.log(res)
        }
      });

      wx.showToast({
        title: '创建成功',
        icon: 'success',
        duration: 1500,
        success: function(res){
          wx.navigateBack()
        }
      })

    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    //获取当前时间
    var DATE = util.formatDate(new Date());
    this.setData({
      date: DATE,
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