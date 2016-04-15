# cqengine map benchmarking

this code creates cqengine indexed collection with 1M records having different type of indexes on 5 fields, and then benchmark search for 1 sec.
It demonstrate UniqueIndex (searching on unique keys), NavigableIndex (searching equals returning more than one row and for searching records which lies between a given range like 'where amount between 50 to 100')
and SuffixIndex (searching string contains, like "where strategy like '%Credit%'")

p.s.- use 5gig heap as indexes will bloat up the memory

Output
[2016-04-15 16:17:27][INFO ][main][App] - collection populated with: 1000000 recs in: 30097 ms
[2016-04-15 16:17:27][INFO ][main][App] - benchmarking hashmap search by key
[2016-04-15 16:17:28][INFO ][main][App] - performed: 6525921 searches in: 1 secs
[2016-04-15 16:17:28][INFO ][main][App] - benchmarking equals for unique index collection
[2016-04-15 16:17:29][INFO ][main][App] - performed: 2124578 searches in: 1 secs
[2016-04-15 16:17:29][INFO ][main][App] - benchmarking equals on Navigable index for equal with two conditions
[2016-04-15 16:17:30][INFO ][main][App] - performed: 740167 searches in: 1 secs
[2016-04-15 16:17:30][INFO ][main][App] - benchmarking between clause on Navigable index
[2016-04-15 16:17:31][INFO ][main][App] - performed: 2558171 searches in: 1 secs
[2016-04-15 16:17:31][INFO ][main][App] - benchmarking string contains clause on suffix index
[2016-04-15 16:17:32][INFO ][main][App] - performed: 2760917 searches in: 1 secs
