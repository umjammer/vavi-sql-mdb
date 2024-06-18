/*
 * Copyright (c) 2018 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.apps.mdbtools;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static com.rainerhahnekamp.sneakythrow.Sneaky.sneaked;


/**
 * MdbFileTest.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2018/02/27 umjammer initial version <br>
 */
public class MdbFileTest {

    static Stream<Arguments> sources() throws IOException {
        return Files.list(Paths.get("src/test/resources/")).filter(p -> p.toString().endsWith(".mdb")).map(Arguments::arguments);
    }

    @ParameterizedTest
    @MethodSource("sources")
    void test0(Path p) throws Exception {
        MdbFile mdb = new MdbFile(p.toString());
//Debug.println(StringUtil.paramStringDeep(mdb, 2));

        mdb.catalogs.forEach(sneaked(catalog -> {
            if (catalog.isUserTable()) {
                Table table = mdb.getTable(catalog.name);
                System.err.println(String.join(", ", table.columns.stream().map(c -> c.type.name()).toArray(String[]::new)));
                System.out.println(String.join(", ", table.columns.stream().map(c -> c.name).toArray(String[]::new)));
                System.out.println();
                for (Object[] values : table.fetchRows()) {
                    for (Object value : values) {
                        System.out.print(value + ", ");
                    }
                    System.out.println();
                }
            }
        }));
    }

    @Test
    void test1() throws Exception {
        Path p = Paths.get(MdbFileTest.class.getResource("/nwind.mdb").toURI());
        MdbFile mdb = new MdbFile(p.toString());

        mdb.catalogs.forEach(sneaked(catalog -> {
            if (catalog.isUserTable()) {
                Table table = mdb.getTable(catalog.name);
                System.out.println(table.name);
                System.err.println(String.join(", ", table.columns.stream().map(c -> c.type.name()).toArray(String[]::new)));
                System.out.println(String.join(", ", table.columns.stream().map(c -> c.name).toArray(String[]::new)));
                System.out.println();
            }
        }));
    }

    @Test
    void test2() throws Exception {
        Path p = Paths.get(MdbFileTest.class.getResource("/ASampleDatabase.accdb").toURI());
        MdbFile mdb = new MdbFile(p.toString());

        mdb.catalogs.forEach(sneaked(catalog -> {
            if (catalog.isUserTable()) {
                Table table = mdb.getTable(catalog.name);
                System.out.println(table.name);
                System.err.println(String.join(", ", table.columns.stream().map(c -> c.type.name()).toArray(String[]::new)));
                System.out.println(String.join(", ", table.columns.stream().map(c -> c.name).toArray(String[]::new)));
                System.out.println();
                for (Object[] values : table.fetchRows()) {
                    for (Object value : values) {
                        System.out.print(value + ", ");
                    }
                    System.out.println();
                }
            }
        }));
    }

    //----

    /** */
    public static void main(String[] args) throws IOException {
        MdbFile mdb = new MdbFile(args[0]);
//Debug.println(StringUtil.paramStringDeep(mdb, 2));

        mdb.catalogs.forEach(sneaked(catalog -> {
            if (catalog.isUserTable()) {
                Table table = mdb.getTable(catalog.name);
                System.out.println(table.name);
                table.columns.forEach(c -> System.err.print(c.type+ ", "));
                System.err.println();
                table.columns.forEach(c -> System.out.print(c.name + ", "));
                System.out.println();
                for (Object[] values : table.fetchRows()) {
                    for (Object value : values) {
                        System.out.print(value + ", ");
                    }
                    System.out.println();
                }
            }
        }));
    }
}
