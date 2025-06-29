# CSS简介
1. CSS：层叠样式表，又叫级联样式表，简称样式表
2. 后缀名为.css
3. css规则：选择器（需要改变样式的HTML元素）+一条或多条声明（样式）
4. 每一条声明=属性名+属性值
```
<style>
	h1{
		color:red;
		font-size:20px;
	}
</style>
```

# CSS引入方式
## 内联样式
```
<p style="color:red;font-size:20px;">内联样式</p>
```
在相关标签中使用style属性；维护成本高
## 内部样式
用`<style></style>`标签在文档头部定义内部样式表；
多个页面不易于维护；

## 外部样式（推荐）
```
<link rel="stylesheet" href="**.css"> 
```

# 选择器
## 全局选择器：
1. 可与任意元素匹配，优先级最低，一般用于样式初始化
```
*{
	margin:0;
	padding:0;
}
```

## 元素选择器
1. 对某一种标签，**统一**添加样式
```
span{
	color:red;
}
```
## 类选择器
规定用原点 **·** 来定义，针对定义的属于一类的所有标签
```
<h2 class="oneclass">你好</h2>
.oneclass{
	width:600px;
}
```
- 类选择器可以被多种标签使用
- 类名不能以数字开头
- 同一标签可以有多个类，用空格隔开
```
<h2 class="classone classtwo">空格隔开两个class即可</h3>
```

## ID选择器
针对某一特定的标签使用，只能使用1次
```
<p id="text">hello</p>
#text{
	color:red;
}
```
- ID唯一
- 不可以数字开头

## 合并选择器
选择器1，选择器2{
balabala···
}
```
<p>选择器1</p>
<h3>选择器2</h3>

p,h3{
	font-size:20px;
	color:red;
}
```

## 选择器的优先级
1. CSS中，权重用数字表示（权重越大，越优先）
	- 内联样式：1000
	- ID选择器：100
	- 类选择器：10
	- 元素选择器：1
2.  同级时，后面的会覆盖前面的

# 字体属性
## color颜色
```
p{
	color:red;
	//color:#ff0000;
	//color:rgb(255,0,0);
	//color:rgba(255,0,0,1);
}
``` 
## font-size字体大小
注：chrome浏览器能接受的最小字体是12px

## font-weight字体粗细

| 值       | 描述                         |
| ------- | -------------------------- |
| bold    | 粗体                         |
| bolder  | 更粗                         |
| lighter | 更细                         |
| 100-900 | 定义由细到粗，400等同于默认，700等同于bold |
```
H1{font-weight:normal;}
div{font-weight:bold;}
p{font-weight:900;}
```
## font-style字体样式

| 值      | 描述    |
| ------ | ----- |
| normal | 默认值   |
| italic | 定义斜体字 |
## font-family字体样式
指定一个元素的字体，每个值之间用逗号分开，字体名称包含空格时，必须加上引号
```
p{
	font-family:"Microsoft YaHei","Simsun";
} 
```

# 背景属性
## background-color背景颜色
```
<div class="box">
</div>

.box{
	width:300px;
	height:300px;
	background-color:red;
}
```
## background-image背景图片
```
.box2{
	width:200px;
	height:300px;
	background-image:url("image.jpg");
}
```

## background-repeat设置如何平铺背景图

| 值         | 描述       |
| --------- | -------- |
| repeat    | 默认值      |
| repeat-x  | 只在水平方向平铺 |
| repeat-y  | 只在垂直方向平铺 |
| no-repeat | 不平铺      |
## background-size设置背景图大小

| 值          | 描述                                         |
| ---------- | ------------------------------------------ |
| length     | 设置宽度和导读，第一个值为宽度，第二个为高度，如果只设置一个，另一个auto     |
| percentage | 计算相对位置区域的百分比，第一个值宽度，第二个为高度，如果值设置一个，另一个auto |
| cover      | 保持图像比例，填充背景区域（可能发生图像裁剪）                    |
| contain    | 保持图像比例，将图片缩放到最适合背景区域的大小（不会裁剪图像）            |
## background-position设置图片渲染的起始位置
默认值为：0% 0% （左上角）

