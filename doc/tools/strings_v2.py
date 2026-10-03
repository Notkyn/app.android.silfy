"""Silfy 2.0 UI strings: single source for res/values*/strings_v2.xml (8 files).

Add a key to SECTIONS and a value for every language in STRINGS, then run:
    python doc/tools/strings_v2.py
Default (values/) is English. Informal "you" in every language.
"""
import os

RES = os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', '..', 'app', 'src', 'main', 'res')
LANGS = ['en', 'uk', 'pl', 'es', 'de', 'it', 'pt', 'fr']

SECTIONS = [
    ('Bottom navigation', ['nav_words', 'nav_categories', 'nav_profile', 'nav_menu', 'nav_start']),
    ('Common', ['dialog_button_cancel', 'dialog_button_ok', 'action_back', 'privacy_policy']),
    ('Splash', ['splash_tagline', 'update_title', 'update_message', 'update_button', 'update_later']),
    ('Welcome', ['welcome_title', 'welcome_subtitle', 'welcome_button']),
    ('Who is learning', ['profiles_title', 'profiles_subtitle', 'profiles_new']),
    ('Create profile', ['create_title', 'create_subtitle', 'create_name_label', 'create_name_hint',
                        'create_language_label', 'create_language_note', 'create_button', 'create_privacy',
                        'create_error_title', 'create_error_message']),
    ('Knowledge levels', ['level_name_excellent', 'level_name_good', 'level_name_average', 'level_name_poor',
                          'level_name_unknown', 'level_desc_excellent', 'level_desc_good', 'level_desc_average',
                          'level_desc_poor', 'level_desc_unknown']),
    ('Difficulty and lists', ['difficulty_easy', 'difficulty_hard', 'list_favourites', 'list_blacklist']),
    ('Good to know', ['gtk_title', 'gtk_kicker', 'gtk_section_progress', 'gtk_title_levels', 'gtk_lead_levels',
                      'gtk_section_difficulty', 'gtk_title_difficulty', 'gtk_lead_difficulty', 'gtk_easy_choose',
                      'gtk_easy_letters', 'gtk_language_pair', 'gtk_hard_type', 'gtk_hard_both', 'gtk_hard_weak',
                      'gtk_section_lists', 'gtk_title_lists', 'gtk_lead_lists', 'gtk_favourites_desc',
                      'gtk_blacklist_desc', 'gtk_previous', 'gtk_next', 'gtk_done']),
]

S = {}


def add(key, en, uk, pl, es, de, it, pt, fr):
    S[key] = dict(zip(LANGS, [en, uk, pl, es, de, it, pt, fr]))


# ---------- Bottom navigation ----------
add('nav_words', 'Words', 'Слова', 'Słowa', 'Palabras', 'Wörter', 'Parole', 'Palavras', 'Mots')
add('nav_categories', 'Categories', 'Категорії', 'Kategorie', 'Categorías', 'Kategorien', 'Categorie', 'Categorias', 'Catégories')
add('nav_profile', 'Profile', 'Профіль', 'Profil', 'Perfil', 'Profil', 'Profilo', 'Perfil', 'Profil')
add('nav_menu', 'Menu', 'Меню', 'Menu', 'Menú', 'Menü', 'Menu', 'Menu', 'Menu')
add('nav_start', 'Start training', 'Почати тренування', 'Rozpocznij trening', 'Empezar a entrenar', 'Training starten',
    "Inizia l'allenamento", 'Começar o treino', "Commencer l'entraînement")

# ---------- Common ----------
add('dialog_button_cancel', 'Cancel', 'Скасувати', 'Anuluj', 'Cancelar', 'Abbrechen', 'Annulla', 'Cancelar', 'Annuler')
add('dialog_button_ok', 'OK', 'OK', 'OK', 'Aceptar', 'OK', 'OK', 'OK', 'OK')
add('action_back', 'Back', 'Назад', 'Wstecz', 'Atrás', 'Zurück', 'Indietro', 'Voltar', 'Retour')
add('privacy_policy', 'Privacy Policy', 'Політику конфіденційності', 'Politykę prywatności', 'Política de privacidad',
    'Datenschutzerklärung', 'Informativa sulla privacy', 'Política de Privacidade', 'Politique de confidentialité')

