package main 

import (
	"fmt"
)
//可变参数传递
func function1(args ...int){
	for i := range args{
		fmt.Printf("args[%d] = %d\n", i , args[i])
	}
	fmt.Println("----------")
}

func function2(params ...int){
	function1(params...)
	function1(params[:2]...)
	function1(params[2:]...)
}

func testFuncParam() {
	function2(1,2,3,4)
}

//返回值
func function3() (result int){
	result = 666
	return
}

func testFuncReturn() {
	res := function3()
	fmt.Println("result = ", res)
}
