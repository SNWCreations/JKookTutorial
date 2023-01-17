# Chapter 5

在你阅读上一章的时候，你应该注意到了我们提到了几次 `Message` ，那就是我们对 KOOK 消息的抽象接口。

本章，我们来讲解 JKook API 的消息体系。

**本章还是没有示例代码。**
（耸肩）

---

**消息是一种实体。**
没有放在 `snw.jkook.entity` 包的原因在上一章已经讲过了。

JKook API 消息体系中的各种类基本统一放在 `snw.jkook.message` 包下。

---

先总览包结构。

```text
snw.jkook.message
|   Message
|   PrivateMessage
|   TextChannelMessage
|   
\---component
    |   BaseComponent
    |   FileComponent
    |   MarkdownComponent
    |   TextComponent
    |   
    \---card
        |   CardBuilder
        |   CardComponent
        |   CardScopeElement
        |   MultipleCardComponent
        |   Size
        |   Theme
        |   
        +---element
        |       BaseElement
        |       ButtonElement
        |       ImageElement
        |       InteractElement
        |       MarkdownElement
        |       PlainTextElement
        |       
        +---module
        |       ActionGroupModule
        |       BaseModule
        |       ContainerModule
        |       ContextModule
        |       CountdownModule
        |       DividerModule
        |       FileModule
        |       HeaderModule
        |       ImageGroupModule
        |       InviteModule
        |       package-info
        |       SectionModule
        |       
        \---structure
                BaseStructure
                Paragraph
```

根据以上结构，本章我们分为三大部分：

消息对象，消息组件，CardMessage（卡片消息）。

## 消息对象

TODO

## 消息组件

TODO

## CardMessage

TODO
