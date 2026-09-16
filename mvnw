#!/usr/bin/env bash
# Simple Maven "wrapper" script: downloads a Maven binary to ~/.m2/wrappers and runs it.
# This is not the official Maven Wrapper but allows building without a preinstalled Maven.

set -e
MAVEN_VERSION="3.8.7"
MAVEN_BASE="apache-maven-${MAVEN_VERSION}"
MAVEN_DIR="${HOME}/.m2/wrappers/${MAVEN_BASE}"

if [ ! -x "${MAVEN_DIR}/bin/mvn" ]; then
  echo "Maven ${MAVEN_VERSION} not found in ${MAVEN_DIR}, downloading..."
  tmpdir=$(mktemp -d)
  archive="$tmpdir/maven.tar.gz"
  url="https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/${MAVEN_BASE}-bin.tar.gz"
  if command -v curl >/dev/null 2>&1; then
    curl -fsSL "$url" -o "$archive"
  elif command -v wget >/dev/null 2>&1; then
    wget -q -O "$archive" "$url"
  else
    echo "curl or wget is required to download Maven. Please install one or install Maven manually." >&2
    exit 1
  fi
  mkdir -p "$MAVEN_DIR"
  tar -xzf "$archive" -C "$tmpdir"
  mv "$tmpdir/${MAVEN_BASE}"/* "$MAVEN_DIR"/
  rm -rf "$tmpdir"
  echo "Maven ${MAVEN_VERSION} downloaded to ${MAVEN_DIR}"
fi

exec "${MAVEN_DIR}/bin/mvn" "$@"
