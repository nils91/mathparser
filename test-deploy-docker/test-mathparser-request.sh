#!/bin/bash
http_code=0
for i in 1 2 3 4 5 6
do
   http_code=$(curl -s -o /dev/null -i -w "%{http_code}" -X POST "http://127.0.0.1:8080/MathParserDev/test/ping" -H  "accept: application/json" -H  "Content-Type: application/json" -d "{\"id\":0}")
   if [ $http_code -eq 200 ]
   then
      echo Deployed correctly
      exit 0
   fi
   sleep 10s
done
echo Not deployed correctly
exit 1
