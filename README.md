# PluginSample
插件化，仿照系统加载类和资源，让主App加载另一个apk文件

![项目结构概览](https://github.com/CoderWalterXu/PluginSample/blob/master/screenshot/1.jpg)


DexClassLoader类加载器加载外部apk的class     
Resouces资源管理器加载外部apk的资源     

pluginapk中的Activity继承pluginlibrary中的PluginBaseActivity,所以生成的apk既可以被系统加载，也可以作为插件被主App加载

参考了以下资料，对此表示感谢：     
https://www.jianshu.com/p/3a5cf6ebfafc
