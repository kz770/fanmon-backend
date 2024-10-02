# ------------------------------부모 테이블-----------------------------------------------
# USER
INSERT INTO user (useruuid, status, role, name, email, password, phone, address, birth, createdat, updatedat) VALUES
                                                                                                                  (UNHEX(REPLACE(UUID(), '-', '')), 'ACTIVE', 'NORMAL', '유저1', 'user1@test.com', '', '01012345678', '서울시 종로구 관철동', '2024-05-05', '2024-05-13 10:00:00', '2024-05-13 10:00:00'),
                                                                                                                  (UNHEX(REPLACE(UUID(), '-', '')), 'ACTIVE', 'NORMAL', '유저2', 'user2@test.com', '', '01011112222', '서울시 노원구 공릉동', '2024-05-05', '2024-05-13 10:00:00', '2024-05-13 10:00:00'),
                                                                                                                  (UNHEX(REPLACE(UUID(), '-', '')), 'ACTIVE', 'ADMIN', '유저3', 'user3@test.com', '', '01011113333', '서울시 강서구 화곡동', '2024-05-05', '2024-05-13 10:00:00', '2024-05-13 10:00:00'),
                                                                                                                  (UNHEX(REPLACE(UUID(), '-', '')), 'BANNED', 'NORMAL', '유저4', 'user4@test.com', '', '01011114444', '서울시 노원구 공릉2동', '2024-05-05', '2024-05-13 10:00:00', '2024-05-13 10:00:00'),
                                                                                                                  (UNHEX(REPLACE(UUID(), '-', '')), 'DELETED', 'NORMAL', '유저5', 'user5@test.com', '', '01011115555', '서울시 노원구 공릉3동', '2024-05-05', '2024-05-13 10:00:00', '2024-05-13 10:00:00');


# MANAGEMENT
INSERT INTO management (managementuuid, status, email, password, name, businessno, createdat, updatedat, address) VALUES
                                                                                                                      (UNHEX(REPLACE(UUID(), '-', '')), 'NOT_APPROVED', 'sm@test.com', '', 'SM', '1234567891', '2024-05-13 10:00:00', '2024-05-13 10:00:00', '서울시 종로구 관철동'),
                                                                                                                      (UNHEX(REPLACE(UUID(), '-', '')), 'APPROVED', 'yg@test.com', '', 'YG', '1234567892', '2024-05-13 10:00:00', '2024-05-13 10:00:00', '서울시 종로구 관철2동'),
                                                                                                                      (UNHEX(REPLACE(UUID(), '-', '')), 'APPROVED', 'jyp@test.com', '', 'JYP', '1234567893', '2024-05-13 10:00:00', '2024-05-13 10:00:00', '서울시 종로구 관철3동'),
                                                                                                                      (UNHEX(REPLACE(UUID(), '-', '')), 'APPROVED', 'edam@test.com', '', 'EDAM', '1234567894', '2024-05-13 10:00:00', '2024-05-13 10:00:00', '서울시 종로구 관철4동'),
                                                                                                                      (UNHEX(REPLACE(UUID(), '-', '')), 'APPROVED', 'fnc@test.com', '', 'FNC', '1234567895', '2024-05-13 10:00:00', '2024-05-13 10:00:00', '서울시 종로구 관철5동');

select * from user;
select * from management;
# -----------------------------자식 테이블------------------------------------------------
# ARTIST
INSERT INTO artist (artistuuid, name, managementuuid, debut, email, password, birth, fname) VALUES
                                                                                                (UNHEX(REPLACE(UUID(), '-', '')), '영케이', UNHEX(REPLACE('208c18d6-8092-11ef-b4db-0a2a78c30fc9', '-', '')), '2015-09-07', 'youngk@example.com', 'YoungK@2023', '1993-08-31', 'youngk.png'),
                                                                                                (UNHEX(REPLACE(UUID(), '-', '')), '아이유', UNHEX(REPLACE('208c1979-8092-11ef-b4db-0a2a78c30fc9', '-', '')), '2008-09-18', 'iu.official@example.com', 'IU@2023', '1993-05-16', 'iu.png'),
                                                                                                (UNHEX(REPLACE(UUID(), '-', '')), '카리나', UNHEX(REPLACE('208c12a6-8092-11ef-b4db-0a2a78c30fc9', '-', '')), '2020-11-17', 'karina_official@example.com', 'Karina@2023', '2000-04-11', 'karina.png'),
                                                                                                (UNHEX(REPLACE(UUID(), '-', '')), '제니', UNHEX(REPLACE('208c173f-8092-11ef-b4db-0a2a78c30fc9', '-', '')), '2016-08-08', 'jennieofficial@example.com', 'Jennie@2023', '1996-01-16', 'jennie.png'),
                                                                                                (UNHEX(REPLACE(UUID(), '-', '')), '정해인', UNHEX(REPLACE('208c1a13-8092-11ef-b4db-0a2a78c30fc9', '-', '')), '2014-03-05', 'jung_haein@example.com', 'JungHaeIn@2023', '1988-04-26', 'junghi.png');


# TEAM
INSERT INTO team (teamuuid, name, managementuuid, debut, description, followers) VALUES
                                                                                 (UNHEX(REPLACE(UUID(), '-', '')), 'DAY6', 'JYP', '2015-09-07', '감성을 자극하는 로크 밴드, DAY6의 음악으로 당신의 하루를 채워보세요!', 1200000),
                                                                                 (UNHEX(REPLACE(UUID(), '-', '')), 'IU', 'EDAM', '2008-09-18', '다양한 장르를 소화하는 만능 아티스트, IU의 세계로 초대합니다!', 3500000),
                                                                                 (UNHEX(REPLACE(UUID(), '-', '')), 'AESPA', 'SM', '2020-11-17', '가상 세계와 현실이 만나는 신개념 걸그룹, AESPA의 매력을 경험해보세요!', 1800000),
                                                                                 (UNHEX(REPLACE(UUID(), '-', '')), 'BLACKPINK', 'YG', '2016-08-08', '전 세계를 사로잡은 글로벌 걸그룹, BLACKPINK의 놀라운 무대를 놓치지 마세요!', 3800000),
                                                                                 (UNHEX(REPLACE(UUID(), '-', '')), '정해인', 'FNC', '2014-03-05', '다양한 캐릭터로 사랑받는 배우, 정해인의 일상과 작품을 공유합니다.', 2000000);

# ARTISTTEAM

# GOODS


select * from artist;
select * from user;
select * from management;
commit;