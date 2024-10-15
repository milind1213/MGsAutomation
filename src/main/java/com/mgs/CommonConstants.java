package com.mgs;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

public class CommonConstants {
    public static final String EXECUTION_ENV ="mgs.execution_env";
    public static final String EXECUTION_PLATFORM ="mgs.executionPlatform";
    public static final String HUB_URL ="mgs.hubUrl";
    public static final String MGS_USERNAME = "mgs.username";
    public static final String MGS_PASSWORD = "mgs.password";
    public static final String MGS_WEBURL = "mgs.webUrl";
    public static final String REST_URL = "mgs.restUrl";
    public static final String REST_URL1 = "mgs.restUrl1";
    public static final String MGS_FD_URL = "mgs.webFdUrl";
    public static final String BROWSER = "mgs.browser";
    public static final String RUNMODE_IS_HEADLESS = "mgs.headless";
    public static final String CURRENT_DIRECTORY = System.getProperty("user.dir");
    public static final String COMMON = "common";
    public static final String MGS = "mgs";
    public static final String GMAIL_USERNAME = "common.username";
    public static final String GMAIL_PASSWORD = "common.password";
    public static final String GMAIL_TO ="common.to";
    public static final String GMAIL_FROM ="common.from";
    public static final String GMAIL_SUBJECT ="common.subject";
    public static final String GMAIL_TEXT ="common.text";
    public static final String LOCAL ="local";
    public static final String REMOTE ="remote";
    public static final String PROD ="prod";
    public static final String MGS_SLACK_CHANENEL = "mgs.slackChannel";
    public static final String MGS_SLACK_TOKEN = "mgs.slackToken";



    public static String generateRandomText(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }
    public static String generateRandomEmail(int length) {
        String randomText = RandomStringUtils.randomAlphanumeric(length);
        return  randomText + "@example.com";

    }
    public static int getRandomNumberInRange(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

}
