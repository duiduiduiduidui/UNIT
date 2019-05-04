SELECT
	u.nickname,
	m.id as mission_id,
	t.`name` as team_name,
	m.`name` as mission_name,
	CONCAT(m.YEAR,'-',m.MONTH,'-',m.DAY) as 日期
FROM mission m
LEFT JOIN team t on m.team = t.id
LEFT JOIN user u on u.openid = m.member
WHERE CONCAT(m.YEAR,'-',m.MONTH,'-',m.DAY) = '2019-04-22'
AND m.state = '0'
AND m.member = '1'