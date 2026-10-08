# Silfy 2.0 — реліз у Google Play (чек-ліст)

Версія в коді: `version=2.0.0` у `gradle.properties` → `versionName = 2.0.0`, `versionCode = 2000099`.

## Версіонування (як у ShrinkPic)
- Єдине джерело — рядок `version=` у `gradle.properties`. `versionName` береться як є, `versionCode` рахує `versionCodeFrom` в `app/build.gradle`:
  `major·1 000 000 + minor·10 000 + patch·100 + кандидат`, де кандидат: `-SNAPSHOT` = 0, `-RC1…RC98` = n, реліз = 99.
  Порядок: `2.0.0-SNAPSHOT` (2000000) < `2.0.0-RC1` (2000001) < `2.0.0` (2000099) < `2.0.1-SNAPSHOT` (2000100).
- Формат строго `X.Y.Z[-RCn|-SNAPSHOT]` — три числа, інакше збірка впаде.
- Зміна версії — окремий коміт, що чіпає лише `gradle.properties`: «Update version to 2.0.1-RC1».
- Файли збірки: `silfy-<версія>-<variant>.apk / .aab` (`archivesBaseName`), напр. `silfy-2.0.0-release.aab`.
- 1.x мали ручні коди до 11, тож 2000099 їх перекриває — Play прийме оновлення.

## Перед збіркою релізу
- [ ] Перевірити на пристрої міграцію з 1.1.10: поставити 1.1.10 → додати слова / категорії / прогрес → встановити 2.0 поверх (див. беклог у `silfy_v2_plan.md`).
- [ ] Пройти онбординг, перемикання / видалення профілів, тренування, меню (усі 7 мов UI).
- [ ] Іконка: launcher на Android 8+ (adaptive, різні маски), 7–7.1 (PNG), тематична іконка Android 13+, системний splash Android 12+.
- [ ] Зібрати підписаний AAB (`:app:bundleRelease`, ключ у `keystore/`).

## Play Console → Store listing
Готові файли — `doc/design_handoff_silfy_v2/play-store/`:
- [ ] App icon 512×512 — `icon-512.png`
- [ ] Feature graphic 1024×500 — `feature-graphic-1024x500.png`
- [ ] Phone screenshots 1080×1920 — `screenshot-1.png` … `screenshot-6.png`
- [ ] Короткий і повний опис: оновити під 2.0 (профілі з мовою перекладу, 7 мов, режими Easy / Hard). Сторінка лише англійською (en-US), український переклад видалити.

## Play Console → App content
- [ ] Data safety: у 2.0 немає входу через email / акаунтів — профілі лише на пристрої. Лишаються Firebase Analytics, Crashlytics, Remote Config — перевірити, що форма це відображає.
- [ ] Privacy policy URL — той самий, що в застосунку (`url_privacy_policy` у `strings_brand.xml`).

## Реліз
- [ ] «What's new» для 2.0 (новий дизайн, 7 мов перекладу, профілі, нове тренування).
- [ ] Після публікації: у Firebase Remote Config підняти `version_code` до `2000099` (versionCode 2.0.0), щоб у 1.x з'явилась пропозиція оновитися (діалог `update_*` на splash). Надалі — завжди versionCode щойно опублікованої версії.
