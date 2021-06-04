package Client.vo;

public class IndicatorsVO {

	// NOTE : 미완성

	private final int NEW_STUDENT_RECRUITMENT = 0x01;	// 입학 경쟁률
	private final int EDU_COST_PERSON = 0x02;		// 1인당 교육비
	private final int START_COMPANY_STATUS = 0x04;	// 학생의 창업 및 창업 지원 현황
	private final int RESEARCH_COST = 0x04;	// 연구비 수혜 실적 (대학, 학과 둘 다 존재)
//	private final int RESEARCH_COST = 0x04;	// 특허 출원 및 등록 실적
//	private final int RESEARCH_COST = 0x04;	// 등록금 현황
//	private final int RESEARCH_COST = 0x04;	// 졸업생의 진학 현황
//	private final int RESEARCH_COST = 0x04;	// 졸업생의 취업 현황
//	private final int RESEARCH_COST = 0x04;	// 장학금 수혜 현황
//	private final int RESEARCH_COST = 0x04;	// 전임교원 연구실적



}
/*
		## 대학

		### 신입생 충원_현황 (입학경쟁률)

		입학경쟁률: NUMBER(8,2)

		### 1인당 교육비

		1인당 교육비: NUMBER(10)

		### 학생의 창업 및 창업 지원 현황

		창업자 수: NUMBER(5)

		창업기업 매출액: NUMBER(12)

		창업기업 자본금: NUMBER(12)

		교비 창업 지원액: NUMBER(12)

		정부 창업 지원액: NUMBER(12)

		창업 전담 교원: NUMBER(5)

		창업 전담 직원: NUMBER(5)

		### 연구비 수혜 실적

		전임교원수: NUMBER(5)

		전임교원 1인당 연구비: NUMBER(10)

		### 특허 출원 및 등록 실적

		국내 해외 특허 등록: NUMBER(5)

		---

		## 학과

		### 등록금 현황

		입학금: NUMBER(10)

		등록금: NUMBER(10)

		### 졸업생의 진학 현황

		국내 전문대학 진학: NUMBER(5)

		국외 전문대학 진학: NUMBER(5)

		국내 대학 진학: NUMBER(5)

		국외 대학 진학: NUMBER(5)

		국내 대학원 진학: NUMBER(5)

		국외 대학원 진학: NUMBER(5)

		국내 진학자 계: NUMBER(5)

		국외 진학자 계: NUMBER(5)

		진학률: NUMBER(8,2)

		### 졸업생의 취업 현황

		남자 취업대상자: NUMBER(5)

		여자 취업대상자: NUMBER(5)

		남자 국내 취업자: NUMBER(5)

		여자 국내 취업자: NUMBER(5)

		남자 해외 취업자: NUMBER(5)

		여자 해외 취업자: NUMBER(5)

		취업률: NUMBER(8,2)

		### 장학금 수혜 현황

		교외 장학금: NUMBER(10)

		교내 장학금: NUMBER(10)

		1인당 장학금: NUMBER(10)

		### 전임교원 연구실적

		전임교원 수: NUMBER(5)

		전임교원 1인당 논문 실적: NUMBER(5)

		### 연구비 수혜 실적

		전임교원 1인당 연구비: NUMBER(10)
*/