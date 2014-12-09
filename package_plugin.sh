#!/bin/bash

files=`ls java/build/server/plugin/*$1*.class`

PLUGIN_SUFFIX=PersistenceManager.jar
jar=$1$PLUGIN_SUFFIX

echo Adding plugin $jar
echo Files:

for file in $files
do
  echo "    $file"
done

jar -cf $jar $files
mv $jar java/dist/plugins

#rm -f $files
