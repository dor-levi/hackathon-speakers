!#/bin/bash

sudo git pull

sudo ~/maven/bin/mvn package

java -jar target/hackathons-speakers-0.0.1.jar server settings.yml