# ---------- Splash ----------
add('splash_tagline', 'English words in your language', 'Англійські слова твоєю мовою',
    'Angielskie słowa w twoim języku', 'Palabras en inglés en tu idioma', 'Englische Wörter in deiner Sprache',
    'Parole inglesi nella tua lingua', 'Palavras em inglês na tua língua', 'Des mots anglais dans ta langue')
add('update_title', 'A new version is available', 'Доступна нова версія', 'Dostępna jest nowa wersja',
    'Hay una nueva versión', 'Neue Version verfügbar', 'È disponibile una nuova versione',
    'Há uma nova versão disponível', 'Une nouvelle version est disponible')
add('update_message', 'Update Silfy in Google Play to get the latest features and fixes.',
    'Онови Silfy у Google Play, щоб отримати нові можливості та виправлення.',
    'Zaktualizuj Silfy w Google Play, aby otrzymać nowe funkcje i poprawki.',
    'Actualiza Silfy en Google Play para obtener las últimas funciones y correcciones.',
    'Aktualisiere Silfy bei Google Play, um die neuesten Funktionen und Korrekturen zu erhalten.',
    'Aggiorna Silfy su Google Play per avere le ultime funzioni e correzioni.',
    'Atualiza o Silfy no Google Play para teres as novidades e correções mais recentes.',
    'Mets à jour Silfy sur Google Play pour profiter des dernières fonctionnalités et corrections.')
add('update_button', 'Update', 'Оновити', 'Aktualizuj', 'Actualizar', 'Aktualisieren', 'Aggiorna', 'Atualizar', 'Mettre à jour')
add('update_later', 'Later', 'Пізніше', 'Później', 'Más tarde', 'Später', 'Più tardi', 'Mais tarde', 'Plus tard')

# ---------- Welcome ----------
add('welcome_title', 'English, in the language you speak', 'Англійська — мовою, якою ти говориш',
    'Angielski w twoim języku', 'Inglés, en el idioma que hablas', 'Englisch in deiner Sprache',
    "L'inglese, nella tua lingua", 'Inglês, na língua que falas', "L'anglais, dans ta langue")
add('welcome_subtitle',
    'Learn words with translations into Ukrainian, Polish, Spanish, German, Italian, Portuguese or French.',
    'Вивчай слова з перекладом українською, польською, іспанською, німецькою, італійською, португальською або французькою.',
    'Ucz się słów z tłumaczeniem na ukraiński, polski, hiszpański, niemiecki, włoski, portugalski lub francuski.',
    'Aprende palabras con traducciones al ucraniano, polaco, español, alemán, italiano, portugués o francés.',
    'Lerne Wörter mit Übersetzungen ins Ukrainische, Polnische, Spanische, Deutsche, Italienische, Portugiesische oder Französische.',
    'Impara parole con traduzioni in ucraino, polacco, spagnolo, tedesco, italiano, portoghese o francese.',
    'Aprende palavras com traduções para ucraniano, polaco, espanhol, alemão, italiano, português ou francês.',
    'Apprends des mots avec leur traduction en ukrainien, polonais, espagnol, allemand, italien, portugais ou français.')
add('welcome_button', 'Get started', 'Почати', 'Zaczynamy', 'Empezar', 'Los geht’s', 'Inizia', 'Começar', 'Commencer')

# ---------- Who is learning ----------
add('profiles_title', 'Who’s learning?', 'Хто вчиться?', 'Kto się uczy?', '¿Quién aprende?', 'Wer lernt?',
    'Chi sta imparando?', 'Quem está a aprender?', 'Qui apprend ?')
add('profiles_subtitle', 'Profiles on this device. Each one has its own translation language and progress.',
    'Профілі на цьому пристрої. У кожного своя мова перекладу й прогрес.',
    'Profile na tym urządzeniu. Każdy ma własny język tłumaczenia i postępy.',
    'Perfiles en este dispositivo. Cada uno tiene su propio idioma de traducción y su progreso.',
    'Profile auf diesem Gerät. Jedes hat seine eigene Übersetzungssprache und seinen eigenen Fortschritt.',
    'Profili su questo dispositivo. Ognuno ha la sua lingua di traduzione e i suoi progressi.',
    'Perfis neste dispositivo. Cada um tem a sua língua de tradução e o seu progresso.',
    'Profils sur cet appareil. Chacun a sa propre langue de traduction et sa progression.')
