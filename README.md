# ECampus
QiLu Software Competition

## 一、项目模块分解

![image](https://github.com/Super262/ECampus/blob/master/screenshots/pic01.png)
### 注解Module
提供代码生成器所需注解。
### 核心Module
路由架构；HTTP请求；照相和图片剪裁；具有共性的通用UI；通用的工具；诸多重复性的处理。
### 业务Module
相应一类业务的特殊UI；相应一类业务需要的通用逻辑；相应一类业务的特殊处理。
### 具体项目Module
项目特有的个别功能；只有该项目需要的第三方库；只有该项目会更改的UI和逻辑；需要在Application Module里使用的一些签名和验证。
### 代码生成器Module
从注解获取信息，通过annotationProcessor或apt生成代码。

## 二、各个包的功能
### app
（具体项目）Android Application
### qilu-annotations
（注解模块）Java Library
### qilu-complier
（注解处理器）Java Library
### qilu-core
（核心模块）Android Library
### qilu-ec
（业务模块）Android Library
### qilu-ui
（特殊UI控件，自定义的UI控件）Android Library

## 三、功能实现
### 矢量图标
android-iconify-fontawesome
### 单个Activity做容器，多个Fragment切换显示
me.yokeyword:fragmentation; me.yokeyword:fragmentation-swipeback
### 高性能网络请求框架
RESTful风格，基于Retrofit2
### 启动图开发和封装
### 通用底部导航设计与封装
### 一键式相机
图片处理裁剪
