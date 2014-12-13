package com.wacai.goodies.properties;

import org.testng.annotations.Test;

/**
 * @author yunshi@wacai.com
 * @since 2014-12-13
 */
public class SimpleMessageSourceTest {

    public static final String EXISTING_KEY = "simple.message.source.author";
    public static final String VALUE_FOR_EXISTING_KEY = "陨石";

    public static final String NON_EXISTING_KEY = "non.exist.key";

    public static final String DEFAULT_MESSAGE = "缺省值";


    public final String baseName = "messages/fixture";

    @Test
    public void testGettingMessage() {
        SimpleMessageSource ms = new SimpleMessageSource(baseName);

        String message = ms.getMessage(EXISTING_KEY);
        assert message.equalsIgnoreCase(VALUE_FOR_EXISTING_KEY);

        String nonExistMessage = ms.getMessage(NON_EXISTING_KEY);
        assert nonExistMessage == null;
    }

    @Test
    public void testGettingMessageWithOrWithoutDefaultMessage() {
        SimpleMessageSource ms = new SimpleMessageSource(baseName);

        String defaultMessage = ms.getMessage(NON_EXISTING_KEY, DEFAULT_MESSAGE);
        assert defaultMessage != null;
        assert defaultMessage.equalsIgnoreCase(DEFAULT_MESSAGE);

        String message = ms.getMessage(EXISTING_KEY, DEFAULT_MESSAGE);
        assert message.equalsIgnoreCase(VALUE_FOR_EXISTING_KEY);
    }
}
