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
WHERE m.member = '1'
AND m.state = '0'
GROUP BY CONCAT(m.YEAR,'-',m.MONTH,'-',m.DAY)