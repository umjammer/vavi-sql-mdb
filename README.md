[![Release](https://jitpack.io/v/umjammer/vavi-sql-mdb.svg)](https://jitpack.io/#umjammer/vavi-sql-mdb)
[![Java CI](https://github.com/umjammer/vavi-sql-mdb/actions/workflows/maven.yml/badge.svg)](https://github.com/umjammer/vavi-sql-mdb/actions/workflows/maven.yml)
[![CodeQL](https://github.com/umjammer/vavi-sql-mdb/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/umjammer/vavi-sql-mdb/actions/workflows/codeql-analysis.yml)
![Java](https://img.shields.io/badge/Java-17-b07219)

# mdbtools

accessing Microsoft Access files directory and it's jdbc implementation (where clause partly works!).

<img src="https://github.com/umjammer/vavi-sql-mdb/assets/493908/3b492010-8725-4387-b1cb-1046b8f3955b" width="120" alt="microsoft access icon"/><sub><a href="https://www.microsoft.com">© Microsoft</a></sub>

## Install

 * [maven](https://jitpack.io/#umjammer/vavi-sql-md)

## Usage

as a jdbc

```java
    DriverManager.registerDriver(new vavi.sql.mdb.jdbc.Driver());
    conn = DriverManager.getConnection("jdbc:mdb:" + "foo/bar.mdb");
```

## References

 * https://github.com/mdbtools/mdbtools
 * https://github.com/ome/ome-mdbtools
 * https://github.com/JSQLParser/JSqlParser

### License

[MDB Tools](https://github.com/mdbtools/mdbtools)

> Files in libmdb, libmdbsql, and libmdbodbc are licensed under LGPL and the utilities and gui program are under the GPL, see COPYING.LIB and COPYING files respectively.

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
* sarg and where clause
