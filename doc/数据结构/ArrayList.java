public class ArrayList {
    // 元素数量
    private int size;

    // 存放元素的数组
    private int[] data;

    public ArrayList() {
        data = new int[5];    // 构造函数，默认就是创建一个容量是10的数组
    }

    /**
     * 数组最后一个位置添加元素，然后将size++
     * @param ele
     */
    public void add(int ele) {
        ensureCapacity();
        data[size] = ele;
        size++;
    }

    /**
     * 根据索引获取数组位置的元素
     * @param index
     * @return
     */
    public int getByIndex(int index) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("获取元素的索引不正确");
        }
        return data[index];
    }

    /**
     * 在index位置插入元素ele
     * @param index
     * @param ele
     */
    public void addByIndex(int index, int ele) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("插入元素的索引不正确");
        }

        ensureCapacity();
        for (int i = size - 1; i >= index; i--) {
            data[i+1] = data[i];
        }
        data[index] = ele;
        size++;
    }

    /**
     * 获取元素的位置
     * @param ele
     * @return
     */
    public int indexOf(int ele) {
        for (int i = 0; i < size; i++) {
            if (data[i] == ele) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 删除index位置的元素
     * @param index
     * @return
     */
    public int removeByIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("删除元素的索引不正确");
        }

        int ret = data[index];

        for (int i = index; i < size - 1; i++) {
            // 后面一个元素 覆盖 前一个位置元素
            data[i] = data[i+1];
        }
        size--;
        return ret;
    }

    /**
     * 进行扩容操作
     */
    private void ensureCapacity() {
        int capacity = data.length;
        // 注意：这里是需要等于的
        if (size >= capacity) {
            resize();
        }
    }

    private void resize() {
        int oldCapacity = data.length;
        int[] newData = new int[oldCapacity * 2];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }

    /**
     * ----------------------- 下面是测试代码用的------------------------
     * @return
     */

    @Override
    public String toString() {
        // 打印形式为: size=5, [99, 88, 77, 66, 55]
        StringBuilder string = new StringBuilder();
        string.append("size=").append(size).append(", [");
        for (int i = 0; i < size; i++) {
            if(0 != i) string.append(", ");
            string.append(data[i]);
        }
        string.append("]");
        return string.toString();
    }

    public static void main(String[] args) {
        ArrayList arrayList = new ArrayList();
        System.out.println("---------- 测试添加操作 ----------");
        arrayList.add(12);
        arrayList.add(13);
        arrayList.add(14);
        arrayList.add(15);
        arrayList.add(16);
        System.out.println(arrayList);
        System.out.println("---------- 测试插入操作 ----------");
        arrayList.addByIndex(4,99);
        System.out.println(arrayList);
        System.out.println("---------- 测试返回index ----------");
        System.out.println(arrayList.indexOf(12));
        System.out.println("---------- 测试删除index位置的元素 ----------");
        System.out.println(arrayList.removeByIndex(4));
        System.out.println(arrayList);
        System.out.println("---------- 测试扩容操作 ----------");
        arrayList.add(88);
        System.out.println(arrayList);

    }

}







