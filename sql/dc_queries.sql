select
  tc.owner as schema_name,
  tc.table_name,
  tc.column_name as column_name,
  tc.data_type as data_type,
  tc.data_length as char_length,
  tc.data_precision as data_precision,
  tc.data_scale as data_scale,
  case when tc.nullable = 'Y' then 1 else 0 end  as nullable,
  ACC.POSITION
 from
   all_tab_columns tc
      LEFT OUTER JOIN ALL_CONSTRAINTS AC
        ON AC.OWNER = TC.OWNER
       AND AC.TABLE_NAME = TC.TABLE_NAME
       AND AC.CONSTRAINT_TYPE = 'P'
 LEFT OUTER JOIN ALL_CONS_COLUMNS ACC
        ON ACC.OWNER = AC.OWNER
       AND ACC.TABLE_NAME = AC.TABLE_NAME
       AND ACC.COLUMN_NAME = TC.COLUMN_NAME
       AND ACC.CONSTRAINT_NAME = AC.CONSTRAINT_NAME
where TC.owner not in ('SYS','SYSTEM','DBSNMP','APPQOSSYS','OUTLN','CTXSYS','XDB','FLOWS_FILES','APEX_040000','MDSYS')
ORDER BY  TC.OWNER, TC.TABLE_NAME;