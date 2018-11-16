package com.xinzhu.xuezhibao.utils;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.xinzhu.xuezhibao.bean.UserBasicInfo;

public class Constants {
    public static final String APP_ID="wx46b14ff64afefa78";
    public static IWXAPI api;   //第三方和微信通信的接口
   // public static String URL="http://192.168.1.112:8080/";
    public static String URL="http://192.168.1.199:8080";
    public static String APP_VERSION="1.0.0";
    public static String AlipayAccount="";
    public static String JPUSH_APPKEY = "68a8c864423f3b707c8c8da3";
    public static int EMOTICON_CLICK_TEXT = 1;
    public static int EMOTICON_CLICK_BIGIMAGE = 2;
    public static int SETTING_USENET=1;
    public static int SETTING_CANNOTIFITION=1;
    public static String TOKEN="";
    public static String ALAPPID="2016091800540358";
    public static String INTENT_ID ="id";
    public static String FROMAPP="fromapp";
    public static String INTENT_COURSE_CLASS="COURSE";
    public static String INTENT_EDITITEM="COURSE";    //修改个人资料时传入要修改的项目
    public static UserBasicInfo userBasicInfo=null;
    public static long PLAYTIME=0;
    //支付宝私钥
    public static String privateKEY ="\n" +
            "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDT4licaHutf7rm\n" +
            "lTSqRw5NEcpyzaJA1xFcCl/TI8D6aFWlejhksyJzrCixEqymYuyzJGKFZVN6KpJU\n" +
            "ewDapgUpRJevW+aVkr7PSysYyXJOqRAEBM4WQBzxM/vAg0Mf2a2Fuy/h8kMMODBv\n" +
            "LgQHnK6M8iH98gc9XhJnNY83NVwZQM7l+BTukydlBrTlIPwNFrMuKrS71uwQs20r\n" +
            "LrD/iQ7cGY8FhLhxZNmGlY+qE5ZymkuP2PZgZ7hl30WkpJ+YmEguH5F2GfQMI/3x\n" +
            "eptmiU98aHxdGhrn7/JC1O58q1Yy+Gi2zfL/QkDKBYnYO1cvakskSUsKCTvU8KWj\n" +
            "X4HW0bX9AgMBAAECggEAOxcjyTLmUwONNyMjVd2NInc0s4Gfn1+bSsTl5ndRKUSR\n" +
            "ph2IvqSqlgHMM13W4mwyQN+DzlnsxuQ6fO701QV56QFlIBWMH+iV6C+5bCc6Bq/l\n" +
            "dIl2hAQLTkVGD3FL9gT5/JKwjt3GTxEYiNkXbJwInewlCLJHzJiAn2Hzd90cuzmy\n" +
            "b0M8tYHeuGM/3dNACA90/uihKe3pjupAG/hZITYdiVMhRuKqnccH+s0LF4gFrH8N\n" +
            "irFAgVJ4ZwEl+fTSLkX7FSv3PucR2RM0dyDNwjpqINKAwfUws8R34sLlzJRjMZQM\n" +
            "l9oRW0Az6Of1kjPU6yiLl/xydquGOjfr20aZBB31IQKBgQD7kTXs+BdS/dueo4/M\n" +
            "Wu4BjKvoSp6t1m0FlnEYG8rUp8OXzQZudzOBSpYxbBF8IjqktLIqOXFIyqNI8FRe\n" +
            "S9WdMdcgVNI26J8NekhsyE91YZGPWn2IRbn4Hn+DrnwkjKNytQ1zhdCpCBKnnOBh\n" +
            "Fi2Q129iS3TS0I2+7SOjO7nDCQKBgQDXniFGnO5RK70IbjRsD3NZQfJvy1ZrroTj\n" +
            "V0Xd5IYl7jywcdn4Omd/dJWMmKTQwfUk/V9sdu1V4iugqj7bW6IchX3fHLzWjw/S\n" +
            "aqgCAaEBkoSLYJGxwc65ijijv/cHxS9oG0p19Zrlb+ZFYgISwaIKnvxb+ck+rbYA\n" +
            "dA9aLT1UVQKBgAvjU9IL80Y3ut4nSo9UqLSoCiOjCxaZ8HL4JgYGojIkb2jIBorH\n" +
            "1UZB2REi0zyzfWP1Kgo2xrSy5r52pPYDzWdtSIY9m+V+Xq3LXorNeHvKjY9hrlb3\n" +
            "PI+OGWaGo1Uy1DKCfc08wJyMRh1nm27bFnImzBuwByhDNL+WAz4ef2vJAoGAG/fq\n" +
            "DxPKIgNPaOmKqT2z068nOHMiyaMAn9CxQmAjOoZVnvmVsYo55KGFyJAASQfvuZWY\n" +
            "LCSdEpfc2uFad+/B7nsuYaODpz01azDkjCYT8XVE9iNXorwmCZExWaRL8dkVFib8\n" +
            "GKL8Lai/MN7OsAon78LBr1iOTzAPPK3RU/wsSWUCgYASzlC9z3j+c8TPRmPiPgQe\n" +
            "YxBZvM9jVhUkBikZsiGjs+TPgHFLKHQ+Hx8NdEYFZgVUZvOIk9PyGYCA+CYe1Vq0\n" +
            "cwoTwZlvgv/RSjtdf7nYA0Aulj1wJ0Z/mnlKyh2/AG/G6BPa1AbfJLJM/WhIwK+Y\n" +
            "CVFd1uSBSUX1YESUbf3i4Q==";
    //支付宝应用公钥
    public static String APPSHAKEY="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0+JYnGh7rX+65pU0qkcOTRHKcs2iQNcRXApf0yPA+mhVpXo4ZLMic6wosRKspmLssyRihWVTeiqSVHsA2qYFKUSXr1vmlZK+z0srGMlyTqkQBATOFkAc8TP7wINDH9mthbsv4fJDDDgwby4EB5yujPIh/fIHPV4SZzWPNzVcGUDO5fgU7pMnZQa05SD8DRazLiq0u9bsELNtKy6w/4kO3BmPBYS4cWTZhpWPqhOWcppLj9j2YGe4Zd9FpKSfmJhILh+Rdhn0DCP98XqbZolPfGh8XRoa5+/yQtTufKtWMvhots3y/0JAygWJ2DtXL2pLJElLCgk71PClo1+B1tG1/QIDAQAB";
}
