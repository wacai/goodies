package com.wacai.goodies.properties;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * A Simple Message Source is a better alternative for plain properties file which can ONLY read in ASCII-encoded messages, which is not friendly for editing and maintainence.
 * With SimpleMessageSource, you can add any charset characters in the properties file as long as you give proper encoding hint when initializing it. Most of the time, default utf8 encoding is good to go.
 *
 * @author yunshi@wacai.com
 * @since 2014-12-13
 */
public class SimpleMessageSource {

    protected ResourceBundleControlUniversal control = null;

    protected ResourceBundle messageSource = null;

    public SimpleMessageSource(String baseName) {
        this(baseName, "properties");
    }

    public SimpleMessageSource(String baseName, String fileSuffix) {
        this(baseName, fileSuffix, null);
    }

    public SimpleMessageSource(String baseName, String fileSuffix, String encoding) {
        validate(baseName, "baseName");
        validate(fileSuffix, "fileSuffix");

        control = new ResourceBundleControlUniversal();
        control.setExtensionName(fileSuffix);
        if (!(encoding == null || encoding.trim().isEmpty())) {
            control.setDefaultEncoding(encoding);
        }
        messageSource = ResourceBundle.getBundle(baseName, control);
    }


    public String getMessage(String key) {
        try {
            return messageSource.getString(key);
        } catch (MissingResourceException e) {
            return null;
        } catch (ClassCastException e) {
            return null;
        }
    }

    public String getMessage(String key, String defaultMessage) {
        if (!messageSource.containsKey(key)) return defaultMessage;
        return getMessage(key);
    }

    protected void validate(String arg, String argLogName) {
        if (arg == null || arg.trim().isEmpty())
            throw new IllegalArgumentException("" + argLogName + " can't be empty or null.");
    }
}
