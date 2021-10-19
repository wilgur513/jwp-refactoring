# 키친포스

## 요구 사항
### Order
#### 주문 등록 기능
* [ ] 주문을 등록할 수 있다.
    *  [ ] 주문 정보에 주문한 테이블의 정보(orderTableId)를 반드시 포함해야 한다.
    *  [ ] 주문한 메뉴 리스트(orderLineItems)가 포함하는 Menu 가 하나 이상이어야 한다.
    *  [ ] 주문 정보는 실제 존재하는 Table, Menu 를 포함해야 한다.

```json
{
  "orderTableId": 1,
  "orderLineItems": [
    {
      "menuId": 1,
      "quantity": 1
    }
  ]
}
```
#### 주문 전체 조회 기능
* [ ] 전체 Order 를 조회할 수 있다.
  
#### 주문 상태 변경 기능 
* [ ] 주문의 상태를 변경할 수 있다.
    * [ ] 주문 상태를 변경할 수 있는 상태는 COOKING, MEAL, COMPLETION 중 하나이다.
    * [ ] 완료된 주문의 상태를 변경할 수 없다.
```json
{
  "orderStatus": "MEAL"
}
```

```json
{
  "orderStatus": "COMPLETION"
}
```

### Product
#### 상품 등록 기능
* [ ] 상품을 등록할 수 있다.
    * [ ] 상품 이름, 가격이 반드시 존재해야 한다.
    * [ ] 상품 가격은 0원 이상이어야 한다.
```json
{
  "name": "강정치킨",
  "price": 17000
}
```
#### 상품 전체 조회 기능
* [ ] 전체 상품을 조회할 수 있다.

### Menu
#### 메뉴 생성 기능
* [ ] 메뉴를 등록할 수 있다.
    * [ ] 메뉴 이름, 가격, 메뉴의 그룹, 메뉴의 포함된 상품이 꼭 존재해야 한다.
    * [ ] 메뉴 가격은 0원 이상이어야 한다.
    * [ ] 메뉴는 상품을 한 개 이상 포함해야 한다.
```json
{
  "name": "후라이드+후라이드",
  "price": 19000,
  "menuGroupId": 1,
  "menuProducts": [
    {
      "productId": 1,
      "quantity": 2
    }
  ]
}
```
#### 메뉴 전체 조회 기능
* [ ] 전체 메뉴를 조회할 수 있다.

### MenuGroup
#### 메뉴 그룹 생성 기능
* [ ] 메뉴 그룹을 등록할 수 있다.
    * [ ] 메뉴 이름이 꼭 존재해야 한다.
```json
{
  "name": "추천메뉴"
}
```

#### 메뉴 그룹 전체 조회 기능 - @GetMapping("/api/menu-groups")
* [ ] 전체 메뉴 그룹을 조회할 수 있다.

### Table
#### 테이블 생성 기능
* [ ] 테이블을 등록할 수 있다.

```json
{
  "numberOfGuests": 0,
  "empty": true
}
```
#### 테이블 전체 조회 기능
* [ ] 전체 테이블을 조회할 수 있다.

#### 테이블 상태 비움 기능
* [ ] 특정 테이블 상태를 변경할 수 있다.
    * [ ] 테이블이 비어있는 경우 true, 사용하고 있는 경우 false를 지정한다.
```json
{
  "empty": false
}
```
#### 테이블 인원 변경 기능
* [ ] 특정 테이블의 방문 손님 수를 변경할 수 있다.
    * [ ] 방문 손님 수는 0 이상이어야 한다.
```json
{
  "numberOfGuests": 4
}
```

### TableGroup
#### 테이블 그룹 생성 기능
* [ ] 테이블 그룹을 등록할 수 있다.
    * [ ] 테이블 그룹으로 지정할 테이블의 id는 실제로 존재해야 한다.
```json
{
  "orderTables": [
    {
      "id": 1
    },
    {
      "id": 2
    }
  ]
}
```

#### 테이블 그룹 삭제 기능
* [ ] 특정 테이블 그룹을 삭제할 수 있다.

