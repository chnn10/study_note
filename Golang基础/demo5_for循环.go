package main

import "fmt"

func main() {
	// for是Go中唯一的循环过程

	// 单个循环条件
	i := 1
	for i <= 4 {
		fmt.Println(i)
		i++
	}

	// 初始化条件
	for j := 7; j <= 9; j++ {
		fmt.Println(j)
	}

	// 不带条件的for循环会一直执行，如果遇到了break，就会跳出循环
	for {
		fmt.Println("loop")
		break
	}
}
