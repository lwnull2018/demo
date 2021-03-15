## 1. Banner展示效果

SpringBoot默认的banner展示效果：

![image.png](https://i.loli.net/2021/03/15/1tjc6FkO9pDREZn.png)

自定义banner展示效果1：

![image.png](https://i.loli.net/2021/03/15/1tjc6FkO9pDREZn.png)

自定义banner展示效果2：

![image.png](https://i.loli.net/2021/03/15/mA4irXa8geyvzPI.png)



## 2. 自定义Banner

自定义 banner 的实现方式有两种，一种是通过重写自定义的 Banner 类来实现，另一种通过 txt 文件来实现。

### 2.1 重写Banner类
 
首先，需要自定义类实现 Banner 接口，实现代码如下：

```
public class MyBanner implements Banner {
    
        private static final String BANNER =
                "  ___ ___         .__  .__          \n" +
                        " /   |   \\   ____ |  | |  |   ____  \n" +
                        "/    ~    \\_/ __ \\|  | |  |  /  _ \\ \n" +
                        "\\    Y    /\\  ___/|  |_|  |_(  <_> )\n" +
                        " \\___|_  /  \\___  >____/____/\\____/ \n" +
                        "       \\/       \\/                  ";
    
        @Override
        public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
            out.println(BANNER);
            out.println();
        }
    
    }
```

其中 BANNER 变量为自定义 banner 的内容，我这放入了一个 hello，然后在 Spring Boot 启动时设置 Banner 类为自定义类，实现代码如下：

```
public static void main(String[] args) {
    SpringApplication springApplication = new SpringApplication(DemoApplication.class);
    // 设置自定义 Banner
    springApplication.setBanner(new MyBanner());
    // 启动 Spring Boot
    springApplication.run(args);
}
```

### 2.2 通过 txt 文件实现
我们可以在 Spring Boot 工程的 /src/main/resources 目录下创建一个 banner.txt 文件，然后将 ASCII 字符画复制进去，就能替换默认的 banner 了，如下图所示：
```
      /¯¯¯¯\
    o-|[][]|-o
      |_--_|
   /¯¯¯¯¯¯¯¯¯¯\
   |||  «»  |||
   |||      |||
  (o)|      |(o)
     |  ||  |
     |__||__|
     |__||__|
     
```
之所以可以使用 banner.txt 文件的方式实现自定义 banner 是因为 Spring Boot 框架在启动时会按照以下顺序，查找 banner 信息：

先在 Classpath 下找 文件 banner.gif 或 banner.jpg 或 banner.png , 先找到谁就用谁；
以上都没有就在 Classpath 下找 banner.txt；
如果都没找到才会使用默认的 SpringBootBanner。
以上知识点是在 SpringApplicationBannerPrinter 源码中得知的，核心源码如下：

```
class SpringApplicationBannerPrinter {
    static final String BANNER_LOCATION_PROPERTY = "spring.banner.location";
    static final String BANNER_IMAGE_LOCATION_PROPERTY = "spring.banner.image.location";
    static final String DEFAULT_BANNER_LOCATION = "banner.txt";
    static final String[] IMAGE_EXTENSION = new String[]{"gif", "jpg", "png"};
    // 忽略非核心源码
    private Banner getBanner(Environment environment) {
        SpringApplicationBannerPrinter.Banners banners = new SpringApplicationBannerPrinter.Banners();
        // 获取图片形式 banner
        banners.addIfNotNull(this.getImageBanner(environment));
        // 获取文字形式 banner
        banners.addIfNotNull(this.getTextBanner(environment));
        if (banners.hasAtLeastOneBanner()) {
            return banners;
        } else {
            return this.fallbackBanner != null ? this.fallbackBanner : DEFAULT_BANNER;
        }
    }

    private Banner getTextBanner(Environment environment) {
        String location = environment.getProperty("spring.banner.location", "banner.txt");
        Resource resource = this.resourceLoader.getResource(location);
        return resource.exists() ? new ResourceBanner(resource) : null;
    }

    private Banner getImageBanner(Environment environment) {
        String location = environment.getProperty("spring.banner.image.location");
        if (StringUtils.hasLength(location)) {
            Resource resource = this.resourceLoader.getResource(location);
            return resource.exists() ? new ImageBanner(resource) : null;
        } else {
            String[] var3 = IMAGE_EXTENSION;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String ext = var3[var5];
                Resource resource = this.resourceLoader.getResource("banner." + ext);
                if (resource.exists()) {
                    return new ImageBanner(resource);
                }
            }
            return null;
        }
    }
}
```
所以我们才能使用 banner.txt 自定义 banner 信息，当然你也可以使用图片的方式来自定义 banner。

> 小技巧：我们可以使用 banner.gif 来实现动态 banner 的效果，动手试试吧。

此种方式实现起来比较简单，且是无代码侵入式的，推荐使用这种方式。

## 3. Banner控制样式

上面讲了 banner 文字部分的修改，我们还可以修改 banner 的演示以及其他属性，例如字体的样式，粗体、斜体等，Spring Boot 为提供了三个枚举类来设定这些样式，他们分别是：

AnsiColor：用来设定字符的前景色；
AnsiBackground：用来设定字符的背景色。
AnsiStyle：用来控制加粗、斜体、下划线等等。
例如，我们可以使用 AnsiColor 来设置颜色，banner.txt 中的信息如下：

```
${AnsiColor.BRIGHT_RED}  _  _              _       _
${AnsiColor.BRIGHT_RED} | || |    ___     | |     | |     ___
${AnsiColor.BRIGHT_YELLOW} | __ |   / -_)    | |     | |    / _ \
${AnsiColor.BRIGHT_YELLOW} |_||_|   \___|   _|_|_   _|_|_   \___/
${AnsiColor.BRIGHT_RED}_|"""""|_|"""""|_|"""""|_|"""""|_|"""""|
${AnsiColor.BRIGHT_RED}"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'"`-0-0-'
```

## 4. 使用Banner输出变量
在 banner.txt 中我们还可以输出一些全局变量，例如：

* ${application.version}：用来获取 MANIFEST.MF 文件中的版本号；
* ${application.formatted-version}：格式化后的 ${application.version} 版本信息；
* ${spring-boot.version}：Spring Boot 版本号；
* ${spring-boot.formatted-version}：格式化后的 ${spring-boot.version} 版本信息。

使用示例如下：

```
      /¯¯¯¯\     
    o-|[][]|-o   
      |_--_|     
   /¯¯¯¯¯¯¯¯¯¯\  
   |||  «»  |||  
   |||      |||  
  (o)|      |(o) 
     |  ||  |    
     |__||__|    
     |__||__|

Spring Boot 版本：${spring-boot.version}

```

## 5. Banner图在线生成

[连接地址](https://link.zhihu.com/?target=https%3A//www.bootschool.net/ascii)


## 6. 隐藏Banner输出
如果我们需要隐藏 banner 信息输出，可以通过以下三种方法实现。

### 6.1 缓过代码关闭Banner
我们可以在 Spring Boot 启动（run）之前设置隐藏 banner，实现代码如下：
```
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(DemoApplication.class);
        // 隐藏 banner
        springApplication.setBannerMode(Banner.Mode.OFF);
        // 启动 Spring Boot
        springApplication.run(args);
    }
}
```
### 6.2 通过配置文件隐藏Banner
在 Spring Boot 的配置文件 application.properties 通过设置如下配置来隐藏 banner 的显示，配置如下：
```
spring.main.banner-mode=off
```

### 6.3 在Idea中隐藏Banner
![image.png](https://i.loli.net/2021/03/15/RbHaorUNfhS2C1c.png)


## 7. 附 彩蛋
文章的末尾，附一个七彩佛祖的 banner 内容：
```
${AnsiColor.BRIGHT_GREEN}$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
${AnsiColor.BRIGHT_YELLOW}$$                                _.ooOoo._                               $$
${AnsiColor.BRIGHT_RED}$$                               o888888888o                              $$
${AnsiColor.BRIGHT_CYAN}$$                               88"  .  "88                              $$
${AnsiColor.BRIGHT_MAGENTA}$$                               (|  ^_^  |)                              $$
${AnsiColor.BRIGHT_GREEN}$$                               O\   =   /O                              $$
${AnsiColor.BRIGHT_RED}$$                            ____/`-----'\____                           $$
${AnsiColor.BRIGHT_CYAN}$$                          .'  \\|       |$$  `.                         $$
${AnsiColor.BRIGHT_MAGENTA}$$                         /  \\|||   :   |||$$  \                        $$
${AnsiColor.BRIGHT_GREEN}$$                        /  _|||||  -:-  |||||-  \                       $$
${AnsiColor.BRIGHT_YELLOW}$$                        |   | \\\   -   $$/ |   |                       $$
${AnsiColor.BRIGHT_GREEN}$$                        | \_|  ''\-----/''  |   |                       $$
${AnsiColor.BRIGHT_YELLOW}$$                        \  .-\___  `-`  ____/-. /                       $$
${AnsiColor.BRIGHT_CYAN}$$                      ___`. .'   /--.--\   `. . ___                     $$
${AnsiColor.BRIGHT_RED}$$                    ."" '<  `.____\_<|>_/____.'  >'"".                  $$
${AnsiColor.BRIGHT_GREEN}$$                  | | :  `- \`.;`.\ _ /``;.`/ - ` : | |                 $$
${AnsiColor.BRIGHT_YELLOW}$$                  \  \ `-.   \_ ___\ /___ _/   .-` /  /                 $$
${AnsiColor.BRIGHT_CYAN}$$            ========`-.____`-.____\_____/____.-`____.-'========         $$
${AnsiColor.BRIGHT_MAGENTA}$$                                  `=---='                               $$
${AnsiColor.BRIGHT_YELLOW}$$            ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^        $$
${AnsiColor.BRIGHT_GREEN}$$                     佛祖保佑          永无BUG         永不修改            $$
${AnsiColor.BRIGHT_YELLOW}$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
${AnsiColor.BRIGHT_YELLOW}
Spring Boot 版本：${spring-boot.version}

``` 
实现效果如下图所示：
![image.png](https://i.loli.net/2021/03/15/mA4irXa8geyvzPI.png)

## 8. 总结
本文我们讲了自定义 banner 的两种方法，自定义 banner 类和 banner.txt 的方式，其中以源码分析的方式讲了为什么可以通过 banner.txt 自定义 banner信息。我们还讲了 banner 样式控制（颜色、字体样式等）和全局变量输出的方法，以及 banner 图在线生成的几个地址，最后还讲了 3 种隐藏 banner 的方法。

## 9. 最后的话
认真写好每一篇原创，只为不辜负你的观看。写作是一件很酷并且能帮助他人的事，我希望能一直坚持下去。如果觉得有用，请随手给我一个赞吧，他将鼓励我产出更好的文章。


