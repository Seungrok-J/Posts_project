-- -- 사용자 생성 SQL
-- CREATE USER postuser WITH PASSWORD 'postuser';
--
-- -- 권한 부여 SQL
-- GRANT ALL PRIVILEGES ON DATABASE postdb TO postuser;

-- users 테이블에 더미 데이터 삽입
INSERT INTO users (created_at, updated_at, nick_name, password, role, user_email, user_name)
VALUES (NOW(), NULL, 'user1', 'password1', 'USER', 'user1@example.com', 'User One');

INSERT INTO users (created_at, updated_at, nick_name, password, role, user_email, user_name)
VALUES (NOW(), NULL, 'user2', 'password2', 'USER', 'user2@example.com', 'User Two');

-- categories 테이블에 더미 데이터 삽입
INSERT INTO categories (cate_name)
VALUES ('General');

INSERT INTO categories (cate_name)
VALUES ('Announcements');

-- boards 테이블에 더미 데이터 삽입
INSERT INTO boards (is_deleted, cate_id, count, created_at, deleted_at, updated_at, user_id, content, file_name, file_path, title)
VALUES (FALSE, 1, 0, NOW(), NULL, NULL, 1, 'This is the first board content.', NULL, NULL, 'First Board');

INSERT INTO boards (is_deleted, cate_id, count, created_at, deleted_at, updated_at, user_id, content, file_name, file_path, title)
VALUES (FALSE, 2, 0, NOW(), NULL, NULL, 2, 'This is the second board content.', NULL, NULL, 'Second Board');

-- comments 테이블에 더미 데이터 삽입
INSERT INTO comments (like_count, board_id, created_at, parent_comment_id, updated_at, user_id, content)
VALUES (0, 1, NOW(), NULL, NULL, 2, 'This is a comment on the first board.');

INSERT INTO comments (like_count, board_id, created_at, parent_comment_id, updated_at, user_id, content)
VALUES (0, 2, NOW(), NULL, NULL, 1, 'This is a comment on the second board.');

-- 대댓글 (부모 댓글에 대한 댓글) 삽입
INSERT INTO comments (like_count, board_id, created_at, parent_comment_id, updated_at, user_id, content)
VALUES (0, 1, NOW(), 1, NULL, 1, 'This is a reply to the first comment.');



-- boards 테이블에 더미 데이터 삽입
INSERT INTO boards (is_deleted, cate_id, count, created_at, deleted_at, updated_at, user_id, content, file_name, file_path, title)
VALUES (FALSE, 1, 0, NOW(), NULL, NULL, 1, 'This is the first board content.', NULL, NULL, 'First Board');

INSERT INTO boards (is_deleted, cate_id, count, created_at, deleted_at, updated_at, user_id, content, file_name, file_path, title)
VALUES (FALSE, 2, 0, NOW(), NULL, NULL, 2, 'This is the second board content.', NULL, NULL, 'Second Board');



-- boards 테이블에 더미 데이터 삽입
INSERT INTO boards (is_deleted, cate_id, count, created_at, deleted_at, updated_at, user_id, content, file_name, file_path, title)
VALUES (FALSE, 1, 0, NOW(), NULL, NULL, 1, 'This is the first board content.', NULL, NULL, '세번째 글');

INSERT INTO boards (is_deleted, cate_id, count, created_at, deleted_at, updated_at, user_id, content, file_name, file_path, title)
VALUES (FALSE, 2, 0, NOW(), NULL, NULL, 2, 'This is the second board content.', NULL, NULL, '네번째 글');



-- boards 테이블에 더미 데이터 삽입
INSERT INTO boards (is_deleted, cate_id, count, created_at, deleted_at, updated_at, user_id, content, file_name, file_path, title)
VALUES (FALSE, 1, 0, NOW(), NULL, NULL, 1, 'This is the first board content.', NULL, NULL, '다섯번째 글');

INSERT INTO boards (is_deleted, cate_id, count, created_at, deleted_at, updated_at, user_id, content, file_name, file_path, title)
VALUES (FALSE, 2, 0, NOW(), NULL, NULL, 2, 'This is the second board content.', NULL, NULL, '여섯번째 글');



-- boards 테이블에 더미 데이터 삽입
INSERT INTO boards (is_deleted, cate_id, count, created_at, deleted_at, updated_at, user_id, content, file_name, file_path, title)
VALUES (FALSE, 1, 0, NOW(), NULL, NULL, 1, 'This is the first board content.', NULL, NULL, '일곱번째 글');

INSERT INTO boards (is_deleted, cate_id, count, created_at, deleted_at, updated_at, user_id, content, file_name, file_path, title)
VALUES (FALSE, 2, 0, NOW(), NULL, NULL, 2, 'This is the second board content.', NULL, NULL, '여덟번째 글');



-- boards 테이블에 더미 데이터 삽입
INSERT INTO boards (is_deleted, cate_id, count, created_at, deleted_at, updated_at, user_id, content, file_name, file_path, title)
VALUES (FALSE, 1, 0, NOW(), NULL, NULL, 1, 'This is the first board content.', NULL, NULL, '아홉번째 글');

INSERT INTO boards (is_deleted, cate_id, count, created_at, deleted_at, updated_at, user_id, content, file_name, file_path, title)
VALUES (FALSE, 2, 0, NOW(), NULL, NULL, 2, 'This is the second board content.', NULL, NULL, '열번째 글');
