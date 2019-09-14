package day1

import (
	"fmt"
)

//批量定义常量，起到类似enum枚举作用
const (
	a = iota //iota数值从0递增
	b = iota
	c = iota
)

func Var_const(){
	var var1 int
	var1 = 1
	var1 = 10
	var var2 int = 20
	const cst int = 30
	fmt.Printf("var1=%d,var2=%d,cst=%d\n",var1,var2,cst)//var1=10,var2=20,cst=30
	fmt.Printf("a=%d,b=%d,c=%d\n",a,b,c)//a=0,b=1,c=2
	const (
		d = 4
		e = 5
		f = 6
	)
	fmt.Printf("d=%d,e=%d,f=%d\n",d,e,f)//d=4,e=5,f=6
}

func PrintConst(){
	
}