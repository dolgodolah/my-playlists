# 내플리스(My Playlists)

- ~~[내플리스 홈페이지](www.myplaylists.shop)~~ AWS 프리티어 기간이 끝났습니다..

- [내플리스 Notion](https://mirror-oatmeal-27d.notion.site/e534804381b5409ab51b070076202822)

### :ok_person: 프로젝트 소개

공부할 때, 비올 때, 놀러 갈 때 즐겨듣는 다양한 노래 플레이리스트들을 `내플리스`에 공유합니다.


<img width="70%" src="https://user-images.githubusercontent.com/75430912/117034231-0e094c00-ad3e-11eb-8d89-58b51605bd4f.gif"/>


#

### :computer: 핵심 기능

- `카카오 로그인`으로 손쉽게 내플리스를 시작할 수 있습니다. (`구글 로그인` 추가 예정)

- `내 플레이리스트 검색`과 `다른 사용자의 플레이리스트 검색`을 할 수 있습니다. 

- 내가 만든 플레이리스트를 `Public`/`Private` 설정으로 여러 사람들에게 공유하거나 본인만 들을 수 있습니다.

- 플레이리스트를 `즐겨찾기`할 수 있습니다. 

#

### :hammer_and_wrench: 기술 스택

`Spring Boot`, `Spraing Data JPA`, `Spring Security`

`React`, `Typescript`

~~`AWS EC2`~~


#


### :mag:  DB 설계
```mysql
create table playlist (
   playlist_id bigint not null auto_increment,
   created_date datetime(6),
   updated_date datetime(6),
   description varchar(255),
   song_count integer not null,
   title varchar(255),
   visibility bit not null,
   user_id bigint,
   author varchar(255),
   primary key (playlist_id),
   foreign key (user_id)
   references user (user_id)
) engine=InnoDB
```

```mysql
create table user (
   user_id bigint not null auto_increment,
   email varchar(255) not null,
   name varchar(255),
   nickname varchar(255),
   primary key (user_id)
) engine=InnoDB
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
   foreign key (user_id)
   references user (user_id),
   foreign key (playlist_id)
   references playlist (playlist_id)
) engine=InnoDB
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




