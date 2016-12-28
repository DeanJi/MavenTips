select * from user_tab_columns 
where table_name in (select table_name from all_tables where tablespace_name = 'TS_XXXX_DATA') 
and upper(column_name) like '%COLUMN_XXX%';


select * from  person s where REGEXP_LIKE(s.name,'[^ -~]');
update person s set s.name=REGEXP_REPLACE(s.name,'[^ -~]','');


SELECT * FROM all_source WHERE OWNER='DB_ACCOUNT' AND UPPER(text) LIKE '%TEXT_XXX%'; -- to find which source calls this 

