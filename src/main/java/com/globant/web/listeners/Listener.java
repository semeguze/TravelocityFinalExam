package com.globant.web.listeners;

import lombok.extern.slf4j.Slf4j;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * Class to represent the listeners to interact with the execution of the suite
 */
@Slf4j
public class Listener implements ITestListener {

    /**
     * Triggered on finish
     *
     * @param instance : {@link ITestContext}
     */
    @Override
    public void onFinish(ITestContext instance) {
        log.info("[!] ... TEST FINISHED");
    }

    /**
     * Triggered on start
     *
     * @param instance : {@link ITestContext}
     */
    @Override
    public void onStart(ITestContext instance) {
        log.info("[!] Welcome to the final exam by Sebastian Mesa");
    }

    /**
     * Triggered on failed but not common
     *
     * @param instance : {@link ITestResult}
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult instance) {
        log.info("[!] THIS IS NOT COMMON TRY AGAIN");
    }

    /**
     * Triggered on failure
     *
     * @param instance : {@link ITestResult}
     */
    @Override
    public void onTestFailure(ITestResult instance) {
        log.info("[!] THE TEST HAS FAILED. Please review {}", instance.getName());
    }

    /**
     * Triggered on test skipped
     *
     * @param instance : {@link ITestResult}
     */
    @Override
    public void onTestSkipped(ITestResult instance) {
        log.info("[!] TEST SKIPPED");
    }

    /**
     * Triggered on test start
     *
     * @param instance : {@link ITestResult}
     */
    @Override
    public void onTestStart(ITestResult instance) {
        log.info("[!] STARTING TEST {} ...", instance.getName());
    }

    /**
     * Triggered on test success
     *
     * @param instance : {@link ITestResult}
     */
    @Override
    public void onTestSuccess(ITestResult instance) {
        log.info("[!] THE TEST {} HAS PASSED :)", instance.getName());
    }

}