# Handoff: Silfy 2.0 — редизайн

## Overview
Новий дизайн Android-додатку Silfy (`ua.notky.silfy`) для вивчення англійських слів. Що змінюється:
- **кілька мов перекладу**: UA, PL, ES, DE, IT, PT, FR (раніше було лише EN↔UA);
- **профіль створюється лише з імʼям**: без email і пароля. Мову перекладу обирають при створенні, змінити її потім не можна;
- **кілька локальних профілів на одному пристрої**, у кожного своя мова й прогрес;
- новий візуальний стиль, набір іконок і матеріали для Play Market.

## About the Design Files
Файли в цьому пакеті — **дизайн-референси в HTML**. Це прототипи, які показують вигляд і поведінку, а не production-код. Завдання — **відтворити їх у наявному Android-проєкті** (Kotlin, XML layouts + DataBinding, Navigation Component, Material Components), використовуючи вже прийняті в ньому патерни. Переносити HTML напряму не треба.

Відкрийте `Silfy Screens.dc.html` у браузері: там усі екрани, іконки й матеріали для Play Market. `SilfyApp.dc.html` — живий інтерактивний прототип.

## Fidelity
**High-fidelity.** Кольори, типографіка, відступи, радіуси й стани фінальні. 1 CSS px у прототипі = 1 dp (кадр 360×780 dp).

---

## Design Tokens

### Colors
| Token | Hex | Використання |
|---|---|---|
| ink / primary | `#100C4E` | основний текст, головні кнопки, bottom nav, темні картки |
| ink-hover | `#231E73` | pressed для primary |
| aqua / accent | `#72E4EC` | FAB «Start», акценти на темному, іконка додатка |
| aqua-tint | `#DDF8FA` | обрані чіпи, фон іконок |
| aqua-row | `#F0FBFC` | виділений рядок у списку |
| bg | `#F3F5FA` | фон екранів |
| surface | `#FFFFFF` | картки, інпути |
| border | `#E6E9F1` | обводка карток (1dp) |
| divider | `#EEF0F6` | розділювачі в картках-списках |
| input-border | `#DDE1EC` | обводка інпутів (1.5dp), focus → `#100C4E` |
| dashed | `#C3C8DA` | пунктир для «New …» (1.5dp dashed) |
| seg-bg | `#E8EBF4` / `#EEF0F7` | фон segmented, нейтральні бейджі й кнопки |
| text-2 | `#5A5E78` | вторинний текст |
| text-3 | `#8A8FA8` | підказки, лічильники |
| placeholder | `#9AA0B8` | placeholder, шеврони |
| disabled btn | `#B9BCCB` | неактивна головна кнопка |
| link / translation | `#2B63E8` | переклад у списку слів, посилання |
| on-dark-2 | `#B9B7E4`, `#9E9BD6`, `#8E8BC4` | вторинний текст на ink |
| success | bg `#E3F6EA`, border `#2FA35A`, text `#146236` / `#1C7A43` | правильна відповідь |
| error | bg `#FDECEC`, border/icon `#E5484D`, text `#94242A` / `#B42F33`, destructive `#D23C41` | помилка, видалення |
| fav | bg `#FFF4D6`, icon `#E0A21E` / `#F2B632`, text `#7A5300` | обрані |
| switch off | `#D5D9E4` | трек вимкненого перемикача |

**Рівні знань** (заміна `state_*`): Unknown `#C9CEDB` · Poor `#A8DFA9` · Average `#6CCB6A` · Good `#3FAE4E` · Excellent `#1F8A3B`. Порожній сегмент — `#E1E5EE`.

**Кольори аватарів** (за порядком): `#BFF1F4`, `#FDE3A7`, `#DCD3FF`, `#CBEFD6`, `#FFD3C9`, `#D5E6FF`. На аватарі — перша літера імені.

