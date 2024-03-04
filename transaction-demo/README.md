# 1. 项目说明
该项目主要用于演示@Transactional 事务注解.

## 1.1 问题
```
A方法没有标注事务，跟A方法同一个类里有个B方法，B方法有标注事务@Transactional，A方法调用B方法，B方法里
操作完数据库后发生异常，请问事务会回滚吗？

结论：不会回滚，数据库操作已生效。
```

## 1.2 解决办法
要理解事务为什么没有生效，需要知道事务是怎么触发的，@Transactional 标注的注解之所以会生效，是因为标注该注解的的方法被代理了，外部调用才会触发，方法A调用方法B是属于内部调用的方式，
解决办法就是想办法用外部调用的方式，所以可以通过ApplicationContext上下文对象获取该服务类，再调用方法B，这样方法B就会触发事务机制了。

解决方式一：
通过 ApplicationContext 上下文对象获取当前代理对象
```java
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 方法testA没有加事务，调用同一个类里的testB方法，testB方法有标注事务，testB中发生异常，看会不会回滚
     * @param dept
     * @return
     */
    @Override
    public int testA(Dept dept) {
        //为了解决方法B中发生异常不会回滚的问题，需要采用代理的方式调用testB方法
        DeptService deptService = (DeptService)applicationContext.getBean(DeptService.class);
        System.out.println("testA deptService " + deptService);
        return deptService.testB(dept);
    }
    
```

解决方式二：
通过 AopContext.currentProxy() 获取当前代理对象，需要在启动类上配置 exposeProxy = true 这个属性暴露代理对象
```java
    /**
     *  开启 exposeProxy = true
     */
    @SpringBootApplication
    @EnableAspectJAutoProxy(exposeProxy = true)
    public class TransactionDemoApplication {
        //......
    }

    /**
     * 方法testA没有加事务，调用同一个类里的testB方法，testB方法有标注事务，testB中发生异常，看会不会回滚
     * @param dept
     * @return
     */
    @Override
    public int testA(Dept dept) {
        //通过AopContext获取当前代理对象，通过代理对象调用方法B就会触发事务
        //DeptService deptService = DeptService.class.cast(AopContext.currentProxy());
        DeptService deptService = (DeptService)AopContext.currentProxy();
        System.out.println("testA deptService " + deptService);
        return deptService.testB(dept);
    }
    
```

[@Transactional 注解及面试题]

[@Transactional 注解及面试题]: https://www.notion.so/Spring-Transactional-cea56683dc5147b298d2aef9324a476b?pvs=4