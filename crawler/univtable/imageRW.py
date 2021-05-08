
from bs4 import BeautifulSoup as bs
from urllib.request import urlopen
from urllib.parse import quote_plus

url = 'https://www.academyinfo.go.kr/image/schlInfoJspImgLoad.do?paramUrl=/infofile/PDF_UPLOAD/ANNOUNCE/20160427104625246.jpg'


with urlopen(url) as f:
    with open('./images/img' + '00' + '.jpg', 'wb') as h:  # w - write b - binary
        img = f.read()
        h.write(img)


