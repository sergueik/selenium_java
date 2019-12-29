### Info

This directory contains a replica of [sukgu/shadow-automation-selenium](https://github.com/sukgu/shadow-automation-selenium) shadow ROOT DOM automation javascript API project with minor modifications:

* downgraded to Junit 4 with required re-annotation of the test methods
* converted to classic maven directory layout, removed deployment-related part from maven project
* added some Selenium driver initialization boilerplate code 
* fixed the test landing page `https://www.virustotal.com` starting link `*[data-route='url']`
