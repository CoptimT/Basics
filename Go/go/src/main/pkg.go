package main 

// 1.
//import (
//    "fmt"
//)
// 2.给包起别名
import io "fmt"
// 3.调用无需包名 
import . "fmt"
// 4.不用包
import _ "fmt"

func testPkg() {
	io.Println("2.给包起别名")
	Println("3.调用无需包名 ")
}

