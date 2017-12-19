
import java.net.URLClassLoader;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Troubles with classloading and registering a new driver
 * to {@link DriverManager} that was loaded by {@link URLClassLoader}.
 * Using setup from http://www.kfu.com/~nsayer/Java/dyn-jdbc.html
 */
public class DriverDelegation implements Driver {
    private Driver driver;

    DriverDelegation(final Driver d) {
        this.driver = d;
    }

    @Override
    public boolean acceptsURL(final String u) throws SQLException {
        return this.driver.acceptsURL(u);
    }

    @Override
    public Connection connect(final String u, final Properties p) throws SQLException {
        return this.driver.connect(u, p);
    }

    @Override
    public int getMajorVersion() {
        return this.driver.getMajorVersion();
    }

    @Override
    public int getMinorVersion() {
        return this.driver.getMinorVersion();
    }

    @Override
    public DriverPropertyInfo[] getPropertyInfo(final String u, final Properties p)
            throws SQLException {
        return this.driver.getPropertyInfo(u, p);
    }

    @Override
    public boolean jdbcCompliant() {
        return this.driver.jdbcCompliant();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return driver.getParentLogger();
    }
}
