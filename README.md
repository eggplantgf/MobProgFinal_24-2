java/com.example.mobprogfinal_v1/

├── adapters/
│   ├── PostsAdapter : 게시글 리스트를 RecyclerView로 표시하기 위한 어댑터
│   └── CommentItemAdapter : 댓글 리스트를 RecyclerView로 표시하기 위한 어댑터

├── auth/
│   ├── LoginActivity : Firebase Auth를 사용해 이메일과 비밀번호로 로그인
│   └── SignupActivity : Firebase Auth를 사용해 이메일과 비밀번호로 계정을 생성

├── board/
│   ├── BoardActivity : 게시판 화면의 메인 액티비티
│   ├── PostDetailActivity : 게시글 상세 화면 + 댓글 표시
│   └── PostFormActivity : 게시글 작성 및 수정 화면

├── models/
│   ├── Comment : 댓글 데이터 모델 클래스
│   └── Post : 게시글 데이터 모델 클래스

├── providers/
│   ├── AuthManager : 사용자 인증 관리. 로그인, 회원가입 기능
│   ├── CommentsManager : Firebase Realtime Database를 활용한 댓글 데이터 관리
│   └── PostsManager : Firebase Realtime Database를 활용한 게시글 데이터 관리
└── MainActivity


layout/
├── activity_board.xml : 게시판 화면의 레이아웃 - 게시글 리스트
├── activity_login.xml : 로그인 화면
├── activity_post_detail.xml : 게시글 상세 화면
├── activity_post_form.xml : 게시글 작성/수정 화면
├── activity_signup.xml : 회원가입 화면
├── item_comment.xml : 댓글 리스트 항목
└── item_post.xml : 게시글 리스트 항목
