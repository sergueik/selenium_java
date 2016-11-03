### Info

This directory contains a replica of 
[GitHub Coverage Jenkins Plugin](https://github.com/terma/github-pr-coverage-statu://github.com/terma/github-pr-coverage-status) 
intended to be used together with 
[Active Choices Plugin](https://github.com/jenkinsci/active-choices-plugin) 
to trigger multiple github Pull Request merge in multiple branches according to deployment pipeline.

### .hpi Dependencies:
* structs 
* scm-api
* plain-credentials 
* token-macro 
* git-client  
* workflow-scm-step
* git
* github
* workflow-step-api
* github-pullrequest - 

### The github-pullrequest.hpi

The dependency `github-pullrequest.hpi` appears to contain two jars:
`github-pullrequest.jar` that contains a subset of 
[github-api](https://github.com/kohsuke/github-api)
and `findbugs-annotations.jar` which is not essential for our purposes.

To actually merge the PR, will need to add some of java gitlab API to the dependencies, work in progress.


### Misc.

```
# https://docs.gitlab.com/ee/api/
# https://about.gitlab.com/applications/#api-clients
# https://github.com/timols/java-gitlab-api
# GitlabAPI.java
# getProjects
# getOpenMergeRequests(Serializable projectId) 
# getOpenMergeRequests(GitlabProject project - strong-typed
# updateMergeRequest(Serializable projectId, Integer mergeRequestId, String targetBranch,
#                                                 Integer assigneeId, String title, String description, String stateEvent,
#                                                 String labels) throws IOException {
#
# acceptMergeRequest(GitlabProject project, Integer mergeRequestId, String mergeCommitMessage) 
# https://github.com/jenkinsci/gitlab-hook-plugin (ruby)
# https://github.com/jenkinsci/gitlab-plugin
# does not use org.gitlab.api
# has its own
# MergeRequestNotifier.java
# GitLabWebHookCause.java
# CauseData.java
#  - these does not seem to place REST calls, just a hashmap for Jelly
# GitLabApi.java
# getProject
# getBranches
# getMergeRequests
# createMergeRequestNote
# acceptMergeRequest
```

### Author
[Serguei Kouzmine](kouzmine_serguei@yahoo.com)


 