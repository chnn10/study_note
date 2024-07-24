package main

import (
	"fmt"
	"log"
	"net/http"
)

func main() {
	http.HandleFunc("/", indexHandler)
	http.HandleFunc("/hello", helloHandler)
	log.Fatal(http.ListenAndServe(":9999", nil))

	// http://localhost:9999/
	// http://localhost:9999/hello
}

// 处理所有路劲为"/"的HTTP请求，请求到达的时候，会遍历请求的头部信息，并将其写入HTTP响应中
func indexHandler(w http.ResponseWriter, req *http.Request) {
	fmt.Fprintf(w, "url.path = %q\n", req.URL.Path)
}

func helloHandler(w http.ResponseWriter, req *http.Request) {
	for k, v := range req.Header {
		fmt.Fprintf(w, "header[%q] = %q\n", k, v)
	}
}
