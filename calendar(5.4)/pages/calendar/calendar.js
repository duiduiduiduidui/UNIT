var cd = require("../../pages/calendar-data.js")
var date = new Date()
import initCalendar, {
  getSelectedDay,
  setTodoLabels,
  clearTodoLabels,
  switchView,
} from '../../template/index';

Page({


  /**
   * 页面的初始数据
   */
  data: {
    task: '',
    day: date.getDate() < 10 ? '0' + date.getDate() : date.getDate(),
    month: (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1),
    year: new Date().getFullYear(),
    days: [],
    type: 1, //1 个人日历 0 团队日历
    version: 0, //0 队员日历 1队长日历 
    flag: false,
    show1: false, //控制下拉列表的显示隐藏，false隐藏、true显示
    show2:false,
    selectData: cd.data.total_team, //下拉列表的数据
    selectPerson:cd.teamData.group_member,
    index1: 0, //选择的下拉列 表下标,
    index2:0,
    dailywork: cd.data.today_mission,
    teamwork: cd.teamData.today_mission
  },

  // 点击下拉显示框
  selectTap1() {
    this.setData({
      show1: !this.data.show1,
    });
  },
  // 点击下拉列表
  optionTap1(e) {
    let Index = e.currentTarget.dataset.index; //获取点击的下拉列表的下标
    this.setData({
      index1: Index,
      show1: !this.data.show1,
      version: cd.data.total_team[Index].version
    });
    this.gc()
    clearTodoLabels()
    this.onLoad()
    this.onShow()
    setTimeout(() => {
      setTodoLabels({
        circle: true,
        days: this.data.days,
      });
      console.log("ctx", this.data.days)
    }, 1000);
  },

  selectTap2() {
    this.setData({
      show2: !this.data.show2,
    });
  },
  // 点击下拉列表
  optionTap2(e) {
    let Index = e.currentTarget.dataset.index; //获取点击的下拉列表的下标
    this.setData({
      index2: Index,
      show1: !this.data.show1
    });
  },

  onLoad: function(options) {
    if(this.data.type==1){
      this.pc()
    }else{
      this.gc()
    }
  },


  onShow: function() {
    var that = this
    initCalendar({
      // multi: true, // 是否开启多选,
      // disablePastDay: true, // 是否禁选过去日期
      // defaultDay: '2018-8-8', // 初始化日历时指定默认选中日期，如：'2018-3-6' 或 '2018-03-06'
      /**
       * 选择日期后执行的事件
       * @param { object } currentSelect 当前点击的日期
       * @param { array } allSelectedDays 选择的所有日期（当mulit为true时，才有allSelectedDays参数）
       */
      afterTapDay: (currentSelect, allSelectedDays) => {
        this.setData({
          data: getSelectedDay(),
          year: getSelectedDay()[0].year, 
          month: getSelectedDay()[0].month < 10 ? '0' + getSelectedDay()[0].month : getSelectedDay()[0].month,
          day: getSelectedDay()[0].day < 10 ? '0' + getSelectedDay()[0].day : getSelectedDay()[0].day,
        })
        if(this.data.type==1){
          this.pc()
          this.setData({
            dailywork: cd.data.today_mission
          })
        }else{
          this.gc()
          this.setData({
            dailywork: cd.teamData.today_mission
          })
        }

        
      },
      whenChangeMonth(current, next) {
        // console.log(current);
        // console.log(next);
      },
      
      /**
       * 日期点击事件（此事件会完全接管点击事件）
       * @param { object } currentSelect 当前点击的日期
       * @param { object } event 日期点击事件对象
       */
      // onTapDay(currentSelect, event) {
      //   console.log(currentSelect);
      //   console.log(event);
      // },
      /**
       * 日历初次渲染完成后触发事件，如设置事件标记
       */
      afterCalendarRender(ctx) {
        
        // 异步请求
        setTimeout(() => {
          setTodoLabels({
            circle: true,
            days: that.data.days,    
          });
        }, 1000);
        // enableArea(['2018-10-7', '2018-10-28']);
      }
    });
  },
  switchView() {
    if (!this.weekMode) {
      switchView('week');
    } else {
      switchView();
    }
  },

  taskTodo: function(e) {
    this.setData({
      task: e.detail.value
    })
  },

  confirm: function(e) {
    console.log(this.data.task)
    wx: wx.request({
      url: '',
      data: {
        year: this.data.year,
        month: this.data.month,
        day: this.data.day,
        task: this.data.task
      },
      header: {},
      method: 'GET',
      dataType: 'json',
      responseType: 'text',
      success: function(res) {},
      fail: function(res) {},
      complete: function(res) {},
    })
  },


  switchCalendar: function() {
    this.data.type++
    this.data.type = this.data.type % 2
    this.setData({
      type:this.data.type
    })
    clearTodoLabels()
    this.onLoad()
    this.onShow()
    setTimeout(() => {
      setTodoLabels({
        circle: true,
        days: this.data.days,
      });
      console.log("ctx", this.data.days)
    }, 1000);
  },

  pc: function() {
    var that = this
    wx.showToast({
      title: '加载中...',
      mask: true,
      icon: 'loading'
    })

    wx.request({
      url: 'https://www.itreep.club/UNIT/PC_Servlet',
      data: {
        openid: "2",
        year: that.data.year,
        month: that.data.month,
        day: that.data.day,
      },
      method: "GET",
      header: {
        'content-type': 'application/json' //默认值
      },
      success: function(res) {
        console.log("pc成功", res)
        cd.data = res.data
        that.setData({
          days: cd.data.mission_num,
          dailywork: cd.data.today_mission,
          selectData:cd.data.total_team
        })
        that.onShow()
        setTimeout(() => {
          setTodoLabels({
            circle: true,
            days: that.data.days,
          });
          console.log("ctx", that.data.days)
        }, 1000);
      },
      fail: function(res) {
        console.log("fail")
      }
    })
  },


  gc: function() {
    var that = this
    wx.showToast({
      title: '加载中...',
      mask: true,
      icon: 'loading'
    })

    wx.request({
      url: 'https://www.itreep.club/UNIT/GC_Servlet',
      data: {
        openid: "2",//app.globaldata.openid
        year: that.data.year,
        month: that.data.month,
        day: that.data.day,
        team_id: that.data.selectData[that.data.index1].team_id,
      },
      method: "GET",
      header: {
        'content-type': 'application/json' //默认值
      },
      success: function(res) {
        console.log("gc成功", res)
        cd.teamData = res.data
        that.setData({
          days: cd.teamData.mission_num,
          dailywork: cd.teamData.today_mission,
          selectPerson:cd.teamData.group_member
        })
      },
      fail: function(res) {
        console.log("fail")
      }
    })
  },

  lc: function(lab,mid) {
    wx.request({
      url: 'https://www.itreep.club/UNIT/Leader_Method_Servlet',
      data: {
        openid: "1",//app.globaldata.openid
        team_id: this.data.selectData[this.data.index1].team_id,
        year: this.data.year,
        month: this.data.month,
        day: this.data.day,
        lable:lab,
        mission_name:this.data.task,
        misssion_id:mid
      },
      method: "GET",
      header: {
        'content-type': 'application/json' //默认值
      },
      success: function(res) {
        console.log("成功", res)
      },
      fail: function(res) {
        console.log("fail")
      }
    })
  },

  insert:function(e){
    this.lc(1,e.currentTarget.dataset.id)
  },

  delete:function(e){
    this.lc(2, e.currentTarget.dataset.id)
  },

  update:function(e){
    this.lc(3, e.currentTarget.dataset.id)
  },

  apply:function(e){
    
  }
})