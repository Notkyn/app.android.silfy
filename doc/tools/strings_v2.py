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
    ('Common', ['dialog_button_cancel', 'dialog_button_ok', 'action_back', 'action_done', 'privacy_policy']),
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
    ('Dictionary', ['dictionary_greeting', 'dictionary_title', 'dictionary_add_word', 'dictionary_search_hint',
                    'dictionary_sort_level', 'dictionary_tab_all', 'dictionary_empty_title', 'dictionary_empty_all',
                    'dictionary_empty_search', 'dictionary_empty_favourites', 'dictionary_empty_blacklist']),
    ('Word form', ['word_title_edit', 'word_title_new', 'word_en_label', 'word_en_hint', 'word_en_error',
                   'word_en_exists', 'word_translation_label', 'word_translation_hint', 'word_translation_note',
                   'word_translation_error', 'word_categories_label', 'word_categories_add', 'word_blacklist_note',
                   'word_level_label', 'word_level_reset', 'word_save', 'word_delete', 'word_delete_title',
                   'word_delete_message', 'word_delete_button', 'word_error_load', 'word_error_save',
                   'word_error_delete']),
    ('Add to categories', ['categories_sheet_title', 'categories_sheet_empty']),
    ('Categories', ['categories_title', 'categories_new', 'categories_new_hint']),
    ('Category', ['category_edit', 'category_delete', 'category_add_word', 'category_empty', 'category_error_save',
                  'category_error_delete']),
    ('Category name', ['category_name_label', 'category_name_hint', 'category_save', 'category_error_empty',
                       'category_error_chars', 'category_error_exists']),
    ('Delete category', ['category_delete_title', 'category_delete_message', 'category_delete_button']),
    ('Session setup', ['setup_title_start', 'setup_title_menu', 'setup_difficulty', 'setup_easy_note',
                       'setup_hard_note', 'setup_duration', 'setup_minutes', 'setup_limit_mistakes', 'setup_words',
                       'setup_words_all', 'setup_include_blacklist', 'setup_categories_note', 'setup_start',
                       'setup_save', 'setup_error_save', 'setup_empty_title', 'setup_empty_message']),
    ('Session', ['session_word_number', 'session_caption_choose', 'session_caption_letters', 'session_caption_type',
                 'session_type_hint', 'session_correct_answer', 'session_feedback_correct',
                 'session_feedback_level_up', 'session_feedback_wrong', 'session_check', 'session_next',
                 'session_erase', 'session_error_start']),
    ('End session', ['session_end_title', 'session_end_message', 'session_end_keep', 'session_end_button']),
    ('Results', ['results_time', 'results_mistakes', 'results_ended', 'results_title', 'results_accuracy',
                 'results_words_selected', 'results_words_used', 'results_correct', 'results_wrong',
                 'results_again', 'results_back']),
]

# Plurals: quantities each language needs (CLDR), value — the text for every quantity
PLURAL_QUANTITIES = {
    'en': ['one', 'other'],
    'uk': ['one', 'few', 'many', 'other'],
    'pl': ['one', 'few', 'many', 'other'],
    'es': ['one', 'many', 'other'],
    'de': ['one', 'other'],
    'it': ['one', 'many', 'other'],
    'pt': ['one', 'many', 'other'],
    'fr': ['one', 'many', 'other'],
}
PLURAL_SECTIONS = [
    ('Counters', ['plural_categories', 'plural_words', 'plural_words_match']),
]

S = {}
P = {}


def add(key, en, uk, pl, es, de, it, pt, fr):
    S[key] = dict(zip(LANGS, [en, uk, pl, es, de, it, pt, fr]))


def add_plural(key, **values):
    """values: lang → tuple of texts in PLURAL_QUANTITIES[lang] order"""
    P[key] = {lang: dict(zip(PLURAL_QUANTITIES[lang], texts)) for lang, texts in values.items()}


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
add('action_done', 'Done', 'Готово', 'Gotowe', 'Listo', 'Fertig', 'Fatto', 'Concluído', 'Terminé')
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


# ---------- Dictionary ----------
add('dictionary_greeting', 'Hi, %1$s', 'Привіт, %1$s', 'Cześć, %1$s', 'Hola, %1$s', 'Hallo, %1$s', 'Ciao, %1$s',
    'Olá, %1$s', 'Salut, %1$s')
add('dictionary_title', 'Dictionary', 'Словник', 'Słownik', 'Diccionario', 'Wörterbuch', 'Dizionario', 'Dicionário',
    'Dictionnaire')
