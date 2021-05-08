
import openpyxl # 엑셀 활용 라이브러리

import requests # 크롤링 라이브러리
from bs4 import BeautifulSoup


# excel 파일 불러오기

excellFile = openpyxl.load_workbook('C:/Users/kimsh/Desktop/list.xlsx')

sheet = excellFile['Sheet1']
cells = sheet['A1' : 'E1']


rowNum = 0


# 크롤링 대상 웹 주소
base_url = 'https://www.academyinfo.go.kr/'
result_url = base_url + "search/search.do"
detailPage_url = base_url + "popup/pubinfo1690/list.do?schlId="

# 크롤링 대상 태그

imgTag = '#contentsWrap > div > div > div.college-search-result > div.college-data-wrap > div.img-box > img'


for row in cells :

    rowNum += 1
    list = []

    for cell in row :
        list.append(cell.value)

    postData = {'kwd': list[1]}  # post 요청 위한 데이터 (학교이름)

    response01 = requests.post(result_url, data=postData)
    soup01 = BeautifulSoup(response01.content, "html.parser")







