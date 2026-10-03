# Silfy 2.0 — план реалізації редизайну

Дизайн: `doc/design_handoff_silfy_v2/` (README.md, screenshots/, SilfyApp.dc.html).

---

## ▶ Як продовжити (стан на 2026-10-03)

**Готово: кроки 1–5. Наступний — крок 6 «Словник».** Гілка `develop`, останній коміт `00eb788` (онбординг). Нічого не запушено. Збірка проходить. На пристрої ще не запускали.

### Як працюємо (домовленості з користувачем)
- **Покроково.** Спершу read-only дослідження, потім короткий підсумок + пронумеровані підкроки + рішення, які треба прийняти, і запитання «з чого починаємо?». Редагувати файли — лише після відповіді. Зазвичай відповідь — «роби всі підкроки».
- **Не збирати без запиту.** Після правок описати зміни й запитати, чи збирати. Збирати лише на явне «збери».
- **Комітити лише на прохання** («зроби коміт»), у `develop`, з рядком `Co-Authored-By`. Перед комітом перевірити `git log` / `git status`: користувач буває робить reword комітів в Android Studio, і HEAD міняється.
- Спілкування українською. Звіти — коротко, з посиланнями на файли.
- Після кожного кроку оновлювати цей файл (✅ у списку кроків + розділ «Крок N — що зроблено»).

### Команда збірки
```bash
JAVA_HOME=/c/Users/Jeka/.jdks/jbr-17.0.14 PATH=$JAVA_HOME/bin:$PATH java -cp gradle/wrapper/gradle-wrapper.jar org.gradle.wrapper.GradleWrapperMain :app:assembleDebug --console=plain -q
```
Скрипта `gradlew` немає (він у .gitignore). `java` у PATH — 1.8, тому JDK 17 треба вказувати явно.

### Підводні камені середовища
- **Bash heredoc** з апострофами чи бекслешами іноді ламається. Великі файли й Python-скрипти краще писати через Write.
- **Закінчення рядків.** Робоча копія — CRLF (`core.autocrlf=true`). Python із `newline='\n'` змінює EOL у файлах, де вміст не мінявся. Після масових правок поверніть такі файли через `git checkout` (дивіться `git diff --stat`: файли без змін вмісту не мають потрапляти в коміт).
- **Safe Args** — Java-плагін: необовʼязкові аргументи задаються сеттером (`Directions.toX(required).setOnboarding(true)`), а не named args.
- **Room.** Будь-яка зміна entity — це новий `DATABASE_VERSION`, міграція в `repository/db/migration/`, і після збірки звірка з `app/schemas/.../N.json`. Схему комітимо.
- **Рядки UI** тільки через `doc/tools/strings_v2.py`: додати ключ у `SECTIONS` і значення для всіх 8 мов, потім `python doc/tools/strings_v2.py`. XML руками не правити. Неперекладне — в `values/strings_brand.xml`.
- **Перемикання мови** (`ActiveProfileUseCase.set/clear`) перезапускає activity. Логіку, що має це пережити, тримайте в activity-scoped ViewModel, а навігаційні події обробляйте один раз (`consumeState()`).

### Шаблон нового екрана (як зроблено в кроці 5)
- Fragment наслідує `BaseBindingFragment<Binding>`, layout у `<layout>`, ViewModel — `@HiltViewModel` через `activityViewModels()`, підписка `observe(liveData, ::render)`.
- Edge-to-edge: activity викликає `drawBehindSystemBars()`, фрагмент — `applySystemBarsPadding(top/bottom)` на корені чи нижній панелі, а в `onResume` — `setLightSystemBars(...)`.
- Стилі: тільки `Widget.Silfy.*` / `TextAppearance.Silfy.*` / `ds_*`. Іконки — `ic_lc_*`. Діалоги — `showSilfyDialog`, шторки — `BaseSilfyBottomSheet`.
- Мови: `AppLanguage` (code / badge / nativeName), назви мов мовою UI — `util/LanguageNames.kt`.

### Крок 6 «Словник» — план (ще не погоджений з користувачем)
Що зараз: `MainActivity` (стара тема `Theme.Silfy`, `BottomNavigationView` + кнопка Go) і `nav_graph_main`. У ньому `WordsFragment` / `WordsViewModel` (`LoadAllWordUseCase` + `Word*SortFactory` + 3 DAO з ~25 запитами на кожну комбінацію сортувань), `WordsEditFragment` / `WordsEditViewModel`, `SelectCategoryBottomsheet`, `StateViewModel` (сортування, пошук, стани). Лічильники для вкладок уже є в `DictionaryDao` (`getCountAll`, `getCountFavourites`, `getCountBlacks`).

