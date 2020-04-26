### Info

This directory contains a replica of 
[Java email sender through Yahoo! Mail](https://www.codeproject.com/Articles/5266074/Send-Email-with-Java-and-Yahoo-Mail)
article code modified to accept command line arguments. 

### Testing
See the original article on configuring the account.
The test account `sergueik_test` was created and the password for accessing it by `example.yahoo_sendmail` generated
```sh
mvn clean package
java -jar target/example.yahoo_sendmail.jar -from sergueik_test@yahoo.com -to sergueik_test@yahoo.com -password xxx -debug
```
works as expected

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
