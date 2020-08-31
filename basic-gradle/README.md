### Info

This directory contains basic gradle testng  project cloned from [gradle-example](https://github.com/dev9com/gradle-example)

### Setup

* to avoid installing dependencies, install gradle from binary archive
```sh
wget https://services.gradle.org/distributions/gradle-6.6.1-bin.zip
unzip gradle-6.6.1-bin.zip 
sudo mv gradle-6.6.1 /opt
```
* then set to be the default
```sh
sudo update-alternatives --install /usr/bin/gradle gradle /opt/gradle-6.6.1/bin/gradle 1
```
### See Also

 * [Building Java Projects with Gradle](https://spring.io/guides/gs/gradle/)
