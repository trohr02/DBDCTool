select *
from all_tables
where owner = 'HR'


select *
from v$database



select
  t.table_name as table_name,
  t.owner as schema_name,
  d.name as database_name,
from
  all_tables t
  cross join v$database d
  
  
select
  tc.owner as schema_name,
  tc.table_name as table_name,
  tc.column_name as column_name,
  tc.data_type as data_type,
  tc.data_length as data_length,
  tc.data_precision as data_precision,
  tc.data_scale as data_scale,
  tc.nullable as nullable,
  tc.column_id as dbInnerId
 from
  all_tab_columns tc; 
  
  
from all_columns
  
  
from
  all_tab_columns


select *
from all_tab_columns
where owner = 'HR'

  
select *
from all_ind_columns
where index_owner = 'HR'


select *
from all_indexes
where owner = 'HR'


select
from 
  all_tab_columns c
  inner join all_cons_columns cc
    on c.table_name = cc.table_name
    and c.column_name = cc.column_name
  inner join all_indexes i
      on i.table_owner = c.owner_name
      and i.table_name = c.table_name
where owner = 'HR'


