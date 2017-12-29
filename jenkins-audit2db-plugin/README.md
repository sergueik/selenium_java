Jenkins Audit to Database Plugin
================================

This Jenkins plugin allows the recording of build information to a database.

Installation
------------
Database connections are established via JDBC, so you have to ensure a valid
JDBC driver for your database can be found by this plugin. This can be
accomplished in two ways:

### Use the Jenkins classpath
If Jenkins is running as a standalone application, you can put the JDBC 
driver package in the `war/WEB-INF/lib` directory. If Jenkins is running
inside a J2EE container (e.g. Tomcat) you can use the container's classpath 
instead (consult the container's documentation for details).

### Use the plugin's classpath
Regardless of whether jenkins is running as a standalone application or
as a web application inside a J2EE container, you can put the JDBC driver
package in `$JENKINS_HOME/plugins/audit2db/WEB-INF/lib`. This directory
will be created the first time you run the plugin inside Jenkins, so if
you can't see it (and assuming you have actually already installed the
audit to databasep plugin), then try restarting Jenkins.

This plugin has been tested with the following JDBC drivers:

    org.hsqldb.jdbc.JDBCDriver
    
    oracle.jdbc.driver.OracleDriver
    
    com.microsoft.sqlserver.jdbc.SQLServerDriver

Now [add the plugin][3] to your Jenkins installation, but before you
can use it we need to set up the audit database. In the Jenkins
configuration page, enter the JDBC connection details for your audit
database and test the connection. If the connection is successful
click on the `Advanced` button. Another button will appear. This
will allow you to generate the data definition script to set up
the audit database. If you have any DBAs, it is a good idea to pass
this script over to them now. It is also a good idea to discuss in 
detail your audit reporting requirements with your DBAs, so that they 
can configure the appropriate indexes on the audit tables and help
you build your reporting queries.

Note
----
If you want to use Windows integrated authentication with the
SQL Server JDBC driver, then read carefully [this article][2].

Contributing
------------
Contributions are always welcome. Here's how:

1. Fork the plugin repo.
2. Create a branch (`git checkout -b my_audit2db`)
3. Commit your changes (`git commit -am "Added cool stuff."`)
4. Push to the branch (`git push origin my_audit2db`)
5. Open a [Pull Request][1]
6. Wait. Good things will come.

[1]: https://help.github.com/articles/creating-a-pull-request
[2]: http://blogs.msdn.com/b/jdbcteam/archive/2007/06/18/com-microsoft-sqlserver-jdbc-sqlserverexception-this-driver-is-not-configured-for-integrated-authentication.aspx?PageIndex=2
[3]: https://wiki.jenkins-ci.org/display/JENKINS/Plugins#Plugins-Howtoinstallplugins
