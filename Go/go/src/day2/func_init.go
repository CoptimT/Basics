package day2

import (
	"fmt"
)

//初始化函数，包被引用时优先于函数执行
func init(){
	fmt.Println("this is func_init init.")
}

//首字母大写表示public
func Add(a,b int) int{
	return a+b
}

func Minus(a,b int) int{
	return a-b
}