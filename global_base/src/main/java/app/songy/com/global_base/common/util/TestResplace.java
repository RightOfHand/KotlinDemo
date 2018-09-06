package app.songy.com.global_base.common.util;

/**
 * Description:
 * Created by song on 2018/7/11.
 * email：bjay20080613@qq.com
 */
public class TestResplace {
     public static void main(String[] args){
         String content="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                 "<!DOCTYPE html\n" +
                 "        PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\"\n" +
                 "        \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" +
                 "<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">\n" +
                 "<head>\n" +
                 "    <meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">\n" +
                 "    <title>Title</title>\n" +
                 "</head>\n" +
                 "<body>\n" +
                 "<h1>html content</h1>\n" +
                 "<h2> replace song</h2>\n" +
                 "\n" +
                 "\n" +
                 "</body>\n" +
                 "</html>";
         content=content.replaceAll(
                 "<h1[^>]*>(.*?)</h1>",
                 "<h1>$1 - HTML replaced</h1>");
         System.out.println(content);
     }
}