add('profiles_new', 'New profile', 'Новий профіль', 'Nowy profil', 'Nuevo perfil', 'Neues Profil', 'Nuovo profilo',
    'Novo perfil', 'Nouveau profil')

# ---------- Create profile ----------
add('create_title', 'Create your profile', 'Створи профіль', 'Utwórz profil', 'Crea tu perfil', 'Profil erstellen',
    'Crea il tuo profilo', 'Cria o teu perfil', 'Crée ton profil')
add('create_subtitle', 'No email, no password — just your name and the language you want translations in.',
    'Без пошти й пароля — лише твоє імʼя та мова, якою перекладати слова.',
    'Bez e-maila i hasła — tylko twoje imię i język tłumaczeń.',
    'Sin correo ni contraseña: solo tu nombre y el idioma de las traducciones.',
    'Keine E-Mail, kein Passwort – nur dein Name und die Sprache für die Übersetzungen.',
    'Niente email né password: solo il tuo nome e la lingua delle traduzioni.',
    'Sem email nem palavra-passe — só o teu nome e a língua das traduções.',
    'Ni e-mail ni mot de passe : juste ton prénom et la langue des traductions.')
add('create_name_label', 'Your name', 'Твоє імʼя', 'Twoje imię', 'Tu nombre', 'Dein Name', 'Il tuo nome', 'O teu nome', 'Ton prénom')
add('create_name_hint', 'e.g. Olena', 'напр. Олена', 'np. Ola', 'p. ej., Lucía', 'z. B. Anna', 'es. Giulia', 'p. ex. Inês', 'ex. Camille')
add('create_language_label', 'Translate words into', 'Перекладати слова на', 'Tłumacz słowa na', 'Traducir las palabras a',
    'Wörter übersetzen in', 'Traduci le parole in', 'Traduzir palavras para', 'Traduire les mots en')
add('create_language_note',
    'The language can’t be changed later. To learn with another language, create one more profile.',
    'Мову не можна буде змінити. Щоб вчитися з іншою мовою, створи ще один профіль.',
    'Języka nie można później zmienić. Aby uczyć się z innym językiem, utwórz kolejny profil.',
    'El idioma no se puede cambiar después. Para aprender con otro idioma, crea otro perfil.',
    'Die Sprache kann später nicht geändert werden. Um mit einer anderen Sprache zu lernen, erstelle ein weiteres Profil.',
    "La lingua non potrà essere cambiata. Per imparare con un'altra lingua, crea un altro profilo.",
    'A língua não pode ser alterada depois. Para aprender com outra língua, cria mais um perfil.',
    'La langue ne pourra pas être modifiée. Pour apprendre avec une autre langue, crée un autre profil.')
add('create_button', 'Create profile', 'Створити профіль', 'Utwórz profil', 'Crear perfil', 'Profil erstellen',
    'Crea profilo', 'Criar perfil', 'Créer le profil')
add('create_privacy', 'By continuing you accept the %1$s', 'Продовжуючи, ти приймаєш %1$s', 'Kontynuując, akceptujesz %1$s',
    'Al continuar, aceptas la %1$s', 'Mit dem Fortfahren akzeptierst du die %1$s', "Continuando accetti l'%1$s",
    'Ao continuar, aceitas a %1$s', 'En continuant, tu acceptes la %1$s')
add('create_error_title', 'Couldn’t create the profile', 'Не вдалося створити профіль', 'Nie udało się utworzyć profilu',
    'No se pudo crear el perfil', 'Profil konnte nicht erstellt werden', 'Impossibile creare il profilo',
    'Não foi possível criar o perfil', 'Impossible de créer le profil')
add('create_error_message', 'Something went wrong. Please try again.', 'Щось пішло не так. Спробуй ще раз.',
    'Coś poszło nie tak. Spróbuj ponownie.', 'Algo salió mal. Inténtalo de nuevo.',
    'Etwas ist schiefgelaufen. Bitte versuche es erneut.', 'Qualcosa è andato storto. Riprova.',
    'Algo correu mal. Tenta novamente.', 'Un problème est survenu. Réessaie.')

