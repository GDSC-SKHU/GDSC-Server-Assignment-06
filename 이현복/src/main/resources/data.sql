use jpa;

insert into team(create_date, last_modified_date, name)
values (CURRENT_DATE, CURRENT_DATE, 'server'),
       (CURRENT_DATE, CURRENT_DATE, 'web'),
       (CURRENT_DATE, CURRENT_DATE, 'mobile');




insert into member(create_date, last_modified_date, age, name, team_id)
values (CURRENT_DATE, CURRENT_DATE, 24, '한현수', 1),
       (CURRENT_DATE, CURRENT_DATE, 24, '이한길', 1),
       (CURRENT_DATE, CURRENT_DATE, 23, '조성우', 1),
       (CURRENT_DATE, CURRENT_DATE, 22, '곽성은', 1),
       (CURRENT_DATE, CURRENT_DATE, 20, '김가현', 1),
       (CURRENT_DATE, CURRENT_DATE, 22, '김수연', 1),
       (CURRENT_DATE, CURRENT_DATE, 22, '김자경', 1),
       (CURRENT_DATE, CURRENT_DATE, 23, '방예혁', 1),
       (CURRENT_DATE, CURRENT_DATE, 24, '서명진', 1),
       (CURRENT_DATE, CURRENT_DATE, 23, '이지선', 1),
       (CURRENT_DATE, CURRENT_DATE, 22, '이지윤', 1),
       (CURRENT_DATE, CURRENT_DATE, 25, '이현복', 1),
       (CURRENT_DATE, CURRENT_DATE, 22, '임정연', 1),
       (CURRENT_DATE, CURRENT_DATE, 24, '전홍영', 1),
       (CURRENT_DATE, CURRENT_DATE, 24, '허지영', 1),
       (CURRENT_DATE, CURRENT_DATE, 25, '오혜성', 2),
       (CURRENT_DATE, CURRENT_DATE, 22, '한슬희', 2),
       (CURRENT_DATE, CURRENT_DATE, 22, '김하은', 3),
       (CURRENT_DATE, CURRENT_DATE, 25, '주동석', 3);


insert into member(create_date, last_modified_date, age, name, team_id)
values (CURRENT_DATE, CURRENT_DATE, 25, '김유진', 3),
       (CURRENT_DATE, CURRENT_DATE, 22, '왕서희', 3),
       (CURRENT_DATE, CURRENT_DATE, 25, '김수빈', 3),
       (CURRENT_DATE, CURRENT_DATE, 26, '김재관', 2),
       (CURRENT_DATE, CURRENT_DATE, 25, '박현렬', 3),
       (CURRENT_DATE, CURRENT_DATE, 25, '노욱진', 2),
       (CURRENT_DATE, CURRENT_DATE, 23, '김신아', 2),
       (CURRENT_DATE, CURRENT_DATE, 23, '김효중', 2),
       (CURRENT_DATE, CURRENT_DATE, 28, '이찬균', 2),
       (CURRENT_DATE, CURRENT_DATE, 25, '이진우', 2),
       (CURRENT_DATE, CURRENT_DATE, 22, '전유진', 2),
       (CURRENT_DATE, CURRENT_DATE, 24, '최재훈', 2),
       (CURRENT_DATE, CURRENT_DATE, 20, '김은총', 3),
       (CURRENT_DATE, CURRENT_DATE, 21, '이은지', 2),
       (CURRENT_DATE, CURRENT_DATE, 24, '박신영', 3),
       (CURRENT_DATE, CURRENT_DATE, 22, '박진희', 3),
       (CURRENT_DATE, CURRENT_DATE, 23, '이예슬', 2),
       (CURRENT_DATE, CURRENT_DATE, 21, '백세희', 2);