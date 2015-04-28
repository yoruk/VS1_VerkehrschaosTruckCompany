#!/bin/bash

if [ -f VerkehrschaosTruckCompany.jar ]; then
        rm VerkehrschaosTruckCompany.jar
        echo "alte VerkehrschaosTruckCompany.jar geloescht"
fi

cd bin
jar -cmf ../src/META-INF/MANIFEST.MF ../VerkehrschaosTruckCompany.jar verkehrschaos/*.class verkehrschaostruckcompany/*.class
cd ..

java -jar VerkehrschaosTruckCompany.jar Streets 20000 localhost TruckAG ost
