ECMAScript是一种语言标准，JavaScript是ECMASript的一种实现。
# JavaScript的使用
## 嵌入网页
```
<head>
	<script src="####.js"></script>
</head>
```
- 一个单独的`<script>`标签不能同时有`src`特性和内部包含的代码。

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
3. `while`循环
4. `do···while`循环：先执行一次

# Map
```
let m = new Map([['Michael', 95], ['Bob', 75], ['Tracy', 85]]);
m.get('Michael'); // 95
```

```
let m = new Map(); // 空Map
m.set('Adam', 67); // 添加新的key-value
m.set('Bob', 59);
m.has('Adam'); // 是否存在key 'Adam': true
m.get('Adam'); // 67
m.delete('Adam'); // 删除key 'Adam'
m.get('Adam'); // undefined
```
一个`key`只能对应一个`value` ,对同一个key再次赋值会覆盖前面的值

# Set
```
let s = new Set([1, 2, 3, 3, '3']);
s; // Set {1, 2, 3, "3"}重复元素自动过滤
s.add(4);
s.delete(3);
s;//Set{1,2,4,'3'}
```
# Iterable
1. ES6引入了新的`iterable`类型，含`Array`、`Map`、`Set`。
2. 用`for ··· of`遍历`iterable`类型
3. `for···in `和`for···of`的区别：
`for···in`实际上是遍历对象的属性名称，把数组的索引视为属性，`for···of`只循环集合本身的元素。
4. `iterable`内置的`forEach`方法，接受一个函数，每次迭代自动回调该函数
```
let a = ['A', 'B', 'C'];
a.forEach(function (element, index, array) {
    // element: 指向当前元素的值
    // index: 指向当前索引
    // array: 指向Array对象本身
    console.log(`${element}, index = ${index}`);
});
```
# 函数
## 函数的定义及调用
1. 
```
//定义1
function abs(x) {
    函数体
}
//定义2
let abs = function (x) {
    函数体
};

```
2. `arguments`只在函数内部起作用，并且永远指向当前函数的调用者传入的所有参数
3. `rest`参数
```
function foo(a, b, ...rest) {
    console.log('a = ' + a);
    console.log('b = ' + b);
    console.log(rest);
}

foo(1, 2, 3, 4, 5);
// 结果:
// a = 1
// b = 2
// Array [ 3, 4, 5 ]

foo(1);
// 结果:
// a = 1
// b = undefined
// Array []
```
## 变量作用域
1. `var`声明的变量作用于函数体内部
2. `JavaScript`中，会先扫描整个函数体的语句，将所有用`var`申明的变量提升到函数顶部，但**不会**提升变量的**赋值**。
3. 全局作用域：不在任何函数内定义的变量具有全局作用域。实际上，`JavaScript`默认有一个全局对象`window`，全局作用域的变量实际上被绑定到`window`的一个属性
4. `let`和`const`具有块级作用域
## 解构赋值
1. `ES6`开始，`JavaScript`引入解构赋值，可以同时对一组变量进行赋值
```
let [x, y, z] = ['hello', 'JavaScript', 'ES6'];

// x, y, z分别被赋值为数组对应元素:
console.log(`x = ${x}, y = ${y}, z = ${z}`);

let [, , z] = ['hello', 'JavaScript', 'ES6']; // 忽略前两个元素，只对z赋值第三个元素
z; // 'ES6'
```

```
let person = {
    name: '小明',
    age: 20,
    gender: 'male',
    passport: 'G-12345678',
    school: 'No.4 middle school'
};
let {name, age, passport} = person;

// name, age, passport分别被赋值为对应属性:
console.log(`name = ${name}, age = ${age}, passport = ${passport}`);

```

