#!/bin/bash

dbOn=$(./is-this-thing-on.sh local-db)
if [ $dbOn -eq 0 ]; then
    echo "Cannot connect to database. Check if it is running."
    exit 1
fi

echo "Provide information to register"

read -p "Email: " email
read -p "Age: " age 
read -p "Username: " username
read -r -p "Password: " password

emailTaken=$(./is-email-taken.sh $email)
while [ $emailTaken -ne 0 ]; do
    echo "This email is already taken, enter another one or cancel with \q!"
    read -r -p "Email: " email

    if [ $email = "\q" ]; then
        echo "Cancelling registration"
        exit 0
    fi
    emailTaken=$(./is-email-taken.sh $email)
done

userTaken=$(./is-username-taken.sh $username)
while [ $userTaken -ne 0 ]; do
    echo "This username is already taken, enter another one or cancel with \q!"
    read -r -p "Username: " username

    if [ $username = "\q" ]; then
        echo "Cancelling registration"
        exit 0
    fi
    userTaken=$(./is-username-taken.sh $username)
done

insert="INSERT INTO social_app.app_users (email, age, username, password) VALUES ('$email', $age, '$username', '$password')"

echo $insert | docker exec -i local-db psql -U postgres