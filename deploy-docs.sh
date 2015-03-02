#!/bin/sh
# Deploys docs to gh-pages
#DIR=$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )
git subtree push --prefix Documentation origin gh-pages
echo "Docs were deployed to: https://duralf2.github.io/EDA260-Team06/"
