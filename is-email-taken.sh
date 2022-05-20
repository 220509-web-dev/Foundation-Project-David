#!/bin/bash

if [ -z $1 ]; then
    echo "Invalid usage, please provide an email!"
    exit 1
fi

if [ $(./is-this-thing-on.sh local-db) -eq 0 ]; then
    echo "Cannot connect to db!"
    exit 1
fi

query="select * from social_app.app_users where email = '$1';"

echo "$query" | docker exec -i local-db psql -U postgres | grep $1 | wc -l