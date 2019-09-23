#!/bin/bash

LENGTH=$1

function rand(){
	MATRIX="0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz~!@#$%^&*()_+="

	while [ "${n:=1}" -le "$LENGTH" ]
	do
		PASS="$PASS${MATRIX:$(($RANDOM%${#MATRIX})):1}"
		let n+=1
	done
	echo "$PASS"
}

function forRandom(){
	j=$1
	for (( i = 1; i <= j; i++ ));
	do
		echo "--------------" $i  "--------------------------------" >> generator.log
		rnd=$(rand LENGTH) #长度
		echo "pwd:"  $rnd >> generator.log
		VAR=$(java -cp druid-1.1.12.jar com.alibaba.druid.filter.config.ConfigTools $rnd )
		# 判断调用是否成功
		if [ $? -ne 0 ]; then
			echo "=====调用失败 ====="
			exit 1
		fi
		# 成功获得返回值
		echo $VAR >>generator.log #把日志输出到generator.log文件中
		echo $i "完成"


	done

}

echo '' > generator.log
forRandom $2

exit 0


