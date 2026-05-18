# Kreeda-Ankana — Setup Guide
=============================================

## Step 1: Firebase Setup (REQUIRED)

1. Go to https://console.firebase.google.com/
2. Click **Add Project** → name it "KreedaAnkana"
3. Inside the project, click **Add App** → select Android
4. Package name: `com.kreedaankana`
5. Download the `google-services.json` file
6. Place it at: `app/google-services.json`  ← MUST be here

### Enable Firebase Realtime Database:
1. In Firebase Console → **Realtime Database** → Create Database
2. Start in **Test Mode** (for development)
3. Go to **Rules** tab and paste the contents of `firebase_rules.json`
4. Click **Publish**

---

## Step 2: Open in Android Studio

1. Open Android Studio (Hedgehog or newer)
2. File → Open → select the `KreedaAnkana` folder
3. Wait for Gradle sync to finish
4. Make sure you have placed `google-services.json` in `app/`

---

## Step 3: Run the App

1. Connect a physical Android device (Android 8.0+) or use AVD
2. Click the **Run** ▶ button
3. First launch shows the **Onboarding** screen — enter team name, captain, sport

---

## Project Structure Summary

```
Screens:
  Onboarding    → Team setup (one-time)
  Ground Calendar → CalendarView + 7 time slots (6AM–8PM, 2hr blocks)
  Challenge Board → Post/Accept/Decline/Counter match challenges
  Score Wall      → Post & view match results
  Profile         → Team info + match history from local Room DB

Tech Stack:
  Language    : Kotlin
  Architecture: MVVM + LiveData
  Local DB    : Room (teams, match history, booking cache)
  Remote DB   : Firebase Realtime Database (bookings, challenges, scores)
  UI          : Material Design 3, RecyclerView, CardView, BottomNav
  DI          : Manual (no Hilt/Dagger for simplicity)
  Preferences : DataStore
```

---

## Troubleshooting

| Problem | Fix |
|---|---|
| Build fails with "google-services.json not found" | Add your `google-services.json` to `app/` folder |
| Firebase permission denied | Publish the rules from `firebase_rules.json` |
| Double booking not prevented | Ensure Firebase rules are published correctly |
| App crashes on launch | Check logcat — likely missing google-services.json |

---

## Features Implemented (matching requirements)

- ✅ FR-01 Ground Calendar — Grid of 7 two-hour slots, color-coded FREE/BOOKED
- ✅ FR-02 Slot Booking — Firebase Transaction prevents double-booking (client + server)
- ✅ FR-03 Challenge Board — Post, Accept, Decline, Counter-propose
- ✅ FR-04 Score Wall — Post results, reverse chronological order
- ✅ FR-05 Team Profile — Room DB stores team + match history
- ✅ FR-06 Data Sync — Firebase Realtime DB with instant updates
- ✅ NFR-02 Offline — Calendar + profile readable offline via Room cache
- ✅ NFR-03 Data Integrity — Double-booking prevented both client-side and via Firebase rules
- ✅ NFR-04 Usability — Bold, sporty dark UI (orange + dark background)
- ✅ NFR-05 Compatibility — minSdk 26 (Android 8.0)
