#!/bin/bash

sudo docker volume rm $(sudo docker volume ls)
sudo docker network rm $(sudo docker network ls)