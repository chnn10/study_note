package main

import "fmt"

func main() {
	// map是Go内建的关联数据类型

	// 创建一个空的map，使用内建的函数make，mak(map[key]value)
	m := make(map[string]int)
	m["key1"] = 1
	m["key2"] = 2
	fmt.Println("map", m)

	// 使用name[key]
	v1 := m["key1"]
	fmt.Println("v1", v1)

	// 使用lan可以获取键值对的数量
	fmt.Println("len of m is", len(m))

	// 使用delete可以从map中删除键值对
	delete(m, "key1")
	fmt.Println("map", m)

	// 初始化一个新的map
	map1 := map[string]int{"key1": 1, "key2": 2}
	fmt.Println(map1)
}
