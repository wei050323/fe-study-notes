# 1. 文档：DOM中的D
创建一个网页并将它加载到web浏览器时，网页文档就转换为一个文档对象。
# 2. 对象：DOM中的O
## 对象分类
分三类：
- 用户定义对象
- 内建对象：内建在JavaScript中的对象
- 宿主对象：由浏览器提供的对象

## BOM：浏览器对象模型
## DOM：
浏览器窗口内的document对象的主要功能：处理网页内容

# 3. 模型：DOM中的M
1. DOM代表着加载到浏览器窗口的当前网页
2. 网页文档可以用家谱树表示

# 4. 节点
## 元素节点
1. DOM的原子是元素节点，标签的名字就是元素的名字
2. 唯一的根元素是`<html>`
## 文本节点
1. `HTML`中，文本节点包含在元素节点的内部（就是标签中的文字部分）
## 属性节点
1. 放在起始标签里，用来对元素做出更具体的描述

## 获取元素
### getElementById
1. `document.getElementById(id)`   id值必须放在引号内
返回一个<font color="#ff0000">对象</font>，即id对应的那个独一无二的元素

### getElementByTagName
1. `'document.getElementByTagName(tag)`,参数为标签的名字，引号
返回一个<font color="#ff0000">对象数组</font>
2. 通配符`"*"`：所有的东西

### getElementByClassName
1. `document.getElementByClassName(class)`,参数为类名
返回一个对象数组（具有相同类名）
2. 查找具有多个类名的元素
`document.getElementByClassName("important sale")`指定多个类名时，在字符串参数中用空格分隔类名即可。（顺序无关，只要含有这两个类名就算）

# 5. 获取和设置属性
## getAttribute
1. `object.getAttribute(attribute)`参数为要查询的属性的名字

## setAttribute
1. `object.setAttribute(attribute,value)` 修改对象对应的属性的值
2. 通过`setAttribute`修改的属性值不会反映在文档本身的源代码里
3. 原因：DOM的工作模式：先加载文档的静态内容，在动态刷新，动态刷新不影响文档的静态内容

## childNodes
`element.childNodes` 获取元素的所有子元素，返回一个包含这个元素所有子元素的数组

## nodeTylpe属性
1. `node.nodeType`获取节点的`nodeType`属性
2. `nodeType`的属性值为数字：
- 元素节点：1
- 属性节点：2
- 文本节点：3

## nodeValue属性
1. 想要得到p标签里面的文本的值，其实是p标签的第一个子元素的`nodeValue` ，也就是`p.firstChild.nodeValue`