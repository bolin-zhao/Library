# Library
## 一个基于JavaWeb的图书后台管理系统

- #### 前言: 

  > 该项目出自b站的楠哥, 讲的干货非常多,我之前做过一个JavaWeb的仿小米商城前后台项目,git仓库里有源码, 两个项目对比,也各有各自的优缺点.

- #### 缺点:

  > 先说缺点, 缺点是关于数据库方面的, 小米商城使用的是druid连接池,使用QueryRunner操作数据层,而本项目使用的是c3p0连接池,使用最普通的 JDBC utils工具类操作数据层, 需要频繁的书写类似setInt, 预编译等代码, 较为繁琐.

- #### 优点:

  > 1. ##### 分页
  >
  > 这个项目做完,我对MVC 三层架构开发有了突破性的认识, 虽然没有采用任何的主流的前端框架, 但是, 依靠原生的技术,实现了分页等功能,大家都知道,手动实现分页是一件非常繁琐的事情, 但是 明白了原理之后, 其实就是对数据库sql语句的一个limit操作罢了, 再加上一个小小的算法, n*(n-1)
  >
  > 2. ##### 过滤器
  >
  > 之前对过滤器filter的理解有些局限性, 只是用来解决中文乱码的问题, 过滤的范围是/* 全局, 设为 `utf-8`就ok了, 这次是对/reader 和 /admin 进行局部过滤, 获取session,检查用户/管理员是否已登录,借此获取用户id等属性
  >
  > 3. ##### 重定向/请求转发
  >
  > `request.getHttpDispather("路径").forward(request,response)`和`response.sendRedirect("路径")`
  >
  > 两者很好区别, 主要看你是否携带数据, 是否想刷新页面, 请求转发能做到,但是重定向只是单纯的改变了路径, 由于session缓存的存在,并没有刷新页面, 重定向一般用于登陆失败,密码账号有误,然后重定向到 login.jsp 类似的场景, 退出登录也是让session自杀, 重定向到首页或登录页, 一般的更新页面的操作还是使用请求转发.
  >
  > 4. 重定向的另外一种应用
  >
  > 也不要因为上面的一段话, 忽视了重定向的另外一种的应用, 就是在业务逻辑中, 如果出现了重复类似的功能, 当你写代码时,你不需要将代码再copy一份过来, 而是直接使用重定向,将操作重定向到对应的servlet方法里,例如:
  >
  > ```java
  > // 处理完同意/拒绝后,再跳转到当前页,实现刷新页面
  > if (Integer.parseInt(state)==1 || Integer.parseInt(state)==2){
  >    resp.sendRedirect("/admin?page=1");
  > }else{
  > resp.sendRedirect("/admin?method=getBorrowed&page=1");
  > }
  > ```
  >
  > 当时的我看到这种写法是非常震惊的, 居然还有这种操作? 我在小米商城里是一直在搬砖啊, 一半的代码都是无意义的,甚至更多, 这算是一种优化吧!
  >
  > 5. 多态的使用
  >
  > 用户和管理员登录, 是两个角色, 拥有不同的分工和权限, 登录时,我之前是把它们分开,写在2个servlet中的, 但是学完本项目, 我认识到了多态的优点,它完美的诠释了万物皆对象的观点, 直接把登录环节封装成了一个Object对象, 如果对象等于reader时,就读者登录, 如果是admin就管理员登录, 包括后面的把方法封装成一个method, 用 swith(method){case "方法名1".....} 这些操作给我留下了非常深刻得印象.
  >
  > 例如 : 
  >
  > ```java
  > // 多态的应用,根据type判断角色登录,用Object代替了admin/user,多角色用这种方法更好!
  > Object object = loginService.login(username, password, type);
  >    if (object != null) {
  >        HttpSession session = request.getSession();
  >    	switch (type) {
  >         case "reader":
  >             Reader reader = (Reader) object;
  >             session.setAttribute("reader", reader);
  >             response.sendRedirect("/book?page=1");
  >             // 跳转到读者页
  >             break;
  >         case "admin":
  >            Admin admin = (Admin) object;
  >            session.setAttribute("admin", admin);
  >            // Model
  >           response.sendRedirect("/admin?method=findAllBorrow&page=1");
  >            break;
  >     }
  > } else {
  >        response.sendRedirect("login.jsp");
  > }
  > ```

  

  ### 总结:  

  > 整个项目下来, 对于jsp页面也有了一些粗浅的认识, 能够通过`req.setAttribute("list",list)`和`req.getAttribute("reader",reader)` 前后台设置传递属性值, 还需要加强 jstl 和 el 表达式的应用, 哎, 也不知道还有多少项目在使用 J S P, 我做的这些又是否会有意义 !  

  

  

  

  

  

  

  

  

  

 