# ---------- Knowledge levels ----------
add('level_name_excellent', 'Excellent', 'Відмінно', 'Doskonale', 'Excelente', 'Ausgezeichnet', 'Eccellente', 'Excelente', 'Excellent')
add('level_name_good', 'Good', 'Добре', 'Dobrze', 'Bien', 'Gut', 'Buono', 'Bom', 'Bien')
add('level_name_average', 'Average', 'Середньо', 'Średnio', 'Regular', 'Mittel', 'Medio', 'Médio', 'Moyen')
add('level_name_poor', 'Poor', 'Слабко', 'Słabo', 'Flojo', 'Schwach', 'Scarso', 'Fraco', 'Faible')
add('level_name_unknown', 'Unknown', 'Невідомо', 'Nieznane', 'Desconocido', 'Unbekannt', 'Sconosciuto', 'Desconhecido', 'Inconnu')
add('level_desc_excellent', 'Solid knowledge', 'Тверді знання', 'Solidna wiedza', 'Dominio sólido', 'Sicheres Wissen',
    'Conoscenza solida', 'Conhecimento sólido', 'Connaissance solide')
add('level_desc_good', 'Know it most of the time', 'Знаєш майже завжди', 'Znasz je prawie zawsze', 'La sabes casi siempre',
    'Meistens gewusst', 'La sai quasi sempre', 'Sabes quase sempre', 'Tu le sais presque toujours')
add('level_desc_average', 'Getting there', 'Вже краще', 'Coraz lepiej', 'Vas mejorando', 'Wird langsam', 'Ci sei quasi',
    'Estás quase lá', 'Ça vient')
add('level_desc_poor', 'You’ve seen it a few times', 'Траплялося кілька разів', 'Pojawiło się kilka razy',
    'La has visto unas cuantas veces', 'Schon ein paarmal gesehen', "L'hai vista qualche volta",
    'Já a viste algumas vezes', "Tu l'as vu quelques fois")
add('level_desc_unknown', 'Just added, needs practice', 'Щойно додане, треба практики', 'Dopiero dodane, wymaga ćwiczeń',
    'Recién añadida, necesita práctica', 'Gerade hinzugefügt, braucht Übung', 'Appena aggiunta, va esercitata',
    'Acabada de adicionar, precisa de prática', 'Tout juste ajouté, à pratiquer')

# ---------- Difficulty and lists ----------
add('difficulty_easy', 'Easy', 'Легко', 'Łatwy', 'Fácil', 'Leicht', 'Facile', 'Fácil', 'Facile')
add('difficulty_hard', 'Hard', 'Складно', 'Trudny', 'Difícil', 'Schwer', 'Difficile', 'Difícil', 'Difficile')
add('list_favourites', 'Favourites', 'Обрані', 'Ulubione', 'Favoritas', 'Favoriten', 'Preferite', 'Favoritas', 'Favoris')
add('list_blacklist', 'Blacklist', 'Чорний список', 'Czarna lista', 'Lista negra', 'Sperrliste', 'Lista nera', 'Lista negra', 'Liste noire')

# ---------- Good to know ----------
add('gtk_title', 'Good to know', 'Варто знати', 'Warto wiedzieć', 'Conviene saber', 'Gut zu wissen', 'Buono a sapersi',
    'É bom saber', 'Bon à savoir')
add('gtk_kicker', *(['%1$d / %2$d · %3$s'] * 8))
add('gtk_section_progress', 'Progress', 'Прогрес', 'Postępy', 'Progreso', 'Fortschritt', 'Progressi', 'Progresso', 'Progression')
add('gtk_title_levels', 'Every word has a level', 'Кожне слово має рівень', 'Każde słowo ma poziom',
    'Cada palabra tiene un nivel', 'Jedes Wort hat ein Level', 'Ogni parola ha un livello', 'Cada palavra tem um nível',
    'Chaque mot a un niveau')