| 值             | 描述                                             |
| ------------- | ---------------------------------------------- |
| left top      | 左上                                             |
| left center   | 左中                                             |
| left bottom   | 左下                                             |
| right top     | 右上角                                            |
| right center  | 右中                                             |
| center top    | 中上                                             |
| center center | 中中                                             |
| center bottom | 中下                                             |
| x% y%         | 第一个值时水平位置，第二个是垂直位置。默认为0%,0%；如果只设定一个值，其他值默认50%； |
| xpos ypos     | 单位是像素                                          |
# 文本属性
## text-align文本水平对齐方式
```
h1{text-align:center}
h2{text-align:left}
h3{text-align:right}
```
## text-decoration规定文本修饰
```
h1{text-decoration:underline}//下划线
h2{text-decoration:overline}//上划线
h3{text-decoration:line-through}//删除线
```

## text-transform控制文本大小写
```
h1{text-transform:uppercase}//定义全部大小字母
h2{text-transform:capitalize}//定义每个单词开头大写
h3{text-transform:lowercase}//定义全部单词小写
```

## text-indent文本块中首行文本的缩进
```
p{
	text-indent:50px;
}
```

# 表格属性
## border表格边框
```
<table>
	<tr>
		<td>单元格1</td>
		<td>单元格1</td>
		<td>单元格1</td>
	</tr>
	<tr>
		<td>单元格1</td>
		<td>单元格1</td>
		<td>单元格1</td>
	</tr>

table,td{
	border:1px solid black;
}
```
## border-collapse折叠边框
```
//折叠成一个单一的边框
table{border-collapse:collapse;}
table,td{border:1px solid black;}
```

## width、height表格的宽度高度
```
table{
	width:100%;
}
td{
	height:50px;
}
```
## text-align表格文字对齐
默认左中对齐
```
td{
	text-align:center;//水平对齐方式
	vertical-align:bottom;//垂直对齐方式
}
```
## padding表格内边框
```
td{padding:15px;}
```
## 表格颜色
```
table,td,th{border:1px solid green;}
td{
	background-color:green;
	color:white;
}
```
`<th></th>`是表头单元格

# 关系选择器
## 后代选择器
```
选择所有被E包含的F元素，中间用空格隔开
E F{   }
```
## 子代选择器
```
选择所有E元素的直接子元素F，对更深一层的元素不起作用，用>表示
E>F{ }
```
## 相邻兄弟选择器
```
选择紧跟E元素后的F元素，用+号表示，选择相邻（向下）的第一个兄弟元素
E+F{ }
```
## 通用兄弟选择器
```
选择E元素之后的所有兄弟F元素
E~F{ }
```

# 盒子模型
1. 所有HTML元素都可以看作盒子，在CSS中，盒子模型是用来设计和布局时使用的。
2. 包括margin外边距、border边框、padding内边距和content实际内容。
![[Pasted image 20250628194912.png]]
- margin：外边距，透明                 margin-left/right/top/bottom
- border：边框（solid实线  颜色）
- padding：边框（第一个值代表上下，第二个左右）    padding-left/right/top/bottom
- content：内容

# 弹性盒子模型flex box
1. 弹性盒子模型：弹性容器+弹性子元素
2. 通过设置`display`属性的值为`flex`来定义
3. 默认弹性盒子里的元素水平摆放

