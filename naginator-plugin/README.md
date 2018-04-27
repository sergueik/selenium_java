### Info
This directory contains a replica of
[Naginator Plugin for Jenkins]() projet intended to be a fork and feature development of the same.

The __Naginator__ plugin allows one to automatically reschedule a build after a build failure, by adding a certain post-build action (Publisher subclass) to the job configuration:

![Default Usage](https://github.com/sergueik/selenium_java/blob/master/naginator-plugin/screenshots/default.png)

More info is available on the plugin's [Wiki page](https://wiki.jenkins-ci.org/display/JENKINS/Naginator+Plugin)

### Objective

To implement a complex retry policy for different errors (e.g. a networking errors with artifactory may be flagged error and qualify for a bigger number of retries than e.g. a provision error due to incorrect resource ordering (often happen during new module or profile development, and recovered in the second and subsequent provision , hence one retry is sufficient while the Puppet code error is to be flagged as an error and stop the build without any retries) one may either add a few `com.chikli.hudson.plugin.naginator.NaginatorPublisher` XML nodes to the job configuration like the example below:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project>
  <actions/>
  <description>test the  naginator plugin functionality</description>
  <logRotator class="hudson.tasks.LogRotator">
    <daysToKeep>-1</daysToKeep>
    <numToKeep>-1</numToKeep>
    <artifactDaysToKeep>-1</artifactDaysToKeep>
    <artifactNumToKeep>-1</artifactNumToKeep>
  </logRotator>
  <keepDependencies>false</keepDependencies>
  <properties>
    <com.chikli.hudson.plugin.naginator.NaginatorOptOutProperty plugin="naginator@1.17.2">
      <optOut>false</optOut>
    </com.chikli.hudson.plugin.naginator.NaginatorOptOutProperty>
  </properties>
  <scm class="hudson.scm.NullSCM"/>
  <canRoam>true</canRoam>
  <disabled>false</disabled>
  <blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>
  <blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>
  <triggers/>
  <concurrentBuild>false</concurrentBuild>
  <builders>
    <hudson.tasks.Shell>
      <command>echo Retry BAR
exit 1</command>
    </hudson.tasks.Shell>
  </builders>
  <publishers>
    <com.chikli.hudson.plugin.naginator.NaginatorPublisher plugin="naginator@1.17.2">
      <regexpForRerun>(FOO|foo)</regexpForRerun>
      <rerunIfUnstable>true</rerunIfUnstable>
      <rerunMatrixPart>false</rerunMatrixPart>
      <checkRegexp>true</checkRegexp>
      <regexpForMatrixStrategy>TestParent</regexpForMatrixStrategy>
      <delay class="com.chikli.hudson.plugin.naginator.FixedDelay">
        <delay>10</delay>
      </delay>
      <maxSchedule>1</maxSchedule>
    </com.chikli.hudson.plugin.naginator.NaginatorPublisher>
    <com.chikli.hudson.plugin.naginator.NaginatorPublisher plugin="naginator@1.17.2">
      <regexpForRerun>(BAR|bar)</regexpForRerun>
      <rerunIfUnstable>true</rerunIfUnstable>
      <rerunMatrixPart>false</rerunMatrixPart>
      <checkRegexp>true</checkRegexp>
      <regexpForMatrixStrategy>TestParent</regexpForMatrixStrategy>
      <delay class="com.chikli.hudson.plugin.naginator.FixedDelay">
        <delay>10</delay>
      </delay>
      <maxSchedule>2</maxSchedule>
    </com.chikli.hudson.plugin.naginator.NaginatorPublisher>
  </publishers>
  <buildWrappers/>
</project>

```

Note constructing the configuration seems to not be possible in Jenkins UI, only manually editing  the `congif.xml`.

The other option is to fork and extend the `Naginator` class to allow the `` getter to override the `maxSchedule` through creating a stealth `maxScheduleOverride` property for that. The condiguration secsion for the clas will look like
```xml
  <publishers>
    <com.chikli.hudson.plugin.naginator.NaginatorPublisher plugin="naginator@1.17.2">
      <regexpForRerun>(BAR|bar): (\d)</regexpForRerun>
      <rerunIfUnstable>true</rerunIfUnstable>
      <rerunMatrixPart>false</rerunMatrixPart>
      <checkRegexp>true</checkRegexp>
      <regexpForMatrixStrategy>TestParent</regexpForMatrixStrategy>
      <delay class="com.chikli.hudson.plugin.naginator.FixedDelay">
        <delay>10</delay>
      </delay>
      <maxSchedule>2</maxSchedule>
    </com.chikli.hudson.plugin.naginator.NaginatorPublisher>
  </publishers>
</project>
```

and the build log message to trigger the specific number of retries would look like:

```shell
echo Retry BAR: 3
exit 1
```

![Updated Usage](https://github.com/sergueik/selenium_java/blob/master/naginator-plugin/screenshots/updated.png)

One can enable or suppress overriding the Maximum number of successive failed build retries (`maxSchedule`) through the checkbox (`maxScheduleOverrideAllowed`). When allowed the value of `maxSchedule` is computed from the  trigger message in the failed build log and updated in the `NaginatorPublisher`:
```java
int maxScheduleOverride = 0;
maxScheduleOverride = Integer.parseInt(matcher.group(1));
assertThat(maxScheduleOverride, greaterThan(0));
LOGGER.log(Level.FINEST,
	"Got maxScheduleOverride = " + maxScheduleOverride);
if (naginatorPublisher.isMaxScheduleOverrideAllowed()) {
	naginatorPublisher.setMaxScheduleOverride(maxScheduleOverride);
}
```
This allows for a more granular control of the rebuild policies from the build side.

### Run application

Ruild and install the package into the  Jenkins instance
```cmd
mvn clean compile hpi:hpi
```
### License
This project is licensed under the terms of the MIT license.

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
