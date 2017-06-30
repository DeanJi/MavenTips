select * from user_tab_columns 
where table_name in (select table_name from all_tables where tablespace_name = 'TS_XXXX_DATA') 
and upper(column_name) like '%COLUMN_XXX%'; -- to find which tables contain column_name like COLUMN_XXX


select * from  person s where REGEXP_LIKE(s.name,'[^ -~]'); -- to find the records whose name contain special character
update person s set s.name=REGEXP_REPLACE(s.name,'[^ -~]','');  -- to replace special character with blank(remove special character)


SELECT * FROM all_source WHERE OWNER='DB_ACCOUNT' AND UPPER(text) LIKE '%TEXT_XXX%'; -- to find which source calls this 

SELECT SYS_CONTEXT ('USERENV', 'SERVER_HOST') FROM DUAL;  -- to show the DB server host name


----------------------------------------To make the tables read only/write start--------------------------------------------------------------

SET SERVEROUTPUT ON

BEGIN
	
   FOR results in (SELECT * FROM USER_TABLES WHERE READ_ONLY='NO')
   --FOR results in (SELECT * FROM USER_TABLES WHERE READ_ONLY='YES')

   LOOP

   		EXECUTE IMMEDIATE 'ALTER TABLE ' || results.TABLE_NAME ||  ' READ ONLY ';
			--EXECUTE IMMEDIATE 'ALTER TABLE ' || results.TABLE_NAME ||  ' READ WRITE ';
   END LOOP;

END;


-----------------------------------------To make the tables read only/write end----------------------------------------------------------------



----------------------------------------To clean up the tables start--------------------------------------------------------------

select distinct OBJECT_TYPE, count(*) from  user_objects group by OBJECT_TYPE order by OBJECT_TYPE; -- Check how many DB objects before cleaning up


create table temp_drop_obj(query) as
select ' drop ' || object_type || ' ' || object_name
|| decode(object_type,
'CLUSTER', ' including tables cascade constraints ',
'TABLE', ' cascade constraints ',
' ')
from user_objects where object_name<>'TEMP_DROP_OBJ' and object_type in ('CLUSTER', 'TABLE', 'VIEW', 'SEQUENCE', 'SYNONYM','FUNCTION','PROCEDURE', 'PACKAGE', 'TYPE', 'JAVA CLASS', 'JAVA SOURCE', 'LOB');



SET SERVEROUTPUT ON

begin
	
   for sqry in (select query from temp_drop_obj order by query asc)

   loop

   		dbms_output.put_line('query:'|| sqry.query);

      BEGIN
      		execute immediate sqry.query ;
          EXCEPTION 
          WHEN OTHERS THEN
          DBMS_OUTPUT.PUT_LINE('ERROR');
      END;

   end loop;

end;



purge recyclebin;



select distinct OBJECT_TYPE, count(*) from  user_objects group by OBJECT_TYPE order by OBJECT_TYPE; -- Check how many DB objects after cleaning up


-----------------------------------------To clean up the tables end----------------------------------------------------------------