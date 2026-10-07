# Silfy 2.0 — план реалізації редизайну

Дизайн: `doc/design_handoff_silfy_v2/` (README.md, screenshots/, SilfyApp.dc.html).

---

## ▶ Як продовжити (стан на 2026-10-07)

**Готово: кроки 1–8 закомічено й зібрано** (у кроках 7–8 була одна помилка — бракувало `View.` у `SessionFragment`). **Крок 9 «Профіль» закомічено (67bcaeb). Крок 10 «Меню»: підкроки 1–5 закомічено (7611ff3); підкрок 6 «велике прибирання» закомічено окремо (b67123f). Крок 11 закомічено без збірки.** Далі: зібрати (на запит), виправити помилки — потім перевірка на пристрої (беклог) і реліз за `doc/play_store_release_2_0.md`. Гілка `develop`. Нічого не запушено. На пристрої ще не запускали.

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

### Крок 6 «Словник» — початковий план (виконано, підсумок — у «Крок 6 — що зроблено»)
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
6. ✅ Словник (зібрано).
7. ✅ Категорії (зібрано).
8. ✅ Тренування (зібрано). Разом з ним зроблено 6b «Training mode» з меню.
9. ✅ Профіль.
10. ✅ Меню (+ велике прибирання старих ресурсів).
11. ✅ Іконка додатка, системний splash, версія 2.0, чек-ліст для Play Store (закомічено, не зібрано).

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

## Крок 6 — що зроблено

Рішення користувача: `Main` переходить на нову тему вже зараз; сортування — 3 режими, старі комбіновані DAO видалено; англійське слово, яке вже є, — помилка; рівень у формі лише показується + «Reset» (як у дизайні); при збереженні перша літера англійського слова стає великою.

- **`MainActivity`** — тема `Theme.Silfy.V2`, `adjustResize`, edge-to-edge. `SilfyBottomNav` плаває над вмістом (12/14dp + системна панель). Вибрана вкладка береться з поточного екрана (замість `onBackPressed`), на редагуванні слова панель схована. Start → `to_activity_go`, як і раніше.
  - Екрани з `V2_DESTINATIONS` (`fragment_words`, `fragment_words_edit`) самі обробляють інсети. Старим екранам nav host дає відступи: зверху статус-бар, знизу `ds_nav_content_inset` (94dp) + системна панель. Коли екран переводимо на 2.0 — додати його в `V2_DESTINATIONS`.
- **Дані списку** — `WordListDao`: один запит (вкладка `DictionaryTab.ordinal`, пошук, `WordSortMode.ordinal`), `COLLATE NOCASE`. Level = `state DESC` (Unknown перший), далі A–Z. Лічильники вкладок — `getCounts()` (LiveData `WordCounts`). Видалено `LoadAllWordUseCase`, `Word*SortDao`, `Word*SortFactory`, `TabWords`. Схема БД не змінилася.
- **2a/2b** — `WordsFragment` + activity-scoped `WordsViewModel` (`Filter`: tab / search / sort; профіль live для «Hi, {name}»). Рядки — `DictionaryWordAdapter`, `item_dictionary_word.xml`; роздільники — `ListCardDividerDecoration` (знадобиться в кроці 7). Порожній стан: підказка залежить від пошуку / вкладки.
- **2c** — `WordsEditFragment` (`@AndroidEntryPoint`) + fragment-scoped `WordsEditViewModel` (`SavedStateHandle`, аргумент `wordId` у `nav_graph_main`, −1 — нове слово). Стан форми — `WordForm` (валідація там же). Помилка EN показується під час введення, перекладу — теж; «Save word» неактивна, поки форма невалідна.
  - `SaveWordUseCase`: нормалізація (`util/WordText.kt`: `normalizeWordEn` — пробіли + велика перша літера; `normalizeTranslation` — «a ,b, » → «a, b») і перевірка дубля без урахування регістру (`WordDao.findByEnIgnoreCase`) → `WordExistsException` → «This word is already in your dictionary».
  - Валідація: EN `^[a-zA-Z' -]+$`; переклад ігнорує порожні частини між комами.
  - Рівень: шкала з 5 сегментів (`item_level_segment.xml`), «Reset» → Unknown (бали 0). Якщо рівень не змінювали, бали зберігаються як були.
