#!/bin/sh
set -e
BASEDIR=$(dirname "$0")
"$BASEDIR/mvnw.cmd" "$@"
