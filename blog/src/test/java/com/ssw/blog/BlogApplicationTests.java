package com.ssw.blog;


import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogApplicationTests {
//    private final  static String tagUrl="http://www.imomoe.in";
//    @Autowired
//    private ComicService comicService;
//    @Autowired
//    private CategoryMapper categoryMapper;
//    public  Connection.Response jsoup(String targetUrl)
//    {
//
//        Connection connection = Jsoup.connect(targetUrl);
//        connection.header("Cookie","UM_distinctid=172459284d32f3-0c980a4e93a69f-3c3f5807-100200-172459284d445f; bdshare_firstime=1590305131068; __music_index__=2; ASPSESSIONIDQQBTQRDA=KNDFMECBEFJMPCBEEJMEIPOA; qike123=%u4E03%u539F%u7F6A%u7B2C%u4E09%u5B63%20%u7B2C01%u96C6^http%3A//www.imomoe.in/player/7707-0-0.html_$_%u4E03%u539F%u7F6A%u7B2C%u4E09%u5B63%20%u7B2C02%u96C6^http%3A//www.imomoe.in/player/7707-0-1.html_$_%u5C0F%u4E66%u75F4%u7684%u4E0B%u514B%u4E0A%uFF1A%u4E3A%u4E86%u6210%u4E3A%u56FE%u4E66%u7BA1%u7406%u5458%u4E0D%u62E9%u624B%u6BB5%uFF01%u7B2C%u4E8C%u5B63%20%u7B2C03%u96C6^http%3A//www.imomoe.in/player/7878-0-2.html_$_%u5C0F%u4E66%u75F4%u7684%u4E0B%u514B%u4E0A%uFF1A%u4E3A%u4E86%u6210%u4E3A%u56FE%u4E66%u7BA1%u7406%u5458%u4E0D%u62E9%u624B%u6BB5%uFF01%u7B2C%u4E8C%u5B63%20%u7B2C01%u96C6^http%3A//www.imomoe.in/player/7878-0-0.html_$_|; Hm_lvt_38c112aee0c8dc4d8d4127bb172cc197=1590886036,1590892026,1590903042,1590903103; CNZZDATA1260742008=1673374538-1590304473-https%253A%252F%252Fwww.baidu.com%252F%7C1590903690; ASPSESSIONIDSQAQQRDA=NPKOICKBCJPMKHAOLHHNGHIB; Hm_lpvt_38c112aee0c8dc4d8d4127bb172cc197=1590908427");
//        connection.header("Host","www.imomoe.in");
//        connection.ignoreHttpErrors(true);
//        try
//        {
//            return connection.method(Connection.Method.GET).execute();
//        } catch (IOException e)
//        {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    @Test
//    public void saveComic(String url) throws IOException
//    {
//        ComicReq req= new ComicReq();
//        Element body = jsoup(url).parse().body();
//            //获取标题
//        String title = body.select(".names").text();
//        req.setName(title);
//        //获取封面
//        String image = body.select(".spay").next().attr("src");
//        Elements span = body.select(".alex").select("span");
//        req.setImage(image);
//        //获取地区
//        String area = span.get(0).select("a").text();
//        if(!"大陆".equals(area)&&!"日本".equals(area)){
//            area="其他";
//        }
//        req.setArea(area);
//        //获取分类id集合
//        try
//        {
//            List<Integer> categoryIds= span.get(1).select("a").stream().map(
//                    e->
//                    {
//                       Integer i = categoryMapper.selectIdByName(e.text());
//                        if(i==null){
//                            categoryMapper.insert(new Category(e.text()));
//                            i=categoryMapper.selectIdByName(e.text());
//                        }
//                        return i;
//                    }
//            ).collect(Collectors.toList());
//            req.setCategoryIds(categoryIds);
//        }catch (Exception e){
//
//
//        }
//        //获取年代
//        String onlineYear = span.get(2).select("a").text();
//        req.setOnlineYear(onlineYear);
//       //获取视频源
//        String href = body.select("#play_0").select("li").select("a").get(0).attr("href");
//        req.setVideosString(tagUrl+href);
//        //获取简介
//        String content = body.select(".info").text().substring(1);
//        req.setContent(content);
//        try
//        {
//            comicService.save(req);
//        } catch (Exception e)
//        {
//            System.out.println(title+"添加失败");
//            e.printStackTrace();
//        }
//    }
//    @Test
//    public  void saveAll()
//    {
//        for(int i=4;i<=8;i++){
//            Element body = null;
//            try
//            {
//                System.out.println("爬取第"+i+"页");
//                body = jsoup("http://www.imomoe.in/so.asp?page="+i+"&fl=0&pl=time&dq=%CC%A8%CD%E5").parse().body();
//                body.select(".pics ul h2 a").stream().limit(12).forEach(element -> {
//
//                    try
//                    {
//                        saveComic(tagUrl+element.attr("href"));
//                    } catch (IOException e)
//                    {
//                        e.printStackTrace();
//                    }
//
//                });
//            } catch (IOException e)
//            {
//                System.out.println("第"+i+"页爬取失败");
//                e.printStackTrace();
//            }
//
//
//        }
//    }
}
