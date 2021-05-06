import requests # 크롤링 라이브러리

from bs4 import BeautifulSoup

result_url = "https://www.academyinfo.go.kr/search/search.do"

univName = '고려대'

data ={'kwd' : univName}

response = requests.post('https://www.academyinfo.go.kr/search/search.do', data = data)
soup = BeautifulSoup(response.content, "html.parser")

print(soup.select('#contentsWrap > div > div > div.college-search-result > div.college-data-wrap > div.info-box > div > div:nth-child(1) > span > a')[0]['href'][2:])