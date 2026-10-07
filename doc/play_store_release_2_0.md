# Silfy 2.0 — реліз у Google Play (чек-ліст)

Версія в коді: `VERSION_NAME = '2.0'`, `VERSION_CODE = 11` (кореневий `build.gradle`).

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
- [ ] Короткий і повний опис: оновити під 2.0 (профілі з мовою перекладу, 7 мов, режими Easy / Hard). Тексти ще не написані.

## Play Console → App content
- [ ] Data safety: у 2.0 немає входу через email / акаунтів — профілі лише на пристрої. Лишаються Firebase Analytics, Crashlytics, Remote Config — перевірити, що форма це відображає.
- [ ] Privacy policy URL — той самий, що в застосунку (`url_privacy_policy` у `strings_brand.xml`).

## Реліз
- [ ] «What's new» для 2.0 (новий дизайн, 7 мов перекладу, профілі, нове тренування).
- [ ] Після публікації: у Firebase Remote Config підняти `version_code` до `11`, щоб у 1.x з'явилась пропозиція оновитися (діалог `update_*` на splash).
