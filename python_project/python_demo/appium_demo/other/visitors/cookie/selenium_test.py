import random
import time

from selenium import webdriver

# 各种移动端
user_agent_list_3 = [
    "Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_3_3 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5",
    "Mozilla/5.0 (iPod; U; CPU iPhone OS 4_3_3 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5",
    "Mozilla/5.0 (iPad; U; CPU OS 4_2_1 like Mac OS X; zh-cn) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8C148 Safari/6533.18.5",
    "Mozilla/5.0 (iPad; U; CPU OS 4_3_3 like Mac OS X; en-us) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8J2 Safari/6533.18.5",
    "Mozilla/5.0 (Linux; U; Android 2.2.1; zh-cn; HTC_Wildfire_A3333 Build/FRG83D) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
    "Mozilla/5.0 (Linux; U; Android 2.3.7; en-us; Nexus One Build/FRF91) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
    "MQQBrowser/26 Mozilla/5.0 (Linux; U; Android 2.3.7; zh-cn; MB200 Build/GRJ22; CyanogenMod-7) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
    "Opera/9.80 (Android 2.3.4; Linux; Opera Mobi/build-1107180945; U; en-GB) Presto/2.8.149 Version/11.10",
    "Mozilla/5.0 (Linux; U; Android 3.0; en-us; Xoom Build/HRI39) AppleWebKit/534.13 (KHTML, like Gecko) Version/4.0 Safari/534.13",
    "Mozilla/5.0 (BlackBerry; U; BlackBerry 9800; en) AppleWebKit/534.1+ (KHTML, like Gecko) Version/6.0.0.337 Mobile Safari/534.1+",
    "Mozilla/5.0 (SymbianOS/9.4; Series60/5.0 NokiaN97-1/20.0.019; Profile/MIDP-2.1 Configuration/CLDC-1.1) AppleWebKit/525 (KHTML, like Gecko) BrowserNG/7.1.18124",
    "Mozilla/5.0 (compatible; MSIE 9.0; Windows Phone OS 7.5; Trident/5.0; IEMobile/9.0; HTC; Titan)",
]


class SeleniumClient:
    gd_cai_url = 'https://surl.amap.com/4jViED1j7T9'  # 分享链接
    gd_main_url = 'https://surl.amap.com/4kS7TFI1Mj'

    def __init__(self, proxy=''):
        self.chromeOptions = webdriver.ChromeOptions()
        # 设置中文
        self.agent = random.choice(user_agent_list_3)
        self.chromeOptions.add_argument('lang=zh_CN.UTF-8')
        self.chromeOptions.add_argument('user-agent="%s"' % self.agent)
        if proxy: self.chromeOptions.add_argument("--proxy-server=%s" % proxy)
        self.browser = webdriver.Chrome(options=self.chromeOptions)

    def go_url(self, *_urls):
        cookies = self.browser.get_cookies()
        print(self.agent)
        # print(f"main: cookies = {cookies}")
        # 清除浏览器cookies
        self.browser.delete_all_cookies()
        try:
            for url in _urls:
                self.browser.get(url)
                self.print_content()
            self.quit()
        except Exception as e:
            print('请求超时', e)

    def print_content(self):
        time.sleep(1)
        span_text = self.browser.find_elements_by_xpath('//span[@class="name text-overflow"]')[0]
        print(span_text.text)

    def quit(self):
        if self.browser: self.browser.quit()


if __name__ == '__main__':
    selenium = SeleniumClient()
    selenium.go_url(SeleniumClient.gd_cai_url, SeleniumClient.gd_main_url)
