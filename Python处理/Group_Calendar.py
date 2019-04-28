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
本脚本用于生成给团队日历界面传输所需要的json串，包括团队日历每天任务数查询逻辑    团队某天任务列表查询逻辑
'''
'''
Group_mission_num_SQL用于生成团队日历每天任务数SQL,team_id为team表当中的自增id
'''
def list_name(keyname, value1, dict1=None):
    dict1 = dict(zip(keyname, value1))
    return dict1
def Group_mission_num_SQL(team_id):
    sql = '''
    SELECT
	t.`name` as team_name,
	COUNT(*) as mission_num,
	m.year,
	m.month,
	m.day,
	CASE 
	WHEN CONCAT(m.YEAR,'-',m.MONTH,'-',m.DAY) < CURDATE() then '1'	
	WHEN SUM(m.is_proposed) >= 1 then '2'
	ELSE '3' END AS 'signal'
    FROM mission m
    LEFT JOIN team t on m.team = t.id
    WHERE t.id = '%s'
    AND m.state = '0'
    GROUP BY CONCAT(m.YEAR,'-',m.MONTH,'-',m.DAY)
    '''  % (team_id)
    return sql
def Group_today_mission(team_id, date):
    '''
    Group_today_mission用于生成团队日历某天任务列表查询SQL
    :param team_id:team表中自增id
    :param date:当前日期或者点击的日期
    :return:返回查询逻辑的SQL
    '''
    sql = '''
    SELECT
	m.id as mission_id,
	t.`name` as team_name,
	m.`name` as mission_name,
	u.nickname,
	CONCAT(m.YEAR,'-',m.MONTH,'-',m.DAY) as 日期
    FROM mission m
    LEFT JOIN team t on m.team = t.id
    LEFT JOIN user u on m.member = u.openid
    WHERE CONCAT(m.YEAR,'-',m.MONTH,'-',m.DAY) = '%s'
    AND m.state = '0'
    AND t.id = '%s'
    ''' %(date,team_id)
    return sql
def Gourp_menmeber(teamid):
    '''
    Group_member用于查取全部队伍中全部的队员信息，用于队长日历界面分配任务
    :param teamid: team表中自增id
    :return: 返回查询逻辑的SQL
    '''
    sql = '''
    SELECT U.openid,u.nickname
    FROM user u
    LEFT JOIN user_team ut on u.openid = ut.`user`
    where ut.team = '%s'
    ''' % team_id
    return sql
def run(openid,team_id,date):
    today_mission_sql = Group_today_mission(team_id,date)
    mission_num_sql = Group_mission_num_SQL(team_id)
    group_member_sql = Gourp_menmeber(team_id)
    file_road = "D:\\working\\software_engineering\\UNIT\\GC_json\\%s.json" % (openid)
    fileObject = open(file_road, 'w', encoding='utf8')
    start_string = '{"mission_num":['
    fileObject.write(start_string)
    cursor.execute(mission_num_sql)
    result = cursor.fetchall()
    key1 = ['team_name', 'mission_num', 'year', 'month', 'day', 'signal']
    sum = 0
    for line in result:
        a1 = {}
        arr = list_name(key1, line, a1)
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
    cursor.execute(today_mission_sql)
    result = cursor.fetchall()
    key2 = ['mission_id','team_name','mission_name','nickname','date']
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
    fileObject.write('"group_member":[')
    cursor.execute(group_member_sql)
    result = cursor.fetchall()
    key3 = ['openid','nickname']
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
    print("团队日历所需数据写入完成")

if __name__ == "__main__":
    openid = sys.argv[1]
    team_id = sys.argv[2]
    date = sys.argv[3]
    run(openid,team_id,date)