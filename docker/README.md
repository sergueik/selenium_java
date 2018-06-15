### Info

This directory contains examples from the artcle  [Java-приложения и Docker (in Russian)](https://habr.com/company/billing/blog/350138/).
The original git repository of that article can be found on  [alexff91/Java-meetup-2018](https://github.com/alexff91/Java-meetup-2018).

To run examples, set up [Docker environment in a Ubuntu 16.04](https://www.digitalocean.com/community/tutorials/how-to-install-and-use-docker-on-ubuntu-16-04).
This will be referenced asa host below. 

One can use VirtualBox VM running on a Windows 8.1 laptop or dual-boot the said laptop to Ubuntu via 
[bootable USB drive](https://tutorials.ubuntu.com/tutorial/tutorial-create-a-usb-stick-on-windows#0).

The minor difference is we will be using Oracle JDK, not OpenJDK, in the Ubuntu host. 
This adds a bit of challenge when installing Maven and Gradle while keeping these apps from adding alternatives java.


The [Docker Toolbox on legacy Windows](https://docs.docker.com/toolbox/) essentially relies on running a VirtualBox Linux VM anyway.

### See also