add('dictionary_add_word', 'Add word', 'Додати слово', 'Dodaj słowo', 'Añadir palabra', 'Wort hinzufügen',
    'Aggiungi parola', 'Adicionar palavra', 'Ajouter un mot')
add('dictionary_search_hint', 'Search words', 'Шукати слова', 'Szukaj słów', 'Buscar palabras', 'Wörter suchen',
    'Cerca parole', 'Procurar palavras', 'Rechercher des mots')
add('dictionary_sort_level', 'Level', 'Рівень', 'Poziom', 'Nivel', 'Level', 'Livello', 'Nível', 'Niveau')
add('dictionary_tab_all', 'All', 'Усі', 'Wszystkie', 'Todas', 'Alle', 'Tutte', 'Todas', 'Tous')
add('dictionary_empty_title', 'Nothing here yet', 'Тут поки порожньо', 'Na razie pusto', 'Aún no hay nada',
    'Hier ist noch nichts', 'Ancora niente qui', 'Ainda não há nada aqui', "Rien pour l'instant")
add('dictionary_empty_all', 'Tap + to add your first word.', 'Натисни +, щоб додати перше слово.',
    'Stuknij +, aby dodać pierwsze słowo.', 'Toca + para añadir tu primera palabra.',
    'Tippe auf +, um dein erstes Wort hinzuzufügen.', 'Tocca + per aggiungere la tua prima parola.',
    'Toca em + para adicionares a primeira palavra.', 'Appuie sur + pour ajouter ton premier mot.')
add('dictionary_empty_search', 'No words match your search.', 'Жодне слово не збігається з пошуком.',
    'Żadne słowo nie pasuje do wyszukiwania.', 'Ninguna palabra coincide con tu búsqueda.',
    'Kein Wort passt zu deiner Suche.', 'Nessuna parola corrisponde alla ricerca.',
    'Nenhuma palavra corresponde à pesquisa.', 'Aucun mot ne correspond à ta recherche.')
add('dictionary_empty_favourites', 'Tap a word and turn on Favourites to see it here.',
    'Відкрий слово й увімкни «Обрані», щоб воно з’явилося тут.',
    'Otwórz słowo i włącz Ulubione, aby pojawiło się tutaj.',
    'Abre una palabra y activa Favoritas para verla aquí.',
    'Öffne ein Wort und aktiviere Favoriten, damit es hier erscheint.',
    'Apri una parola e attiva Preferite per vederla qui.',
    'Abre uma palavra e ativa Favoritas para a veres aqui.',
    'Ouvre un mot et active Favoris pour le voir ici.')
add('dictionary_empty_blacklist', 'Blacklisted words are skipped in training.',
    'Слова з чорного списку пропускаються в тренуванні.', 'Słowa z czarnej listy są pomijane w treningu.',
    'Las palabras de la lista negra se omiten en el entrenamiento.',
    'Wörter auf der Sperrliste werden im Training übersprungen.',
    "Le parole nella lista nera vengono saltate nell'allenamento.",
    'As palavras da lista negra são ignoradas no treino.',
    "Les mots de la liste noire sont ignorés à l'entraînement.")

# ---------- Word form ----------
add('word_title_edit', 'Edit word', 'Редагувати слово', 'Edytuj słowo', 'Editar palabra', 'Wort bearbeiten',
    'Modifica parola', 'Editar palavra', 'Modifier le mot')
add('word_title_new', 'New word', 'Нове слово', 'Nowe słowo', 'Nueva palabra', 'Neues Wort', 'Nuova parola',
    'Nova palavra', 'Nouveau mot')
add('word_en_label', 'Word in English', 'Слово англійською', 'Słowo po angielsku', 'Palabra en inglés',
    'Wort auf Englisch', 'Parola in inglese', 'Palavra em inglês', 'Mot en anglais')
add('word_en_hint', 'e.g. journey', 'напр. journey', 'np. journey', 'p. ej. journey', 'z. B. journey', 'es. journey',
    'p. ex. journey', 'p. ex. journey')
add('word_en_error', 'Use English letters only', 'Лише англійські літери', 'Tylko angielskie litery',
    'Solo letras inglesas', 'Nur englische Buchstaben', 'Solo lettere inglesi', 'Só letras inglesas',
    'Lettres anglaises uniquement')
add('word_en_exists', 'This word is already in your dictionary', 'Це слово вже є у твоєму словнику',
    'To słowo jest już w twoim słowniku', 'Esta palabra ya está en tu diccionario',
    'Dieses Wort ist schon in deinem Wörterbuch', 'Questa parola è già nel tuo dizionario',
    'Esta palavra já está no teu dicionário', 'Ce mot est déjà dans ton dictionnaire')
