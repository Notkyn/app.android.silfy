# Silfy 2.0 — план реалізації редизайну

Дизайн: `doc/design_handoff_silfy_v2/` (README.md, screenshots/, SilfyApp.dc.html).

## Прийняті рішення (2026-10-03)

- **Рівні слів**: лишаємо поточну бальну систему (`WordState.minCount` 0–100: пороги 30/60/80/100).
  На 5 рівнів дизайну мапимо: Unknown=0, Poor=1, Average=2, Good=3, Excellent=4 (кількість заповнених барів).
  Логіку «+1/−1» з README **не** впроваджуємо.
- **Мова UI = мова профілю.** Підтримуються всі 7 мов: uk, pl, es, de, it, pt, fr.
  Профіль з укр → увесь UI українською; назви категорій теж мовою профілю.
  (До створення профілю — системна мова, якщо вона серед 7, інакше англійська.)
- **Переклади словника** на pl/es/de/it/pt/fr генерує Claude (≈2700 слів), далі вибіркова перевірка.

## Кроки

1. ✅ Design system: кольори, шрифти, типографіка, радіуси/відступи, стилі компонентів, іконки, тема.
2. ✅ Базові компоненти: bottom nav, segmented control, індикатор рівня, бейдж мови, діалог, bottom sheet.
3. Шар даних: новий `Profile` (name, lang, avatarColor), узагальнений переклад замість `ua`, міграція Room v1→v2, `RawWord` з мапою мов, локаль застосунку з профілю.
4. Контент: переклади словника на 6 мов, категорії, `strings.xml` для 7 мов.
5. Онбординг: Splash, Welcome, Who's learning, Create profile.
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
