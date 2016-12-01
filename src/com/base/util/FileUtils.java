/**
 * 
 */
package com.base.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.base.log.LogUtil;

/**
 * @author yangtao
 *
 */
public class FileUtils {

	private static final Logger logger=LogUtil.getLogger(FileUtils.class.getName());
    public static Properties getProperties(InputStream fis) {
        try {
            Properties result = new Properties();
        
            if (fis != null)
                result.load(fis);
            return result;
        } catch (Exception e) {
        	logger.error("", e);
        } finally {
            if (fis != null)
                try {
                    fis.close();
                } catch (IOException e) {
                	logger.error("", e);
                }
        }
        return null;
    }
}
