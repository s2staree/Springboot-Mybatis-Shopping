# 온라인쇼핑 플랫폼 만들기 5단계 (MyBatis)

-----------------------------------------------------------------------------------------------------------
## 1단계 (기본 CRUD + JSP)

### View 생성
- 상품 등록 페이지
- 상품 목록 페이지 (html table 사용)
- 상품 상세보기 페이지 (삭제/수정)
- 상품 수정 페이지

### 기능 (Ajax X) (Service X)
- 상품 등록 (post - form태그) 주소 : /product/add
- 상품 목록보기 (get) 주소 : /product
- 상품 상세보기 (get) 주소 : /product/{id}
- 상품 삭제하기 (post - form태그) : /product/{id}/delete
- 상품 수정하기 (post - form태그) : /product/{id}/edit

### 테이블 생성
```sql
create table product(
    product_id INT PRIMARY KEY auto_increment,
    product_name VARCHAR(20) NOT NULL,
    product_price INT NOT NULL,
    product_qty INT NOT NULL,
    created_at TIMESTAMP NOT NULL
);
```

### 더미데이터
```sql
INSERT INTO product(product_name, product_price, product_qty, created_at) VALUES('바나나', 3000, 98, NOW());
INSERT INTO product(product_name, product_price, product_qty, created_at) VALUES('딸기', 2000, 100, NOW());
```

-----------------------------------------------------------------------------------------------------------
## 2단계 (Ajax)

### 고급 기능
- 상품 등록시 동일한 상품명 못들어가게 하기 (상품명 중복확인)
- 값 변경되는 거 감지해야 함 (이벤트)

-----------------------------------------------------------------------------------------------------------
## 3단계 (로그인 + 세션 + 연관관계)

서버1 (판매자 서버) / 서버2 (구매자 서버) : DB 를 공유

- (구매 테이블 필요)
- (유저 테이블 필요) : 구매자

### View 생성
- 회원가입 페이지
- 로그인 페이지
- 구매하기 페이지
- 구매목록 페이지

### 기능 추가사항
- 회원 가입
- 로그인
- 상품 목록보기 (기존과 동일)
- 상품 상세보기 (기존과 다름 : 구매하기 버튼)
- 상품 구매하기
- 상품 구매 후 상품 재고수정
- 상품 구매목록보기
+ 상품 구매취소
+ 상품 구매취소 후 상품 재고복원

### 테이블, 더미데이터
```sql
DROP TABLE product;

create table product(
    product_id INT PRIMARY KEY auto_increment,
    product_name VARCHAR(20) NOT NULL UNIQUE,
    product_price INT NOT NULL,
    product_qty INT NOT NULL,
    created_at TIMESTAMP NOT NULL
);

INSERT INTO product(product_name, product_price, product_qty, created_at) VALUES('바나나', 3000, 98, NOW());
INSERT INTO product(product_name, product_price, product_qty, created_at) VALUES('딸기', 2000, 100, NOW());
/*--------------------------------------------------------------*/
create table user(
    user_id INT PRIMARY KEY auto_increment,
    user_name VARCHAR(20) NOT NULL UNIQUE,
    user_password VARCHAR(20) NOT NULL,
    user_email VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

INSERT INTO user(user_name, user_password, user_email, created_at) VALUES('user', '0000', 'user@email.com', NOW());
/*--------------------------------------------------------------*/
create table orders(
    order_id INT PRIMARY KEY auto_increment,
    user_id INT NOT NULL,
    order_user_name VARCHAR(20) NOT NULL,
    product_id INT NOT NULL,
    order_product_name VARCHAR(20) NOT NULL,
    order_product_price INT NOT NULL,
    order_product_qty INT NOT NULL,
    created_at TIMESTAMP NOT NULL
);
```

-----------------------------------------------------------------------------------------------------------
## 4단계 (판매자/구매자 role 구분하기)

- admin : 판매자 (관리자)
- customer : 구매자 (손님)

### 테이블, 더미데이터
```sql
DROP TABLE user;

create table user(
    user_id INT PRIMARY KEY auto_increment,
    user_name VARCHAR(20) NOT NULL UNIQUE,
    user_password VARCHAR(20) NOT NULL,
    user_email VARCHAR(20) NOT NULL,
    user_role VARCHAR(20) NOT NULL DEFAULT('customer'),
    /* user_role에 해당되는 값이 없으면 NULL값 대신 DEFAULT인 'customer'가 넣어짐!*/
    created_at TIMESTAMP NOT NULL
);

INSERT INTO user(user_name, user_password, user_email, user_role, created_at) VALUES('admin', '0000', 'admin@email.com', 'admin', NOW());
INSERT INTO user(user_name, user_password, user_email, user_role, created_at) VALUES('customer', '0000', 'customer@email.com', 'customer', NOW());
```

-----------------------------------------------------------------------------------------------------------
## 5단계 (관리자 - 회원목록보기/주문목록보기)

### View 생성
- 회원 목록 페이지
- 회원 주문목록 페이지
+ 회원 정보수정 페이지

### 기능 추가사항
- 회원 목록보기 (전체회원목록/손님회원목록)
- 회원 주문목록보기 (전체주문목록/오늘주문목록)
+ 회원 주문철회
+ 회원 주문철회 후 상품 재고복원
+ 회원 정보수정
+ 회원 정보삭제
+ 회원 가입시 아이디 중복확인 (아이디 변경감지)

-----------------------------------------------------------------------------------------------------------