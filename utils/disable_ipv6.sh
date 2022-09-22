#!/bin/sh
# origin: https://www.thegeekdiary.com/how-to-disable-ipv6-on-ubuntu-18-04-bionic-beaver-linux/
# temporarily disable.
# NOTE: the recommended way of make the above sysctl changes permanent
# does not apppear to work
sudo sysctl -w net.ipv6.conf.all.disable_ipv6=1
sudo sysctl -w net.ipv6.conf.default.disable_ipv6=1
sudo sysctl -w net.ipv6.conf.lo.disable_ipv6=1

