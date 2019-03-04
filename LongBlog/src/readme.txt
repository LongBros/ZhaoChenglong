CREATE SCHEMA `longblog` DEFAULT CHARACTER SET utf8 ;

1.<!-- 博客表：博客id，标题，内容，发布时间，作者 -->
b_Id,b_Title,b_Content,b_Author

CREATE TABLE `blogs` (
  `b_Id` int(11) NOT NULL AUTO_INCREMENT,
  `b_Title` varchar(45) NOT NULL,
  `b_Content` varchar(5555) NOT NULL,
  `b_Time` varchar(45) NOT NULL,
  `b_Author` varchar(45) NOT NULL,
  `b_ImagePath` varchar(45) DEFAULT NULL,
  `b_ViewNum` int(11) DEFAULT NULL,
  `b_ComNum` int(11) DEFAULT NULL,
  `cat_Id` int(11) DEFAULT NULL,
  PRIMARY KEY (`b_Id`),
  UNIQUE KEY `b_Id_UNIQUE` (`b_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

  <!---->  Html注释，会发送到客户端
  <%----%>  JSP注释，不会发送到客户端
  
  2.category表
  cat_Id,cat_Name,cat_Num
  CREATE TABLE `category` (
  `cat_Id` int(11) NOT NULL AUTO_INCREMENT,
  `cat_Name` varchar(45) NOT NULL,
  `cat_Num` int(11) DEFAULT NULL,
  PRIMARY KEY (`cat_Id`),
  UNIQUE KEY `cat_Id_UNIQUE` (`cat_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
  
  INSERT INTO `longblog`.`category` (`cat_Name`,`cat_Num`) VALUES ('IT技术','0');
  
  个人日记，学习笔记，精选评论，内涵段子，自我感悟，突现点子
  3.阅读情况表viewcon
  v_Id,b_Id,v_Time,v_Ip
  CREATE TABLE `longblog`.`viewcon` (
  `v_Id` INT NOT NULL AUTO_INCREMENT,
  `b_Id` INT NOT NULL,
  `v_Time` VARCHAR(45) NOT NULL,
  `v_Ip` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`v_Id`),
  UNIQUE INDEX `v_Id_UNIQUE` (`v_Id` ASC));
  4.评论表comment
  c_Id,b_Id,c_Content,c_Time,c_Ip
  
CREATE TABLE `comment` (
  `c_Id` int(11) NOT NULL AUTO_INCREMENT,
  `b_Id` int(11) NOT NULL,
  `c_Content` varchar(545) NOT NULL,
  `c_Time` varchar(25) NOT NULL,
  `c_Ip` varchar(25) NOT NULL,
  `c_ImgPath` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`c_Id`),
  UNIQUE KEY `c_Id_UNIQUE` (`c_Id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;


5。回收站recycle bin    删除的博客deleted blog
5.9日拟新建仪表，存放删除的博客，实现回收站功能，该表的字段与博客表相似，较之加了一id和删除时间字段
CREATE TABLE `d_blogs` (
  `d_Id` int(11) DEFAULT NULL,
  `b_Id` int(11) NOT NULL AUTO_INCREMENT,
  `b_Title` varchar(45) NOT NULL,
  `b_Content` varchar(5555) NOT NULL,
  `b_Time` varchar(45) NOT NULL,
  `b_Author` varchar(45) NOT NULL,
  `b_ImagePath` varchar(45) DEFAULT NULL,
  `b_ViewNum` int(11) DEFAULT NULL,
  `b_ComNum` int(11) DEFAULT NULL,
  `cat_Id` int(11) DEFAULT NULL,
  `d_Time` varchar(45) NOT NULL,
  PRIMARY KEY (`b_Id`),
  UNIQUE KEY `b_Id_UNIQUE` (`b_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

6.网站访问日志表-----网站每被访问一次，添加一条记录
log_vis访问日志
访问者ip l_Ip,访问时间l_Time
CREATE TABLE `log_vis` (
  `l_Id` int(11) NOT NULL AUTO_INCREMENT,
  `l_Ip` varchar(15) NOT NULL,
  `l_Time` varchar(25) NOT NULL,
  PRIMARY KEY (`l_Id`),
  UNIQUE KEY `l_Id_UNIQUE` (`l_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

7.留言板m_board------留言板message board
留言id，头像路径，留言内容，留言者ip，留言时间

8.每日一话
1).爬取网络上的，将每一天爬取的当天的每日一话录入数据库

2).自己录入数据库的，    自己定义一个字符串数组，存放很多个话，每天显示随机的一条
CREATE TABLE `longblog`.`sentence` (
  `s_Id` INT NOT NULL AUTO_INCREMENT,
  `s_Date` VARCHAR(20) NOT NULL,
  `s_Chinese` VARCHAR(100) NOT NULL,
  `s_English` VARCHAR(100) NOT NULL,
  `s_Time` VARCHAR(20) NOT NULL,
  `s_Ciba` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`s_Id`),
  UNIQUE INDEX `s_Id_UNIQUE` (`s_Id` ASC));

ALTER TABLE `longblog`.`sentence` 
CHANGE COLUMN `s_Chinese` `s_Chinese` VARCHAR(150) NOT NULL ,
CHANGE COLUMN `s_English` `s_English` VARCHAR(150) NOT NULL ,
CHANGE COLUMN `s_Ciba` `s_Ciba` VARCHAR(150) NOT NULL ;

9.作者表author
作者id-a_Id，作者姓名-a_Name，作者手机号-a_Phone，作者QQ号-a_QQ，登录密码-a_Pass，性别-a_Sex，
CREATE TABLE `author` (
  `a_Id` int(11) NOT NULL AUTO_INCREMENT,
  `a_Name` varchar(5) NOT NULL,
  `a_QQ` varchar(15) NOT NULL,
  `a_Phone` varchar(15) DEFAULT NULL,
  `a_Pass` varchar(15) NOT NULL,
  `a_Sex` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`a_Id`),
  UNIQUE KEY `a_Id_UNIQUE` (`a_Id`),
  UNIQUE KEY `a_Name_UNIQUE` (`a_Name`),
  UNIQUE KEY `a_QQ_UNIQUE` (`a_QQ`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