add('gtk_lead_levels', 'Right answers move a word up. Mistakes can move it down quickly.',
    'Правильні відповіді підвищують рівень слова. Помилки можуть швидко його знизити.',
    'Dobre odpowiedzi podnoszą poziom słowa. Błędy mogą go szybko obniżyć.',
    'Las respuestas correctas suben el nivel de una palabra. Los errores pueden bajarlo rápido.',
    'Richtige Antworten bringen ein Wort nach oben. Fehler können es schnell wieder senken.',
    'Le risposte giuste fanno salire una parola. Gli errori possono farla scendere in fretta.',
    'As respostas certas fazem uma palavra subir. Os erros podem fazê-la descer depressa.',
    'Les bonnes réponses font monter un mot. Les erreurs peuvent vite le faire redescendre.')
add('gtk_section_difficulty', 'Difficulty', 'Складність', 'Trudność', 'Dificultad', 'Schwierigkeit', 'Difficoltà',
    'Dificuldade', 'Difficulté')
add('gtk_title_difficulty', 'Two ways to train', 'Два способи тренуватися', 'Dwa sposoby nauki', 'Dos formas de entrenar',
    'Zwei Arten zu trainieren', 'Due modi di allenarsi', 'Duas formas de treinar', "Deux façons de s'entraîner")
add('gtk_lead_difficulty', 'Pick the level in Training mode. You can change it any time.',
    'Обери рівень у режимі тренування. Його можна змінити будь-коли.',
    'Wybierz poziom w trybie treningu. Możesz go zmienić w każdej chwili.',
    'Elige el nivel en el modo de entrenamiento. Puedes cambiarlo cuando quieras.',
    'Wähle das Level im Trainingsmodus. Du kannst es jederzeit ändern.',
    'Scegli il livello nella modalità di allenamento. Puoi cambiarlo quando vuoi.',
    'Escolhe o nível no modo de treino. Podes mudá-lo a qualquer momento.',
    "Choisis le niveau dans le mode d'entraînement. Tu peux le changer à tout moment.")
add('gtk_easy_choose', 'Choose the right translation', 'Обери правильний переклад', 'Wybierz poprawne tłumaczenie',
    'Elige la traducción correcta', 'Wähle die richtige Übersetzung', 'Scegli la traduzione giusta',
    'Escolhe a tradução certa', 'Choisis la bonne traduction')
add('gtk_easy_letters', 'Build the translation from letters', 'Склади переклад із літер', 'Ułóż tłumaczenie z liter',
    'Forma la traducción con letras', 'Setze die Übersetzung aus Buchstaben zusammen',
    'Componi la traduzione con le lettere', 'Forma a tradução com letras', 'Compose la traduction avec des lettres')
add('gtk_language_pair', *(['%1$s → %2$s'] * 8))
add('gtk_hard_type', 'Type the word without hints', 'Введи слово без підказок', 'Wpisz słowo bez podpowiedzi',
    'Escribe la palabra sin pistas', 'Tippe das Wort ohne Hinweise', 'Scrivi la parola senza suggerimenti',
    'Escreve a palavra sem dicas', 'Tape le mot sans indices')
add('gtk_hard_both', 'Both directions, extra letters', 'В обидва боки, зайві літери', 'W obie strony, dodatkowe litery',
    'En ambos sentidos, letras de más', 'In beide Richtungen, zusätzliche Buchstaben',
    'In entrambe le direzioni, lettere in più', 'Nos dois sentidos, letras a mais', 'Dans les deux sens, lettres en plus')
add('gtk_hard_weak', 'Weak words come up more often', 'Слабкі слова трапляються частіше',
    'Słabe słowa pojawiają się częściej', 'Las palabras flojas salen más a menudo', 'Schwache Wörter kommen öfter dran',
    'Le parole deboli escono più spesso', 'As palavras fracas aparecem mais vezes', 'Les mots faibles reviennent plus souvent')
add('gtk_section_lists', 'Lists', 'Списки', 'Listy', 'Listas', 'Listen', 'Liste', 'Listas', 'Listes')
add('gtk_title_lists', 'Mark words your way', 'Познач слова по-своєму', 'Oznaczaj słowa po swojemu',
    'Marca las palabras a tu manera', 'Markiere Wörter auf deine Art', 'Segna le parole a modo tuo',
    'Marca as palavras à tua maneira', 'Marque les mots à ta façon')