Підкроки, які варто запропонувати:
1. **`MainActivity` → `Theme.Silfy.V2`:**
   - `SilfyBottomNav` замість `BottomNavigationView` + `button_go`; відступи 12/14dp і інсети;
   - Start веде на налаштування сесії, поки що як і раніше — `to_activity_go`.

   Увага: решта старих фрагментів у Main (категорії, профіль, меню) отримають нову тему, але ще старі layout. Перевірити, що нічого не зламалося.
2. **Список слів (2a/2b):**
   - шапка «Hi, {name}» + «Dictionary» + кнопка «+» (48, ink, aqua-іконка);
   - пошук 48dp і кнопка сортування, що по колу перемикає A–Z → Z–A → Level;
   - `SegmentedControl` (стиль `.Tabs`) All / Favourites / Blacklist з лічильниками;
   - картка-список: EN 16/600 + ★/⊘, переклад 14 `link`, `LevelIndicatorView` + назва рівня 11sp (`level_name_*`);
   - порожній стан «Nothing here yet».

   Сортування спростити до 3 режимів. Можна прибрати старі `SortLang` / `SortType` / `SortState` і більшість запитів у `Word*SortDao` — одна DAO з `ORDER BY` за режимом і фільтр вкладки.
3. **Редагування / нове слово (2c):**
   - поля «Word in English» (валідація `^[a-zA-Z' -]+$`, «Use English letters only») і «Translation» з бейджем мови профілю + підказка «Separate them with commas»;
   - чіпи категорій (`Widget.Silfy.Chip.Entry`) + пунктирний «Add»;
   - рядки-перемикачі Favourites / Blacklist;
   - шкала рівня з «Reset» (лише для наявного слова);
   - кнопка видалення в тулбарі; «Save word» внизу.
4. **«Add to categories» (2d)** — `BaseSilfyBottomSheet` з чекбоксами (плитка з літерою на кольорі з палітри) + «Done». Замінює `bottomsheet_select_category`.
5. **Видалення слова (2e)** — `showSilfyDialog(DANGER, destructive = true)`.
6. **Рядки** всіма 8 мовами, прибирання старих layout / класів словника, замість `WordState.title` (старі рядки) — `level_name_*`.

Рішення, які варто винести на користувача:
- Чи переносити Main на нову тему вже зараз (тимчасово змішаний вигляд інших вкладок)?
- Чи можна спростити сортування до 3 режимів і видалити старі комбіновані DAO?
- «Hi, {name}» — мовою профілю (так).

### Кроки 7–11 — нотатки
- **7 Категорії:**
  - 3a: сітка 2 колонки, картка з плиткою-літерою 44 (колір з `avatar_colors` за індексом) + пунктирна «New category»;
  - 3b: екран категорії з FAB «Add word»;
  - 3c: шторка «Name» з помилками (порожня, недопустимі символи, «already exists»);
  - 3d: діалог «Words stay in your dictionary».

  `AvatarView` варто навчити квадратної форми з радіусом.
- **8 Тренування:**
  - 4a: налаштування сесії — картки з `SegmentedControl`, switch, чіпи категорій, лічильник «{n} words match», кнопка Start (accent);
  - 4b: «No words match»;
  - 4c–4h: режими Choose / Letters / Type;
  - 4i: «End session?» — таймер на паузі, поки відкритий діалог (через `onDismiss`);
  - 4j: результати з кільцем точності (custom View).

  Лишається бальна система рівнів. Hard-режим: вага = 5 − рівень.
- **9 Профіль:**
  - 5a: аватар 64, мова, Progress — стрічка розподілу рівнів (custom View), плитки, Switch / Delete;
  - 5b: шторка редагування — 5 кольорів + фото → `UpdateProfileUseCase.Params(name, avatarColor)`;
  - 5c: шторка перемикання профілів з бейджем «Active»;
  - 5d: видалення → «Who's learning» / створення.
- **10 Меню:**
  - 6a: ink-картка профілю + «Switch», розділи;
  - 6b: Training mode — ті самі картки, що 4a, з кнопкою «Save settings»;
  - 6c: Dictionary — reset / default / clear з success-плашкою;
  - 6d: діалоги;
  - 6e–6g: підключити готовий `GoodToKnowFragment` (`onboarding = false`);
  - прибрати Admin і модуль `content`, контакти, privacy.

  Після цього видалити старий `values/strings.xml`, старі `info`-фрагменти, `StateViewModel`-залишки і старі `ic_*` / `bg_*` / стилі. `values/` стане повністю англійським.
