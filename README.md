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
![image](https://github.com/junhyeong00/Trend-up-backend/assets/107493122/a46f521a-a5ea-4094-84e0-8cbc84fddb19)

<br>


## 도메인 설계
<p align="center">
  <br>
  <img width="850" alt="도메인" 
    src="https://github.com/junhyeong00/Trend-up-backend/assets/107493122/443179ea-409c-4467-bb34-ab3e56a52c2d">
  <br>
</p>


<br>

## 아키텍쳐
<p align="center">
  <br>
  <img width="800" alt="아키텍쳐" 
    src="https://github.com/junhyeong00/Trend-up-backend/assets/107493122/d144515f-bf48-46d1-b358-bef773c84734">
  <br>
</p>

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

## 👊 기술적 도전 및 문제 해결 과정
- [Redis 캐시(Cache)를 적용해 조회 성능 50% 개선](https://velog.io/@jhbae0420/Redis-캐시Cache를-적용해-조회-성능-개선하기)
- Transactional
  - [@Transactional은 모든 작업을 RollBack해줄까?](https://velog.io/@jhbae0420/Transactional의-RollBack) 
  - [@Transactional(readOnly = true)를 사용하는 이유와 주의할점](https://velog.io/@jhbae0420/TransactionalreadOnly-true를-사용하는-이유와-주의할점)
- [비공개 문의글 작업](https://velog.io/@jhbae0420/비공개-문의글-작업-코딩시간-늘리기)
- 장바구니
  - [장바구니 상품을 어디에 저장할까?](https://velog.io/@jhbae0420/장바구니-상품을-어디에-저장할까-작업-의도-생각하기)
  - [db에 json 형태의 string을 저장해도 될까?](https://velog.io/@jhbae0420/db에-json-형태의-string을-저장해도-될까)

<br>

### **Redis Cache를 적용해 조회 성능 50% 개선**

**구현 이슈**

- 서버를 무료 플랜으로 배포하였더니 상품 목록 화면로딩이 느려지는 현상 발생

**해결**

- 빈번하게 사용되지만 변경이 자주 발생되지 않는 리소스의 조회 요청 성능을 개선하기 위해 Cache 적용
- 쇼핑몰의 특성상 데이터의 일관성이 깨지면 비즈니스에 영향을 주기 때문에 Global Cache 서버를 사용
- Jmeter를 이용해 부하테스트 진행 → 상품목록 조회 평균속도 50% 개선 (415ms > 206ms)
    
    ⇒ 성능 개선 경험을 통해 사용자를 위한 성능적인 측면도 고려하게 됨
    
 <br>


### **장바구니 상품을 Json형태의 String으로 저장**

- LocalStorage에 저장하는 방식을 이용하여 DB를 사용하지 않아도 장바구니를 관리할 수 있도록 구현

**구현 이슈**

- 다른 기기로 접속했을 때 이전에 담아둔 장바구니를 그대로 사용할 수 없다는 사용성 측면의 문제 발생

**해결**

- LocalStorage와 함께 DB도 사용하여 다른 기기에서도 장바구니를 이용할 수 있도록 함. 또한, 상품 변경 및 삭제 시에는 DB에 데이터를 저장하고, 로그인 시에만 데이터를 받아오도록 구현하여 **쿼리 횟수 최소화**
- Json 형태의 String으로 상품 목록을 DB에 저장하여, 변경된 상품 목록을 프론트에서 처리하고 DB에는 변경된 내용만 저장하여 작업을 간단화하고 **DB 부담 최소화**

<br>

### **JUnit5, Mockito 를 이용한 단위 테스트 작성**

- **JUnit**과 **Mockito**를 이용한 독립적인 테스트 코드로 비즈니스 로직의 무결성 검증
- 백엔드 프로젝트 **테스트 코드 168개 / Line coverage 67%**
    
    
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

