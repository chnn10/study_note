package main

import "fmt"

func main() {
	// if和else分支结构在Go中非常直接
	if 7%2 == 0 {
		fmt.Println("7是一个偶数")
	} else {
		fmt.Println("7是一个奇数")
	}

	// 我们也可以不使用else
	if 8%4 == 0 {
		fmt.Println("8可以直接被4整除")
	}

	// 我们也可以在语句之前有一个声明语句
	if i := 9; i < 0 {
		fmt.Println("i是一个负数")
	} else if i < 10 {
		fmt.Println("i是一个个位数")
	} else {
		fmt.Println("i是一个十位以上的数")
	}
}
