package main

import "fmt"

// 结构体是带类型的字段集合

type person struct {
	name string
	age  int
}

// 构造一个新的person结构体
func newPerson(name string) *person {
	p := person{name: name}
	p.age = 42
	return &p
}

func main() {
	// Go语言支持指针，允许在程序中通过引用传递来传递值和数据结构
	fmt.Println(person{"bob", 20})
	fmt.Println(person{name: "james", age: 30})
	fmt.Println(person{name: "paul"})
	fmt.Println(newPerson("hello"))
}
