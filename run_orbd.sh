#!/bin/bash

if [ -d orb.db ]; then
	rm -rf orb.db
	echo "alte orb.db geloescht"
fi

orbd  -ORBInitialPort 20000
