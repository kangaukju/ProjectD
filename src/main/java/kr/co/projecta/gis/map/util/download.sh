#!/bin/bash

if [ -f /home/$USER/.bashrc ]; then
	. /home/$USER/.bashrc
fi
if [ -f /home/$USER/.bash_profile ]; then
	. /home/$USER/.bash_profile
fi

outzip=$1"/juso.zip"
downloadfile=$1"/.juso.tmp"
update_date=`wget http://www.juso.go.kr/support/AddressBuild.do -q -O $downloadfile > /dev/null 2>&1; egrep "downloadAll\(.*[0-9].*\)" $downloadfile | awk -F'downloadAll' '{print $2}' | awk -F'"' '{print $1}' | sed "s/[()' ]//g" | awk -F',' '{print $1","$2}' | sort -t',' -n -k 1 -r | sort -t',' -n -k 2 -r | head -n 1`

 year=`echo $update_date | sed s/\,.*//g`
month=`echo $update_date | sed s/.*\,//g`
filename=$year$month"RDNMADR.zip"
params="reqType=RDNMADR&fileName=$filename&realFileName=$filename&regYmd=$year"

wget -q http://www.juso.go.kr/dn.do?$params -O $outzip

rm -fr $downloadfile
