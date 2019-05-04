var  cd = require("../../pages/calendar-data.js")

Page({

  /**
   * 页面的初始数据
   */
  data:{
    show: false, //控制下拉列表的显示隐藏，false隐藏、true显示
    selectData: cd.data.total_team, //下拉列表的数据
    index: 0, //选择的下拉列 表下标,
  },

  // 点击下拉显示框
  selectTap() {
    this.setData({
      show: !this.data.show,
    });
  },
  // 点击下拉列表
  optionTap(e) {
    let Index = e.currentTarget.dataset.index; //获取点击的下拉列表的下标
    this.setData({
      index: Index,
      show: !this.data.show
    });
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    
  },
})