#!/bin/sh
# Lanzador autonomo (no Gradle Wrapper oficial); descarga Gradle 9.2.1 al inicio.
set -eu
BASE_DIR=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
VERSION=9.2.1
CACHE="$BASE_DIR/.gradle-local"
GRADLE="$CACHE/gradle-$VERSION/bin/gradle"
if [ ! -x "$GRADLE" ]; then
  mkdir -p "$CACHE"
  URL="https://services.gradle.org/distributions/gradle-$VERSION-bin.zip"
  if command -v curl >/dev/null 2>&1; then
    curl -fL "$URL" -o "$CACHE/gradle.zip"
  elif command -v wget >/dev/null 2>&1; then
    wget -q "$URL" -O "$CACHE/gradle.zip"
  else
    echo 'Instale curl o wget para descargar Gradle.' >&2
    exit 1
  fi
  unzip -q "$CACHE/gradle.zip" -d "$CACHE"
  rm -f "$CACHE/gradle.zip"
fi
exec "$GRADLE" "$@"