- **11:** adaptive-іконка 7a (aqua + «s» з крапкою, гліф у безпечній зоні 66%), іконка системного splash, Play Store (готові файли в `doc/design_handoff_silfy_v2/play-store/`), підняти версію до 2.0.

### Беклог / відомі питання
- **Ніде не перевірено на пристрої:** міграція з 1.1.10 (поставити 1.1.10 → дані → нова збірка поверх), онбординг, перемикання мови. Тесту міграції (room-testing) немає.
- 135 сумнівних українських перекладів у `doc/silfy_v2_dictionary_review.md` — чекають рішення користувача.
- `app/src/main/assets/words.json` — старий зразок, не використовується, можна видалити.
- Switch 52×28 замість 48×28 (див. крок 1).
- Старі екрани на Android 15+ малюються під системними панелями — зникне, коли їх переведемо на нову тему.
- Модуль `content` (адмін-генератор JSON) пише старий формат з `"ua"` — прибрати разом з Admin (крок 10).

## Прийняті рішення (2026-10-03)

- **Рівні слів**: лишаємо поточну бальну систему (`WordState.minCount` 0–100: пороги 30/60/80/100).
  На 5 рівнів дизайну мапимо: Unknown=0, Poor=1, Average=2, Good=3, Excellent=4 (кількість заповнених барів).
  Логіку «+1/−1» з README **не** впроваджуємо.
- **Мова UI = мова профілю.** Підтримуються всі 7 мов: uk, pl, es, de, it, pt, fr.
  Профіль з укр → увесь UI українською; назви категорій теж мовою профілю.
  (До створення профілю — системна мова, якщо вона серед 7, інакше англійська.)
- **Переклади словника** на pl/es/de/it/pt/fr генерує Claude (≈2700 слів), далі вибіркова перевірка.
- **У базі один переклад на слово** — мовою профілю (слова й так копіюються окремо для кожного профілю). Мапа мов є лише в assets.
- **Колонку `ua` перейменовано на `translation`** через перестворення таблиці (міграція 1→2).
- **Дані користувачів 1.x мігруємо**, а не стираємо.
- **Мова UI** — через `AppCompatDelegate.setApplicationLocales` (appcompat 1.6.1).
- **Португальська — європейська** (AO90): `autocarro`, `comboio`, `telemóvel`.
- **Після створення профілю показуємо «Good to know»** — це знайомство з додатком; звідти на словник. Сторінки «Good to know» перероблено в кроці 5, у кроці 10 меню лише підключить їх.
- **Мова за замовчуванням на екрані створення профілю** — системна, якщо вона серед 7, інакше українська.
- **Splash:** системний splash (Android 12+) з тим самим ink-фоном плавно переходить у наш 1a.
- **Рядки UI:** старий `values/strings.xml` (українська за замовчуванням) лишається, доки старі екрани не замінено. Нові рядки йдуть у `strings_v2.xml`: англійська за замовчуванням + `values-uk/pl/es/de/it/pt/fr`. **Кожен новий рядок додається одразу в усі 8 файлів.** Джерело — `doc/tools/strings_v2.py` (редагувати скрипт і запускати, XML не правити руками). Неперекладні рядки (логотип, URL, приклади слів) — `values/strings_brand.xml`.

## Кроки

1. ✅ Design system: кольори, шрифти, типографіка, радіуси/відступи, стилі компонентів, іконки, тема.
2. ✅ Базові компоненти: bottom nav, segmented control, індикатор рівня, бейдж мови, діалог, bottom sheet.
3. ✅ Шар даних: новий `Profile` (name, language, avatarColor, photo), `ua` → `translation`, міграція Room 1→2, assets з мапою мов, мова застосунку з профілю.
4. ✅ Контент: переклади словника на 6 мов, категорії, рядки `strings_v2` для 7 мов.
5. ✅ Онбординг: Splash, Welcome, Who's learning, Create profile + Good to know (6e–6g перенесено сюди).
6. Словник.
7. Категорії.
8. Тренування.
9. Профіль.
10. Меню.
11. Іконка додатка і матеріали для Play Store.

## Крок 1 — що зроблено

Усе додано окремими файлами. Старі ресурси не чіпали, тож поточні екрани виглядають як раніше.

