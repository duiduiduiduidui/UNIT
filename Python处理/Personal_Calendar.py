import re
import csv
import re
import pymysql
import json
import pandas as pd
import numpy as np
import random
conn = pymysql.connect(host='127.0.0.1', port=3306, user='root', passwd='68948487', db='UNIT', charset="utf8")  #数据库连接
cursor = conn.cursor()
import sys
'''
本python脚本用来处理个人日历界面需要传输的json串文件
'''
def list_name(keyname, value1, dict1=None):
    dict1 = dict(zip(keyname, value1))
    return dict1
def today_SQL(openid, date):
    '''
       today_mission为用户某天任务列表查询逻辑，查询结果：
       nickname:用户昵称,mission_id:任务id,team_name:队伍名称,mission_name:任务名称,日期（格式：2019-04-22）
       '''
    today_mission = '''
        SELECT
    	u.nickname,
    	m.id as mission_id,
    	t.`name` as team_name,
    	m.`name` as mission_name,
    	CONCAT(m.YEAR,'-',m.MONTH,'-',m.DAY) as 日期
        FROM mission m
        LEFT JOIN team t on m.team = t.id
        LEFT JOIN user u on u.openid = m.member
        WHERE CONCAT(m.YEAR,'-',m.MONTH,'-',m.DAY) = '%s'
        AND m.state = '0'
        AND m.member = '%s'
        ''' % (date, openid)
    return today_mission
def initial_SQL(openid, date):
    '''mission_num_sql 是个人日历每天任务数查询逻辑，查询结果：
    nickname:用户昵称,year，month，day，mission_num：当天任务数量,signal:任务标签（1为已过期，2为已完成未审核，3为未完成未过期）
    '''
    mission_num_sql = '''
    SELECT
	u.nickname,
	COUNT(*) as mission_num,
	YEAR,
	MONTH,
	DAY,
	CASE 
	WHEN CONCAT(m.YEAR,'-',m.MONTH,'-',m.DAY) < CURDATE() then '1'	
	WHEN SUM(m.is_proposed) >= 1 then '2'
	ELSE '3' END AS 'signal'
    FROM mission m
    LEFT JOIN `user` u on u.openid = m.member
    WHERE m.member = '%s'
    AND m.state = '0'
    GROUP BY CONCAT(m.YEAR,'-',m.MONTH,'-',m.DAY)
    ''' % (openid)
    '''
    personal_total_team为选取个人全部队伍集合,查询结果：
    nickname，team_name,team_id,职位:(队长 1 /队员 0 )
    '''
    personal_total_team = '''
    SELECT
	u.nickname,
	t.`name` as team_name,
	t.id as team_id,
	CASE WHEN
	ut.identity = '1' then'1'
	ELSE '0' END AS 职位
    FROM user u
    LEFT JOIN user_team ut on ut.user = u.openid
    LEFT JOIN team t on t.id = ut.team
    WHERE u.openid = '%s'
    ''' % (openid)
    return mission_num_sql,personal_total_team
def initial_run(openid,date):
    # openid = sys.argv[1]
    # date = sys.argv[2]
    file_road = "D:\\working\\software_engineering\\UNIT\\PC_json\\%s_initial.json" % (openid)
    fileObject = open(file_road, 'w', encoding='utf8')
    start_string = '{"total_team":['
    fileObject.write(start_string)
    mission_num_sql, personal_total_team = initial_SQL(openid,date)
    cursor.execute(personal_total_team)
    result = cursor.fetchall()
    key1 = ['nickname','team_name','team_id','version']
    sum = 0
    for line in result:
        a1 = {}
        arr = list_name(key1, line, a1)
        arr = json.dumps(arr, ensure_ascii=False)
        fileObject.write(arr)
        if sum < len(result)-1:
            fileObject.write(",")
            fileObject.write('\n')
            sum += 1
        else:
            sum += 1
    fileObject.write("],")
    fileObject.write('\n')
    fileObject.write('"mission_num":[')
    cursor.execute(mission_num_sql)
    result = cursor.fetchall()
    key2 = ['nickname','mission_num','year','month','day','signal']
    sum = 0
    for line in result:
        a1 = {}
        arr = list_name(key2, line, a1)
        arr = json.dumps(arr, ensure_ascii=False)
        fileObject.write(arr)
        if sum < len(result) - 1:
            fileObject.write(",")
            fileObject.write('\n')
            sum += 1
        else:
            sum += 1
    fileObject.write("],")
    fileObject.write('\n')
    fileObject.write('"today_mission":[')
    today_mission = today_SQL(openid,date)
    cursor.execute(today_mission)
    result = cursor.fetchall()
    key3 = ['nickname', 'mission_id', 'team_name', 'mission_name', 'date']
    sum = 0
    for line in result:
        a1 = {}
        arr = list_name(key3, line, a1)
        arr = json.dumps(arr, ensure_ascii=False)
        fileObject.write(arr)
        if sum < len(result) - 1:
            fileObject.write(",")
            fileObject.write('\n')
            sum += 1
        else:
            sum += 1
    fileObject.write("]}")
    fileObject.close()
    print("个人日历初始化文件写入完成")
def get_Today_run(openid,date):
    file_road = "D:\\working\\software_engineering\\UNIT\\PC_json\\%s_today_mission.json" % (openid)
    fileObject = open(file_road, 'w', encoding='utf8')
    start_string = '{"today_mission":['
    fileObject.write(start_string)
    today_mission = today_SQL(openid, date)
    cursor.execute(today_mission)
    result = cursor.fetchall()
    key3 = ['nickname', 'mission_id', 'team_name', 'mission_name', 'date']
    sum = 0
    for line in result:
        a1 = {}
        arr = list_name(key3, line, a1)
        arr = json.dumps(arr, ensure_ascii=False)
        fileObject.write(arr)
        if sum < len(result) - 1:
            fileObject.write(",")
            fileObject.write('\n')
            sum += 1
        else:
            sum += 1
    fileObject.write("]}")
    fileObject.close()
    print("个人日历",date,"任务写入完成")
if __name__ == "__main__":
    openid = sys.argv[1]
    date = sys.argv[2]
    label = sys.argv[3]
    if label == '1':
        initial_run(openid,date)
    elif label == '2':
        get_Today_run(openid,date)