package hms.ajuba.menu_designer_app.util;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class HttpClientTest extends TestCase {

    @Test
    public void testGET() throws Exception {
        System.out.println("**************************************");
        System.out.println(HttpClient.GET("http://www.mocky.io/v2/55a508b091b721150eff4e5d"));
    }

    @Test
    public void testMock() throws Exception {
        System.out.println("mock");
    }
}