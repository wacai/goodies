package com.wacai.goodies.properties;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * <p>ResourceBundle.getBundle(bundleName, [instance of this class]) will give you a resource bundle that can read in a utf8 encoded file as message source.</p>
 * <p>Most of the time, you will NOT need to use this directly, see {@link com.wacai.goodies.properties.SimpleMessageSource} for usage api.</p>
 *
 * @author yunshi@wacai.com
 * @since 2014-09-16
 */
public class ResourceBundleControlUniversal extends ResourceBundle.Control {

    private String defaultEncoding = "UTF-8";

    /**
     * without dot, just the extension name, like "properties", "xml", etc.
     */
    private String extensionName;

    public ResourceBundle newBundle
            (String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
            throws IllegalAccessException, InstantiationException, IOException {
        // The below is a copy of the default implementation.
        String bundleName = toBundleName(baseName, locale);
        String resourceName = toResourceName(bundleName, extensionName);
        ResourceBundle bundle = null;
        InputStream stream = null;
        if (reload) {
            URL url = loader.getResource(resourceName);
            if (url != null) {
                URLConnection connection = url.openConnection();
                if (connection != null) {
                    connection.setUseCaches(false);
                    stream = connection.getInputStream();
                }
            }
        } else {
            stream = loader.getResourceAsStream(resourceName);
        }
        if (stream != null) {
            try {
                bundle = new PropertyResourceBundle(new InputStreamReader(stream, defaultEncoding));
            } finally {
                stream.close();
            }
        }
        return bundle;
    }

    public String getExtensionName() {
        return extensionName;
    }

    public void setExtensionName(String extensionName) {
        this.extensionName = extensionName;
    }

    public String getDefaultEncoding() {
        return defaultEncoding;
    }

    public void setDefaultEncoding(String defaultEncoding) {
        this.defaultEncoding = defaultEncoding;
    }

}