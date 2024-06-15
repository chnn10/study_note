
package main

import (
	"fmt"
	"unicode/utf8"
)

func main() {
	// Go语言中的字符串是一个只读的byte类型的切片，Go语言中的字符的概念被成为rune，表示的是Unicode编码的整数
	const str = "陈荣一"
	fmt.Println(str)
	fmt.Println("len:", len(str)) // 一个汉字占三个字符

	// 对字符串进行索引会在每个索引处生成原始字节值，遍历的是Unicode的所有字节的十六进制值
	for i := 0; i < len(str); i++ {
		fmt.Print("%x ", str[i])
	}
	fmt.Println()

	// range循环专门处理字符串并解码每个rune在字符串中的偏移量
	for idx, runeValue := range str {
		fmt.Printf("%#U starts at %d\n", runeValue, idx)
	}

	fmt.Println("\nUsing DecodeRuneInString")
	for i, w := 0, 0; 0 < len(str); i += w {
		runeValue, width := utf8.DecodeRuneInString(str[i:])
		fmt.Printf("%#U starts at %d\n", runeValue, i)
		w = width
		examineRune(runeValue)
	}

}

func examineRune(r rune) {
	if r == 't' {
		fmt.Println("found tee")
	} else if r == '陈' {
		fmt.Println("found so sua")
	}
}
