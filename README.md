# IDEA plugin of graphql-java-tools

## [Plugin homepage](https://plugins.jetbrains.com/plugin/23024-graphql-java-tools)

<!-- Plugin description -->
**Graphql-Java-Tools** Plugins is a plugin that enhances idea support for GraphQL Java.

#### Here is the main functions:
* navigate from the graphql Query/Mutation to Java Resolver and from the Resolver back to graphql.

**Graphql-Java-Tools** 是一款增强IDEA对GraphQL Java支持的插件。

#### 主要功能如下：
* 快速从graphql Query/Mutation跳转到Java的Resolver方法及从方法返回到graphql文件

### Getting Started
* Open the **graphql** file and click the arrow on the left to jump to the **Java** Resolver Method.
* Open the **Java** Resolver class file and click the arrow on the left to jump to the **graphql** file.

***Thanks for the contribution of [Free MyBatis Tool](https://github.com/moztl/Free-Mybatis-Tool) @moztl @wuzhizhan*** 
<!-- Plugin description end -->

### 待实现
[*]将匹配机制从名称约定改为泛型检测
[ ]支持Type到Resolver类的跳转
[ ]支持scalar/directive到Java实现的跳转
[ ]支持enum/input到Java枚举类的跳转
[ ]支持通过Schema字段生成JavaResolver代码

### ToDo
[*] Replace name convention-based matching mechanism with generic detection.
[ ] Support navigation from Type to Resolver class.
[ ] Support navigation from scalar/directive to Java implementation.
[ ] Support navigation from enum/input to Java enum class.
[ ] Generate Java Resolver code from Schema fields.