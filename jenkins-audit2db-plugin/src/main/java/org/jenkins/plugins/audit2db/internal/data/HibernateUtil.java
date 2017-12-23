/**
 * 
 */
package org.jenkins.plugins.audit2db.internal.data;

import java.io.File;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.tool.hbm2ddl.SchemaExport;

/**
 * Utility class for Hibernate access.
 * 
 * @author Marco Scata
 *
 */
public class HibernateUtil {
    private final static Logger LOGGER = Logger.getLogger(HibernateUtil.class.getName());

    private static Configuration getConfig(final Properties extraProperties) throws HibernateException {
        LOGGER.log(Level.INFO, Messages.HibernateUtil_LoadConfig());
        final Configuration config = new AnnotationConfiguration().configure();
        if ((extraProperties != null) && !extraProperties.isEmpty()) {
            LOGGER.log(Level.FINE, Messages.HibernateUtil_SettingExtraProps());
            LOGGER.log(Level.FINE, extraProperties.toString());
            config.addProperties(extraProperties);
        }
        return config;
    }

    public static SessionFactory getSessionFactory(final Properties extraProperties) {
        SessionFactory retval = null;

        try {
            // Load base configuration from hibernate.cfg.xml
            final Configuration config = getConfig(extraProperties);
            retval = config.buildSessionFactory();
        } catch (final Exception e) {
            // Make sure you log the exception, as it might be swallowed
            LOGGER.log(Level.SEVERE, Messages.HibernateUtil_FailedSessionFactory(), e);
            throw new RuntimeException(e);
        }

        return retval;
    }

    public static SessionFactory getSessionFactory() {
        return getSessionFactory(null);
    }

    public static Properties getExtraProperties(
            final String driverClass,
            final String driverUrl,
            final String username,
            final String password) {
        final Properties props = new Properties();
        props.put("hibernate.connection.driver_class", driverClass);
        props.put("hibernate.connection.url", driverUrl);
        props.put("hibernate.connection.username", username);
        props.put("hibernate.connection.password", password);

        return props;
    }

    public static String getSchemaDdl(
            final String driverClass,
            final String driverUrl,
            final String username,
            final String password) throws IOException {
        String retval = null;

        final Properties props = getExtraProperties(
                driverClass, driverUrl, username, password);
        final SessionFactory sessionFactory = getSessionFactory(props);
        final String dialect = ((SessionFactoryImplementor)sessionFactory).getDialect().toString();
        props.put("hibernate.dialect", dialect);

        final Configuration config = getConfig(props);
        final SchemaExport generator = new SchemaExport(config);
        final File tempDdlFile = new File("jenkins_audit2db.ddl");
        generator.setOutputFile(tempDdlFile.getPath());
        generator.setFormat(true);
        generator.execute(true, false, false, true);

        final Scanner scanner = new Scanner(tempDdlFile);
        //using a non-matching delimiter will read the whole file
        scanner.useDelimiter("\\Z");
        retval = String.format(Messages.HibernateUtil_GeneratedNote(),
                dialect, scanner.next());
        tempDdlFile.delete();

        return retval;
    }
}
