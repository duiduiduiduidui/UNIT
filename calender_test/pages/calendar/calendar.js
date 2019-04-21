import initCalendar, {
  getSelectedDay,
  setTodoLabels,
  switchView,
} from '../../template/index';

Page({


  /**
   * 页面的初始数据
   */
  data: {
    task: '',
    year: '',
    month: '',
    day: '',
    version:0,//队长的version为0 队员的version为1
    type:1,
    flag: false,
    dailywork: [{
        todoText: '吃饭'
      },
      {
        todoText: '学习'
      },
      {
        todoText: '睡觉'
      }
    ],
    taskData: [{
        year: '2019',
        month: '03',
        day: '15',
        todoText: '睡觉',
        tasknum: '3'
      },
      {
        year: '2019',
        month: '3',
        day: '18',
        todoText: '待办',
        tasknum: '2'
      }, {
        year: '2019',
        month: '4',
        day: '1',
        todoText: '吃饭',
        tasknum: '1'
      },
      {
        year: '2019',
        month: '4',
        day: '3',
        todoText: '吃饭',
        tasknum: '2'
      },

      {
        year: '2019',
        month: '4',
        day: '25',
        todoText: '吃饭',
        tasknum: '3'
      }
    ]
  },

  onLoad: function(options) {
    var time = [{
        day: new Date().getDate(),
        month: new Date().getMonth()+1,
        year: new Date().getUTCFullYear()
      },
      {}
    ]

    this.setData({
      data: time,
      day: new Date().getDate(),
      month: new Date().getDay(),
      year: new Date().getUTCFullYear()
    })
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
        console.log('===============================');
        console.log('当前点击的日期', currentSelect);
        console.log(
          '当前点击的日期是否有事件标记: ',
          currentSelect.hasTodo
        );
        allSelectedDays && console.log('选择的所有日期', allSelectedDays);
        console.log('getSelectedDay方法', getSelectedDay());
        this.setData({
          data: getSelectedDay(),
          year: getSelectedDay()[0].year,
          month: getSelectedDay()[0].month,
          day: getSelectedDay()[0].day,
        })
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
            days: that.data.taskData,
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

  switchCalendar:function(){
    this.data.type++
    this.data.type = this.data.type%2
    this.setData({
      type:this.data.type
    })
  }


})