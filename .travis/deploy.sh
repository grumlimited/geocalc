#!/usr/bin/env bash

source .travis/utils.sh

if isDeployable && ( isDevelBranch || isTagRelease ); then
  mvn -P release --settings .travis/settings.xml deploy
fi

exit $?
