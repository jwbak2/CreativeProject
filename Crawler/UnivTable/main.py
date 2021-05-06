import cx_Oracle # DB 접속 라이브러리

import openpyxl # 엑셀 활용 라이브러리

import requests # 크롤링 라이브러리
from bs4 import BeautifulSoup

from urllib.request import urlopen # Url Img 읽기 위함



# db init

def dbConnect() :
    dsn = cx_Oracle.makedsn("54.160.178.185",1521,"xe")
    return cx_Oracle.connect("crtvp","ganggoo",dsn)

db = dbConnect()
cursor = db.cursor()

##

# db execute
#cursor.execute("select id, from univ")

##


# excel 파일 불러오기

fileDir = 'C:/Users/kimsh/Desktop/list.xlsx'
excelFile = openpyxl.load_workbook(fileDir)

sheet = excelFile['Sheet1']

cells = sheet['A2' : 'E324']    # 읽을 셀 범위 설정


rowNum = 0


# 크롤링 대상 웹 주소
base_url = 'https://www.academyinfo.go.kr/'
result_url = base_url + "search/search.do"
detailPage_url = base_url + "popup/pubinfo1690/list.do?schlId="

# 크롤링 대상 태그

imgTag = '#contentsWrap > div > div > div.college-search-result > div.college-data-wrap > div.img-box > img'
addressTag = '#contentsWrap > div > div > div.college-search-result > div.college-data-wrap > div.info-box > div > div:nth-child(2) > span > span:nth-child(2)'
webUrlTag = '#contentsWrap > div > div > div.college-search-result > div.college-data-wrap > div.info-box > div > div:nth-child(1) > span > a'
telTag = '#contentsWrap > div > div > div.college-search-result > div.college-data-wrap > div.info-box > div > div:nth-child(3) > span:nth-child(1) > span.info-value'
introTag = '#contentsWrap > div > div > div.college-info-box > div.college-desc > p'



for row in cells :
    rowNum += 1
    list = []

    for cell in row :
        list.append(cell.value)

    postData = {'kwd': list[1]}  # post 요청 위한 데이터 (학교이름)



    response01 = requests.post(result_url, data=postData)           # 검색결과페이지 req
    soup01 = BeautifulSoup(response01.content, "html.parser")

    univNum = soup01.select_one(imgTag)['onclick'].split('\'')[1]

    response02 = requests.get(detailPage_url + univNum )            # 디테일 페이지 req
    soup02 = BeautifulSoup(response02.content, "html.parser")


    addressStr = soup01.select(addressTag)[0].get_text().strip()    #학교 주소
    webUrlStr = soup01.select(webUrlTag)[0]['href'][2:]             #학교 웹 주소
    telStr = soup01.select(telTag)[0].text.strip()                  #학교 대표번호
    imgUrl = base_url + soup01.select_one(imgTag)['src']            #학교 로고 이미지 주소
    introStr = soup02.select(introTag)[0].text                      #학교 소개 정보


    with urlopen(imgUrl) as f :                                     # 학교 이미지파일 open
         imgData = f.read()

    # db 삽입 위한 데이터 정리 (현재 스파게티 코드)
    list.append(addressStr)
    list.append(telStr)
    list.append(webUrlStr)
    list.append(introStr)


    univ_id = list[0]
    univ_name = list[1]
    univ_type = list[2]
    univ_establishment_cls = list[3]
    univ_area = list[4]
    univ_address = list[5]
    univ_representative_number = list[6]
    univ_homepage_url = list[7]
    univ_logo_image_file = imgData
    univ_introduction = list[8]
    userview = 0

    # insert문
    sqlStr = '''insert into CRTVP."univ"
                values (:univ_id, :univ_name, :univ_type, :univ_establishment_cls, :univ_area, :univ_address, :univ_representative_number, :univ_homepage_url, :univ_logo_image_file, :univ_introduction, :userview)'''

    # insert문 실행
    cursor.execute(sqlStr, (univ_id, univ_name, univ_type, univ_establishment_cls, univ_area, univ_address, univ_representative_number,
                           univ_homepage_url, univ_logo_image_file, univ_introduction, userview)
                   )


    print(list)


# 커밋 안하면 저장안됨
db.commit()


# db close
cursor.close()
db.close()

##