```
let person = {
    name: '小明',
    age: 20,
    gender: 'male',
    passport: 'G-12345678',
    school: 'No.4 middle school'
};

// 把passport属性赋值给变量id:
let {name, passport:id} = person;
name; // '小明'
id; // 'G-12345678'
// 注意: passport不是变量，而是为了让变量id获得passport属性:
passport; // Uncaught ReferenceError: passport is not defined
```
2. 交换两个变量
```
let x=1,y=2;
[x,y]=[y,x];
```
3. 快速获取当前页面的域名和路径：
```
let {hostname:domain, pathname:path} = location;
```
## 方法
1. 绑定到对象上的函数成为方法
2. `this`变量指向当前对象
3. 使用`that`捕获`this`后，可以在方法内部定义其他函数
```
'use strict';

let xiaoming = {
    name: '小明',
    birth: 1990,
    age: function () {
        let that = this; // 在方法内部一开始就捕获this
        function getAgeFromBirth() {
            let y = new Date().getFullYear();
            return y - that.birth; // 用that而不是this
        }
        return getAgeFromBirth();
    }
};

xiaoming.age(); // 25
```
4. 重定义`this`的指向问题：
- `apply`：把参数打包成`Array`再传入
```
function getAge() {
    let y = new Date().getFullYear();
    return y - this.birth;
}

let xiaoming = {
    name: '小明',
    birth: 1990,
    age: getAge
};

xiaoming.age(); // 25
getAge.apply(xiaoming, []); // 25, this指向xiaoming, 参数为空
```
- `call()` :把参数按顺序传入
对于普通函数调用，常把`this`绑定为`null`
```
Math.max.apply(null, [3, 5, 4]); // 5
Math.max.call(null, 3, 5, 4); // 5
```
## 高阶函数
1. 定义：可以接受另一个函数作为参数的函数
```
function add(x, y, f) {
    return f(x) + f(y);
}
```
### map( )
1. 使用`map`将函数`f(x)`作用在`Array`的每一个元素并把结果生成一个新的`Array`
```
function pow(x) {
    return x * x;
}

let arr = [1, 2, 3, 4, 5, 6, 7, 8, 9];
let results = arr.map(pow); // [1, 4, 9, 16, 25, 36, 49, 64, 81]
console.log(results);
```
### reduce( )
```
[x1, x2, x3, x4].reduce(f) = f(f(f(x1, x2), x3), x4)
```
1. `reduce()`把一个函数作用在数组上，这个函数必须接受两个参数，`reduce`把结果继续和序列的下一个元素做累计计算
2. 数组元素只有一个时，需要补上0
```
let arr = [123];
arr.reduce(function (x, y) {
    return x + y;
}, 0); // 123
```

### filter( )
```
let arr = [1, 2, 4, 5, 6, 9, 10, 15];
let r = arr.filter(function (x) {
    return x % 2 !== 0;
});
r; // [1, 5, 9, 15]
```
`filer()`把传入的函数一次作用于每个元素，根据返回值是`true`还是`false`决定保留还是丢弃该元素。

### sort( )
1. `sort()`方法默认把所有元素先转换成`String`再排序。
```
// 看上去正常的结果:
['Google', 'Apple', 'Microsoft'].sort(); // ['Apple', 'Google', 'Microsoft'];

// apple排在了最后:
['Google', 'apple', 'Microsoft'].sort(); // ['Google', 'Microsoft", 'apple']

// 并非按大小排！:
[10, 20, 1, 2].sort(); // [1, 10, 2, 20]

```
2. 可以自定义`sort()`
```
//倒叙排序的实现
let arr = [10, 20, 1, 2];
arr.sort(function (x, y) {
    return y - x;
}); // [20, 10, 2, 1]
```
给`sort()`传入的比较函数接受`x`和`y`两个参数，返回负数，这两个数位置不变，返回整数，位置交换。
- <font color="#245bdb">`sort()`方法会直接改变当前数组！</font>
### every( )
判断数组的所有元素是否满足测试条件。
```
let arr = ['Apple', 'pear', 'orange'];
console.log(arr.every(function (s) {
    return s.length > 0;
})); // true, 因为每个元素都满足s.length>0

console.log(arr.every(function (s) {
    return s.toLowerCase() === s;
})); // false, 因为不是每个元素都全部是小写
```
### find( )
查找符合条件的第一个元素，找到了返回该元素，否则返回`undefined`
```
let arr = ['Apple', 'pear', 'orange'];
console.log(arr.find(function (s) {
    return s.toLowerCase() === s;
})); // 'pear', 因为pear全部是小写

console.log(arr.find(function (s) {
    return s.toUpperCase() === s;
})); // undefined, 因为没有全部是大写的元素

```
### findIndex( )
作用和`find()`类似，也是查找符合条件的第一个元素，不同之处在于`findIndex()`会返回这个元素的索引，未找到返回-1

### forEach( )
把每个元素依次作用于传入的函数，但不会返回新的数组。`forEach`常用于遍历数组。因此，传入的函数不需要返回值。
```
let arr = ['Apple', 'pear', 'orange'];
arr.forEach(x=>console.log(x)); // 依次打印每个元素
```

# 闭包
