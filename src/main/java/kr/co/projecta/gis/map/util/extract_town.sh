#!/bin/bash

if [ -f /home/$USER/.bashrc ]; then
	. /home/$USER/.bashrc
fi
if [ -f /home/$USER/.bash_profile ]; then
	. /home/$USER/.bash_profile
fi

jusofile=$1
tempfile=$2

iconv -c -f euc-kr -t utf-8 $jusofile | awk -F'|' '{print $1","$2","$3","$4}' | sort -k 3,4 -u -t"," > $tempfile
