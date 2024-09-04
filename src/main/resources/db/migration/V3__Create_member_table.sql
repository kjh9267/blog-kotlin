DROP TABLE IF EXISTS ARTICLE;
DROP TABLE IF EXISTS CATEGORY;
DROP TABLE IF EXISTS MEMBER;

CREATE TABLE CATEGORY (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    mapped_article_count BIGINT NOT NULL
) ENGINE=INNODB CHARSET=UTF8MB4;

CREATE TABLE MEMBER (
    member_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=INNODB CHARSET=UTF8MB4;

CREATE TABLE ARTICLE (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    category_id BIGINT NOT NULL,
    writer_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content LONGTEXT NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES CATEGORY(id) ON UPDATE CASCADE,
    FOREIGN KEY (writer_id) REFERENCES MEMBER(member_id) ON UPDATE CASCADE
) ENGINE=INNODB CHARSET=UTF8MB4;