add('word_translation_label', 'Translation', 'Переклад', 'Tłumaczenie', 'Traducción', 'Übersetzung', 'Traduzione',
    'Tradução', 'Traduction')
add('word_translation_hint', 'e.g. trip', 'напр. подорож, мандрівка', 'np. podróż', 'p. ej. viaje', 'z. B. Reise',
    'es. viaggio', 'p. ex. viagem', 'p. ex. voyage')
add('word_translation_note', 'Several translations? Separate them with commas.',
    'Кілька перекладів? Розділи їх комами.', 'Kilka tłumaczeń? Oddziel je przecinkami.',
    '¿Varias traducciones? Sepáralas con comas.', 'Mehrere Übersetzungen? Trenne sie mit Kommas.',
    'Più traduzioni? Separale con virgole.', 'Várias traduções? Separa-as com vírgulas.',
    'Plusieurs traductions ? Sépare-les par des virgules.')
add('word_translation_error', 'Use letters only, separate translations with commas',
    'Лише літери, переклади розділяй комами', 'Tylko litery, tłumaczenia oddzielaj przecinkami',
    'Solo letras; separa las traducciones con comas', 'Nur Buchstaben, Übersetzungen mit Kommas trennen',
    'Solo lettere, separa le traduzioni con virgole', 'Só letras; separa as traduções com vírgulas',
    'Lettres uniquement, sépare les traductions par des virgules')
add('word_categories_label', 'Categories', 'Категорії', 'Kategorie', 'Categorías', 'Kategorien', 'Categorie',
    'Categorias', 'Catégories')
add('word_categories_add', 'Add', 'Додати', 'Dodaj', 'Añadir', 'Hinzufügen', 'Aggiungi', 'Adicionar', 'Ajouter')
add('word_blacklist_note', 'Skip this word in training', 'Пропускати це слово в тренуванні',
    'Pomijaj to słowo w treningu', 'Omitir esta palabra en el entrenamiento', 'Dieses Wort im Training überspringen',
    "Salta questa parola nell'allenamento", 'Ignorar esta palavra no treino', "Ignorer ce mot à l'entraînement")
add('word_level_label', 'Knowledge level', 'Рівень знання', 'Poziom znajomości', 'Nivel de conocimiento',
    'Wissensstand', 'Livello di conoscenza', 'Nível de conhecimento', 'Niveau de connaissance')
add('word_level_reset', 'Reset', 'Скинути', 'Resetuj', 'Restablecer', 'Zurücksetzen', 'Azzera', 'Repor',
    'Réinitialiser')
add('word_save', 'Save word', 'Зберегти слово', 'Zapisz słowo', 'Guardar palabra', 'Wort speichern', 'Salva parola',
    'Guardar palavra', 'Enregistrer le mot')
add('word_delete', 'Delete word', 'Видалити слово', 'Usuń słowo', 'Eliminar palabra', 'Wort löschen',
    'Elimina parola', 'Eliminar palavra', 'Supprimer le mot')
add('word_delete_title', 'Delete this word?', 'Видалити це слово?', 'Usunąć to słowo?', '¿Eliminar esta palabra?',
    'Dieses Wort löschen?', 'Eliminare questa parola?', 'Eliminar esta palavra?', 'Supprimer ce mot ?')
add('word_delete_message', 'It will be removed from your dictionary and all categories.',
    'Його буде видалено зі словника й усіх категорій.', 'Zostanie usunięte ze słownika i wszystkich kategorii.',
    'Se quitará de tu diccionario y de todas las categorías.',
    'Es wird aus deinem Wörterbuch und allen Kategorien entfernt.',
    'Verrà rimossa dal tuo dizionario e da tutte le categorie.',
    'Será removida do teu dicionário e de todas as categorias.',
    'Il sera retiré de ton dictionnaire et de toutes les catégories.')
add('word_delete_button', 'Delete', 'Видалити', 'Usuń', 'Eliminar', 'Löschen', 'Elimina', 'Eliminar', 'Supprimer')
add('word_error_load', 'Couldn’t open the word', 'Не вдалося відкрити слово', 'Nie udało się otworzyć słowa',
    'No se pudo abrir la palabra', 'Wort konnte nicht geöffnet werden', 'Impossibile aprire la parola',
    'Não foi possível abrir a palavra', "Impossible d'ouvrir le mot")
add('word_error_save', 'Couldn’t save the word', 'Не вдалося зберегти слово', 'Nie udało się zapisać słowa',
    'No se pudo guardar la palabra', 'Wort konnte nicht gespeichert werden', 'Impossibile salvare la parola',
    'Não foi possível guardar a palavra', "Impossible d'enregistrer le mot")