add('gtk_lead_lists', 'Two lists help you tune what shows up in training.',
    'Два списки допомагають налаштувати, що буде в тренуванні.',
    'Dwie listy pomagają dopasować to, co pojawia się w treningu.',
    'Dos listas te ayudan a ajustar lo que aparece en el entrenamiento.',
    'Zwei Listen helfen dir festzulegen, was im Training vorkommt.',
    "Due liste ti aiutano a scegliere cosa compare nell'allenamento.",
    'Duas listas ajudam-te a ajustar o que aparece no treino.',
    "Deux listes t'aident à régler ce qui apparaît à l'entraînement.")
add('gtk_favourites_desc', 'Words you want to focus on. Train only them from the Start screen.',
    'Слова, на яких хочеш зосередитися. На екрані старту можна тренувати лише їх.',
    'Słowa, na których chcesz się skupić. Na ekranie startu możesz trenować tylko je.',
    'Palabras en las que quieres centrarte. Desde la pantalla de inicio puedes entrenar solo esas.',
    'Wörter, auf die du dich konzentrieren willst. Auf dem Startbildschirm kannst du nur diese trainieren.',
    'Parole su cui vuoi concentrarti. Dalla schermata iniziale puoi allenare solo quelle.',
    'Palavras em que te queres concentrar. No ecrã inicial podes treinar só essas.',
    "Les mots sur lesquels tu veux te concentrer. Depuis l'écran de démarrage, tu peux n'entraîner que ceux-là.")
add('gtk_blacklist_desc', 'Words you already know or don’t need. They’re skipped unless you include them.',
    'Слова, які вже знаєш або які не потрібні. Вони пропускаються, якщо їх не ввімкнути.',
    'Słowa, które już znasz lub których nie potrzebujesz. Są pomijane, chyba że je włączysz.',
    'Palabras que ya sabes o que no necesitas. Se omiten a menos que las incluyas.',
    'Wörter, die du schon kennst oder nicht brauchst. Sie werden übersprungen, außer du nimmst sie mit auf.',
    'Parole che conosci già o che non ti servono. Vengono saltate, a meno che tu non le includa.',
    'Palavras que já sabes ou de que não precisas. São ignoradas, a menos que as incluas.',
    "Les mots que tu connais déjà ou dont tu n'as pas besoin. Ils sont ignorés, sauf si tu les inclus.")
add('gtk_previous', 'Previous', 'Назад', 'Wstecz', 'Anterior', 'Zurück', 'Indietro', 'Anterior', 'Précédent')
add('gtk_next', 'Next', 'Далі', 'Dalej', 'Siguiente', 'Weiter', 'Avanti', 'Seguinte', 'Suivant')
add('gtk_done', 'Done', 'Готово', 'Gotowe', 'Listo', 'Fertig', 'Fatto', 'Concluído', 'Terminé')


def escape(value):
    return (value.replace('&', '&amp;').replace('<', '&lt;').replace('>', '&gt;')
            .replace("'", "\\'").replace('"', '\\"'))


def main():
    keys = [k for _, ks in SECTIONS for k in ks]
    missing = [k for k in keys if k not in S] + [k for k in S if k not in keys]
    assert not missing, 'keys without section/value: %s' % missing
    for lang in LANGS:
        folder = os.path.join(RES, 'values' if lang == 'en' else 'values-' + lang)
        os.makedirs(folder, exist_ok=True)
        lines = ['<?xml version="1.0" encoding="utf-8"?>']
        if lang == 'en':
            lines.append('<!--  Generated by doc/tools/strings_v2.py — edit the script, not this file  -->')
        lines.append('<resources>')
        for n, (title, section_keys) in enumerate(SECTIONS):
            if n:
                lines.append('')
            lines.append('    <!--  %s  -->' % title)
            for key in section_keys:
                value = S[key][lang]
                assert value, (key, lang)
                attr = ' formatted="false"' if '%' in value and '$' not in value else ''
                lines.append('    <string name="%s"%s>%s</string>' % (key, attr, escape(value)))
        lines.append('</resources>')
        with open(os.path.join(folder, 'strings_v2.xml'), 'w', encoding='utf-8', newline='\n') as f:
            f.write('\n'.join(lines) + '\n')
    print('%d keys x %d languages' % (len(keys), len(LANGS)))


if __name__ == '__main__':
    main()
