
import java.sql.*;
import javax.sql.*;
import java.util.zip.*;
import java.io.*;
import java.net.*;

public class jdbcLoad {

  private static String serverName = System.getProperty("serverName", "localhost");
  private static String portNumber = System.getProperty("portNumber", "5432");
  private static String databaseName = System.getProperty("databaseName", "crashrec");
  private static String user = System.getProperty("user", "crashrec");
  private static String password = System.getProperty("password", "crashrec");
  private static String jdbcUrl = System.getProperty("jdbcUrl");
  private static String driver = System.getProperty("driver", "lib/postgresql-9.4-1201.jdbc41.jar");
  private static String psqlConnectionPattern = "jdbc:postgresql://%s:%s/%s";

  public static void main(String[] args) throws Exception {
    String connectionUrl = jdbcUrl;
    if (jdbcUrl == null) connectionUrl = String.format(psqlConnectionPattern, serverName, portNumber, databaseName);
    System.out.println("Going to connect with following connection url: " + connectionUrl);

    File driverFile = FileLoader.getFile(driver);
    System.out.println("Going to use driver file " + driverFile.getAbsolutePath());

    String driverClassName = null;

    //get the zip file content
    try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(driverFile))) {
      //get the zipped file list entry
      ZipEntry zipEntry = zipInputStream.getNextEntry();
      while(zipEntry!=null){
        if(zipEntry.getName().equals("META-INF/services/java.sql.Driver")) {
          String[] driverClassNames = slurp(zipInputStream).split("\\n");
          driverClassName = driverClassNames[0].trim().replaceAll("[\r\n]+$", "");;
          System.out.println(zipEntry.getName() + ":" + driverClassName);
          break;
        }
        // System.out.println("Other: " + zipEntry.getName());
        zipEntry = zipInputStream.getNextEntry();
      }
    }

    ClassLoader driverClassLoader = FileLoader.loadJar(driverFile.getAbsolutePath());

    // trouble with class loading - see http://www.kfu.com/~nsayer/Java/dyn-jdbc.html
    //Driver driverInstance = (Driver) Class.forName(driverClassName, true, driverClassLoader).newInstance();
    // -- OR --
    @SuppressWarnings("unchecked")
    Class<Driver> driverClazz = (Class<Driver>) driverClassLoader.loadClass(driverClassName);
    Driver driverInstance = driverClazz.newInstance();
    // -- OR --
    // No dynamic class loading :/
    // Driver driverInstance = (Driver) Class.forName(driverClassName).newInstance();

    DriverDelegation driverDelegation = new DriverDelegation(driverInstance);
    DriverManager.registerDriver(driverDelegation);

    Connection conn = DriverManager.getConnection(connectionUrl, user, password);
    DatabaseMetaData md = conn.getMetaData();
    System.out.printf("driver name: '%s', major version: '%s', minor version: '%s'%n",
      md.getDriverName(), md.getDatabaseMajorVersion(), md.getDatabaseMinorVersion());
  }

  public static String slurp(final InputStream is) {
    final char[] buffer = new char[1024];
    final StringBuilder out = new StringBuilder();
    try {
        final Reader in = new InputStreamReader(is, "UTF-8");
        for (;;) {
            int rsz = in.read(buffer, 0, buffer.length);
            if (rsz < 0)
                break;
            out.append(buffer, 0, rsz);
        }
    }
    catch (UnsupportedEncodingException uee) {
       throw new RuntimeException(uee);
    }
    catch (IOException ioe) {
       throw new RuntimeException(ioe);
    }
    return out.toString();
}
}