add('word_error_delete', 'Couldn’t delete the word', 'Не вдалося видалити слово', 'Nie udało się usunąć słowa',
    'No se pudo eliminar la palabra', 'Wort konnte nicht gelöscht werden', 'Impossibile eliminare la parola',
    'Não foi possível eliminar a palavra', 'Impossible de supprimer le mot')

# ---------- Add to categories ----------
add('categories_sheet_title', 'Add to categories', 'Додати до категорій', 'Dodaj do kategorii', 'Añadir a categorías',
    'Zu Kategorien hinzufügen', 'Aggiungi alle categorie', 'Adicionar a categorias', 'Ajouter aux catégories')
add('categories_sheet_empty', 'No categories yet. Create one in Categories.',
    'Категорій ще немає. Створи першу у вкладці «Категорії».',
    'Nie ma jeszcze kategorii. Utwórz pierwszą w zakładce Kategorie.',
    'Aún no hay categorías. Crea la primera en Categorías.',
    'Noch keine Kategorien. Erstelle die erste unter Kategorien.',
    'Ancora nessuna categoria. Crea la prima in Categorie.',
    'Ainda não há categorias. Cria a primeira em Categorias.',
    "Aucune catégorie pour l'instant. Crée la première dans Catégories.")

# ---------- Categories ----------
add('categories_title', 'Categories', 'Категорії', 'Kategorie', 'Categorías', 'Kategorien', 'Categorie', 'Categorias',
    'Catégories')
add('categories_new', 'New category', 'Нова категорія', 'Nowa kategoria', 'Nueva categoría', 'Neue Kategorie',
    'Nuova categoria', 'Nova categoria', 'Nouvelle catégorie')
add('categories_new_hint', 'Group your words', 'Згрупуй свої слова', 'Pogrupuj swoje słowa', 'Agrupa tus palabras',
    'Gruppiere deine Wörter', 'Raggruppa le tue parole', 'Agrupa as tuas palavras', 'Regroupe tes mots')

# ---------- Category ----------
add('category_edit', 'Edit category', 'Редагувати категорію', 'Edytuj kategorię', 'Editar categoría',
    'Kategorie bearbeiten', 'Modifica categoria', 'Editar categoria', 'Modifier la catégorie')
add('category_delete', 'Delete category', 'Видалити категорію', 'Usuń kategorię', 'Eliminar categoría',
    'Kategorie löschen', 'Elimina categoria', 'Eliminar categoria', 'Supprimer la catégorie')
add('category_add_word', 'Add word', 'Додати слово', 'Dodaj słowo', 'Añadir palabra', 'Wort hinzufügen',
    'Aggiungi parola', 'Adicionar palavra', 'Ajouter un mot')
add('category_empty', 'No words in this category yet', 'У цій категорії ще немає слів',
    'W tej kategorii nie ma jeszcze słów', 'Aún no hay palabras en esta categoría',
    'In dieser Kategorie gibt es noch keine Wörter', 'Ancora nessuna parola in questa categoria',
    'Ainda não há palavras nesta categoria', "Aucun mot dans cette catégorie pour l'instant")
add('category_error_save', 'Couldn’t save the category', 'Не вдалося зберегти категорію',
    'Nie udało się zapisać kategorii', 'No se pudo guardar la categoría', 'Kategorie konnte nicht gespeichert werden',
    'Impossibile salvare la categoria', 'Não foi possível guardar a categoria', "Impossible d'enregistrer la catégorie")
add('category_error_delete', 'Couldn’t delete the category', 'Не вдалося видалити категорію',
    'Nie udało się usunąć kategorii', 'No se pudo eliminar la categoría', 'Kategorie konnte nicht gelöscht werden',
    'Impossibile eliminare la categoria', 'Não foi possível eliminar a categoria', 'Impossible de supprimer la catégorie')

# ---------- Category name ----------
add('category_name_label', 'Name', 'Назва', 'Nazwa', 'Nombre', 'Name', 'Nome', 'Nome', 'Nom')
add('category_name_hint', 'e.g. Travel', 'напр. Подорожі', 'np. Podróże', 'p. ej. Viajes', 'z. B. Reisen',
    'es. Viaggi', 'p. ex. Viagens', 'p. ex. Voyages')
add('category_save', 'Save', 'Зберегти', 'Zapisz', 'Guardar', 'Speichern', 'Salva', 'Guardar', 'Enregistrer')
add('category_error_empty', 'Enter a name', 'Введи назву', 'Wpisz nazwę', 'Escribe un nombre', 'Gib einen Namen ein',
    'Inserisci un nome', 'Escreve um nome', 'Saisis un nom')
