# rippleView
rippleView

有时候根据产品设计，要做一个带水波纹效果的动画，如下图。基本原理就是，中间是一个图层，水波纹是一张图片分别执行了缩放和移动的动画。

> * 先创建一个自定义Dialog，用于显示全屏的半遮罩view
> * 创建到水波纹效果的自定义View。它继承自RelativeLayout。
> * 需要美工提供带渐变效果环的图片。这种方式最简单，当然还可以用代码实现，代码实现的话，这个带渐变效果的环比较难搞，读者可以自己尝试下，毕竟图片也能实现效果。
> * 测试代码

![ScanRipple](http://www.haowuyun.com/store/thumbs/2017/0719/19133545bwpe.gif)

除了您现在看到的这个 Cmd Markdown 在线版本，您还可以前往以下网址下载：

### [具体详解参见文章](http://www.haowuyun.com/view/393)
