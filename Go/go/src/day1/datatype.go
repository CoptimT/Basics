package day1

import (
	"fmt"
)

// bool byte int32 int64 float32 float64
// char string complex complex64 complex128
func DataType(){
	Bool()
	Float()
	Complex()
	StdInput()
}

//day1-17
func Bool(){
	var a bool
	var b = false
	c := true
	//false false true
	fmt.Println(a,b,c)
}

//day1-18
func Float(){
	var a float32 = 3.14
	b := 3.14
	//a=3.140000,float32, b=3.140000,float64
	fmt.Printf("a=%f,%T, b=%f,%T\n",a,a,b,b)
}

//day1-22 复数
func Complex(){
	var a complex128 = 2.1 + 3.14i
	//a= (2.1+3.14i) real= 2.1 imag= 3.14
	fmt.Println("a=",a,"real=",real(a),"imag=",imag(a))
	//a=complex128
	fmt.Printf("a=%T\n",a)
}

//day1-24
func StdInput(){
	var a int32
	fmt.Scanf("%d", &a)
	fmt.Println("a=",a)
	fmt.Scan(&a)
	fmt.Println("a=",a)
}



