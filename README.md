# 온라인쇼핑 플랫폼 만들기 5단계 (MyBatis)

-----------------------------------------------------------------------------------------------------------
## 1단계 (기본 CRUD + JSP)

### View 생성
- 상품등록 페이지
- 상품목록 페이지 (html table 사용)
- 상품상세보기 페이지 (삭제, 수정)
- 상품수정 페이지

### 기능 (ajax 필요 없음) (서비스 만들 필요 없음)
- 상품 등록(post - form태그)  주소 : /product/add
- 상품 목록보기(get) 주소 : /product
- 상품 상세보기(get) 주소 : /product/{id}
- 상품 삭제하기(post - form태그) : /product/{id}/delete
- 상품 수정하기(post - form태그) : /product/{id}/edit

### 테이블 생성
```sql
create table product(
    product_id int primary KEY auto_increment,
    product_name varchar(20) NOT null,
    product_price INT NOT null,
    product_qty INT NOT null,
    created_at TIMESTAMP NOT null
);
```

### 더미데이터
```sql
INSERT INTO product(product_name, product_price, product_qty, created_at) VALUES('바나나', 3000, 98, NOW());
INSERT INTO product(product_name, product_price, product_qty, created_at) VALUES('딸기', 2000, 100, NOW());
```

-----------------------------------------------------------------------------------------------------------
## 2단계 (AJAX)

### 고급 기능
- 상품 등록시 동일한 상품명 못들어가게 하기 (상품명 중복확인하기)
- 값 변경되는 거 감지해야 함 (이벤트)

-----------------------------------------------------------------------------------------------------------
## 3단계 (로그인+세션+연관관계)

서버1 (판매자 서버) / 서버2 (구매자 서버) : DB 를 공유

- (구매 테이블 필요)
- (유저 테이블 필요) : 구매자

### 기능 추가사항
- 회원가입
- 로그인
- 상품목록 보기 (기존과 동일)
- 상품상세 보기 (기존과 다름 : 구매하기 버튼)
- 구매 후 상품 재고 수정
+ 구매취소 후 상품 재고 복원
- 구매목록 보기

### 테이블, 더미데이터
```sql
DROP TABLE product;

create table product(
    product_id int primary KEY auto_increment,
    product_name varchar(20) NOT null UNIQUE,
    product_price INT NOT null,
    product_qty INT NOT null,
    created_at TIMESTAMP NOT null
);

INSERT INTO product(product_name, product_price, product_qty, created_at) VALUES('바나나', 3000, 98, NOW());
INSERT INTO product(product_name, product_price, product_qty, created_at) VALUES('딸기', 2000, 100, NOW());
/*--------------------------------------------------------------*/
create table user(
    user_id int primary KEY auto_increment,
    user_name varchar(20) NOT null UNIQUE,
    user_password varchar(20) NOT null,
    user_email varchar(20) NOT null,
    created_at TIMESTAMP NOT null
);

INSERT INTO user(user_name, user_password, user_email, created_at) VALUES('user', '0000', 'user@email.com', NOW());
/*--------------------------------------------------------------*/
create table orders(
    order_id int primary KEY auto_increment,
    user_id int NOT null,
    order_user_name varchar(20) NOT null,
    product_id int NOT null,
    order_product_name varchar(20) NOT null,
    order_product_price int NOT null,
    order_product_qty int NOT null,
    created_at TIMESTAMP NOT null
);
```

-----------------------------------------------------------------------------------------------------------
## 4단계 (판매자/구매자 role구분하기)

- admin : 판매자(관리자)
- customer : 구매자

### 테이블, 더미데이터
```sql
DROP TABLE user;

create table user(
    user_id int primary KEY auto_increment,
    user_name varchar(20) NOT NULL UNIQUE,
    user_password varchar(20) NOT null,
    user_email varchar(20) NOT null,
    user_role VARCHAR(20) NOT null DEFAULT('customer'),
    /* user_role에 해당되는 값이 없으면 null값 대신 DEFAULT인 'customer'가 넣어짐!*/
    created_at TIMESTAMP NOT null
);

INSERT INTO user(user_name, user_password, user_email, user_role, created_at) VALUES('admin', '0000', 'admin@email.com', 'admin', NOW());
INSERT INTO user(user_name, user_password, user_email, user_role, created_at) VALUES('customer', '0000', 'customer@email.com', 'customer', NOW());
```

-----------------------------------------------------------------------------------------------------------
## 5단계 (관리자-회원목록보기/주문목록보기)

### 기능 추가사항
- 회원 목록 보기
- 회원 주문목록 보기
+ 회원 정보 수정
+ 회원 정보 삭제
+ 회원 가입시 아이디 중복확인

-----------------------------------------------------------------------------------------------------------