add('category_error_chars', 'Letters, numbers and _-/\\| only', 'Лише літери, цифри та _-/\\|',
    'Tylko litery, cyfry i _-/\\|', 'Solo letras, números y _-/\\|', 'Nur Buchstaben, Ziffern und _-/\\|',
    'Solo lettere, numeri e _-/\\|', 'Só letras, números e _-/\\|', 'Lettres, chiffres et _-/\\| uniquement')
add('category_error_exists', 'A category with this name already exists', 'Категорія з такою назвою вже є',
    'Kategoria o tej nazwie już istnieje', 'Ya existe una categoría con este nombre',
    'Eine Kategorie mit diesem Namen gibt es schon', 'Esiste già una categoria con questo nome',
    'Já existe uma categoria com este nome', 'Une catégorie portant ce nom existe déjà')

# ---------- Delete category ----------
add('category_delete_title', 'Delete this category?', 'Видалити цю категорію?', 'Usunąć tę kategorię?',
    '¿Eliminar esta categoría?', 'Diese Kategorie löschen?', 'Eliminare questa categoria?',
    'Eliminar esta categoria?', 'Supprimer cette catégorie ?')
add('category_delete_message', 'Words stay in your dictionary — only the category is removed.',
    'Слова лишаться у словнику — видалиться лише категорія.',
    'Słowa zostaną w słowniku — usunięta zostanie tylko kategoria.',
    'Las palabras se quedan en tu diccionario; solo se elimina la categoría.',
    'Die Wörter bleiben in deinem Wörterbuch – nur die Kategorie wird entfernt.',
    'Le parole restano nel tuo dizionario: viene rimossa solo la categoria.',
    'As palavras ficam no teu dicionário — só a categoria é removida.',
    'Les mots restent dans ton dictionnaire : seule la catégorie est supprimée.')
add('category_delete_button', 'Delete', 'Видалити', 'Usuń', 'Eliminar', 'Löschen', 'Elimina', 'Eliminar', 'Supprimer')

# ---------- Counters (plurals) ----------
add_plural('plural_categories',
           en=('%d category', '%d categories'),
           uk=('%d категорія', '%d категорії', '%d категорій', '%d категорії'),
           pl=('%d kategoria', '%d kategorie', '%d kategorii', '%d kategorii'),
           es=('%d categoría', '%d categorías', '%d categorías'),
           de=('%d Kategorie', '%d Kategorien'),
           it=('%d categoria', '%d categorie', '%d categorie'),
           pt=('%d categoria', '%d categorias', '%d categorias'),
           fr=('%d catégorie', '%d catégories', '%d catégories'))
add_plural('plural_words',
           en=('%d word', '%d words'),
           uk=('%d слово', '%d слова', '%d слів', '%d слова'),
           pl=('%d słowo', '%d słowa', '%d słów', '%d słowa'),
           es=('%d palabra', '%d palabras', '%d palabras'),
           de=('%d Wort', '%d Wörter'),
           it=('%d parola', '%d parole', '%d parole'),
           pt=('%d palavra', '%d palavras', '%d palavras'),
           fr=('%d mot', '%d mots', '%d mots'))
add_plural('plural_words_match',
           en=('%d word matches these settings', '%d words match these settings'),
           uk=('%d слово відповідає налаштуванням', '%d слова відповідають налаштуванням',
               '%d слів відповідають налаштуванням', '%d слова відповідають налаштуванням'),
           pl=('%d słowo pasuje do ustawień', '%d słowa pasują do ustawień', '%d słów pasuje do ustawień',
               '%d słowa pasuje do ustawień'),
           es=('%d palabra coincide con estos ajustes', '%d palabras coinciden con estos ajustes',
               '%d palabras coinciden con estos ajustes'),
           de=('%d Wort passt zu diesen Einstellungen', '%d Wörter passen zu diesen Einstellungen'),
           it=('%d parola corrisponde a queste impostazioni', '%d parole corrispondono a queste impostazioni',
               '%d parole corrispondono a queste impostazioni'),
           pt=('%d palavra corresponde a estas definições', '%d palavras correspondem a estas definições',
               '%d palavras correspondem a estas definições'),
           fr=('%d mot correspond à ces réglages', '%d mots correspondent à ces réglages',
               '%d mots correspondent à ces réglages'))

# ---------- Session setup ----------
add('setup_title_start', 'New session', 'Нова сесія', 'Nowa sesja', 'Nueva sesión', 'Neue Session', 'Nuova sessione',
    'Nova sessão', 'Nouvelle session')
