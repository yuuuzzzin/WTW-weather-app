
# ☀️ 날씨 어때 ?

![language](https://img.shields.io/badge/language-Kotlin-9cf)
![비동기처리](https://img.shields.io/badge/%EB%B9%84%EB%8F%99%EA%B8%B0%EC%B2%98%EB%A6%AC-CoRoutine-6054d1)
![network](https://img.shields.io/badge/network-Retrofit2-yellow)
![database](https://img.shields.io/badge/database-Room-d9fff8)
![image](https://img.shields.io/badge/image-Glide-edfcd2)
## 위치 기반 날씨 어플리케이션 '날씨어때?'

<img src="https://user-images.githubusercontent.com/57751515/120882526-6e2c3000-c613-11eb-8f7a-7b794c3ac99d.png" width="280">![wtw](https://user-images.githubusercontent.com/57751515/116548614-71a30c00-a92f-11eb-8aa1-75c450a55017.gif)

### :iphone: 화면 구성

<img src="https://user-images.githubusercontent.com/57751515/120883685-9323a180-c619-11eb-945e-feffcce4f649.png" width="280">

    1. 사용자의 현재 위치를 받아와 상단에 보여주는 텍스트뷰
    2. 현재 날씨와 날씨 이미지, 최고/최저 온도 레이아웃
    3. 현재 시간부터 24시간 동안의 날씨정보를 1시간 단위로 출력해 가로 스크롤 하며 볼 수 있는 리사이클러뷰
    4. 오늘 이후(내일)부터 일주일간의 날씨정보를 볼 수 있는 리사이클러뷰

### ✅ 기능
--------------------------------------
- 사용자의 현재 위치를 받아와 출력
- Retrofit2를 이용해 Weather API 날씨 정보 받아오기
- 비동기 처리 작업으로 코루틴 적용
- 날씨를 통해 현재, 시간별, 일별로 나누어 화면에 출력
- 날씨 정보를 Room을 이용해 Database에 저장
- Glide로 날씨별 이미지 로드

### 📁 프로젝트 구조
```
wtw_weather_app/
├─ adapter/
│  ├─ DailyAdapter.kt : 일별 날씨 recyclerview를 위한 adapter
│  └─ HourlyAdapter.kt : 시간별 날씨 recyclerview를 위한 adapter
├─ api_model/
│  ├─ CurrentAndHourly.kt : 시간별 날씨, 기온 정보를 위한 data class
│  ├─ Daily.kt : 일별 날씨, 기온 정보를 위한 data class
│  ├─ DailyTemp.kt : 일별 최저/최고 기온 정보를 위한 data class
│  ├─ Weather.kt : 날씨 API로부터 가져올 날씨 정보를 위한 data class
│  └─ WeatherApi.kt : 날씨 API로부터 가져올 날씨 정보를 위한 data class
├─ database/
│  ├─ CurrentDao.kt : 현재 날씨 database에 접근하는 객체
│  ├─ CurrentEntity.kt : 현재 날씨 entity
│  ├─ DailyDao.kt : 일별 날씨 database에 접근하는 객체
│  ├─ DailyEntity.kt : 일별 날씨 entity
│  ├─ HourlyDao.kt : 시간별 날씨 database에 접근하는 객체
│  └─ HourlyEntity.kt : 시간별 날씨 entity
├─ manage/
│  ├─ DatabaseManager.kt : Entity와 DAO를 묶어 database를 생성하고 관리
│  ├─ LocationManager.kt : 사용자의 현재 위치를 가져옴
│  └─ NetworkHelper.kt : retrofit 객체를 생성하고 api server에 요청 및 응답 관리
├─ set/
│  ├─ DailySet.kt : 일별 날씨 recyclerview adapter에 추가할 일별 날씨 data set
│  ├─ HourlySet.kt : 시간별 날씨 recyclerview adapter에 추가할 시간별 날씨 data set
MainActivity.kt : 비동기식으로 서버와 통신하도록 함
SplashActivity.kt : splash 화면을 위한 액티비티
WeatherApiService.kt : HTTP 요청을 수행하는 Call 메소드가 있는 API 인터페이스(APIService)
```

### 🛶 기술
--------------------------------------
- `Android Studio`
- 사용 언어 : `Kotlin`
- 비동기 처리 : `CoRoutine`
- 네트워크 : `Retrofit2`
- 데이터베이스 : `Room`
- 이미지 처리 : `Glide`
