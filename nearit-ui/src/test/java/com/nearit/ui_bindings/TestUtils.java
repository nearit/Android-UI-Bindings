package com.nearit.ui_bindings;

import it.near.sdk.logging.NearLogger;

/**
 * @author Federico Boschini
 */
public class TestUtils {

    public static NearLogger emptyLogger() {
        return new NearLogger() {
            @Override
            public void v(String tag, String msg) {

            }

            @Override
            public void v(String tag, String msg, Throwable tr) {

            }

            @Override
            public void d(String tag, String msg) {

            }

            @Override
            public void d(String tag, String msg, Throwable tr) {

            }

            @Override
            public void i(String tag, String msg) {

            }

            @Override
            public void i(String tag, String msg, Throwable tr) {

            }

            @Override
            public void w(String tag, String msg) {

            }

            @Override
            public void w(String tag, String msg, Throwable tr) {

            }

            @Override
            public void e(String tag, String msg) {

            }

            @Override
            public void e(String tag, String msg, Throwable tr) {

            }
        };
    }

}
