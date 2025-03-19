# Didit-backend

## <span style="background-color: rgba(255, 222, 33, 0.4); color: white;">Lint</span>

ktlint를 사용하며, 자동 검사를 위해서는 컴파일을 해야합니다.
코드 스타일 관련 설정은 `.editorconfig` 내에 존재합니다.

### ktlint formatting commit 시 자동화

```bash
./gradlew installGitHooks
chmod +x .githooks/pre-commit
```

### 스크립트를 통해 pre-push 적용

```bash
sh ./.githooks/inithookspath.sh
```

이후 `git push` 시, 자동 문법 검사가 실행됩니다.

### 수동 검사

코드 문법 검사를 실행하려면:

```bash
./gradlew ktlintCheck
```

### 포맷팅

코드를 규칙에 맞게 자동으로 수정하려면:

```bash
./gradlew ktlintFormat
```

## <span style="background-color: rgba(255, 222, 33, 0.4); color: white;">DDL 문서 작성 컨벤션</span>

[참고 문서](https://www.red-gate.com/blog/database-devops/flyway-naming-patterns-matter)

DDL 문서 작성 시 아래의 컨벤션을 반드시 준수해야 합니다.

### 파일명 구조

```
<접두사><버전>__<설명>.sql
```

### 컨벤션 상세

#### 접두사

접두사는 파일 이름 앞에 붙으며 대문자로 명시합니다:

| 접두사 | 설명            |
|-----|---------------|
| `V` | 버전 변경 마이그레이션  |
| `U` | 실행 취소 마이그레이션  |
| `R` | 반복 가능한 마이그레이션 |

#### 버전

- 특정 마이그레이션에 대해 고유해야 합니다
- 버전이 지정된 마이그레이션과 실행 취소 마이그레이션은 공통 버전 번호를 공유해야 합니다
- 논리적 순서를 따라야 합니다 (예: `1.0`, `1.1` 등)

#### 구분자

- 버전과 설명 사이에 이중 밑줄(`__`)을 사용해야 합니다
- 이는 파일의 기능적 명명 부분과 설명적 부분을 구분합니다

#### 설명

- 이후에는 그냥 텍스트입니다. 단어 사이에 밑줄(`_`)을 사용하면 공백으로 번역됩니다.

## <span style="background-color: rgba(255, 222, 33, 0.4); color: white;">코드 작성 규칙(컨벤션)</span>

### 8. Import 문 작성 규칙

- 와일드카드(*) import 사용 금지
- 필요한 클래스만 명시적으로 import

```kotlin
// 잘못된 예시
import com.example.domain.*

// 올바른 예시
import com.example.domain.User
import com.example.domain.Order
```

<details>
  <summary style="background-color: rgba(255, 236, 131, 0.4); color: white; display: inline; cursor: pointer">Intellij 와일드카드(*) import 비활성화 하는 방법 (click!)</summary>

1. Settings 열기

| MacOS                                                    | Windows                                                  |
|----------------------------------------------------------|----------------------------------------------------------|
| <img height="284" alt="image" src="./.gitImage/1.png" /> | <img height="284" alt="image" src="./.gitImage/2.png" /> |

2. "`kotlin`" 검색
3. `Code style` -> `Kotlin` -> `Imports` 탭으로 이동

<img width="650" alt="image" src="./.gitImage/3.png" />

4. `Top-Level Symbols` & `Java Statics and Enum Members`: Use single name import 으로 설정
5. `Packages to Use Import with '*'` -> `import java.util.*` -> 체크 해제

<img width="650" alt="image" src="./.gitImage/4.png" />
</details>
# Didit
