#!/bin/bash

sudo systemctl start redis
sudo systemctl start postgresql
sudo systemctl start neo4j
(cd ./src/main/webapp && npm start)
