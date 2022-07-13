#!/bin/bash
DIR=${1:-dummy}
# NOTE: can also pass the filename as --file argument
DEFAULT_DB='cache.db'
java -cp target/example.rrd-cachedb.jar:target/lib/* example.App --path data --save --collect $DIR

QUERY='SELECT count(1) from cache_table'
cp ~/$DEFAULT_DB ~/cache.$DIR.db
# NOTE: can continue with same database for all directories
# backing up per-directory databases for recovery after error or interruption
sqlite3 ~/cache.$DIR.db "${QUERY}"
ls -l ~/cache.$DIR.db
