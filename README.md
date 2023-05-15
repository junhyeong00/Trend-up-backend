# 🛒 trend-up-backend

<p align="center">
  <br>
  <img width="1199" alt="스크린샷 2023-02-09 오후 1 25 49" 
    src="https://user-images.githubusercontent.com/107493122/219388305-fbd48a57-d26d-400d-8c8c-b0f750e26694.png">
  <br>
</p>

## 프로젝트 소개
Trend Up은 전자기기 쇼핑몰로 사용자가 필요한 물건을 쉽게 탐색하고, 리뷰와 문의를 통해 신뢰도 높은 물건을 구매할 수 있는 것에 가치를 두고 만들었습니다.

## 배포 URL 
- 사용자: https://trend-up.fly.dev
- 어드민 : https://trend-up-admin.fly.dev

## GitHub 
- 서버 :  https://github.com/junhyeong00/Trend-up-backend
- 사용자 프론트 :  https://github.com/junhyeong00/Trend-up-frontend
- 어드민 프론트 : https://github.com/junhyeong00/Trend-up-admin

## 개발 기간
- 2022.11.26 ~ 2022.01.20
- 일주일 간격으로 8번의 스프린트를 진행했습니다.
<br>

## 기술 스택
### Backend
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"></a>
<img src="https://img.shields.io/badge/spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white">

### Database
<img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgreSQL&logoColor=white"/>
<br>

## 주요 기능

### 사용자

- 상품 카테고리별 조회 및 검색
- 상품 세부 정보 조회
- 상품 장바구니 담기
- 상품 주문 / 결제
- 주문 목록 및 상세 내역 조회
- 리뷰 작성
- 배송상태 조회
- 문의 작성
- 로그인 / 회원가입

### 관리자

- 상품 등록 및 관리
- 문의 답변 작성
- 주문 내역 조회 및 배송 관리
- 대시보드 조회

<br>

## 아키텍쳐
<p align="center">
  <br>
  <img width="600" alt="스크린샷" 
    src="https://github.com/junhyeong00/Trend-up-backend/assets/107493122/d144515f-bf48-46d1-b358-bef773c84734">
  <br>
</p>

<br>

## 👊 기술적 도전 및 문제 해결 과정
- Transactional
  - [@Transactional은 모든 작업을 RollBack해줄까?](https://velog.io/@jhbae0420/Transactional의-RollBack) 
  - [@Transactional(readOnly = true)를 사용하는 이유와 주의할점](https://velog.io/@jhbae0420/TransactionalreadOnly-true를-사용하는-이유와-주의할점)
- [비공개 문의글 작업](https://velog.io/@jhbae0420/비공개-문의글-작업-코딩시간-늘리기)
- 장바구니
  - [장바구니 상품을 어디에 저장할까?](https://velog.io/@jhbae0420/장바구니-상품을-어디에-저장할까-작업-의도-생각하기)
  - [db에 json 형태의 string을 저장해도 될까?](https://velog.io/@jhbae0420/db에-json-형태의-string을-저장해도-될까)

<br>

## 도메인 설계
<p align="center">
  <br>
  <img width="1199" alt="스크린샷 2023-02-09 오후 1 25 49" 
    src="https://user-images.githubusercontent.com/107493122/219573612-6a8be4d4-0330-404f-b8ac-2b073b74d6e5.png">
  <br>
</p>

<br>

## 🏃🏻‍♂️ 프로젝트 Sprint별 목표 
- Sprint#1
  - 프로젝트 기획
- Sprint#2
  - 쇼핑몰에서 상품을 확인하고 구매할 수 있다.
- Spring#3
  - 쇼핑몰에서 주문한 내역들을 확인할 수 있다. 
- Spring#4
  - 사용자는 제품에 대한 정보(신뢰도 확인)를 얻기 위해 리뷰를 확인할 수 있고, 다른 사람에게 도움을 주기 위해 구매한 제품에 대해 리뷰를 작성할 수 있다. 
- Spring#5
  - 사용자는 리뷰 삭제, 수정을 할 수 있고, 장바구니를 이용해 여러개의 상품을 한번에 구매할 수 있다.
- Sprint#6
  - 회원은 상품에 대한 문의를 작성할 수 있고 카카오를 이용하여 로그인 및 결제를 할 수 있다.
  - 관리자는 상품 등록을 할 수 있다. 
- Sprint#7
  - 관리자는 상품 관리를 위해 상품 목록에서 상품을 수정,삭제 할 수 있으며, 주문관리에서 상품의 배송상태를 변경할 수 있다. 
  - 관리자는 작성된 문의에 대한 답변을 작성할 수 있다. 

