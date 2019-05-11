/*
 * Copyright (c) 2018 by Naohide Sano, All rights reserved.
 *
 * Programmed by Naohide Sano
 */

package vavi.apps.mdbtools;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import static com.rainerhahnekamp.sneakythrow.Sneaky.sneaked;

import static org.junit.jupiter.api.Assertions.fail;


/**
 * MdbFileTest.
 *
 * @author <a href="mailto:umjammer@gmail.com">Naohide Sano</a> (umjammer)
 * @version 0.00 2018/02/27 umjammer initial version <br>
 */
public class MdbFileTest {

    @Test
    public void test() {
        fail("Not yet implemented");
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

/* */
