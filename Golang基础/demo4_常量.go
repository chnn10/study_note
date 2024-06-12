package main

import (
	"fmt"
	"math"
)

const s string = "constant"

func main() {
	// Go支持字符、字符串、布尔和数值和常量

	// const用于声明一个常量，其可以出现在任何var语句可以出现的地方
	fmt.Println(s)

	const n = 50000000
	const d = 3e20 / n
	fmt.Println(d)
	fmt.Println(math.Sin(n))
}