add('setup_title_menu', 'Training mode', 'Режим тренування', 'Tryb treningu', 'Modo de entrenamiento',
    'Trainingsmodus', 'Modalità allenamento', 'Modo de treino', "Mode d'entraînement")
add('setup_difficulty', 'Difficulty', 'Складність', 'Poziom trudności', 'Dificultad', 'Schwierigkeit', 'Difficoltà',
    'Dificuldade', 'Difficulté')
add('setup_easy_note', 'Choose the translation or build it from letters. English → %s.',
    'Обирай переклад або складай його з літер. English → %s.',
    'Wybierz tłumaczenie albo ułóż je z liter. English → %s.',
    'Elige la traducción o fórmala con letras. English → %s.',
    'Wähle die Übersetzung oder setze sie aus Buchstaben zusammen. English → %s.',
    'Scegli la traduzione o componila con le lettere. English → %s.',
    'Escolhe a tradução ou forma-a com letras. English → %s.',
    'Choisis la traduction ou compose-la avec des lettres. English → %s.')
add('setup_hard_note', 'Type words without hints, both directions. Weaker words come up more often.',
    'Вводь слова без підказок, в обидва боки. Слабші слова трапляються частіше.',
    'Wpisuj słowa bez podpowiedzi, w obie strony. Słabsze słowa pojawiają się częściej.',
    'Escribe las palabras sin pistas, en ambas direcciones. Las más flojas salen más a menudo.',
    'Tippe Wörter ohne Hinweise, in beide Richtungen. Schwächere Wörter kommen öfter dran.',
    'Scrivi le parole senza suggerimenti, in entrambe le direzioni. Le più deboli escono più spesso.',
    'Escreve as palavras sem dicas, nos dois sentidos. As mais fracas aparecem mais vezes.',
    'Tape les mots sans indices, dans les deux sens. Les mots plus faibles reviennent plus souvent.')
add('setup_duration', 'Duration', 'Тривалість', 'Czas trwania', 'Duración', 'Dauer', 'Durata', 'Duração', 'Durée')
add('setup_minutes', '%d min', '%d хв', '%d min', '%d min', '%d Min.', '%d min', '%d min', '%d min')
add('setup_limit_mistakes', 'Limit mistakes', 'Обмежити помилки', 'Limit błędów', 'Limitar errores',
    'Fehler begrenzen', 'Limita gli errori', 'Limitar erros', 'Limiter les erreurs')
add('setup_words', 'Words', 'Слова', 'Słowa', 'Palabras', 'Wörter', 'Parole', 'Palavras', 'Mots')
add('setup_words_all', 'All words', 'Усі слова', 'Wszystkie słowa', 'Todas las palabras', 'Alle Wörter',
    'Tutte le parole', 'Todas as palavras', 'Tous les mots')
add('setup_include_blacklist', 'Include blacklisted words', 'Додати слова з чорного списку',
    'Uwzględnij słowa z czarnej listy', 'Incluir palabras de la lista negra', 'Wörter der Sperrliste einbeziehen',
    'Includi le parole della lista nera', 'Incluir palavras da lista negra', 'Inclure les mots de la liste noire')
add('setup_categories_note', 'None selected — all words are used.', 'Нічого не вибрано — беруться всі слова.',
    'Nic nie wybrano — używane są wszystkie słowa.', 'Sin selección: se usan todas las palabras.',
    'Nichts ausgewählt – alle Wörter werden verwendet.', 'Nessuna selezione: si usano tutte le parole.',
    'Nada selecionado — são usadas todas as palavras.', 'Aucune sélection : tous les mots sont utilisés.')
add('setup_start', 'Start', 'Почати', 'Start', 'Empezar', 'Starten', 'Inizia', 'Começar', 'Commencer')
add('setup_save', 'Save settings', 'Зберегти налаштування', 'Zapisz ustawienia', 'Guardar ajustes',
    'Einstellungen speichern', 'Salva impostazioni', 'Guardar definições', 'Enregistrer les réglages')
add('setup_error_save', 'Couldn’t save the settings', 'Не вдалося зберегти налаштування',
    'Nie udało się zapisać ustawień', 'No se pudieron guardar los ajustes',
    'Einstellungen konnten nicht gespeichert werden', 'Impossibile salvare le impostazioni',
    'Não foi possível guardar as definições', "Impossible d'enregistrer les réglages")
add('setup_empty_title', 'No words match', 'Немає відповідних слів', 'Brak pasujących słów',
    'Ninguna palabra coincide', 'Keine passenden Wörter', 'Nessuna parola corrisponde',
    'Nenhuma palavra corresponde', 'Aucun mot ne correspond')