### Typography
- **Unbounded** (Google Fonts, 500/600/700): заголовки та цифри.
- **Onest** (Google Fonts, 400/500/600/700): весь інший текст. Обидва шрифти підтримують кирилицю й латиницю з діакритикою.

| Роль | Шрифт | Розмір / weight |
|---|---|---|
| Заголовок екрана | Unbounded | 26sp/700, letter-spacing −0.4, line-height 1.0–1.15 |
| Заголовок у тулбарі | Unbounded | 18sp/700 |
| Заголовок bottom sheet / діалогу | Unbounded | 20sp/700 · 19sp/700 |
| Слово в тренуванні | Unbounded | 38sp/700 |
| Великі цифри статистики | Unbounded | 24sp/700 (20sp у профілі) |
| Body | Onest | 15sp/400, line-height 1.5 |
| Назва рядка | Onest | 15–16sp/600 |
| Label над полем | Onest | 13sp/600, `#5A5E78` |
| Caption | Onest | 12–13sp/400–500 |
| Кнопка | Onest | 16sp/600 (Start — 700) |
| Бейдж мови | Onest | 10–11sp/700 |

### Radii
Картки 20–24 · головна кнопка 18 · інпути 16 · кнопки-іконки 14–16 · segmented: контейнер 14–15, опція 10–11 · чіпи 12 · бейдж 7 · bottom sheet зверху 28 · діалог 28 · bottom nav 24 · аватари — коло.

### Spacing
Горизонтальні поля екрана — 16dp (форми 20dp). Проміжок між картками — 10–12dp. Внутрішній padding картки — 16dp. Висота рядка в списку: 54–64dp. Головна кнопка 56dp, закріплена внизу з padding `12 / 20 / 26`.

### Shadows
- Bottom nav: `0 10 30 rgba(16,12,78,.28)`.
- FAB «Add word»: `0 8 24 rgba(16,12,78,.28)`.
- Діалог: `0 20 50 rgba(10,8,40,.25)`.
- Активна опція segmented: `0 1 3 rgba(16,12,78,.12)`.
- Scrim: `rgba(10,8,40,.45)`.

---

## Global components
- **Bottom navigation** (екрани Words, Categories, Profile, Menu): плаваюча, `left/right 12dp`, `bottom 14dp`, висота 68, радіус 24, фон ink. П'ять пунктів: Words (`book-open`), Categories (`layout-grid`), **Start** (центральна кнопка 56×56, радіус 19, фон aqua, іконка `play` 24, без підпису), Profile (`user-round`), Menu (`menu`). Пункт 60×52: активний має фон `rgba(114,228,236,.14)`, колір aqua; неактивний — `#8E8BC4`. Іконка 22, підпис 10sp/600. Замінює `menu_bottom_navigation.xml`.
- **Картка-список**: біла, border 1dp `#E6E9F1`, радіус 22. Рядки розділені лінією 1dp `#EEF0F6`.
- **Segmented control**: фон `#EEF0F7`, padding 4, gap 4. Опція висотою 38; активна — біла з тінню, текст ink, неактивна — `#5A5E78`.
- **Switch**: трек 48×28, коло 22; увімкнений — ink, вимкнений — `#D5D9E4`.
- **Індикатор рівня**: 4 вертикальні бари 5dp завширшки, висоти 8/11/14/17, радіус 3. Кількість заповнених = рівень (0–4), колір — колір рівня.
- **Бейдж мови**: `UA/PL/ES/DE/IT/PT/FR`, 11sp/700, фон `#EEF0F7`, радіус 7.
- **Діалог** (замінює AlertDialog): відступи 20 з боків, по центру. Іконка в плитці 48×48 (радіус 16) → заголовок → текст → дві кнопки 50dp (Cancel `#EEF0F7` / OK ink або `#E5484D` для видалення).
- **Bottom sheet**: радіус 28 зверху, handle 40×4 `#DDE1EC`, padding `10 20 26`.

---

## Screens
Нумерація відповідає бейджам у `Silfy Screens.dc.html`.

