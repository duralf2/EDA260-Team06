#!/bin/sh
# Deploys docs to gh-pages
#DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
git subtree push --prefix Documentation origin gh-pages