| Файл | Що всередині |
|---|---|
| `res/values/ds_colors.xml` | токени кольорів з README (ink, aqua, surface, success/error/fav, рівні, аватари, `avatar_colors` array) |
| `res/values/ds_dimens.xml` | радіуси, відступи, розміри (`ds_*`) |
| `res/values/ds_type.xml` | `TextAppearance.Silfy.*` + `Widget.Silfy.Text.*` (з line-height) |
| `res/values/ds_components.xml` | `Widget.Silfy.Button.*`, `TextInputLayout`, `Chip`, `Switch`, `CheckBox`, `RadioButton`, `Card`, `Badge`, `Divider`, `ShapeAppearance.Silfy.*` |
| `res/values/ds_theme.xml` | `Theme.Silfy.V2`, `Theme.Silfy.V2.Ink` (splash/welcome) |
| `res/font/unbounded_*.ttf`, `onest_*.ttf` + `unbounded.xml`, `onest.xml` | шрифти в APK (статичні ваги з Google Fonts, SIL OFL 1.1), сімейства для `fontWeight`; кирилиця й діакритика для всіх 7 мов перевірені |
| `res/color/ds_*.xml` | state lists: кнопки, інпут, чіп, switch, nav, ripple |
| `res/drawable/ds_*.xml` | трек і повзунок switch, пунктирні фони, бейдж, роздільник |
| `res/drawable/ic_lc_*.xml` | 61 іконка Lucide; колір через `android:tint` (за замовчуванням ink), перефарбовуються через `app:tint` / `iconTint` |

Відхилення від дизайну:
- Switch має 52×28 замість 48×28 (обмеження вимірювання `SwitchCompat`: ширина = 2 × ширина повзунка). Якщо треба піксель у піксель, у кроці 2 зробимо власну View.

Скрипт конвертації SVG → VectorDrawable лежав у scratchpad сесії. Якщо знадобиться ще раз: circle/rect/line/polyline/polygon → pathData, `strokeColor` білий + `android:tint`.

## Крок 2 — що зроблено

Компоненти готові, але ще ніде не підключені. Підключаємо разом з екранами.

| Компонент | Де | Використання |
|---|---|---|
| Індикатор рівня | `ui/view/level/LevelIndicatorView.kt`, `WordLevel.kt` | `app:indicatorLevel` / `setWordState(state)`; `WordState.level` (0..4) і `levelColor` — мапінг бальної системи на 5 рівнів |
| Segmented control | `ui/view/segmented/SegmentedControl.kt` | style `Widget.Silfy.Segmented` (картки) або `.Tabs` (словник); `app:segmentedEntries`, `selectedIndex`, `setCounts()`, `setOnOptionSelectedListener` |
| Bottom nav | `ui/view/nav/SilfyBottomNav.kt`, `item_bottom_nav*.xml` | `selectedTab`, `setOnTabClickListener(Tab)`, `setOnStartClickListener`; відступи 12/14dp задає екран |
| Бейджі | стилі `Widget.Silfy.Badge`, `.Small`, `.OnInk`, `.Active` | TextView зі стилем |
| Діалог | `ui/dialog/SilfyDialog.kt`, `dialog_silfy.xml` | `showSilfyDialog(icon, DialogTone.DANGER, title, message, okText, cancelText, destructive, onOk, onCancel, onDismiss)`; `cancelText = null` → одна кнопка; повертає `Dialog` (для паузи таймера) |
| Bottom sheet | `ui/dialog/BaseSilfyBottomSheet.kt` | наслідувати замість `BaseBindingBottomSheetDialogFragment`; корінь — style `Widget.Silfy.Sheet`, далі View `Widget.Silfy.Sheet.Handle` і заголовок `Widget.Silfy.Text.SheetTitle` |
| Чекбокс / радіо | `ds_checkbox`, `ds_radio` у стилях `Widget.Silfy.CheckBox` / `RadioButton` | як у дизайні: 24dp ink з aqua-галочкою / кільце 22dp з точкою |

Нюанси:
- Діалог і bottom sheet — це ThemeOverlay поверх `Theme.Silfy.V2`. Шрифти й стилі кнопок вони беруть з теми activity, тож на старій темі виглядатимуть неповно.
- Нові рядки поки лише англійською в `values/strings_v2.xml`. Переклади додамо в кроці 4.

## Крок 3 — що зроблено

