SELECT
	t.`name`,
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
WHERE t.id = '1'
AND m.state = '0'
GROUP BY CONCAT(m.YEAR,'-',m.MONTH,'-',m.DAY)