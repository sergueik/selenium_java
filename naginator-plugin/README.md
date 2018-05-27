### Info
This directory contains a replica of
[Naginator Plugin for Jenkins]() projet intended to be a fork and feature development of the same.

The __Naginator__ plugin allows one to automatically reschedule a build after a build failure, by adding a certain post-build action (Publisher subclass) to the job configuration:

![Default Usage](https://github.com/sergueik/selenium_java/blob/master/naginator-plugin/screenshots/default.png)

More info is available on the plugin's [Wiki page](https://wiki.jenkins-ci.org/display/JENKINS/Naginator+Plugin)

### Objective

To implement a complex retry policy for different errors (e.g. a networking errors with artifactory may be flagged error and qualify for a bigger number of retries than e.g. a provision error due to incorrect resource ordering (often happen during new module or profile development, and recovered in the second and subsequent provision , hence one retry is sufficient while the Puppet code error is to be flagged as an error and stop the build without any retries) one may
add multiple `com.chikli.hudson.plugin.naginator.NaginatorPublisher`
XML nodes each representing a vaild job `publisher` class intance configuration like in the example shown below:

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
However creating of such configuration seems not be allowd in Jenkins UI, though if the job `congif.xml` is edited manually, all added publishers will bei visible in the UI but option to add more will bre greyed: 

![Default Usage](https://github.com/sergueik/selenium_java/blob/master/naginator-plugin/screenshots/new_retry_greyed.png)

The other option is to fork and extend the project code and
allow the `NaginatorPublisherScheduleAction` class to set the `maxScheduleOverride`
property of `NaginatorPublisher` class and let it override

The condiguration section for the job will become:

```xml
<publishers>
  <com.chikli.hudson.plugin.naginator.NaginatorPublisher plugin="naginator@1.18.0">
    <regexpForRerun>RETRY LOG MESSAGE: (\d)</regexpForRerun>
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
```

and the build log message to trigger the specific number of retries would look like:

```shell
echo RETRY LOG MESSAGE: 3
exit 1
```

![Updated Usage](https://github.com/sergueik/selenium_java/blob/master/naginator-plugin/screenshots/updated.png)

One can enable overriding the *Maximum number of successive failed
build retries* through the *Override maximum retries* checkbox.

When checked, the value of `maxSchedule` is computed at the time the console output of a failed
build is examined to detect the trigger log message, using the regular expression supplied by the configuration,
and updated in the `NaginatorPublisher`:
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
For example  if _Search Log For Message_ is set to somewhat more complex regular expression
```shell
ERROR: (?:NETWORK ERROR|ARTIFACTORY ERROR) RETRY: (\d)
```

Then failed buld with the Log message
`ERROR: NETWORK ERROR RETRY: 2` will be rerun max _2_ times while a failed build with the message `ERROR: ARTIFACTORY ERROR RETRY: 1`
will rerun once if failed.
This is shorter than to provide multiple _Retry build after failue_ configuration blocks.

The *Search for Log message* does not have to be very complex.
Only thing to make certain is that the nuber of retries
is captured into the first group, as manifesed by parenthesis in the example  above.
The `(?:` is a non-capturing group.


This allows for a more granular control of the rebuild policies from the build cofiguration and makes it a responsibility of the build.

### Run application

Ruild and install the package into the  Jenkins instance
```cmd
mvn clean compile hpi:hpi
```

### Note:

The `naginator` plugin is not very co-operative with `jenkins-multijob-plugin`: only the very first (failed)
build is recorded in the parent configuration, the rest is invisible to the parent.

Note: one has to install `jenkins-multijob-plugin` plugin has quite a few dependencies:
`token-macro`,`parameterized-trigger`,`conditional-buildstep`,`envinject`,
`envinject-api`,`script-security`,`built-on-column`,`run-condition`,`workflow-step-api`

### License
This project is licensed under the terms of the MIT license.

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)
