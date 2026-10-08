# Data safety — Silfy 2.0 (чернетка)

Відповіді для Play Console → App content → Data safety. Назви полів — англійською, як у консолі.
Звірено з кодом 2.0 і з документацією Google станом на 2026-10-08 (джерела внизу).

Що реально йде з пристрою: тільки Firebase — Analytics (автоматичні події), Crashlytics, Remote Config
(перевірка оновлення). Профілі, фото, слова, статистика — лише локально, тому їх **не** декларуємо:
дані, що обробляються тільки на пристрої, Play не вважає «collected».

## 1. Data collection and security

| Питання | Відповідь | Чому |
| --- | --- | --- |
| Does your app collect or share any of the required user data types? | **Yes** | Firebase Analytics / Crashlytics / Remote Config |
| Is all of the user data collected by your app encrypted in transit? | **Yes** | Firebase шле все по HTTPS/TLS |
| Which methods of account creation does your app support? | **My app does not allow users to create an account** | Локальні профілі — не акаунти, на сервер нічого не йде |
| Do you provide a way for users to request that their data is deleted? | **Yes** | Полісі обіцяє видалення Firebase-даних по email (див. «Відкриті питання») |

## 2. Data types

Для кожного типу нижче однакові відповіді у під-формі:

- **Collected or shared?** → Collected (не Shared: Firebase — service provider, що обробляє дані від нашого імені, Play це sharing не вважає)
- **Processed ephemerally?** → No
- **Required or optional?** → Required (користувач не може вимкнути збір)

| Категорія → тип | Purpose | Звідки |
| --- | --- | --- |
| Location → **Approximate location** | Analytics | GA визначає грубу геолокацію з замаскованої IP; Remote Config бере код країни |
| App activity → **App interactions** | Analytics | GA: screen views, sessions, app opens |
| App info and performance → **Crash logs** | Analytics | Crashlytics: stack traces при падінні |
| App info and performance → **Diagnostics** | Analytics | Crashlytics: стан застосунку й метадані пристрою при падінні; Firebase user agent |
| Device or other IDs → **Device or other IDs** | Analytics, App functionality | GA app-instance ID, Firebase installation ID, Crashlytics installation UUID; FID потрібен Remote Config для перевірки оновлення |

**Не декларуємо:**

- Personal info → Name: ім'я профілю лишається на пристрої.
- Photos and videos: фото профілю копіюється в приватну пам'ять застосунку, нікуди не відправляється.
- App activity → In-app search history / Other user-generated content: слова й категорії — тільки локально.
- Financial info / Purchase history: покупок у застосунку немає.
- Advertising ID: дозвіл `AD_ID` вирізано в маніфесті (`tools:node="remove"`), реклами немає.

## 3. Відкриті питання перед відправкою

- [ ] **Advertising ID — підстрахуватися.** `AD_ID` прибраний, тож на Android 13+ ID віддається нулями. Для гарантії на всіх версіях можна додати в маніфест `<meta-data android:name="google_analytics_adid_collection_enabled" android:value="false" />`. Окремий крок у коді — вирішити, чи робимо.
- [ ] **Видалення даних.** Відповідь «Yes» означає, що на запит треба реально видалити дані: Analytics — через User Deletion API за app-instance ID, який користувач сам не знає. Альтернатива — відповісти «No» (для застосунку без акаунтів це допустимо) і прибрати обіцянку з полісі.
- [ ] **Firebase BoM 30.3.1 (2022).** Документація Google описує поточні версії SDK. Відповіді вище покривають і старі, і нові (нові додають Firebase sessions SDK — це теж Diagnostics / Device IDs), але при оновленні BoM варто переглянути.
- [ ] **Data retention в Firebase Analytics** ≤ 14 місяців — полісі це обіцяє.
- [ ] **Privacy policy URL** у Play Console: `https://sites.google.com/view/silfy-policy/policy` (той самий, що в `strings_brand.xml`).
- [ ] **Target audience** у Play Console — 13+, як у полісі.

## Джерела

- [Firebase: Prepare for Google Play's data disclosure requirements](https://firebase.google.com/docs/android/play-data-disclosure) — Crashlytics, Installations, Remote Config
- [Google Analytics: Prepare for Google Play's data disclosure requirements](https://support.google.com/analytics/answer/11582702) — app-instance ID, masked IP → coarse location, lifecycle events
