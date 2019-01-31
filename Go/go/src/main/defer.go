package main 

import (
	"fmt"
)
//类型断言
func testDefer() {
	var arr = make([]interface{}, 3)
	arr[0] = 100
	arr[1] = "hello"
	
	for index,data := range arr{
		if value,ok := data.(int); ok == true{
			fmt.Printf("%d int value is %d\n",index,value)
		}else if value,ok := data.(string); ok == true{
			fmt.Printf("%d string value is %s\n",index,value)
		}
	}
	fmt.Println()
	
	for _,d := range arr{
		switch t := d.(type){
			case int:
				fmt.Printf("int type is %v\n",t)
				fmt.Printf("int type is %T\n",t)
				iType := fmt.Sprintf("%T", t)
				fmt.Println(iType)
			case string:
				fmt.Printf("string type is %v\n",t)
			default:
				fmt.Printf("default type is %v\n",t)
		}
	}
	
}