- `models/enums/AppLanguage.kt` — 7 мов: `code` (uk…), `badge` (UA…), `nativeName`, `englishName`.
- `Profile`: `name`, `language: AppLanguage` (у БД — код через `AppLanguageConverter`), `avatarColor` (індекс у `R.array.avatar_colors`), `photo`, `createTime`. Email, прізвище та `ExistProfileUseCase` прибрано. `CreateProfileUseCase.Params(name, language)`.
- `ActiveProfileUseCase.set/clear` — єдине місце, де змінюється активний профіль: зберігає id і перемикає мову (`util/AppLocale`). На API < 33 мову зберігає `AppLocalesMetadataHolderService` (`autoStoreLocales`). Splash один раз виставляє мову для профілів, перенесених з 1.x.
- Слово: `WordLocal.translation` / `Word.translation`, `GoLangType.TRANSLATION`, `SortLang.TRANSLATION_*`, SQL у `Word*SortDao`.
- Валідація: переклад — будь-які літери (`\p{L}`) + апострофи; категорія — літери, цифри, `_-/\|`; імʼя профілю — непорожнє.
- Assets: `categories.json` — `{key, en, uk}`, слова — `{en, uk, categories: [key]}`. Засів (`SaveDefaultDataUseCase`, `ResetDefaultWordsUseCase`) бере мову профілю, назви категорій — `title(language)` з fallback на `en`. Слова без перекладу мовою профілю пропускаються.
- Room: версія 2, `exportSchema = true` (схеми в `app/schemas/`), `Migration1To2`:
  - profile перестворюється; імʼя = `first_name`, а якщо порожнє — частина email до `@`; мова `uk`;
  - word перестворюється з колонкою `translation`;
  - категорії «Спорт/Sport» → «Спорт» (якщо в користувача ще нема такої назви).

Тимчасово, до кроку 5: старий екран входу створює профіль лише за імʼям, мова — `uk`. Старі екрани профілю й меню показують імʼя та мову замість email.

`app/src/main/assets/words.json` (старий зразок з `"ua"`) у коді не використовується.

## Крок 4 — що зроблено

- Словник: 2731 слово × pl/es/de/it/pt/fr у `assets/words/*.json` (порядок полів: en, uk, pl, es, de, it, pt, fr, categories). Категорії в `categories.json` — 7 мов + en.
- Правила перекладу та вибірка для перевірки — `doc/silfy_v2_dictionary_review.md`. Там же список із 141 сумнівного українського перекладу. Шість критичних виправлено (`egg`, `noon`, `wool`, `angle`, `proud`, `coast`), решту — ні.
- Валідація перекладу тепер пропускає дефіс (`будь-який`, `guarda-chuva`).
- `strings_v2.xml` перекладено на 7 мов.

## Крок 5 — що зроблено

- Маршрут старту (`SplashViewModel` → `StartRoute`):
  - є активний профіль → `MainActivity`;
  - перший запуск без профілів → Welcome;
  - інакше → «Who's learning» (без профілів одразу відкриває створення).

  Прапорець «Welcome показано» лежить у DataStore.
- `AuthActivity` (тема `Theme.Silfy.V2`) — граф онбордингу: перший екран задається через `EXTRA_START`. Вихід і видалення профілю з меню відкривають його без extra, тобто «Who's learning».
- Екрани:
  - `SplashFragment` (1a);
  - `WelcomeFragment` (1b);
  - `ProfilesFragment` (1c);
  - `CreateProfileFragment` (1d);
  - `GoodToKnowFragment` (6e–6g, аргументи `languageCode` та `onboarding`).

  Логіка — в `OnboardingViewModel`, вона спільна для activity і переживає її перезапуск при зміні мови.
- Створення профілю: створити → зробити активним (мова застосунку перемикається, activity перезапускається) → засів словника → «Good to know» → словник.
- Edge-to-edge: `ui/view/SystemBars.kt` (`drawBehindSystemBars`, `applySystemBarsPadding`, `setLightSystemBars`). Нижня панель піднімається над клавіатурою.
- `AvatarView` — літера на кольорі профілю або фото. `util/LanguageNames.kt` — назви мов мовою інтерфейсу через `Locale`.
- Видалено: `AuthFragment`, `AuthInfoFragment`, `AuthViewModel`, `AuthModel`, `AuthUiState`, `MoreProfileBottomsheet` і їхні layout, `MoreProfileAdapter`, `ProfileDiffUtil`.

Тимчасово:
- Після онбордингу відкривається ще старий головний екран (кроки 6–10).
- Іконка системного splash — поточна іконка додатка (крок 11).
