#!/bin/bash


if [ -z $1 ]; then
  echo "You need to provide a container name!"
  exit 1
fi

docker ps | grep $1 | wc -l