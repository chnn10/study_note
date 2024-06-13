package main

import "fmt"

func retMultiValues() (int, int) {
	return 2, 3
}

func main() {
	// Go语言支持返回多个值，这里Go会经常用到
	// 我们可以使用多赋值操作来使用这两个不同的返回值
	// 如果你仅仅需要返回一个值，可以使用空白标识符_
	a, b := retMultiValues()
	fmt.Println(a)
	fmt.Println(b)
	_, c := retMultiValues()
	fmt.Println(c)
}