add('setup_empty_message', 'Try another word list, add categories or include blacklisted words.',
    'Спробуй інший список слів, додай категорії або слова з чорного списку.',
    'Wybierz inną listę słów, dodaj kategorie albo uwzględnij czarną listę.',
    'Prueba otra lista de palabras, añade categorías o incluye la lista negra.',
    'Probier eine andere Wortliste, füge Kategorien hinzu oder nimm die Sperrliste dazu.',
    "Prova un'altra lista di parole, aggiungi categorie o includi la lista nera.",
    'Experimenta outra lista de palavras, adiciona categorias ou inclui a lista negra.',
    'Essaie une autre liste de mots, ajoute des catégories ou inclue la liste noire.')

# ---------- Session ----------
add('session_word_number', 'Word %d', 'Слово %d', 'Słowo %d', 'Palabra %d', 'Wort %d', 'Parola %d', 'Palavra %d',
    'Mot %d')
add('session_caption_choose', 'Choose the correct translation', 'Обери правильний переклад',
    'Wybierz poprawne tłumaczenie', 'Elige la traducción correcta', 'Wähle die richtige Übersetzung',
    'Scegli la traduzione corretta', 'Escolhe a tradução correta', 'Choisis la bonne traduction')
add('session_caption_letters', 'Build the translation from letters', 'Склади переклад із літер',
    'Ułóż tłumaczenie z liter', 'Forma la traducción con letras', 'Setze die Übersetzung aus Buchstaben zusammen',
    'Componi la traduzione con le lettere', 'Forma a tradução com letras', 'Compose la traduction avec les lettres')
add('session_caption_type', 'Type the word in English', 'Введи слово англійською', 'Wpisz słowo po angielsku',
    'Escribe la palabra en inglés', 'Tippe das Wort auf Englisch', 'Scrivi la parola in inglese',
    'Escreve a palavra em inglês', 'Tape le mot en anglais')
add('session_type_hint', 'Type in English', 'Англійською', 'Po angielsku', 'En inglés', 'Auf Englisch', 'In inglese',
    'Em inglês', 'En anglais')
add('session_correct_answer', 'Correct answer:', 'Правильна відповідь:', 'Poprawna odpowiedź:', 'Respuesta correcta:',
    'Richtige Antwort:', 'Risposta corretta:', 'Resposta correta:', 'Bonne réponse :')
add('session_feedback_correct', 'Correct!', 'Правильно!', 'Dobrze!', '¡Correcto!', 'Richtig!', 'Giusto!', 'Certo!',
    'Correct !')
add('session_feedback_level_up', 'Correct! Level up for “%s”', 'Правильно! Рівень «%s» зріс',
    'Dobrze! Poziom „%s” w górę', '¡Correcto! «%s» sube de nivel', 'Richtig! „%s“ steigt eine Stufe auf',
    'Giusto! «%s» sale di livello', 'Certo! «%s» sobe de nível', 'Correct ! « %s » monte de niveau')
add('session_feedback_wrong', 'Not quite — it’s “%s”', 'Не зовсім — правильно «%s»', 'Nie całkiem — to „%s”',
    'Casi: es «%s»', 'Nicht ganz – richtig ist „%s“', 'Non proprio: è «%s»', 'Não exatamente — é «%s»',
    'Pas tout à fait : c’est « %s »')
add('session_check', 'Check', 'Перевірити', 'Sprawdź', 'Comprobar', 'Prüfen', 'Verifica', 'Verificar', 'Vérifier')
add('session_next', 'Next', 'Далі', 'Dalej', 'Siguiente', 'Weiter', 'Avanti', 'Seguinte', 'Suivant')
add('session_erase', 'Erase letter', 'Стерти літеру', 'Usuń literę', 'Borrar letra', 'Buchstaben löschen',
    'Cancella lettera', 'Apagar letra', 'Effacer la lettre')
add('session_error_start', 'Couldn’t start the session', 'Не вдалося почати сесію', 'Nie udało się rozpocząć sesji',
    'No se pudo empezar la sesión', 'Session konnte nicht gestartet werden', 'Impossibile avviare la sessione',
    'Não foi possível começar a sessão', 'Impossible de commencer la session')

# ---------- End session ----------
add('session_end_title', 'End this session?', 'Завершити сесію?', 'Zakończyć sesję?', '¿Terminar la sesión?',
    'Session beenden?', 'Terminare la sessione?', 'Terminar a sessão?', 'Terminer la session ?')
