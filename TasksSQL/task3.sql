WITH last_status AS (
    SELECT 
        klient_id,
        status,
        LAG(status) OVER (PARTITION BY klient_id ORDER BY kontakt_ts) AS prev_status,
        TRUNC(kontakt_ts) AS data
    FROM statuses
)
SELECT 
    data,
    COUNT(CASE WHEN status = 'zainteresowany' THEN 1 END) AS sukcesy,
    COUNT(CASE WHEN status = 'niezainteresowany' THEN 1 END) AS utraty,
    COUNT(CASE WHEN status IN ('poczta_głosowa', 'nie_ma_w_domu') THEN 1 END) AS do_ponowienia,
    COUNT(CASE WHEN status = 'niezainteresowany' AND prev_status = 'zainteresowany' THEN 1 END) AS zainteresowani_utraty,
    COUNT(CASE WHEN status = 'zainteresowany' AND prev_status = 'niezainteresowany' THEN 1 END) AS niezainteresowani_sukcesy
FROM last_status
GROUP BY data
ORDER BY data;