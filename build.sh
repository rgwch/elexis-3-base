#! /bin/bash

mvn -V -Dtycho.localArtifacts=ignore -Dmaven.test.skip=true clean verify

