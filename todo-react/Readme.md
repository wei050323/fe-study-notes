# React+Ts+Node+MongoDB 构建 TodoApp 项目总结

## 1 设计 API

### 1.1 启动

#### 1.1.1 初始化 NodeJS 应用

npm init -y

#### 1.1.2 构建项目目录

![alt text](image.png)

#### 1.1.3 配置 tsconfig.json

![alt text](image-1.png)

- outDir：编译后的 JS 输出到`dist/js`
- rootDir:TypeScript 编译`src`目录
- include:告诉编译器包含哪些文件
- exclude:排除不需要编译的文件

#### 1.1.4 安装 TypeScript

`npm install -g typescript`

#### 1.1.5 安装 EXpress、CORS、MongoDB(Mongoose)

`npm install express cors mongoose`
![alt text](image-2.png)
上图是开源项目的维护者请求资金支持的意思，可以不用管。只是信息提示，不影响包的功能。

#### 1.1.6 安装类型依赖

`npm install --save-dev @types/node @types/express @types/mongoose @types/cors`

#### 1.1.7 安装 nodemon 与 concurrently

`npm install --save-dev concurrently nodemon`

#### 1.1.8 配置 nodemon.json（监听 TS 编译后目录）

![alt text](image-3.png)

#### 1.1.9 更新 package.json 脚本

在`package.json`中加入：

```
"scripts": {
  "build": "tsc",
  "start": "concurrently \"tsc -w\" \"nodemon\""
}
```

含义：

- `tsc -w`:持续编译 TypeScript
- `nodemon`:监视编译后的 JS 并自动重启服务器
- `concurrently`:同时执行两个命令

![alt text](image-4.png)

### 1.2 创建 todo 类型

![alt text](image-5.png)
创建继承`mongoose`提供的`Document`类型的 ITodo 接口。

### 1.3 创建 todo 模块

![alt text](image-6.png)

### 1.4 创建 API 控制器

#### 获取、新增、更新和删除 todo

获取
![alt text](image-7.png)
新增
![alt text](image-8.png)
更新
![alt text](image-9.png)
删除
![alt text](image-10.png)

### 1.5 创建 API 路由

![alt text](image-11.png)
| Router 方法 | 对应 HTTP | 用途 |
| ----------------- | ------- | ---------- |
| `router.get()` | GET | 获取资源 |
| `router.post()` | POST | 创建资源 |
| `router.put()` | PUT | 更新资源（整体更新） |
| `router.patch()` | PATCH | 部分更新 |
| `router.delete()` | DELETE | 删除资源 |

### 1.6 创建服务器

#### 1.6.1 注册 MongoDB Atlas

- 地址：https://www.mongodb.com/atlas/database
- 注册，设置 Username,Password
- 创建一个 Cluster,选择 Free 的。
- 设置 IP 白名单，允许所有 IP 访问`0.0.0.0/0`

#### 1.6.2 在 nodemon.json 中加一些环境变量来保存 MongoDB 的凭据

![alt text](image-12.png)
创建成功后在 MongoDB 看到下图提示：
![alt text](image-13.png)

#### 1.6.3 连接服务器

![alt text](image-14.png)
成功连接如图：
![alt text](image-15.png)

## 2 用 React 和 TypeScript 创建客户端

### 2.1 构建

1. 终端运行：
   `npx create-react-app my-app --template typescript`
   可以根据提示` cd my-app``npm start `测试一下
   ![alt text](image-16.png)
2. 安装 axios
   `npm install axios`
3. 安装完成后，构建项目目录
   ![alt text](image-17.png)
   `src/type.d.ts`被用来存放类型，因为几乎在每个文件中都使用了它们，所以添加了扩展`.d.ts`，使类型全局可用。现在我们不需要再导入它们。

### 2.2 创建 todo 类型

![alt text](image-18.png)

### 2.3 从 API 获取数据

![alt text](image-19.png)
![alt text](image-20.png)
![alt text](image-21.png)

### 2.4 创建组件

#### 添加 todo 表单

![alt text](image-23.png)

### 2.5 展示 Todo

![alt text](image-24.png)

### 2.6 获取和展示数据

![alt text](image-25.png)
![alt text](image-26.png)

## 3 资源
