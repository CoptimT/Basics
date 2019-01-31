package main 

import (
	"fmt"
)
//函数类型 - 多态
func add(a int,b int) int{
	return a + b
}
func minus(a int,b int) int{
	return a - b
}
//回调函数
type myFunc func(a int,b int) int
func calc(a int,b int,c myFunc) int{
	return c(a,b)
}

func testFuncType() {
	
	var f myFunc
	
	f = add
	fmt.Println(f(10,5))
	
	f = minus
	fmt.Println(f(10,5))
	
	fmt.Println(calc(10,5,add))
	fmt.Println(calc(10,5,minus))
}

