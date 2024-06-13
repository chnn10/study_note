package main

import "fmt"

// 声明了一个函数，接受两个int类型的参数，将其相加并返回
func plus(a int, b int) int {
	return a + b
}

// 如果都是同一个类型的，可以在最后一个参数声明就可以了
func plusSameType(a, b, c int) int {
	return a + b + c
}

func main() {
	// 通过函数名字来调用
	ret1 := plus(1, 2)
	fmt.Println(ret1)

	ret2 := plusSameType(1, 2, 3)
	fmt.Println(ret2)
}
