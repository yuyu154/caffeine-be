INSERT INTO shop(id, name, image, address, phone_number) VALUES(100, '어디야 커피 잠실점',
'https://github.com/eunsukko/TIL/blob/master/201912/caffeine/pictures/starbucks_%EC%84%9D%EC%B4%8C%ED%98%B8%EC%88%98.jpg?raw=true',
'서울특별시 송파구 석촌호수로 262 (송파동)', '02-758-8693');
INSERT INTO shop(id, name, image, address, phone_number) VALUES(101, '석촌호수',
'https://github.com/eunsukko/TIL/blob/master/201912/caffeine/pictures/starbucks_%EC%86%A1%ED%8C%8C%EA%B5%AC%EC%B2%AD.jpeg?raw=true',
'서울특별시 송파구 오금로 142 (송파동)','02-421-3622');

INSERT INTO menu_item(name, description, price, shop_id) VALUES('카페라떼', '고소한 카페라떼', 3000, 100);
INSERT INTO menu_item(name, description, price, shop_id) VALUES('고구마라떼', '달달한 라떼', 3000, 100);