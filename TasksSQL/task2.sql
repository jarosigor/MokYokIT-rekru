WITH attempts AS (
	SELECT 
    	klient_id,
    	status,
    	ROW_NUMBER() OVER (PARTITION BY klient_id ORDER BY kontakt_ts DESC) as row_num,
    	COUNT(*) OVER (PARTITION BY klient_id) as attempts_count
	FROM statuses)
SELECT 
	klient_id,
	status
FROM
	attempts
WHERE attempts_count >= 3 AND row_num = 1;

		