### 1. Онбординг і профілі (`activity_splash`, `activity_auth`, `fragment_auth*`)
- **1a Splash**: фон ink. По центру — логотип 112×112 (радіус 34, aqua, «s» Unbounded 72) і «silfy» (Unbounded 34 білим). Внизу підпис «English words in your language».
- **1b Welcome**: показується лише при першому запуску. Верх — ink із «бульбашками перекладу»: apple → яблуко / jabłko / manzana / Apfel. Знизу — біла панель (радіус 32 зверху): заголовок «English, in the language you speak», підзаголовок, кнопка «Get started».
- **1c Who's learning?**: список локальних профілів. Картка профілю: аватар 48, імʼя, «English › {рідна назва мови}», бейдж мови. Остання — пунктирна картка «New profile». Якщо профілів немає, одразу відкривається 1d.
- **1d Create profile**: поле «Your name» і список 7 мов (radio, рядок 54dp: бейдж, рідна назва, англійська назва сірим). Під списком — інфо-блок: «The language can't be changed later…». Кнопка «Create profile» активна лише з непорожнім імʼям. Нижче — «By continuing you accept the Privacy Policy». **Email і прізвище прибрані** (`hint_email`, `hint_lastname`, усі `alert_error_auth_*` про пошту більше не потрібні).

### 2. Словник (`fragment_words`, `layout_header_words`, `item_word`, `layout_search`, `layout_sort_words`)
- **2a/2b Dictionary**: вгорі «Hi, {name}» + заголовок «Dictionary», праворуч кнопка 48×48 ink з `plus` (додати слово). Далі пошук (48dp, `search`) і кнопка сортування, яка по колу перемикає A–Z → Z–A → Level. Під ними segmented-вкладки All / Favourites / Blacklist з лічильниками. Список: слово EN 16/600 (поруч ★ або ⊘, якщо слово в списку), переклад мовою профілю 14/400 `#2B63E8`, праворуч індикатор рівня й назва рівня 11sp. Порожній стан: «Nothing here yet» і підказка.
- **2c Edit / New word** (`fragment_words_edit`, `layout_form_enter_word`): поля «Word in English» (валідація `^[a-zA-Z' -]+$`, текст помилки «Use English letters only») і «Translation» з бейджем мови профілю, підказка «Separate them with commas». Далі категорії (чіпи з ✕ + пунктирний «Add»), перемикачі Favourites і Blacklist («Skip this word in training»). Для наявного слова — також шкала рівня з кнопкою «Reset» і кнопка видалення в тулбарі. Внизу — «Save word».
- **2d Add to categories** — bottom sheet із чекбоксами (замінює `bottomsheet_select_category`).
- **2e Delete word** — діалог.

### 3. Категорії (`fragment_category`, `item_category`, `fragment_category_overview`, `bottomsheet_edit_category`)
- **3a**: вгорі «{n} categories» і заголовок, кнопка `folder-plus`. Сітка 2 колонки, gap 10. Картка: плитка 44×44 з першою літерою (колір з палітри аватарів), назва, «{n} words». Остання — пунктирна «New category».
- **3b Category**: тулбар із кнопками back / edit / delete. Шапка з плиткою 56 і назвою, далі список слів. Плаваюча кнопка «Add word» внизу праворуч.
- **3c New/Edit category** — bottom sheet з полем «Name». Помилки: порожня назва; недопустимі символи (дозволені літери, цифри, `_-/\|`); «A category with this name already exists».
- **3d Delete category** — діалог: «Words stay in your dictionary».

