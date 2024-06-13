package main

import "fmt"

func main() {
	// Slice是Go中一个重要的数据类型，它提供了比数组更强大的序列化交互方式。

	// 使用内建函数make，创建一个长度为3的string类型的slice
	s := make([]string, 3)
	fmt.Println("emp:", s)

	// 设置值和数组是一样的，len也是一样的用法
	s[0] = "a"
	s[1] = "b"
	s[2] = "c"
	fmt.Println("set:", s)
	fmt.Println("get", s[2])
	fmt.Println("len: ", len(s))

	// 内建函数append，该函数会返回一个包含了一个或者多个新值的slice
	s = append(s, "d")
	fmt.Println("emp:", s)
	s = append(s, "e", "f")
	fmt.Println("emp:", s)

	// slice还可以copy，我们可以创建一个空的和s长度相同的slice的c，然后将s复制给c
	c := make([]string, len(s))
	copy(c, s)
	fmt.Println("copy_s_to_c", s)

	// slice还可以通过slice[low:high]语法创建进行切片操作
	l1 := s[2:5] // 这里是不包含5的
	fmt.Println("切片得到l1", l1)

	l2 := s[2:] // 下标2后面的元素，包含2
	fmt.Println("切片得到l2", l2)

	l3 := s[:5] // 下标5后面的元素，不包含5
	fmt.Println("切片得到l3", l3)

}
