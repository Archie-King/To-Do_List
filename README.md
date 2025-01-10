# ToDo-List Project Development Requirements Document -----ing.....

---

## 一、项目结构

```
ToDoList/
├── Backend/                  // 后端项目目录
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/
│   │   │   │   ├── com/
│   │   │   │   │   ├── todo/
│   │   │   │   │   │   ├── controller/        // 控制器
│   │   │   │   │   │   │   ├── AuthController.java
│   │   │   │   │   │   │   ├── TaskController.java
│   │   │   │   │   │   │   ├── CategoryController.java
│   │   │   │   │   │   ├── model/             // 数据模型
│   │   │   │   │   │   │   ├── User.java
│   │   │   │   │   │   │   ├── Task.java
│   │   │   │   │   │   │   ├── Category.java
│   │   │   │   │   │   ├── repository/        // 数据库操作
│   │   │   │   │   │   │   ├── UserRepository.java
│   │   │   │   │   │   │   ├── TaskRepository.java
│   │   │   │   │   │   │   ├── CategoryRepository.java
│   │   │   │   │   │   ├── service/           // 业务逻辑
│   │   │   │   │   │   │   ├── AuthService.java
│   │   │   │   │   │   │   ├── TaskService.java
│   │   │   │   │   │   │   ├── CategoryService.java
│   │   │   │   │   │   ├── dto/               // 数据传输对象
│   │   │   │   │   │   │   ├── AuthRequest.java
│   │   │   │   │   │   │   ├── AuthResponse.java
│   │   │   │   │   │   │   ├── TaskDTO.java
│   │   │   │   │   │   │   ├── CategoryDTO.java
│   │   │   │   │   │   └── exception/         // 异常处理
│   │   │   │   │   │   │   ├── CustomException.java
│   │   │   │   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   │   │   │   ├── BadRequestException.java
│   │   │   │   │   │   │   ├── ErrorHandler.java
│   │   │   │   │   ├── Application.java       // 主程序入口
│   │   │   ├── resources/
│   │   │   │   ├── application.properties     // 配置文件
│   │   │   │   ├── data.sql                   // 初始化数据
│   │   │   │   └── schema.sql                 // 数据库表定义
│   ├── pom.xml                                // Maven 配置文件
│   ├── Dockerfile                             // Docker 配置
│   └── README.md                              // 项目说明
│
└── Frontend/                // 前端项目目录
    ├── public/              // 公共静态资源
    ├── src/                 // 源代码
    │   ├── components/      // 可复用组件
    │   ├── pages/           // 页面组件
    │   ├── services/        // API 服务
    │   ├── utils/           // 工具函数
    │   ├── App.js           // 应用主入口
    │   └── index.js         // 渲染入口
    ├── package.json         // 前端依赖配置
    ├── .env                 // 环境变量
    ├── Dockerfile           // Docker 配置
    └── README.md            // 项目说明
```

---

## 二、后端部分开发需求

### 1. 功能列表及详细需求
1. **用户管理**
    - 用户注册：支持用户名、邮箱和密码注册。
    - 用户登录：JWT 认证，支持登录并生成 Token。
    - 用户权限：不同用户仅能管理自己的任务。

2. **任务管理**
    - 创建任务：包括标题、描述、截止日期和状态。
    - 编辑任务：可修改任务内容和状态。
    - 删除任务：支持删除特定任务。
    - 查看任务：支持按状态、关键字、日期筛选任务。

3. **分类管理**
    - 添加分类：为任务增加分类标签。
    - 编辑分类：修改分类名称。
    - 删除分类：删除分类时需要检查相关任务。

4. **其他功能**
    - 数据校验：防止输入无效数据。
    - 异常处理：统一错误返回格式。
    - 日志管理：记录关键操作。

### 2. API 设计
1. 用户管理
```
POST /api/auth/register
POST /api/auth/login
GET  /api/users/me
```
2. 任务管理
```
POST /api/tasks
GET  /api/tasks
GET  /api/tasks/{id}
PUT  /api/tasks/{id}
DELETE /api/tasks/{id}
```
3. 分类管理
```
POST /api/categories
GET  /api/categories
PUT  /api/categories/{id}
DELETE /api/categories/{id}
```

### 3. 数据库设计

**表 1：users（用户表）**
```
CREATE TABLE users (
  id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(50) UNIQUE NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL
);
```
**表 2：tasks（任务表）**
```
CREATE TABLE tasks (
  id INT PRIMARY KEY AUTO_INCREMENT,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  status VARCHAR(20) DEFAULT 'pending',
  due_date DATE,
  category_id INT,
  user_id INT,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (category_id) REFERENCES categories(id)
);
```
**表 3：categories（分类表）**
```
CREATE TABLE categories (
  id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(50) UNIQUE NOT NULL
);
```

---

## 三、前端部分开发需求

### 1. 页面设计

1. 登录/注册页面
    - 表单输入框：用户名、邮箱、密码。
    - 登录成功后跳转到任务管理页面。
2. 任务管理页面
    - 显示任务列表。
    - 筛选任务：按状态、分类、日期。
    - 添加/编辑/删除任务。
3. 分类管理页面
    - 显示分类列表。
    - 添加/编辑/删除分类。

### 2. 功能模块与前端交互
- 使用 Axios 与后端 API 交互。
- 使用 React Context 或 Redux 管理状态。
- 页面跳转和动态路由使用 React Router。

### 3. 样式建议
- 使用 MUI 组件库（Material-UI）。
- 颜色主题：
    - 主色：#1976D2（蓝色）
    - 次色：#FF4081（粉色）
    - 背景色：#F5F5F5
- 布局建议：
    - 顶部导航栏固定。
    - 响应式设计支持移动端。

---

## 四、总结
1. 本项目适合初学者学习 Java 后端开发，包含常见功能模块和数据库操作。
2. 前端与后端通过 RESTful API 完成交互，满足全栈开发需求。
3. 未来支持 Docker 部署和云端托管，提供扩展能力。

需要具体代码实现或某些功能模块的详细示例时，请进一步讨论！

