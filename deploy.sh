#!/usr/bin/env bash

P2=${WEBSPACE}/p2/elexis-base

mkdir -p ${P2}/${BUILD_NUMBER}

cp -R ch.elexis.base.p2site/target/repository/* ${P2}/${BUILD_NUMBER}

rm ${P2}/latest
ln -s ${P2}/${BUILD_NUMBER} ${P2}/latest
