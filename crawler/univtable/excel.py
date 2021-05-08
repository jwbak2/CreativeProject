
import openpyxl # 엑셀 활용 라이브러리


# excel 파일 불러오기

excellFile = openpyxl.load_workbook('C:/Users/kimsh/Desktop/list.xlsx')

sheet = excellFile['Sheet1']
cells = sheet['A1' : 'E2']


rowNum = 0


for row in cells :
    rowNum += 1

    list = []

    for cell in row :
        list.append(cell.value)


    print(list)


