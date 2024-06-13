package main

import "fmt"

func sum(nums ...int) {
	fmt.Print(nums, " ")
	total := 0
	for _, num := range nums {
		total += num
	}
	fmt.Println(total)
}

func main() {
	// 可变参数，在调用的时候，可以传递任意数量的参数
	sum(1, 2)
	sum(1, 2, 3)
}