add('session_end_message', 'Answers so far are already saved to your progress.',
    'Відповіді, які вже є, збережено у твоєму прогресі.', 'Dotychczasowe odpowiedzi są już zapisane w postępach.',
    'Las respuestas hasta ahora ya se guardaron en tu progreso.',
    'Deine bisherigen Antworten sind schon im Fortschritt gespeichert.',
    'Le risposte date finora sono già salvate nei tuoi progressi.',
    'As respostas até agora já estão guardadas no teu progresso.',
    'Tes réponses jusqu’ici sont déjà enregistrées dans ta progression.')
add('session_end_keep', 'Keep going', 'Продовжити', 'Kontynuuj', 'Seguir', 'Weitermachen', 'Continua', 'Continuar',
    'Continuer')
add('session_end_button', 'End session', 'Завершити', 'Zakończ', 'Terminar', 'Beenden', 'Termina', 'Terminar',
    'Terminer')

# ---------- Results ----------
add('results_time', 'Time’s up', 'Час вийшов', 'Koniec czasu', 'Se acabó el tiempo', 'Zeit ist um', 'Tempo scaduto',
    'Acabou o tempo', 'Temps écoulé')
add('results_mistakes', 'Mistake limit reached', 'Досягнуто ліміту помилок', 'Osiągnięto limit błędów',
    'Límite de errores alcanzado', 'Fehlerlimit erreicht', 'Limite di errori raggiunto', 'Limite de erros atingido',
    "Limite d'erreurs atteinte")
add('results_ended', 'Session ended', 'Сесію завершено', 'Sesja zakończona', 'Sesión terminada', 'Session beendet',
    'Sessione terminata', 'Sessão terminada', 'Session terminée')
add('results_title', 'Nice work, %s!', 'Чудова робота, %s!', 'Dobra robota, %s!', '¡Buen trabajo, %s!',
    'Gut gemacht, %s!', 'Ottimo lavoro, %s!', 'Bom trabalho, %s!', 'Beau travail, %s !')
add('results_accuracy', 'accuracy', 'точність', 'trafność', 'precisión', 'Genauigkeit', 'precisione', 'precisão',
    'précision')
add('results_words_selected', 'Words selected', 'Слів вибрано', 'Wybrane słowa', 'Palabras elegidas',
    'Ausgewählte Wörter', 'Parole scelte', 'Palavras escolhidas', 'Mots choisis')
add('results_words_used', 'Words used', 'Слів пройдено', 'Użyte słowa', 'Palabras usadas', 'Geübte Wörter',
    'Parole usate', 'Palavras usadas', 'Mots utilisés')
add('results_correct', 'Correct', 'Правильно', 'Poprawne', 'Correctas', 'Richtig', 'Giuste', 'Certas', 'Justes')
add('results_wrong', 'Wrong', 'Помилки', 'Błędne', 'Incorrectas', 'Falsch', 'Sbagliate', 'Erradas', 'Fausses')
add('results_again', 'Another session', 'Ще одна сесія', 'Kolejna sesja', 'Otra sesión', 'Noch eine Session',
    "Un'altra sessione", 'Outra sessão', 'Une autre session')
add('results_back', 'Back to dictionary', 'До словника', 'Wróć do słownika', 'Volver al diccionario',
    'Zurück zum Wörterbuch', 'Torna al dizionario', 'Voltar ao dicionário', 'Retour au dictionnaire')


def escape(value):
    return (value.replace('\\', '\\\\').replace('&', '&amp;').replace('<', '&lt;').replace('>', '&gt;')
            .replace("'", "\\'").replace('"', '\\"'))


def main():
    keys = [k for _, ks in SECTIONS for k in ks]
    missing = [k for k in keys if k not in S] + [k for k in S if k not in keys]
    assert not missing, 'keys without section/value: %s' % missing
    plural_keys = [k for _, ks in PLURAL_SECTIONS for k in ks]
    missing = [k for k in plural_keys if k not in P] + [k for k in P if k not in plural_keys]
    assert not missing, 'plurals without section/value: %s' % missing
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
        for title, section_keys in PLURAL_SECTIONS:
            lines.append('')
            lines.append('    <!--  %s  -->' % title)
            for key in section_keys:
                items = P[key][lang]
                assert list(items) == PLURAL_QUANTITIES[lang] and all(items.values()), (key, lang)
                lines.append('    <plurals name="%s">' % key)
                for quantity, value in items.items():
                    lines.append('        <item quantity="%s">%s</item>' % (quantity, escape(value)))
                lines.append('    </plurals>')
        lines.append('</resources>')
        with open(os.path.join(folder, 'strings_v2.xml'), 'w', encoding='utf-8', newline='\n') as f:
            f.write('\n'.join(lines) + '\n')
    print('%d keys + %d plurals x %d languages' % (len(keys), len(plural_keys), len(LANGS)))


if __name__ == '__main__':
    main()
