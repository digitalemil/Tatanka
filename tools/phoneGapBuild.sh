#!/bin/bash
export DISTDIR=$1
rm -fr $DISTDIR
mkdir $DISTDIR
cp -r tools/.git $DISTDIR
cp -r $2/*  $DISTDIR
cd $DISTDIR
find $DISTDIR -name "*.svn" | xargs -i rm -fr {}
git init
git add *
git commit -m "Push For Phonegap"
git remote add origin $5
git push -f origin master
curl -u $4 -X PUT -d 'data={"pull":"true"}' https://build.phonegap.com/api/v1/apps/$3
sleep 120
curl -u $4 -X POST  https://build.phonegap.com/api/v1/apps/$3/build

