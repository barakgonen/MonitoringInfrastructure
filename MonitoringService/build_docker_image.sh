#!/bin/bash

mkdir tmpDeps
cp -r ~/.m2/repository/org/example/* tmpDeps/
sudo docker build . -t $1
rm -rf tmpDeps