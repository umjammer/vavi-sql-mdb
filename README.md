[![Release](https://jitpack.io/v/umjammer/vavi-sql-mdb.svg)](https://jitpack.io/#umjammer/vavi-sql-mdb)
[![Actions Status](https://github.com/umjammer/vavi-sql-mdb/workflows/Java%20CI/badge.svg)](https://github.com/umjammer/vavi-sql-mdb/actions)

# mdbtools

access Microsoft Access files directory or by jdbc (where clause partly works!).

## TODO

 * don't forget the purpose, because I made it to read the wmp DB.
  If you just do JDBC, Type 1 is fine.
 * ~~jdbc-nize~~
   * ~~prepared statement~~
   * details (metadata, implementation of operator)
 * memo: cut with 0 at the last?
 * currency: implement in java Currency
 * fuzzing
   * https://github.com/mdbtools/mdbtools/tree/dev/src/fuzz
   * https://github.com/CodeIntelligenceTesting/jazzer
   * https://bazel.build/

## License

[MDB Tools](https://github.com/mdbtools/mdbtools)

> Files in libmdb, libmdbsql, and libmdbodbc are licensed under LGPL and the utilities and gui program are under the GPL, see COPYING.LIB and COPYING files respectively.

## References

 * https://github.com/mdbtools/mdbtools
 * https://github.com/ome/ome-mdbtools
 * https://github.com/JSQLParser/JSqlParser