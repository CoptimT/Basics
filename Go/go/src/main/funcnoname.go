package main 

import (
	"fmt"
)
//匿名函数 闭包 捕获外部变量
func testFuncNoName() {
	a := 100
	str := "hello"
	func(){
		fmt.Printf("a = %d, str = %s", a, str)	
	}()
}

