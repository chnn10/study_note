package main

import (
	"fmt"
	"time"
)

func main() {
	// switch是多分支情况的快捷的条件语句

	// 一个最基本的switch
	i := 2
	fmt.Print("write ", i, " as ")
	switch i {
	case 1:
		fmt.Println("one")
	case 2:
		fmt.Println("two")
	case 3:
		fmt.Println("three")
	}

	// 同一个case语句中，我们可以使用逗号来分隔多个表达式。
	switch time.Now().Weekday() {
	case time.Saturday, time.Sunday:
		fmt.Println("今天是周末")
	default:
		fmt.Println("今天是工作日")
	}

	// 不带表达式的switch语句是实现if/else逻辑的另一种方式
	t := time.Now()
	switch {
	case t.Hour() < 12:
		fmt.Println("现在是上午的时间")
	default:
		fmt.Println("现在是下午的时间")
	}

	// 类型开关，比较类型而非值，可以用来发现一个接口值的类型。这个案例中，变量t在每一个分支中会有相应的类型\
	whatAmI := func(i interface{}) {
		switch t := i.(type) {
		case bool:
			fmt.Println("bool类型")
		case int:
			fmt.Println("int类型")
		default:
			fmt.Printf("未知类型 %T\n", t)
		}
	}

	whatAmI(true)
	whatAmI(1)
	whatAmI("hello")

}