### 4. Тренування (`fragment_menu_training_settings`, `activity_go`, `fragment_go`, `layout_go_answer_*`, `bottomsheet_go_stats`)
- **4a Session setup** (натискання Start у nav): тулбар ✕ + «New session». Картки налаштувань:
  - Difficulty: Easy / Hard, під ним пояснення;
  - Duration: 5 / 10 / 30 min;
  - Limit mistakes: switch + 3 / 5 / 10;
  - Words: All words / Favourites + switch «Include blacklisted words»;
  - Categories: чіпи з множинним вибором. Якщо нічого не обрано, використовуються всі слова.

  Внизу лічильник «{n} words match these settings» і кнопка **Start** (aqua, ink-текст, іконка `play`).
- **4b** Немає слів за критеріями → діалог «No words match».
- **4c–4h Session**: зверху кнопка ✕, таймер (ink-чіп, `timer`, `mm:ss`) і лічильник помилок (`heart-crack`, «2/5»). Далі ink-картка з «Word N», бейджем напряму (`EN → UA` / `UA → EN`), самим словом (Unbounded 38) і підказкою режиму. Режими:
  - **Choose**: сітка 2×2 з 4 варіантів, висота 76, радіус 20. Після вибору правильний варіант підсвічується success-кольором, обраний неправильний — error, решта сіріють;
  - **Letters**: слоти 40×50 під літери, плитки 52×56 (біла, тінь `0 2 0 #DDE1EC`), кнопка стирання (`delete`). На рівні Hard додаються зайві літери;
  - **Type**: інпут 64dp, 22sp, по центру. Після перевірки — рамка success/error і рядок «Correct answer: …».

  Після відповіді зʼявляється плашка з результатом («Correct! Level up for “apple”» / «Not quite — it's “…”»), а кнопка змінюється Check → Next.
- **4i End session?** — діалог (✕ або системне «назад»).
- **4j Results**: ink-картка з причиною завершення («Time's up» / «Mistake limit reached» / «Session ended»), «Nice work, {name}!» і кільцем точності 164dp (conic, aqua). Під нею сітка 2×2: Words selected, Words used, Correct (success), Wrong (error). Кнопки «Another session» (aqua) і «Back to dictionary» (ghost).

### 5. Профіль (`fragment_profile`, `layout_header_profile`, `bottomsheet_edit_profile*`, `bottomsheet_more_profiles`)
- **5a**: заголовок «Profile» + кнопка редагування. Картки:
  - аватар 64, імʼя, «Learning since {date}»;
  - мова перекладу: «English → {мова}», бейдж, пояснення, що мову змінити не можна;
  - Progress: стрічка розподілу слів за рівнями (12dp), легенда, 3 плитки: Excellent / Favourites / Blacklist;
  - Switch profile / Delete profile (червоним).
- **5b Edit profile** — bottom sheet: аватар, 5 кольорів + кнопка фото (`camera`), поле імені.
- **5c Switch profile** — bottom sheet зі списком профілів; активний має бейдж «Active», в кінці «New profile».
- **5d Delete profile** — діалог. Після видалення відкривається 1c, або 1d, якщо профілів не лишилося.

### 6. Меню (`fragment_menu`, `layout_menu_*`, `fragment_menu_dictionary`, `fragment_menu_info`, `fragment_info_*`)
- **6a**: ink-картка поточного профілю з кнопкою «Switch». Список розділів: Training mode (`target`), Dictionary (`library`), Good to know (`lightbulb`) — кожен з підписом і шевроном. Окрема картка: Contact us (`mail`), Privacy policy (`shield-check`). Внизу «Silfy 2.0 · made in Ukraine». Пункт Admin прибраний з UI.
- **6b Training mode**: ті самі картки, що в 4a, але тулбар «←», а кнопка внизу — «Save settings».
- **6c Dictionary**: картка All words з кнопками «Reset progress» і «Default set»; картки Favourites і Blacklist з кнопкою «Clear». Після дії — success-плашка (наприклад, «Progress reset»).
- **6d** — діалоги підтвердження (reset / defaults / clearFav / clearBlack).
- **6e–6g Good to know**: 3 сторінки (ViewPager2) — Levels, Difficulty (Easy / Hard), Lists (Favourites / Blacklist). Навігація: кнопки ‹ › (52×52) і точки, активна — 22×8.

---

## Interactions & Behavior
- Переходи: зберегти наявні анімації з `nav_graph_*`.
- Рівень слова: правильна відповідь +1, неправильна −1 (у межах 0–4).
- Hard-режим: слова з нижчим рівнем трапляються частіше (вага = 5 − рівень).
- Сесія завершується, коли вийшов час або досягнуто ліміт помилок.
- Таймер ставиться на паузу, поки відкритий діалог.
- Кнопки: pressed-стан — ink → `#231E73`; ghost-кнопки та рядки — фон `#E7EAF3` / `#F7F8FC`.
- Кнопка неактивна, доки форма невалідна (`#B9BCCB`).

## State / Data changes
- `Profile { id, name, lang: uk|pl|es|de|it|pt|fr, avatarColor|photo, createdAt }` — зберігається локально, без email та авторизації (Firebase Auth більше не потрібен для входу).
- `Word { en, translations: Map<lang, String>, categories, level 0..4, favourite, blacklist }`. Прогрес, списки й категорії — окремо для кожного профілю.
- Словник за замовчуванням (`assets/words/*.json`) зараз містить лише `ua`. Потрібні поля `pl`, `es`, `de`, `it`, `pt`, `fr`.
- Назви категорій (`categories.json`: «Спорт/Sport») треба локалізувати за мовою профілю або відображати англійською.

## Assets
- `icons/*.svg` — Lucide (ISC), stroke 2, 24×24. Імпортувати в Android як Vector Asset. Відповідність старим drawable — у секції 8 файлу `Silfy Screens.dc.html`.
- Іконка додатка — варіант **7a** (секція 7): фон `#72E4EC`, «s» (Unbounded 700) кольору `#100C4E` із крапкою. Adaptive icon: гліф у безпечній зоні 66%.
- Play Market (секції 9–10): іконка 512×512, банер 1024×500, 6 скриншотів 1080×1920.
- `assets/old-*.png` — старі логотипи для порівняння.

## Screenshots
PNG @2x (720×1560 = 360×780 dp) у `screenshots/`. Префікс у назві = бейдж екрана:
- `1a-splash` · `1b-welcome` · `1c-profiles` · `1d-create-profile`
- `2a-dictionary-all` · `2b-dictionary-favourites` · `2c-edit-word` · `2d-add-to-categories` · `2e-delete-word`
- `3a-categories` · `3b-category` · `3c-new-category` · `3d-delete-category`
- `4a-session-setup` · `4b-no-words-dialog` · `4c-choose-idle` · `4d-choose-correct` · `4e-choose-wrong` · `4f-letters` · `4g-type` · `4h-type-wrong` · `4i-end-session-dialog` · `4j-results`
- `5a-profile` · `5b-edit-profile` · `5c-switch-profile` · `5d-delete-profile` · `5e-profile-polish` (той самий екран для профілю з польською)
- `6a-menu` · `6b-training-mode` · `6c-dictionary-settings` · `6d-reset-progress-dialog` · `6e/6f/6g-good-to-know-1..3`
- `7-app-icon-options` · `8-icon-set` (сітка іконок + відповідність старим drawable)

Готові файли для Play Console — у `play-store/`: `icon-512.png`, `feature-graphic-1024x500.png`, `screenshot-1..6.png` (1080×1920).

## Files
- `Silfy Screens.dc.html` — головне полотно: усі екрани, іконки, Play Market.
- `SilfyApp.dc.html` — інтерактивний прототип. Логіка й тексти — в `<script data-dc-script>`: `LANGS`, `LEVELS`, `LVC`, `dialogFor()`.
- `Silfy Icons.dc.html`, `Silfy Play Store.dc.html` — ті самі секції окремими файлами.
- `s-icon.js`, `support.js` — допоміжні файли для рендеру прототипу; в Android їх не переносити.
