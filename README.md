# 내플리스(My Playlists)

- [내플리스 시작하기](https://www.myplaylists.shop)

- [내플리스 Notion](https://mirror-oatmeal-27d.notion.site/e534804381b5409ab51b070076202822)

### :ok_person: 프로젝트 소개

공부할 때, 비올 때, 놀러 갈 때 즐겨듣는 다양한 노래 플레이리스트들을 `내플리스`에 공유합니다.


<img width="70%" src="https://user-images.githubusercontent.com/75430912/117034231-0e094c00-ad3e-11eb-8d89-58b51605bd4f.gif"/>


#

### :computer: 핵심 기능

- `카카오 로그인`, `구글 로그인`으로 손쉽게 내플리스를 시작할 수 있습니다.

- 노래 검색 시 `유튜브 API`를 통해 상위 5개의 검색 결과가 노출됩니다. 

- 플레이리스트 검색을 통해 `내 플레이리스트`와 `다른 사용자의 플레이리스트`를 쉽게 찾을 수 있습니다.

- `Public`/`Private` 설정으로 내가 만든 플레이리스트를 여러 사람들에게 공유하거나 본인에게만 노출되도록 할 수 있습니다.

- 즐겨 듣는 플레이리스트를 `즐겨찾기`에 추가할 수 있습니다.

#

### :hammer_and_wrench: 기술 스택

`Java`, `Kotlin`, `Spring Boot`, `Spring Data JPA`, `MySQL`

`React`, `Typescript`

`Google Youtube Data API`, `Kakao Developers API`

~~`AWS EC2`~~


#


### :mag:  DB 설계
```mysql
CREATE TABLE `playlist` (
    `playlist_id` bigint NOT NULL AUTO_INCREMENT,
    `created_date` datetime(6) DEFAULT NULL,
    `updated_date` datetime(6) DEFAULT NULL,
    `description` varchar(255) DEFAULT NULL,
    `title` varchar(255) DEFAULT NULL,
    `visibility` bit(1) NOT NULL,
    `user_id` bigint NOT NULL,
    PRIMARY KEY (`playlist_id`),
    KEY `FK_USER_ID_PLAYLIST` (`user_id`),
    CONSTRAINT `FK_USER_ID_PLAYLIST` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB
```

```mysql
CREATE TABLE `user` (
    `user_id` bigint NOT NULL AUTO_INCREMENT,
    `created_date` datetime(6) DEFAULT NULL,
    `updated_date` datetime(6) DEFAULT NULL,
    `email` varchar(255) NOT NULL,
    `name` varchar(255) DEFAULT NULL,
    `nickname` varchar(255) DEFAULT NULL,
    `oauth_type` int NOT NULL,
    PRIMARY KEY (`user_id`)
) ENGINE=InnoDB
```

```mysql
create table song (
   song_id bigint not null auto_increment,
   playlist_id bigint,
   user_id bigint,
   created_date datetime(6),
   updated_date datetime(6),
   description varchar(255),
   title varchar(255),
   video_id varchar(255),
   primary key (song_id),
   foreign key (playlist_id)
   references playlist (playlist_id)
) engine=InnoDB
create index idx_user_id on song (user_id)
```

```mysql
create table bookmark (
   bookmark_id bigint not null auto_increment,
   playlist_id bigint,
   user_id bigint,
   primary key (bookmark_id),
   foreign key (playlist_id)
   references playlist (playlist_id),
   foreign key (user_id)
   references user (user_id)
) engine=InnoDB
```

<img width="70%" src="https://user-images.githubusercontent.com/75430912/119460955-2d622a80-bd7a-11eb-880a-6352567e150b.jpg"/>

<img width="70%" src="https://user-images.githubusercontent.com/75430912/120918572-131c3b00-c6f0-11eb-913e-0f0a6021cbb7.jpg"/>




