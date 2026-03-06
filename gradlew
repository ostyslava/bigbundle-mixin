#!/usr/bin/env sh
##############################################################################
# Gradle wrapper script for Unix
##############################################################################
APP_NAME="Gradle"
APP_BASE_NAME=$(basename "$0")
APP_HOME=$(cd "$(dirname "$0")" && pwd -P)

JAVA_EXE="$JAVA_HOME/bin/java"
[ -z "$JAVA_HOME" ] && JAVA_EXE="java"

CLASSPATH="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"

exec "$JAVA_EXE" \
  -classpath "$CLASSPATH" \
  org.gradle.wrapper.GradleWrapperMain \
  "$@"
