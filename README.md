# IDEA plugin of graphql-java-tools

## [Plugin homepage](https://plugins.jetbrains.com/plugin/23024-graphql-java-tools)
[中文](./README_zh.md)
<!-- Plugin description -->
**Graphql-Java-Tools** Plugins is a plugin that enhances IDEA support for [GraphQL Java Tools](https://github.com/graphql-java-kickstart/graphql-java-tools) which based on [Graphql Java](https://github.com/graphql-java/graphql-java).

#### Here is the main functions:
* Navigate from the graphql Query/Mutation to Java Resolver and from the Resolver back to graphql.

### Getting Started
* Open the **graphql** file and click the arrow on the left to jump to the **Java** Resolver Method.
* Open the **Java** Resolver class file and click the arrow on the left to jump to the **graphql** file.

***Thanks for the contribution of [Free MyBatis Tool](https://github.com/moztl/Free-Mybatis-Tool) @moztl @wuzhizhan*** 
<!-- Plugin description end -->

### ToDo
- [x] Replace name convention-based matching mechanism with generic detection.
- [ ] Support navigation from Type to Resolver class.
- [ ] Support navigation from scalar/directive to Java implementation.
- [ ] Support navigation from enum/input to Java enum class.
- [ ] Generate Java Resolver code from Schema fields.