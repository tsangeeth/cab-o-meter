#!/usr/bin/env bash
set -euo pipefail
ROOT="$(cd "$(dirname "$0")/.." && pwd)"
export JAVA_HOME="${JAVA_HOME:-/opt/jdk-25}"
export PATH="$JAVA_HOME/bin:/opt/maven/bin:$PATH"
cd "$ROOT"
mvn -q spring-boot:run
