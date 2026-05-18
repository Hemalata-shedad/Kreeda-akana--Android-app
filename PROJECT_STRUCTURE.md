# Kreeda-Ankana — Full Project Structure

```
KreedaAnkana/
├── app/
│   ├── build.gradle
│   ├── google-services.json          ← YOU must add your own from Firebase Console
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/kreedaankana/
│       │   ├── KreedaAnkanaApp.kt
│       │   ├── data/
│       │   │   ├── local/
│       │   │   │   ├── AppDatabase.kt
│       │   │   │   ├── TeamDao.kt
│       │   │   │   ├── MatchHistoryDao.kt
│       │   │   │   ├── BookingDao.kt
│       │   │   │   ├── TeamEntity.kt
│       │   │   │   ├── MatchHistoryEntity.kt
│       │   │   │   └── BookingEntity.kt
│       │   │   ├── remote/
│       │   │   │   └── FirebaseRepository.kt
│       │   │   └── model/
│       │   │       ├── Challenge.kt
│       │   │       ├── ScoreEntry.kt
│       │   │       └── SlotBooking.kt
│       │   ├── ui/
│       │   │   ├── onboarding/
│       │   │   │   ├── OnboardingActivity.kt
│       │   │   │   └── OnboardingViewModel.kt
│       │   │   ├── main/
│       │   │   │   └── MainActivity.kt
│       │   │   ├── calendar/
│       │   │   │   ├── CalendarFragment.kt
│       │   │   │   ├── CalendarViewModel.kt
│       │   │   │   └── SlotAdapter.kt
│       │   │   ├── challenge/
│       │   │   │   ├── ChallengeFragment.kt
│       │   │   │   ├── ChallengeViewModel.kt
│       │   │   │   ├── ChallengeAdapter.kt
│       │   │   │   └── PostChallengeDialog.kt
│       │   │   ├── scorewall/
│       │   │   │   ├── ScoreWallFragment.kt
│       │   │   │   ├── ScoreWallViewModel.kt
│       │   │   │   ├── ScoreAdapter.kt
│       │   │   │   └── PostScoreDialog.kt
│       │   │   └── profile/
│       │   │       ├── ProfileFragment.kt
│       │   │       └── ProfileViewModel.kt
│       │   └── utils/
│       │       ├── Constants.kt
│       │       └── Extensions.kt
│       └── res/
│           ├── layout/ (all XML layouts)
│           ├── values/
│           │   ├── strings.xml
│           │   ├── colors.xml
│           │   └── themes.xml
│           └── drawable/ (shape drawables)
├── build.gradle (project level)
└── settings.gradle
```
