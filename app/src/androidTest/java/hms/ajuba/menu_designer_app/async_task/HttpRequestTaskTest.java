package hms.ajuba.menu_designer_app.async_task;

import android.support.test.runner.AndroidJUnit4;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)

public class HttpRequestTaskTest extends TestCase {

    @Test
    public void testDoInBackground() throws Exception {
        System.out.println("executing");
        new HttpRequestTask().execute();
    }
}