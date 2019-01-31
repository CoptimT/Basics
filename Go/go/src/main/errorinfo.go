package main 

import (
	"fmt"
	"errors"
)
//除法函数
func myDive(a,b int)(result int,err error){
	//err = nil
	if b == 0 {
		err = errors.New("除数不能为0")
	}else{
		result = a/b
	}
	return
}
//数组越界
func myArr(index int){
	defer func(){
		if err := recover(); err != nil{
			fmt.Println(err)//异常提示，但程序继续执行
		}
	}()
	var arr [10]int
	arr[index] = 100
}

func testError() {
	e := errors.New("NullPointer")
	fmt.Printf("%T\n", e)
	e1 := fmt.Errorf("%s", "ParseError")
	fmt.Printf("%T\n", e1)
	
	result,err := myDive(10,2)
	fmt.Printf("%T=%v, %T=%v\n", result,result,err,err)
	result,err = myDive(10,0)
	fmt.Printf("%T=%v, %T=%v\n", result,result,err,err)
	
	//显示调用panic,程序异常终止
	//panic("this is panic")
	//程序异常自动调用panic
	myArr(10)
	fmt.Println("-------")
}