## 통합 테스트
### OrderIntegrationTest
#### 주문 등록
* [ ] 주문 등록 완료
* [ ] 요청에 `orderTableId`가 포함되지 않는  예외 발생
* [ ] 요청에 `orderLineItems`가 빈 리스트인 경우 예외 발생
* [ ] `orderTableId`를 가지는 테이블이 존재하지 않는 경우 예외 발생
* [ ] `orderLineItems`에 정의된 메뉴 리스트 중에 존재하지 않는 메뉴가 있는 경우 예외 발생

#### 주문 조회
* [ ] 전체 주문 조회 완료

#### 주문 상태 변경 
* [ ] 주문의 상태를 변경 완료
* [ ] 변경하려는 상태가 COOKING, MEAL, COMPLETION 중 하나가 아닌 경우 예외 발생
* [ ] 완료된 주문의 상태를 변경하려는 경우 예외 발생

### ProductIntegrationTest
#### 상품 등록
* [ ] 상품을 등록 완료
* [ ] 상품 `name`이 요청에 포함되지 않은 경우 예외 발생
* [ ] 상품 `price`가 존재하지 않는 경우 예외 발생
* [ ] 상품 가격은 0원 이상이 아닌 경우 예외 발생

#### 상품 전체 조회
* [ ] 전체 상품을 조회 완료

### MenuIntegrationTest
#### 메뉴 생성 기능
* [ ] 메뉴를 등록 완료
* [ ] 메뉴 `name`이 요청에 포함되지 않은 경우 예외 발생
* [ ] 메뉴 `price`이 요청에 포함되지 않은 경우 예외 발생
* [ ] 메뉴 `menuGroupId`이 요청에 포함되지 않은 경우 예외 발생
* [ ] 메뉴 `menuIds` 리스트가 한 개 이상 포함하지 않은 경우 예외 발생
* [ ] 메뉴 가격이 0원 이상이 아닌 경우 예외 발생
* [ ] 메뉴는 `menuIds`에 포함된 상품이 모두 존재하지 않은 경우 예외 발생

#### 메뉴 전체 조회
* [ ] 전체 메뉴 조회 완료

### TableIntegrationTest
#### 테이블 생성 기능
* [ ] 테이블 등록 완료

#### 테이블 전체 조회 기능
* [ ] 전체 테이블을 조회 완료

#### 테이블 상태 비움 기능
* [ ] 특정 테이블 상태를 변경 (false -> true)
* [ ] 특정 테이블 상태를 변경 (true -> false)
* [ ] 테이블이 비어있는 경우 true, 사용하고 있는 경우 false를 지정한다.

#### 테이블 인원 변경 기능
* [ ] 특정 테이블의 방문 손님 수를 변경
* [ ] 방문 손님 수는 0 이상이 아닌 경우 예외 발생

### TableGroupIntegrationTest
#### 테이블 그룹 생성 기능
* [ ] 테이블 그룹을 등록 완료
* [ ] 테이블 그룹으로 지정할 테이블의 id가 존재하지 않는 경우 예외 발생

#### 테이블 그룹 삭제 기능
* [ ] 특정 테이블 그룹을 삭제

## 용어 사전

| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 상품 | product | 메뉴를 관리하는 기준이 되는 데이터 |
| 메뉴 그룹 | menu group | 메뉴 묶음, 분류 |
| 메뉴 | menu | 메뉴 그룹에 속하는 실제 주문 가능 단위 |
| 메뉴 상품 | menu product | 메뉴에 속하는 수량이 있는 상품 |
| 금액 | amount | 가격 * 수량 |
| 주문 테이블 | order table | 매장에서 주문이 발생하는 영역 |
| 빈 테이블 | empty table | 주문을 등록할 수 없는 주문 테이블 |
| 주문 | order | 매장에서 발생하는 주문 |
| 주문 상태 | order status | 주문은 조리 ➜ 식사 ➜ 계산 완료 순서로 진행된다. |
| 방문한 손님 수 | number of guests | 필수 사항은 아니며 주문은 0명으로 등록할 수 있다. |
| 단체 지정 | table group | 통합 계산을 위해 개별 주문 테이블을 그룹화하는 기능 |
| 주문 항목 | order line item | 주문에 속하는 수량이 있는 메뉴 |
| 매장 식사 | eat in | 포장하지 않고 매장에서 식사하는 것 |
