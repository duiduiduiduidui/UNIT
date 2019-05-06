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
def Wait_For_Comment_SQL(openid):
    sql = '''
    SELECT 
	t.id,
	t.name
    FROM team t
    LEFT JOIN user_team ut on t.id = ut.team
    WHERE t.state = '1'
    AND ut.user = '%s'
    AND ut.commented = '0'
    '''  % (openid)
    return sql
def Wait_For_Confirm_mission(openid):
    sql = '''
    SELECT
	t.captain,
	m.id,
	t.name,
	u.nickname,
	m.name,
	CONCAT(m.YEAR,'-',m.MONTH,'-',m.DAY) as date
    FROM MISSION m
    LEFT JOIN team t on m.team = t.id
    LEFT JOIN user u on m.member = u.openid
    WHERE t.captain = '%s'
    AND m.is_proposed = '1'
    AND m.state = '0'
    '''  % (openid)
    return sql
def Wait_For_Confirm_Team(openid):
    '''
    Group_member用于查取全部队伍中全部的队员信息，用于队长日历界面分配任务
    :param teamid: team表中自增id
    :return: 返回查询逻辑的SQL
    '''
    sql = '''
    SELECT
	u.openid,
	u.nickname,
	t.id,
	t.name,
	a.info
    FROM application a
    LEFT JOIN team t on a.team = t.id
    LEFT JOIN user u on a.user = u.openid
    WHERE t.captain = '%s' 
    ''' % (openid)
    return sql
def run(openid):
    wfc_sql = Wait_For_Comment_SQL(openid)
    wfcm_sql = Wait_For_Confirm_mission(openid)
    wfct_sql = Wait_For_Confirm_Team(openid)
    file_road = "D:\\working\\software_engineering\\UNIT\\Message_json\\%s.json" % (openid)
    fileObject = open(file_road, 'w', encoding='utf8')
    start_string = '{"data":['
    fileObject.write(start_string)
    cursor.execute(wfc_sql)
    result = cursor.fetchall()
    key1 = ['team_id','team_name']
    sum = 0
    for line in result:
        a1 = {}
        arr = list_name(key1, line, a1)
        arr['message_type'] = "队伍互评"
        arr["message_content"] = arr["team_name"] + "对内互评"
        arr["message_signal"] = "1"
        arr = json.dumps(arr, ensure_ascii=False)
        fileObject.write(arr)
        if sum < len(result) - 1:
            fileObject.write(",")
            fileObject.write('\n')
            sum += 1
        else:
            sum += 1
    if len(result) == 0:
        pass
    fileObject.write('\n')
    cursor.execute(wfcm_sql)
    result = cursor.fetchall()
    if len(result) == 0:
        pass
    else:
        fileObject.write(",")
    key2 = ['captain','mission_id','team_name','nickname','mission_name','date']
    sum = 0
    for line in result:
        a1 = {}
        arr = list_name(key2, line, a1)
        arr["message_type"] = "任务确认"
        arr["message_content"] = arr["nickname"] + " 完成任务: " + arr["mission_name"] + " 请确认"
        arr["message_signal"] = "2"
        arr = json.dumps(arr, ensure_ascii=False)
        fileObject.write(arr)
        if sum < len(result) - 1:
            fileObject.write(",")
            fileObject.write('\n')
            sum += 1
        else:
            sum += 1
    if len(result) == 0:
        pass
    fileObject.write('\n')
    cursor.execute(wfct_sql)
    result = cursor.fetchall()
    if len(result) == 0:
        pass
    else:
        fileObject.write(",")
    key3 = ['openid', 'nickname', 'team_id', 'team_name', 'info']
    sum = 0
    for line in result:
        a1 = {}
        arr = list_name(key3, line, a1)
        arr["message_type"] = "申请确认"
        arr["message_content"] = arr["nickname"] + " 申请加入 " + arr["team_name"]
        arr["message_signal"] = "3"
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
    print("消息中心所需数据所需数据写入完成")

if __name__ == "__main__":
    openid = sys.argv[1]
    # team_id = sys.argv[2]
    # date = sys.argv[3]
    # openid = '1'
    run(openid)