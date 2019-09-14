package main 

import (
	"day1"
	"day2"
	"fmt"
)

//先执行引用包的init函数，再执行main自己的init函数，最后开始main函数
func init(){
	fmt.Println("this is main init.")
}

func main() {
	//testArray()
	//testPointer()
	//testStruct()
	//testSlice()
	//testSlice2()
	//testInterface()
	//testErr()
	//testFuncParam()
	//testFuncReturn()
	//testFuncType()
	//testFuncNoName()
	//testPkg()
	//testDefer()
	//testError()
	//testString()
	//func_init()
	run_day1()
}

//包初始化函数init
func func_init(){
	fmt.Println("10+20=",day2.Add(10,20))
	fmt.Println("10-5=",day2.Minus(10,5))
}

//day1包调用
func run_day1(){
	//var_const.go
	day1.Var_const()
}







