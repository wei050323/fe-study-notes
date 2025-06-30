ECMAScript是一种语言标准，JavaScript是ECMASript的一种实现。
# JavaScript的使用
## 嵌入网页
```
<head>
	<script src="####.js"></script>
</head>
```

```
<head>
	<script>
	</script>
</head>
```
## 编写
不要用记事本，会自己添加BOM头（判断文本文件是哪一种Unicode编码的标记。

# 数据类型和变量
## Number
- NaN：无计算结果
- Infinity：无限大（超过Numbe能表示的最大值）
- 不区分整数和浮点数`12.00 === 12`
## 字符串
## 布尔值
## 比较运算符
1. 
	- `==`：会自动转换数据类型，<font color="#245bdb">答案诡异</font>
	- `===`：常用，数据类型不一致会i返回`false`
2. `NaN`与任何值都不等，包括自己
	判断NaN的唯一方法：
```
isNaN(NaN); //true
```
3. 浮点数比较有误差
## Biglnt
1. 能表示的最大整数：$2^{53}$,超过要用BigInt
2. 使用方式：整数后+n
3. `BigInt( )`可将Number和字符串转换为BigInt
4. `BigInt`之间可以正常运算，但不可和Number一起运算

## null和undefined
1. null:表示一个“空”的值（常用）
2. undefined：值未定义（仅在判断函数参数是否传递时使用）

## 数组
1. 可含任意数据类型

## 对象
1. 键-值对组成的无序集合
```
var person = {
    name: 'Bob',
    age: 20,
    tags: ['js', 'web', 'mobile'],
    city: 'Beijing',
    hasCar: true,
    zipcode: null
};

```
2. 键都是字符串类型，值任意
3. 获取一个对象的属性，用`对象变量.属性名`

## 变量
1. 变量名：大小写英文字母、数字、`&`和`_`的组合。不可数字开头，不可为关键字
2. 同一变量可反复赋值，类型可变
3. 同一变量只可用`var`申明一次
4. 动态语言
5. 使用`var`申明的变量不是全局变量，范围限制在函数体内（强制使用`var`申明的`strict模式`：第一行写`'use strict'`)

# 字符串
## 转义字符
`'和"`可用转义字符`\`标识

| 转移字符   | 含义        |
| ------ | --------- |
| \n     | 换行        |
| \t     | 制表符       |
| `\\`   | \         |
| \x##   | ASCLL字符   |
| \u#### | Unicode字符 |
## 多行字符串
```
`这是一个
多行
字符串`;
```

## 模板字符串：
1. 字符串可用`+`号连接
2. 
```
let name = '小明';
let age = 20;
let message = `你好, ${name}, 你今年${age}岁了!`;
alert(message);
```
## 字符串常见操作
1. 获取长度：`s.length()`
2. 获取指定位置字符：`s[2]`（超出范围的索引不会报错，但返回undefined）
3. 字符串不可变，对某个索引赋值，不报错且无效
4. 下列方法，返回一个新字符串，不改变原字符串

| 方法               | 描述                   |
| ---------------- | -------------------- |
| s.toUpperCase( ) | 全部变大写                |
| s.toLowerCase( ) | 全部变小写                |
| s.indexOf('###') | 返回指定字符串出现位置，未找到返回-1  |
| s.substring(0,5) | 返回指定索引区间的子串（0-5不包含5） |
| s.substring(5)   | 返回索引开始到结束的子串         |
# 数组
1. 直接给`array`的`length`赋一个新值会导致数组大小变化
```
let arr = ['A', 'B', 'C'];
console.log(arr.length); // 3
// 调整数组大小:
arr.length = 6;
console.log(arr); // arr变为['A', 'B', 'C', undefined, undefined, undefined]
// 调整数组大小:
arr.length = 2;
console.log(arr); // arr变为['A', 'B']
```
2. 数组可改变，每个元素可通过索引赋新值
3. 通过索引赋值时，超过范围会自动扩充，中间补`undefined`
4. 

| 方法                      | 解释                                  |
| ----------------------- | ----------------------------------- |
| arr.indexOf('#')        | 查找指定元素位置                            |
| arr.slice(0,3)          | 截取数组的部分元素，返回一个子数组（含前不含后）            |
| arr.slice(2)            | 截取从索引2开始到结束的子数组                     |
| arr.slice()             | 赋值当前数组                              |
| arr.push(##)            | 向末尾添加若干元素                           |
| arr.pop()               | 删除最后一个元素，空数组pop()不报错，返回undefined    |
| arr.unshift(##)         | 在数组头部添加若干元素                         |
| arr.shift( )            | 删掉数组第一个元素，空数组不报错，返回undefined        |
| arr.sort()              | 对当前数组排序，直接修改当前数组                    |
| arr.reverse()           | 反转当前数组元素顺序                          |
| arr.splice(2，3，'a','v') | 从指定索引2开始删除3个元素，再从该位置添加若干元素（返回删除的元素） |
| arr.concat(###)         | 连接两个数组（数组+元素也可），返回一个新数组             |
| arr.join(#)             | 把当前数组的每个元素用指定字符串连接起来，返回连接后的字符串      |
# 对象
1. 属性含特殊字符，必须用`' '`括起来，访问需要用`###['###']`来访问
2. 访问不存在的属性不报错，返回`undefined`
3. 检测某对象是否有某属性：`'###' in ####`(`true`：该对象有这个属性or继承得到)
4. 检测自身具有某属性且非继承：`####.hasOwnProperty('####')

# 条件判断
1. `null`、`undefined`、`0`、`NaN`和空字符串`''` 视为`false`,其他值都视为`true`

# 循环
1. `for`循环三个条件都可省略，无退出条件时必须用`break`退出，否则为死循环。
2. `for···in `：一次循环一个对象的所有属性
```
let o = {
    name: 'Jack',
    age: 20,
    city: 'Beijing'
};
for (let key in o) {
    console.log(key); // 'name', 'age', 'city'
}
```
要过滤掉对象集成的属性需要用`hasOwnProperty()`
