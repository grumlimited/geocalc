#!/usr/bin/env bash

source .travis/utils.sh

if isSignable; then
  mvn -P release --settings .travis/settings.xml install
else
  mvn install
fi
exit $?
