package main

import "fmt"

func main() {
	// range是用于迭代各种各样的数据库的

	// 我们可以使用range对slice中的元素进行求和
	nums := []int{2, 3, 4}
	sum := 0
	for _, num := range nums {
		sum += num
	}
	fmt.Println("sum:", sum)

	// range在数组中和slice中提供对每项的索引和值的访问，上面不需要索引，我们使用了空白标识符_
	for i, num := range nums {
		if num == 3 {
			fmt.Println("index:", i)
		}
	}

	// range在map中迭代键值对
	map1 := map[string]string{"a": "apple", "b": "banana"}
	for k, v := range map1 {
		fmt.Printf("%s ---> %s\n", k, v)
	}

	// range也可以只是遍历map的键
	for k := range map1 {
		fmt.Println("key:", k)
	}
}
