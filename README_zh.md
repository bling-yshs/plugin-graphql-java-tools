# IDEA插件 graphql-java-tools

## [插件主页](https://plugins.jetbrains.com/plugin/23024-graphql-java-tools)

<!-- Plugin description -->
**Graphql-Java-Tools** 是一款增强IDEA对基于[Graphql Java](https://github.com/graphql-java/graphql-java)的脚手架[graphql-java-tools](https://github.com/graphql-java-kickstart/graphql-java-tools)支持的插件。

#### 主要功能如下：
* 快速从graphql Query/Mutation/Field 跳转到Java的Resolver方法及从方法返回到graphql文件

### 使用
* 打开graphql文件并单击左边的箭头以跳转到Java Resolver方法。
* 打开Java Resolver类文件并单击左边的箭头跳转到graphql文件。

***感谢 @moztl @wuzhizhan 在 [Free MyBatis Tool](https://github.com/moztl/Free-Mybatis-Tool)的贡献***
<!-- Plugin description end -->

### 待实现

- [x] 将匹配机制从名称约定改为泛型检测
- [ ] 支持Type到Resolver类的跳转
- [ ] 支持scalar/directive到Java实现的跳转
- [ ] 支持enum/input到Java枚举类的跳转
- [ ] 支持通过Schema字段生成JavaResolver代码