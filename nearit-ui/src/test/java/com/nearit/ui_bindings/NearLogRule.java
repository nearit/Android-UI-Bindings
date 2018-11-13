package com.nearit.ui_bindings;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import it.near.sdk.logging.NearLog;

public class NearLogRule implements TestRule {
    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                NearLog.setLogger(TestUtils.emptyLogger());
                base.evaluate();
            }
        };
    }
}
