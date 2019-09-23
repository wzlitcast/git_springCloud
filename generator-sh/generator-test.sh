#!/bin/bash
count=0
cd ..
folder=runshell
rm -rf $folder
mkdir -p $folder
for welfare_name in `echo $(dirname $var) wel*`;
do  
	mkdir -p ./$welfare_name/src/main/resources/shell
	cp ./generator-sh/spring-boot-test.sh ./$welfare_name/src/main/resources/shell/test.sh
	sed -i "s/service_name/$welfare_name/g" ./$welfare_name/src/main/resources/shell/test.sh
	echo $welfare_name'脚本创建完成'
	count=$[$count+1]  
done 
rm -rf $folder
echo '创建脚本'$count'个'

