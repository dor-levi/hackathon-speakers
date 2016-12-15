#!/bin/bash

sudo git pull

sudo ~/maven/bin/mvn package

java -jar target/hackathon-speakers-1.0.0.jar server settings.yml
