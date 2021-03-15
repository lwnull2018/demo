## Thymeleaf 排除标签（不写在标签中）直接显示内容
一般情况下 Thymeleaf 模板要输出变量需要在某个标签中（如<div>、<span>）写th:text等属性来实现。
但有时我们希望想不写在标签中，直接输出变量的值，比如在 <title> 标签中直接显示变量 msg 的值，而不需要包含在 <span> 等标签中。

### 解决方案一：
使用 th:block

  <title><th:block th:text="${msg}" /> - 服务器错误。</title>
    
### 解决方案二（推荐）：
使用 inline

  <title>[[${msg}]] - 服务器错误。</title>
  
  Hello, [[${user.name}]]!   //[[]]写法会html转义
  Hello, [(${user.name})]!   //[()]写法不会html转义
  
  