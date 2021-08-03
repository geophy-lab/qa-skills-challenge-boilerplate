package com.website.base;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class PropertiesHelper {

    private PropertiesHelper() {
    }

    /**
     * Get property value by a key. Command line and two property files are considered:
     * generic.properties - property file with SUT properties
     * fileName.properties - property file with properties specific to the environment
     * where fileName is defined by a command line parameter env, for example
     * -Denv=stageserver
     *
     * @param key property key
     * @return property value or empty string is there is no property
     */
    public static String getPropertyWrapper(String key) {

        final String environmentPropertyName = "env";
        final String sutPropertyFileName = "generic";

        // Get a property value by its key from the command line
        if (System.getProperty(key) != null)
            return System.getProperty(key);

        // If property is not defined in the command line get it from one of property files

        /* Get environment name from the command line property. It should match the base file name (without extension)
        of the environment property file, e.g.:
        -Denv=devserver for devserver.properties
        -Denv=stageserver for stageserver.properties
         */
        String envPropertyFileName = System.getProperty(environmentPropertyName);

        // Read property files
        ResourceBundle envResourceBundle = ResourceBundle.getBundle(envPropertyFileName);
        ResourceBundle sutResourceBundle = ResourceBundle.getBundle(sutPropertyFileName);

        // Get a property value by its key from the property file
        try {
            // Try to get a property value from the environment property file
            return envResourceBundle.getString(key).trim();
        } catch (MissingResourceException exception1) {
            try {
            /* Try to get a property value from the SUT property file if a property
             has not been found in the environment property file */
                return sutResourceBundle.getString(key).trim();
            } catch (MissingResourceException exception2) {
                // If the property is not defined
                return "";
            }
        }
    }

}
