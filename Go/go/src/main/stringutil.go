package main 

import (
	"fmt"
	"strings"
	"strconv"
)

func testString() {
	str := "hello world,hello go!"
	fmt.Println("Contains = ", strings.Contains(str,"hello"))
	fmt.Println("Contains = ", strings.Contains(str,"java"))
	
	arr := []string{"hello","world","go"}
	fmt.Println("Join = ", strings.Join(arr,"^_^"))
	
	fmt.Println("Index = ", strings.Index(str,"go"))
	
	fmt.Println("Repeat = ", strings.Repeat(str, 3))
	
	fmt.Println("Split = ", strings.Split("hello@world@hello@go", "@"))
	
	fmt.Printf("Trim = [%s]\n", strings.Trim("  are you ok?   "," "))
	
	fmt.Println("Fields = ", strings.Fields(str))
	
	dst := make([]byte, 0, 1024)
	dst = strconv.AppendBool(dst, true)
	dst = strconv.AppendInt(dst, 1024, 10)
	dst = strconv.AppendQuote(dst, "hello")
	fmt.Println("strconv.Append = ", string(dst))
	
	str = strconv.FormatBool(true)
	fmt.Printf("strconv.FormatBool %T = %v\n", str, str)
	str = strconv.FormatFloat(3.14, 'f', -1, 64)
	fmt.Printf("strconv.FormatFloat %T = %v\n", str, str)
	str = strconv.Itoa(100)
	fmt.Printf("strconv.Itoa %T = %v\n", str, str)
	ivalue,_ := strconv.Atoi("100")
	fmt.Printf("strconv.Atoi %T = %v\n", ivalue, ivalue)
	
	boo,err := strconv.ParseBool("false")
	if err == nil{
		fmt.Printf("strconv.ParseBool %T = %v\n", boo, boo)
	}else{
		fmt.Printf("strconv.ParseBool %T = %v\n", err, err)
	}
	
	
}

