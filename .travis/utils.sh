#!/usr/bin/env bash

function notPullRequest {
	if  [ "$TRAVIS_PULL_REQUEST" == "false" ]; then
		return 0;
	else
		return 1;
	fi
}

function isMasterBranch {
	if  [ "$TRAVIS_BRANCH" = 'master' ]; then
		return 0;
	else
		return 1;
	fi
}

function isDevelBranch {
	if  [ "$TRAVIS_BRANCH" = 'devel' ]; then
		return 0;
	else
		return 1;
	fi
}

function isTagRelease {
	if  [ ! -z "$TRAVIS_TAG" ]; then
		return 0;
	else
		return 1;
	fi
}

function isSignable {
	if notPullRequest && ( isMasterBranch || isDevelBranch || isTagRelease ); then
		return 0;
	else
		return 1;
	fi
}

function isDeployable {
	if isSignable && ( isDevelBranch || isTagRelease ); then
		return 0;
	else
		return 1;
	fi
}