## flex-direction指定弹性子元素在父容器中的位置
```
flex-direction:row/row-reserve/column/column-reverse
```
- row:横向左到右排列（左对齐），默认的排列方式
- row-reverse:反转横向排列（右对齐，从后往前排，最后一项排在最前）
- column：纵向排列
- column-reverse：反向纵向排列，从后往前排，最后一项在最上面
## justify-content垂直方向对齐方式
```
justify-content:flex-start|flex-end|center
```
- flex-start：弹性项目向**行头**紧挨着填充（默认值）；第一个弹性项的main-start外边距线被放置在该行的main-start边线——<font color="#245bdb">靠上</font>
- flex-end：向**行尾**紧挨着填充。第一个弹性项的main-end外边距线放置在该行的main-end边线——<font color="#245bdb">靠下</font>
- center：弹性项目**居中**紧挨着填充（如果剩余自由空间为负，弹性项目在两个方向上同时溢出

## align-items水平方向上的对齐方式
```
align-items:flex-start|flex-end|center
```
- flex-start：靠左
- flex-end：靠右
- center：居中（如果该行尺寸小于弹性盒子元素的尺寸，两个方向溢出相同长度）

## flex:设置大小权重来分配盒子空间（子元素上的属性）
1. 每个子元素的大小权重
2. 设置flex属性后，width和weight失效
```
box1{
	flex:2;
	background-color:red;
}
box2{
	flex:2;
	background-color:green;
}
box3{
	flex:1;
	background-color:blue;
}
```

# 浮动float
## 浮动定义
1. `float`属性定义元素在哪个方向浮动，任何元素都可以浮动(相当于位与不同图层)

| 值     | 描述     |
| ----- | ------ |
| left  | 元素向左浮动 |
| right | 元素向右浮动 |
- 浮动后元素脱离了文档流
- 浮动只有左右浮动，没有上下浮动
## 元素向左浮动
![[Pasted image 20250629103319.png ]]
```
<style>
	.box{
		background-color:red;
		float:left; 
	}
	.container{
		background-color:blue;
	}
</style>

<div class="box"></div>
<div class="container"></div>

```
## 向右浮动
![[Pasted image 20250629104207.png]]
## 所有元素向左浮动
```
<style>
	div{
		width:300px;
		height:3300px;
		float:left;
	}
	.box1{
		background-color:red;
	}
	.box2{
		background-color:blue;
	}
	.box{
		background-color:green;
	}
</style>

<div class="box1"></div>
<div class="box2"></div>
<div class="box3"></div>
```
![[Pasted image 20250629104730.png]]

- 横向摆放，容器宽度不足时，会在下一行摆放。

# 清除浮动
## 浮动副作用
- 浮动会造成父元素高度塌陷（高度为0，父元素看不见了）
- 后续元素会受到影响（藏在底层）

## 清除浮动
- 设置父元素高度：在发生父元素高度塌陷时，设置父元素高度
- 受影响的元素增加`clear：left/right/both`来清除
- overflow方式：如果父级塌陷且同级元素也受到了影响，在父级标签样式增加`overflow:hidden` + `clear：both` 
- 伪对象方式：如果父级塌陷且同级元素受影响，为父级标签添加伪类`after`，设置空的内容，且使用`clear:both`
```
<style>
	.container{
		width:300px;
		background-color:red;
	}
	.container::after{
		content:"";
		display:block;
		clear:both;
	}
	.box{
		background-color:green;
		width:200px;
		height：200px;
		float:left;
	}
</style>

<div class="container">
	<div class="box"></div>
	<div class="box"></div>
	<div class="box"></div>
</div>
```

# position定位

| 值        | 描述   |
| -------- | ---- |
| relative | 相对定位 |
| absolute | 绝对定位 |
| fixed    | 固定定位 |
- 绝对定位和固定定位会脱离文档流
- 设置定位后，可以用`left/right/top/bottom：200px`调整位置
- 固定定位：（不随着页面滚动变化）
	- 相对定位和绝对定位是**相对于具有定位的父级元素**进行的调整，如父元素不存在，继续向上逐级寻找，直到顶层文档
## z-index堆叠顺序
z-index值越大，越在上层
```
z-index:1
```
