package main

import ("fmt")

func testPointer() {
	var a int = 10
	var b *int = &a

	fmt.Printf("变量的地址: %x\n", &a)
	fmt.Printf("变量的地址: %x\n", b) //访问指针变量中指向地址的值。
	fmt.Printf("变量的地址: %d\n", *b)//在指针类型前面加上 * 号（前缀）来获取指针所指向的内容。
	
	if(b == nil){
		fmt.Println("空指针")
	}else{
		fmt.Println("非空指针")
	}
}
