package com.website.base;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.website.base.PropertiesHelper.getPropertyWrapper;

public class TstHelper {

    private static final int STACK_TRACE_INDEX_ONE = 1;
    private static final Logger logger = LoggerFactory.getLogger(TstHelper.class);

    private TstHelper() {
    }


    public static int getStackTraceIndexOne() {
        return STACK_TRACE_INDEX_ONE;
    }

    /**
     * Generate a unique name using the given name and the current time stamp
     *
     * @param name base name
     * @return the unique name
     */
    public static String generateUniqueString(String name) {
        final SimpleDateFormat dateTime = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String uniqueName = name + "_" + dateTime.format(new Date());
        String methodName = new Throwable().getStackTrace()[STACK_TRACE_INDEX_ONE].getMethodName();
        logger.debug("{}() - Generated unique name: {}", methodName, uniqueName);
        return uniqueName;
    }

    public static String getPropertyStringWithAssert(String propertyName) {
        return getPropertyWithAssert(propertyName);
    }

    public static int getPropertyIntWithAssert(String propertyName) {
        return Integer.parseInt(getPropertyWithAssert(propertyName));
    }

    private static String getPropertyWithAssert(String propertyName) {
        String propertyValue = getPropertyWrapper(propertyName);
        Assert.assertNotEquals("The property " + propertyName + " is not defined in the property file", "", propertyValue);
        return propertyValue;
    }

}
