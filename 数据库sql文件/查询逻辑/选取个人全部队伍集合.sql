SELECT
	u.nickname,
	t.`name`,
	t.id as team_id,
	CASE WHEN
	ut.identity = '1' then'队长'
	ELSE '队员' END AS 职位
FROM user u
LEFT JOIN user_team ut on ut.user = u.openid
LEFT JOIN team t on t.id = ut.team
WHERE u.openid = '3'