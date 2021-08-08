### Info

Replica of [project](https://github.com/dariusz-szczepaniak/java.jna.WindowsCredentialManager) to read the credentials from
[Windows Credential Manager](https://support.microsoft.com/en-au/windows/accessing-credential-manager-1b5c916a-6a16-889f-8581-fc16e8165ac0).

### Usage

```sh
mvn clean package
java -cp target/example.credential-manager.jar example.App -a
```
will show all credentials
* add some credntials, e.g.
![credentials](https://github.com/sergueik/selenium_jav/blob/master/credential_manager/screenshots/capture.png)
and read them
```sh
java -jar target\example.credential-manager.jar -t some_user
```
will respond with
```sh
address:[some_user], username:[null], password:[super_secret]
```
Multiple 'targets' values can be passed comma-separated 

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)

