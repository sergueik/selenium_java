### Info

Jenkins plugins

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


 github-api/	
 github-branch-source/	
 github-oauth/	
 github-organization-folder/	
 github-pr-coverage-status/
 github-pullrequest/	
 github-sqs-plugin/
 github/
 gitlab-hook/	
 gitlab-logo/
 gitlab-merge-request-jenkins/	
 gitlab-oauth/	
 gitlab-plugin/
 global-build-stats/
 



