#!/bin/bash

if [ -f /home/$USER/.bashrc ]; then
	. /home/$USER/.bashrc
fi
if [ -f /home/$USER/.bash_profile ]; then
	. /home/$USER/.bash_profile
fi

jusofile=$1

echo `iconv -c -f euc-kr -t utf-8 $jusofile | awk -F'|' '{print $2}' | head -n 1`