# 模板语法
1. 文本插值：`<span>{{msg}}</span>`  {{}}将数据解释为纯文本
2. 原始HTML：`
	`<p>Using v-html directive: <span v-html="rawHtml"></span></p>`
3. 响应式绑定attribute:
	`<div v-bind:id="dynamicId"></div>`
	`<div :id="dynamicId"></div>`
	- 布尔型attribute
		`<button :disabled="isButtonDisabled">Button</button>`
		当`isButtonDisabled`值为真或为空时，元素包含`disabled`attribute；为其他假值时忽略
	- 动态绑定一个包含多个attribute的对象时：
```
		data() {
			  return {
			    objectOfAttrs: {
			      id: 'container',
			      class: 'wrapper'
			    }
			  }
        }
	<div v-bind="objectOfAttrs"></div>
```

4. 所有的数据绑定种都支持完整的JavaScript表达式，但每个绑定只支持**单一表达式**
5. 指令：带有`v-`前缀的特殊属性