#!/bin/bash
for i in {1..500}
do
   curl http://localhost:8080/actuator/health
done