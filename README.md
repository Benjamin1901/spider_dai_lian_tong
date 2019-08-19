# 王者农药代练通订单爬虫
> 前记：一个农药代练的同学找到我，说代练抢不到单😢，想让我帮他写个程序：如果有新订单就提醒他去抢单。这里爬取王者农药代练通最新订单信息，代码只写一个Demo，爬取苹果微信最新的20条订单信息，大家可以接着改。

### 代练通接口反爬原理
```
订单接口URL：https://server.dailiantong.com/API/AppService.ashx?Action=LevelOrderList&callback=callback&IsPub=1&GameID=107&ZoneID=1033&ServerID=0&SearchStr=&Sort_Str=&PageIndex=1&PageSize=20&Price_Str=&PubCancel=0&SettleHour=0&FilterType=0&PGType=2&UserID=0&TimeStamp=1565447134&Ver=1.0&AppVer=2.0.0&AppOS=webapp&AppID=webapp&Sign=c611d64305b60702ec3309aacf5cf36f
```
  
  
它们的数据接口主要是通过一个签名参数**Sign**和时间戳参数**TimeStamp**进行校验反爬的，我们去找一下网站的JS代码：  

![找到加密](https://github.com/Benjamin1901/spider_dai_lian_tong/blob/master/pic/p3.png)  
  
  
继续往下看  

![JS签名加密函数](https://github.com/Benjamin1901/spider_dai_lian_tong/blob/master/pic/p1.png)  
  
这加密还是挺简单的：主要是通过一个SignKey、查询参数还有时间戳进行MD5加密  

👇的字符串就是用来得到签名的，构造这样的字符串，进行MD5加密就能得到我们签名了  

![J](https://github.com/Benjamin1901/spider_dai_lian_tong/blob/master/pic/p2.png)  
  
  
得到签名就可以写代码，最后得到的订单的数据：
![image](https://github.com/Benjamin1901/spider_dai_lian_tong/blob/master/pic/p4.png)