- **2d** — `AddToCategoriesBottomsheet` (child fragment, спільна VM з формою), `item_category_check.xml`: плитка з літерою — колір `avatar_colors` за позицією в списку категорій профілю. Галочка застосовується одразу, «Done» закриває.
- **2e** — `showSilfyDialog(DANGER, destructive = true)`.
- `CategoryOverviewFragment` (старий) відкриває форму через `setWordId(...)`.
- Рядки: секції Dictionary / Word form / Add to categories + `action_done`; «A–Z» / «Z–A» — у `strings_brand.xml`.
- Прибрано: `SelectCategoryBottomsheet`, `FormEnterWordLayout`, `HeaderWord(s|Edit)Layout`, `WordStatusBarLayout`, `CategoryInfoForWordLayout`, `SearchLayout` і їхні layout, `HeaderBindingAdapter`, `FormWordBindingAdapter`, `WordStatusBarBindingAdapter`, `WordsStateBindingAdapter`, `FormWordModel`, `WordsModel`, `WordFormType`, `EditableState`, `menu_bottom_navigation.xml`; з `StateModel`/`StateViewModel` — пошук, стан редагування, «вгору»; `WordState.title`.

Лишилося від старого: `bottomsheet_select_category.xml` + `SelectCategoryAdapter` — `CategoryTrainingBottomsheet` (крок 8/10).

Відхилення від дизайну / на що звернути увагу:
- Шапка, пошук і вкладки не прокручуються разом зі списком — прокручується лише картка (зручніше для пошуку).
- Усі англійські слова — з великої літери («Apple»): форма при збереженні, засів словника (`WordLocalMapper` для `WordDto`), дані 1.x — `Migration1To2.capitalizeWords` (міграцію доповнено, бо версія 2 ще ніде не встановлена; якщо в профілі 1.x є і «apple», і «Apple» — «apple» лишається як є). Assets лишаються в нижньому регістрі, тому `fetchAllCrossRefs` шукає слово без урахування регістру. Сортування й пошук від регістру не залежать.
- Пошук `LIKE` для кирилиці та діакритики чутливий до регістру (обмеження SQLite) — як і раніше.
- Інші вкладки (категорії, профіль, меню) тимчасово на новій темі зі старими layout: інші шрифти / чекбокси.

## Крок 7 — що зроблено

Рішення користувача (усе за рекомендацією): спершу зібрати крок 6 (зібрався без помилок); «Add word» на 3b відкриває нове слово, у якому ця категорія вже вибрана; колір плитки стабільний — за id категорії; рядок слова на 3b — компактний (смужки рівня + ★/⊘, без назви рівня).

