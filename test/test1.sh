#!/bin/bash
curl --cookie-jar cookies -X PUT -H "Content-Type: application/json" -d @user.json localhost:8080/users

curl --cookie-jar cookies --cookie cookies -X POST -H "Content-Type: application/json" -d @test.json localhost:8080/characters > out.json
