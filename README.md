# Netty IM

基于 Netty 实现的即时通讯系统，采用 C/S 架构，使用 Protobuf 进行消息序列化，支持用户登录、实时聊天和心跳保活。

## 功能特性

- **实时通讯**：基于 Netty NIO 的高性能消息收发
- **Protobuf 编解码**：高效的二进制消息序列化
- **用户登录**：客户端登录验证与会话管理
- **心跳保活**：客户端/服务端双向心跳检测，自动断线感知
- **消息类型**：支持文本、音频、视频、位置等多种消息类型
- **命令行交互**：客户端提供交互式命令行界面

## 项目结构

```
├── common/       # 公共模块（Protobuf 消息定义、编解码器、雪花 ID、枚举）
├── server/       # 服务端（会话管理、消息转发、登录处理、心跳检测）
└── client/       # 客户端（登录、聊天命令、消息发送器、心跳保活）
```

## 技术栈

- Java 17
- Netty 4（NIO 网络通信）
- Protocol Buffers（消息序列化）
- 雪花算法（分布式 ID 生成）

## 快速开始

```bash
# 编译
mvn clean package

# 启动服务端
java -jar server/target/server.jar

# 启动客户端
java -jar client/target/client.jar
```

启动客户端后通过命令行菜单进行登录和聊天操作。
