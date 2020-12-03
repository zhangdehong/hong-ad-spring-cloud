select table_schema,table_name,column_name,ordinal_position
from information_schema.columns
where table_schema = 'hong-ad-data'
and table_name = 'ad_unit_keyword';