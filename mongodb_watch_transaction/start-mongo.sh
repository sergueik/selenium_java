#!/usr/bin/env bash
mkdir /tmp/data && mongod --dbpath /tmp/data --replSet rs
mongo --eval 'rs.initiate()'
