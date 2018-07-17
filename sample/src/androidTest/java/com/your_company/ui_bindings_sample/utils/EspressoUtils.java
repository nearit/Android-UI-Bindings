package com.your_company.ui_bindings_sample.utils;

import java.util.concurrent.TimeUnit;

/**
 * @author Federico Boschini
 */
public class EspressoUtils {

    public static void pause(int seconds) {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(seconds));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
