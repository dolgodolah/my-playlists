# **내플리스(My Playlists) 프론트**

### _노래 플레이리스트 웹 어플리케이션 프론트 구현하기_

<br>

- [메인 레포 (dolgodolah/my-playlists)](https://github.com/dolgodolah/my-playlists)

- [내플리스 Notion](https://mirror-oatmeal-27d.notion.site/e534804381b5409ab51b070076202822)

<br>

> ## 사용 스택

<br>

- React
- Typescript
- Sass
- RTK

<br>

> ## 디렉토리 구조

<br>

```bash
src
├── components
│   ├── EditBox.tsx
│   ├── Header.tsx
│   ├── PlayBox.tsx
│   ├── Playlist.tsx
│   ├── PlaylistCategory.tsx
│   ├── ProfileMenu.tsx
│   ├── Song.tsx
│   └── YoutubeVideo.tsx
├── routes
│   ├── home.tsx
│   ├── login.tsx
│   └── playlist.tsx
├── shared
│   └── Props.ts
├── store
├── styles
│   ├── components
│   │   ├── header.scss
│   │   ├── playBox.scss
│   │   ├── playlist.scss
│   │   ├── playlistCategory.scss
│   │   ├── song.scss
│   │   └── youtubeVideo.scss
│   ├── config
│   │   ├── _colors.scss
│   │   ├── _font.scss
│   │   └── _reset.scss
│   ├── routes
│   │   ├── home.scss
│   │   └── login.scss
│   └── styles.scss
├── test
│   └── user.ts
├── App.tsx
├── index.tsx
├── react-app-env.d.ts
└── Router.tsx
```

<br>

> ## 페이지 구성

<br>

<table>
  <th>url</th>
  <th>페이지 이름</th>
  <th>설명</th>
  <th>비고</th>
  <tr>
    <td>/</td>
    <td>내 플레이리스트 목록</td>
    <td>내플리스 메인 페이지이다.<br />내 플레이리스트 목록을 보여준다.</td>
    <td></td>
  </tr>
  <tr>
    <td>/</td>
    <td>모든 플레이리스트 목록</td>
    <td>내 플레이리스트를 포함해 public 으로 설정된 모든 플레이리스트 목록을 보여준다.</td>
    <td></td>
  </tr>
  <tr>
    <td>/</td>
    <td>즐겨찾기 플레이리스트 목록</td>
    <td>즐겨찾기한 플레이리스트 목록을 보여준다.</td>
    <td></td>
  </tr>
  <tr>
    <td>/login</td>
    <td>로그인</td>
    <td>
      내플리스는 자체 로그인 서비스를 제공하지 않고,
      Oauth 2.0을 제공한다. (카카오 로그인, 구글 로그인)
      릴리즈 버전에는 구글 로그인만 있는데
      개발 버전에는 카카오 로그인도 있다.
    </td>
    <td></td>
  </tr>
  <tr>
    <td>/playlist</td>
    <td>선택 플레이리스트 상세화면</td>
    <td>선택한 플레이리스트에 수록된 노래들을 보여준다.</td>
    <td></td>
  </tr>
</table>
