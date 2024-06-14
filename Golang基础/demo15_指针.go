package main

import "fmt"

// 这里是int类型的，值传递，zeroVal将从调用它的那个函数中得到一个实参的拷贝：val
func zeroVal(val int) {
	val = 0
}

// 这里使用的是int的指针，函数内的*ptr会解引用这个指针，从它的内存地址得到这个地址当前对应的值
func zeroPtr(ptr *int) {
	*ptr = 0
}

func main() {
	// Go语言支持指针，允许在程序中通过引用传递来传递值和数据结构
	i := 1
	fmt.Println("init:", i)

	zeroVal(i)
	fmt.Println("zeroVal:", i)

	zeroPtr(&i)
	fmt.Println("zeroPtr:", i)
	fmt.Println("ptr:", &i)
}
