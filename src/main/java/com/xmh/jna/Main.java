package com.xmh.jna;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

/**
 * .
 *
 * @author 谢明辉
 * @date 2022/1/24
 */

public class Main {
    public static void main(String[] args) {
        args = new String[]{"hello", "jna", "bye"};
        CLibrary.INSTANCE.printf("Hello, World\n");
        for (int i = 0; i < args.length; i++) {
            CLibrary.INSTANCE.printf("Argument %d: %s\n", i, args[i]);
        }
    }

    public interface CLibrary extends Library {
        CLibrary INSTANCE = Native.load((Platform.isWindows() ? "msvcrt" : "c"),
                CLibrary.class);

        void printf(String format, Object... args);
    }

}
