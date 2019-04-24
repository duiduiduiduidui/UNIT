// pages/signup/signup.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    tip: "",
    array: ['大一', '大二', '大三', '大四', '其他'],

    index: 0,

    defaultlabels: [{
        isSelected: false,
        id: 0,
        name: 'matlab',
      },

      {
        isSelected: false,
        id: 1,
        name: 'python',
      },
      {
        isSelected: false,
        id: 2,
        name: '商业模型',
      },
      {
        isSelected: false,
        id: 3,
        name: '数据挖掘',
      },
      {
        isSelected: false,
        id: 4,
        name: 'PS',
      },
      {
        isSelected: false,
        id: 5,
        name: '视频编辑',
      },

      {
        isSelected: false,
        id: 6,
        name: '前端',
      },
      {
        isSelected: false,
        id: 7,
        name: '数据库',
      },
      {
        isSelected: false,
        id: 8,
        name: 'LeTax',
      },
      {
        isSelected: false,
        id: 9,
        name: 'C++',
      },
      {
        isSelected: false,
        id: 10,
        name: '0',
      },

      {
        isSelected: false,
        id: 11,
        name: '1',
      },
      {
        isSelected: false,
        id: 12,
        name: '2',
      },
      {
        isSelected: false,
        id: 13,
        name: '3',
      },
      {
        isSelected: false,
        id: 14,
        name: '4',
      },
    ],
    addlabel:[],
    userlabel: [],
    userlabel_amount: 0,
    currentLabelIndex: 0,

    nickname: "",
    email: "",
    grade: "",
    userlabels: "",
  },

  //自定义数组删除的方法
  remove: function(array, val) {
    for (var i = 0; i < array.length; i++) {
      if (array[i] == val) {
        array.splice(i, 1);
      }
    }
    return array;
  },

  //输入昵称、邮箱
  nicknameInput: function(e) {
    this.setData({
      nickname: e.detail.value
    })
  },
  emailInput: function(e) {
    this.setData({
      email: e.detail.value
    })
  },

  //选择年级
  changeGrade: function(e) {
    console.log('用户年级发送选择改变，携带值为', e.detail.value)
    this.setData({
      index: e.detail.value
    })
  },

  //标签被选中
  onChooseLabel: function(e) {
    if (this.data.userlabel_amount == 5 && (!this.data.defaultlabels[e.target.id].isSelected)) {
      var that = this
      wx.showModal({
        title: '提示',
        content: '最多只能添加五个标签哦',
      })
    } else {
      //更改选中状态、选中数目
      var isSelected = !this.data.defaultlabels[e.target.id].isSelected
      var id = e.target.id
      var str = "defaultlabels[" + id + "].isSelected"
      var amount = this.data.userlabel_amount
      if (isSelected) {
        //增加一个
        this.setData({
          [str]: isSelected,
          userlabel_amount: amount + 1,
        })
      } else {
        //减少一个
        this.setData({
          [str]: isSelected,
          userlabel_amount: amount - 1,
        })
      }
    }
  },

  //取消选中
  onCancelLabel: function(e) {
    var id = e.target.id
    var str = "defaultlabels[" + id + "].isSelected"
    this.setData({
      [str]: false,
      userlabel_amount: this.data.userlabel_amount - 1,
    })
  },

  //换一组
  changeGroup: function(e) {
    var index = this.data.currentLabelIndex
    var currentindex = index + 1
    if (index != 2) {
      this.setData({
        currentLabelIndex: currentindex
      })
    } else {
      this.setData({
        currentLabelIndex: 0
      })
    }
  },

  //提交表单
  submitinfo: function(e) {
    console.log(this.data.userlabelid)
    console.log(this.data.nickname)
    if (this.data.nickname.length == 0 || this.data.email.length == 0) {
      this.setData({
        tip: '提示：昵称和邮箱不能为空！'
      })
    } else {
      var openid = ""
      var getlabel = ""
      for (let i = 0; i <= 14; i++) {
        if (this.data.defaultlabels[i].isSelected) {
          getlabel = getlabel + this.data.defaultlabels[i].name + " "
        }
      }
      openid = wx.getStorageSync("user")
      var photourl = wx.getStorageSync("userInfo").avatarUrl
      var gradeid = this.data.index
      var grade = this.data.array[gradeid]
      wx.request({
        url: 'https://www.itreep.club/UNIT/WX_storeuserinfoServlet', //本地服务器地址
        data: {
          openid: openid,
          nickname: this.data.nickname,
          email: this.data.email,
          grade: grade,
          label: getlabel,
          photourl: photourl,
        },
        method: 'GET', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT 
        header: {}, // 设置请求的 header 
        success: function(res) {
          console.log(res)
        }
      });
    }
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    var openid = app.globalData.openid
    var resp = ''

    wx.request({
      url: 'https://www.itreep.club/UNIT/WX_signedupServlet', //本地服务器地址
      data: {
        openid: openid,
      },
      method: 'GET', // OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT 
      header: {}, // 设置请求的 header 
      success: function(res) {
        console.log(res)
        resp = res.data
      },
    });
    if (resp == 'no') {
      //navigate到日历
    }
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