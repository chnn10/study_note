package main

import "fmt"

func main() {
	// todo Go中，数组是一个具有编号且长度固定的元素序列
	// 创建可以存放5个int元素的数组a，默认值是0
	var a [5]int
	fmt.Println("emp:", a)

	// 可以使用array[index] = value，来设置数组指定位置的值。
	a[4] = 100
	fmt.Println("set:", a)
	fmt.Println("get", a[4])

	// 内置函数len可以返回数组的长度
	fmt.Println("len:", len(a))

	// 初始化一个数组
	b := [5]int{1, 2, 3, 4, 5}
	fmt.Println("array[5]", b)
}