- **Дані.** `CategoryDao.getSummaries` — категорії з кількістю слів (`LEFT JOIN` + `COUNT`, без завантаження слів) → `CategorySummary`. `getById` / `getLiveDataById`. Усі списки категорій — `ORDER BY category_id` (порядок створення). `WordListDao.getCategoryWords` — слова категорії A–Z. Схема БД не змінилася.
- **Колір плитки** — `ui/view/avatar/CategoryTile.kt`: `categoryTileColor(context, id)` = `avatar_colors[(id − 1) mod 6]`, `TextView.setCategoryTile(id, title)`. Шторка 2d теж перейшла на нього. Фони `ds_bg_tile` (44, радіус 14) і `ds_bg_tile_large` (56, радіус 18). `AvatarView` не чіпали.
- **3a** — `CategoryFragment` (`@AndroidEntryPoint`) + `CategoriesViewModel` (live). `CategoryGridAdapter`: картки `item_category.xml` + остання пунктирна `item_category_new.xml`; відступи — `GridSpacingItemDecoration`. Шапка й сітка прокручуються разом (NestedScrollView), знизу відступ під плаваючу панель.
- **3b** — `CategoryOverviewFragment` + fragment-scoped `CategoryOverviewViewModel` (аргумент `categoryId` у `nav_graph_main`). Тулбар back / edit / delete, плитка 56 + назва + «{n} words», картка зі списком (`DictionaryWordAdapter(showLevelName = false)`) прокручується всередині й закінчується над FAB «Add word» (`Widget.Silfy.Button.Fab`). Панель навігації на екрані схована (`DESTINATIONS_WITHOUT_NAV`). Якщо категорію видалено — назад до сітки (`closeOnce()`).
- **«Add word»** → `fragment_words_edit` з `wordId = −1` і новим аргументом `categoryId`: `WordsEditViewModel` одразу додає цю категорію до нового слова.
- **3c** — `EditCategoryBottomsheet` (`BaseSilfyBottomSheet`, `@AndroidEntryPoint`, `show(fm, categoryId?)`) + `EditCategoryViewModel` (`CategoryNameForm`). Відкривається з клавіатурою. Перевірка — на «Save»: порожня назва / недопустимі символи (`checkCategoryName`) / «already exists»; помилка зникає під час введення. `SaveCategoryUseCase`: нормалізує пробіли (`normalizeCategoryName`), дубль — без урахування регістру для будь-якої абетки (порівняння в Kotlin, бо `NOCASE` лише ASCII) → `CategoryExistsException`.
- **3d** — `showSilfyDialog(DANGER, destructive = true)` → `DeleteCategoryUseCase` (як і раніше: зв'язки слово–категорія + категорія).
- **Рядки** — секції Categories / Category / Category name / Delete category. `strings_v2.py` навчився `<plurals>` (`PLURAL_QUANTITIES` за CLDR: uk/pl — one/few/many/other, es/it/pt/fr — one/many/other, en/de — one/other): `plural_categories`, `plural_words`. `escape()` тепер екранує `\`.
- **Прибрано:** `CategoryAdapter`, `CategoryOverviewModel`, `EditCategoryModel`, `HeaderCategoryOverviewLayout`, `CategoryInfoLayout` (+ layout), `LoadCategoryWithWordsUseCase`, `CategoryDao.getCategoryWithWords`, `WordAdapter` / `item_word.xml` / `WordDiffUtil`, `SortWordsLayout` / `layout_sort_words.xml`, `SortBindingAdapter`, `WordSort`, `SortLang` / `SortType` / `SortState`, `ic_sort_*`, кольори `sort_*`, сортування в `StateViewModel` / `StateModel`, `VALIDATION_CATEGORY_*` і `checkCategoryIsExist`, `stateViewModel` у `MainActivity`.

Лишилося від старого: `CategoryViewModel` + `LoadAllCategoryUseCase` (категорії зі словами) — для `CategoryTrainingBottomsheet` (крок 8/10). Старі рядки категорій у `values/strings.xml` — до кроку 10.

На що звернути увагу:
- Картка слів на 3b прокручується всередині (як на 2a), а не весь екран, як у дизайні, — щоб не розгортати сотні рядків одразу.
- Тінь FAB — стандартна (elevation 8), без кольору ink: `outlineSpotShadowColor` потребує API 28.

## Крок 8 — що зроблено

Рішення користувача (усе за рекомендацією): режими й напрями — як у дизайні; Start зберігає налаштування, 6b зроблено тим самим екраном; старі значення без міграції (ліміт помилок → найближче з 3/5/10, ∞ → 30 хв); «Level up» лише коли рівень справді виріс; Next — вручну; таймер на паузі під час діалогу 4i і у фоні; «Another session» одразу з тими самими налаштуваннями, «Back to dictionary» → вкладка Words; ★/⊘ під час сесії прибрано; зайві літери (Hard) — з перекладів інших слів.

- **Налаштування** — `SessionSettings` (difficulty, minutes 5/10/30, isMistakeLimit + maxMistakes 3/5/10, isFavouritesOnly, isBlacklistIncluded, categoryIds; порожньо = всі слова). `SessionSettingsUseCase.load/save` читає й пише стару таблицю `settings` + `settings_category_cross` (рядок на профіль, `settings_id = user_id`), без міграції. Видалені категорії відкидаються (relation + перетин у VM).
- **Пул слів** — `WordListDao.getSessionWords` / `countSessionWords` (LiveData для «{n} words match»): один SQL-запит замість завантаження всіх слів з категоріями. Порожній `IN ()` не передаємо — замість нього `-1`.
- **Логіка** — `session/SessionEngine.kt` (чистий Kotlin) + `SessionQuestion` (Choose / Letters / Type):
  - Easy: Choose або Letters, EN → мова; Hard: Type (мова → EN), Choose, Letters; вага слова 5 − рівень; не те саме слово двічі поспіль;
  - питання будується з першого перекладу (до коми); Choose — до 3 хибних варіантів з усього словника, без перекладів самого слова; якщо інших слів нема — Letters;
  - Letters — переклад без пробілів, у нижньому регістрі; Hard + 2 літери з перекладів інших слів;
  - Type — без урахування регістру, зайвих пробілів і ’ / '.
- **Запис відповіді** — `UpdateSessionStatsUseCase` тепер повертає новий `WordState`; бали ±1 тримаються в межах 0..100 (раніше могли йти в мінус / вище 100). `FetchStartSessionUseCase.fetch(settings)` → `FetchSessionResult.Success(session, words, dictionary)` / `Empty` / `Failure`.
- **4a / 6b** — `SessionSetupFragment` + `SessionSetupViewModel` (fragment-scoped). Аргумент `isMenu` (у `nav_graph_main` для `fragment_menu_training_settings` = true): ← + «Training mode» + «Save settings» (ink); інакше ✕ + «New session» + Start (aqua). Картки: `SegmentedControl` ×4, перемикачі (рядок цілком клікабельний), чіпи категорій (`item_category_filter_chip`, `Widget.Silfy.Chip`), «{n} words match» (plurals). Start при 0 словах → 4b.
- **4c–4i** — `SessionFragment` + activity-scoped `SessionViewModel` (Step: Loading / Running / Finished / Empty / Failure; `State` питання; `secondsLeft` окремим LiveData, щоб тік не перемальовував питання). Таймер — корутина, `pause/resume(PauseReason.DIALOG | BACKGROUND)`. Ліміт помилок перевіряється на Next (спершу видно відповідь). Choose: відповідь на першому тапі, Next неактивна до вибору; Letters / Type: Check активна, коли всі літери на місці / щось введено. Back і ✕ → 4i (`showSilfyDialog`, NEUTRAL, `log-out`). Після смерті процесу сесії нема → активність закривається.
- **4j** — `SessionResultsFragment`: причина (Time's up / Mistake limit reached / Session ended), «Nice work, {name}!», `ui/view/ring/AccuracyRingView` (трек білий 12 %, дуга aqua від 12 години), 2 × 2 (`item_session_stat`). «Words used» — показані слова (як у дизайні). Back = «Back to dictionary»: `MainActivity` з `EXTRA_OPEN_WORDS` (`onNewIntent` → вкладка Words).
- **`GoActivity`** — `Theme.Silfy.V2`, edge-to-edge, `adjustResize`; `nav_graph_go`: setup → session (setup знімається зі стеку) → results → session.
- **Ресурси**: стилі `Widget.Silfy.Button.Icon.Outlined`, `.Option`, `Widget.Silfy.Text.CardTitle`, форма `ShapeAppearance.Silfy.Card.Results`; іконки-обгортки `ic_lc_*_15/17/18` (layer-list з розміром, minSdk 24); фони `ds_bg_session_chip*`, `ds_bg_direction_badge`, `ds_bg_letter_tile`, `ds_bg_feedback`, `ds_bg_stat_tile*`, `ds_bg_reason_badge`. Рядки — секції Session setup / Session / End session / Results + plural `plural_words_match`; таймер, бейдж напряму й «79%» — у `strings_brand.xml`.
- **Прибрано**: `GoFragment`, `GoViewModel`, `GoUpdateViewModel`, `GoModel`, `GoStatsModel`, моделі відповідей, `SymbolModel`, `Symbol.kt`, адаптери відповідей, `GoUiState`, `GoMode`, `GoStatsBottomsheet`, `ui/layout/go`, `ui/binding/go`, `UpdateWordMarkUseCase`, `TrainingSettingsMenuFragment` + `TrainingSettingsViewModel` / `TrainingSettingsModel` / `TrainingMenuBindingAdapter` / `layout_training_*`, `CategoryTrainingBottomsheet`, `SelectCategoryAdapter`, `CategoryInWordAdapter`, `CategoryDiffUtil`, `CategoryViewModel`, `LoadAllCategoryUseCase`, `CategoryWithWords`, `CategoryDao.getCategoriesWithWordsByLiveData`, старі use case налаштувань і `TrainingSettings` + мапери, `AppMode`, `ItemCategoryBindingAdapter`, `ViewModelAction.kt`, усі їхні layout.

Лишилося від старого: `GoLangType` + `Word.checkByType` і `util/FetchWordsForStudyUtils.kt` (`fetchWords`) — ними користується Admin (крок 10). `GoStatsType` (id причини в `session_stats`) зі старими рядками-заголовками.

На що звернути увагу:
- Тінь плиток Letters — плоска смужка 2dp (layer-list), як `box-shadow: 0 2px 0` у дизайні.
- Підсумковий заголовок і кільце в дизайні накладаються (скриншот 4j) — у нас між ними відступ 18dp.
- «English → %s» у підказці Easy лишається англійською в усіх мовах (як у дизайні: English → Українська).

## Крок 9 — що зроблено

Рішення користувача (усе за рекомендацією): перемикання профілю перезапускає `MainActivity` з нуля на словнику; «New profile» у 5c відкриває 1d, Back повертає в застосунок; у 5b 5 кольорів (з 6 у палітрі), колір прибирає фото, фото знімає вибір кольору; кнопку «Вийти з профілю» прибрано; старе меню профілю лишається до кроку 10; легенда показує всі 5 рівнів, з нулями теж.

- **Дані.** `WordListDao.getProfileStats` — один запит: total / favourites / blacklist + 5 рівнів (`SUM(state = N)`) → `ProfileStats` (`levels` — Excellent … Unknown). Схема БД не змінилася.
- **`UpdateProfileUseCase`** — `Params(name, avatarColor, photo: PhotoChange)` (`Keep` / `Remove` / `Set(uri)`): нове фото масштабується й копіюється в `files/avatars` (на `Dispatchers.IO`), старий файл видаляється лише після збереження профілю. `UploadProfilePhotoUseCase` влито сюди й видалено.
- **5a** — `ProfileFragment` (`@AndroidEntryPoint`) + fragment-scoped `ProfileViewModel` (профіль і статистика live). Картки: аватар 64 + «Learning since {date}» (`DateFormat.getBestDateTimePattern(uiLocale, "yMMMd")`); мова «English → Українська» (`session_direction` + `englishLanguageName()`, як в 1c) + бейдж + пояснення; Progress — `ui/view/level/LevelDistributionView` (сегменти пропорційні, розрив 2dp, кінці округлені; без слів — уся смужка `level_empty`), легенда (`item_level_legend`, FlexboxLayout), плитки `item_progress_tile` (Excellent / Favourites / Blacklist; новий колір `fav_text_2`); рядки Switch / Delete. Екран у `V2_DESTINATIONS`.
- **5b** — `EditProfileBottomsheet` (`BaseSilfyBottomSheet`) + sheet-scoped `EditProfileViewModel` (`EditProfileForm`): прев'ю аватара йде за іменем / кольором / фото; кружки 34dp з кільцем 2.5dp ink (`ds_swatch*`), камера — `GetContent("image/*")`. Нічого не зберігається до «Save»; «Save» неактивна з порожнім ім'ям. Клавіатура не відкривається сама.
- **5c** — `SwitchProfileBottomsheet` + `SwitchProfileViewModel`: рядки `item_profile_switch` (активний — фон `aqua_row` + бейдж «Active», тап по ньому закриває шторку), пунктирний «New profile» (`ds_bg_dashed_row`). Інший профіль → `ActiveProfileUseCase.set` → `MainActivity` з `NEW_TASK | CLEAR_TASK`. «New profile» → `AuthActivity` з новим `START_CREATE` (старт одразу з 1d).
- **5d** — `showSilfyDialog(DANGER, destructive = true)` → `DeleteProfileUseCase` → `ActiveProfileUseCase.clear()` → `AuthActivity` (`START_PROFILES`, нова задача) → 1c, або 1d, якщо профілів не лишилося. `DeleteProfileUiState`: Idle / Deleting / Deleted / Failure (старе меню профілю підлаштовано).
- **Рядки** — секції Profile / Edit profile / Delete profile; «Excellent 8» — `profile_legend_item` у `strings_brand.xml`.
- **Прибрано:** `ProfileModel`, `EditProfileModel`, `ProfileHeaderLayout`, `ProfileInfoLayout`, `ProfileInfoType`, `ModelsBindingAdapter`, `ProfileBindingAdapter` + `ProfileEditBindingAdapter` (зник дубль `set_avatar`), `ProfileInfoBindingAdapter`, `EditProfilePhotoBottomsheet`, `UploadProfilePhotoUseCase`, layout `layout_header_profile` / `layout_profile_info` / `bottomsheet_edit_profile_photo`, drawable `bg_avatar_placeholder(_add)` / `ic_edit`.

Лишилося від старого: `ProfileMenuFragment` + `MenuProfileViewModel` / `MenuProfileAdapter` (крок 10: «Switch» на картці 6a відкриватиме 5c). Їхній `set_avatar_dark` (жив у видаленому `ProfileEditBindingAdapter`) перенесено в `ProfileMenuBindingAdapter` — прибрати разом зі старим меню. Старі рядки / стилі старого профілю (`alert_title_exit_profile`, `text_sign_up_time`, `View.Header.Name` тощо) — до кроку 10.

На що звернути увагу:
- Вибране фото (`content://`) після смерті процесу може не відкритися — тоді «Save» покаже помилку.
- Порядок профілів у 5c — як у 1c (новіші зверху), активний не переноситься нагору.

## Крок 10 — що зроблено

Рішення користувача (усе за рекомендацією): «Default set» замінює всі категорії, і створені користувачем теж (як у тексті 6d); «Clear» неактивна, коли список порожній; success-плашка висить до наступної дії або виходу з екрана; версія внизу — `BuildConfig.VERSION_NAME`; на ink-картці клікабельна лише «Switch»; модуль `content` видалено повністю; велике прибирання (підкрок 6) — окремим комітом.

- **6a** — `MenuFragment` (`@AndroidEntryPoint`) + `MenuViewModel` (профіль live). Ink-картка: аватар 48, ім'я, «English → мова», «Switch» → шторка 5c. Розділи (`item_menu_section`, 64dp: плитка 40 + назва + підпис + шеврон): Training mode → 6b, Dictionary → 6c, Good to know → 6e–6g. Посилання (`Widget.Silfy.Menu.LinkRow`, іконки `ic_lc_mail_20` / `ic_lc_shield_check_20`): Contact us (`ContactUsUseCase`, тексти з activity-контексту — мовою профілю) і Privacy policy (`url_privacy_policy`). Footer «Silfy {версія} · made in Ukraine».
- **6c** — `DictionarySettingsFragment` + `DictionarySettingsViewModel` (лічильники — live `WordListDao.getCounts`; `run(Action)` → `State` Idle / Working / Done(action) / Failure). Картки (`item_dictionary_counter`), кнопки `Widget.Silfy.Button.Small.Settings` (13sp, іконка 16, до 2 рядків — для довгих перекладів). Плашка — `ic_lc_circle_check_18` на `success_bg`.
- **6d** — 4 діалоги як у дизайні: reset (NEUTRAL, `rotate-ccw`), defaults (WARNING, `list-restart`), clear favourites (WARNING, `eraser`), clear blacklist (NEUTRAL, `eraser`); OK — ink.
- **`ResetDefaultWordsUseCase`** — `categoryDao.clearAll(userId)` замість видалення лише стандартних категорій.
- **6e–6g** — у `nav_graph_main` новий `fragment_menu_good_to_know` (`GoodToKnowFragment`, `onboarding = false`, `languageCode` — мова профілю).
- **`MainActivity`** — меню, 6c і Good to know у `V2_DESTINATIONS`; 6c і Good to know без нижньої панелі.
- **Рядки** — секції Menu / Dictionary settings / Dictionary settings dialogs.
- **Прибрано:** `AdminMenuFragment` + `MenuAdminViewModel`, модуль `content` (папка, `settings.gradle`, залежність), `ProfileMenuFragment` / `MenuProfileViewModel` / `MenuProfileAdapter` / `ProfileMenuDiffUtil`, `InfoMenuFragment` + `ui/fragment/info/*` + `InfoPageAdapter`, старий `DictionaryMenuFragment` / `MenuDictionaryViewModel` / `DictionaryInfoUseCase` / `DictionaryInfo` / `DictionaryUiState` / `DictionaryDao`, `ui/layout/menu/*`, `ui/binding/menu/*`, `models/observable/*` (`StateModel`, `MenuModel`, `AdminStateModel`, `ProfileMenuItemModel`), `StateViewModel`, `MenuButtonType` / `MenuHeaderType` / `DictionaryCardType`, `GoLangType` + `Word.checkByType` / `getValueByType`, `FetchWordsForStudyUtils`, `StatsPointType`, `AnswerType`, `AddSpace*ItemDecorator`, layout старого меню / info / admin.

### Підкрок 6 — велике прибирання

Старих екранів більше нема, тож прибрано все, на що ніхто не посилається:
- **Рядки:** видалено `values/strings.xml` (156 рядків, українська за замовчуванням). `app_name` → `strings_brand.xml`. `GoStatsType` більше не тримає старих заголовків (лише `id`; тексти причин — у `SessionResultsFragment`). Тепер `values/` повністю англійська: `strings_v2.xml` + `strings_brand.xml`.
- **Код:** `ValidationServiceImp` + `ServiceModule`, `ValidationMode`, `checkProfileName`, `ResourceProvider` + `AppModule`, `extension/Context.kt` (`showAlert`), `extension/List.kt` / `RadioButton.kt` / `String.kt`, `util/TimeUtils.kt`, `res/menu/*`; `WordState.image` (старі `ic_word_state_*`).
- **Тема:** застосунок у маніфесті тепер `Theme.Silfy.V2` (раніше `Theme.Silfy`); видалено `themes.xml`, `style_*.xml`, `attrs.xml` (старі styleable), `integers.xml`, `dimens.xml`, шрифт Roboto (+ `font_certs.xml`). У `colors.xml` лишився тільки `transparent`.
- **Ресурси:** скриптом (з урахуванням ланцюжків «ресурс, яким користується лише невикористаний») видалено 88 старих drawable, ~60 кольорів, ~70 dimen, ~90 стилів. Design-system (`ds_*`, `ic_lc_*`, `*.Silfy.*`) не чіпали, навіть невикористане — це токени з кроку 1.
- Збірка впала один раз: `activity_splash.xml` мав `style="@style/Root"` (жив у видаленому `themes.xml`) — замінено на `match_parent`.
- Модуль `base` не чіпали (у ньому є свій `ValidationService` тощо — прибирати окремо, якщо треба).

## Крок 11 — що зроблено

Рішення користувача (усе за рекомендацією): «s» — векторний контур із `unbounded_bold.ttf` власним скриптом; версія `2.0` / `versionCode 11`; тексти для Play Store не пишемо, лише чек-ліст; перемикач 52×28 поки не чіпаємо.

- **Іконка 7a** — `doc/tools/app_icon.py` (без сторонніх бібліотек для вектора; PNG — Pillow): читає гліф «s» з TTF (cmap → loca → glyf, квадратичні криві) і вписує його в рамку з дизайнерського `icon-512.png` («s» — x 145..365, y 170..361; крапка — коло (376.5, 135.5), r 33.5 на полотні 512). Полотно 512 → видимі 72dp у шарі 108dp (відступ 18dp), тож усе в безпечній зоні 66dp.
  - `drawable/ic_launcher_foreground.xml` — передній план, він же `<monochrome>` (тематичні іконки Android 13) і іконка системного splash. Фон — `@color/aqua`.
  - `mipmap-*/ic_launcher.png` / `ic_launcher_round.png` для API 24–25 — з того ж контуру (заокруглений квадрат / коло, відступ 1dp, суперсемплінг ×8).
  - Видалено старі `ic_launcher_foreground.png` і колір `ic_launcher_background` (#61E8F0).
  - Щоб змінити іконку — правити скрипт і запускати `python doc/tools/app_icon.py`.
- **Системний splash (Android 12+)** — `values-v31/ds_theme.xml`: `windowSplashScreenAnimatedIcon` = передній план 7a, `windowSplashScreenIconBackgroundColor` = aqua, фон ink.
- **Версія** — `VERSION_NAME = '2.0'`, `VERSION_CODE = 11`; футер меню — «Silfy 2.0 · made in Ukraine».
- **Play Store** — `doc/play_store_release_2_0.md`: що перевірити перед релізом, які файли куди вантажити, Data safety, «What's new», і після публікації підняти `version_code` у Firebase Remote Config до 11 (тоді 1.x покаже діалог оновлення).
- Видалено `assets/words.json` (старий зразок; словник читається з `assets/words/words_N.json`).
