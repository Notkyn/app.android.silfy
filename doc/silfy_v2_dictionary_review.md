# Silfy 2.0 — переклади словника: звіт для перевірки

Словник за замовчуванням (`app/src/main/assets/words/*.json`, 2731 слів) перекладено на pl, es, de, it, pt (європейська), fr.

## Правила перекладу

- Перекладається англійське слово; український варіант лише підказує значення й частину мови.
- Переважно один варіант, 2–3 через кому лише для справді багатозначних слів.
- Без артиклів. Німецькі іменники з великої, решта з малої.
- Прикметники в чоловічому роді, дієслова в інфінітиві.
- Португальська — європейська, за реформою орфографії AO90 (`atividade`, `ótimo`, `autocarro`, `comboio`, `telemóvel`).
- Цифр і знаків `!` у перекладах немає (їх не пропускає валідація при редагуванні слова). Дефіс дозволено (`guarda-chuva`, `après-midi`).

## Категорії

| key | en | uk | pl | es | de | it | pt | fr |
|---|---|---|---|---|---|---|---|---|
| sport | Sport | Спорт | Sport | Deporte | Sport | Sport | Desporto | Sport |
| city | City | Місто | Miasto | Ciudad | Stadt | Città | Cidade | Ville |
| animals | Animals | Тварини | Zwierzęta | Animales | Tiere | Animali | Animais | Animaux |
| job | Job | Робота | Praca | Trabajo | Arbeit | Lavoro | Trabalho | Travail |
| flat | Flat | Квартира | Mieszkanie | Piso | Wohnung | Appartamento | Apartamento | Appartement |
| food | Food | Їжа | Jedzenie | Comida | Essen | Cibo | Comida | Nourriture |

## 40 випадкових слів

| en | uk | pl | es | de | it | pt | fr |
|---|---|---|---|---|---|---|---|
| access | доступ | dostęp | acceso | Zugang | accesso | acesso | accès |
| afterwards | після | potem | después | danach | dopo | depois | ensuite |
| bread | хліб | chleb | pan | Brot | pane | pão | pain |
| budget | бюджет | budżet | presupuesto | Budget | bilancio | orçamento | budget |
| cancel | скасовувати | anulować | cancelar | absagen | annullare | cancelar | annuler |
| celebrity | знаменитість | celebryta | famoso | Berühmtheit | celebrità | celebridade | célébrité |
| characteristic | характеристика | cecha | característica | Merkmal | caratteristica | característica | caractéristique |
| civil | цивільна людина | cywilny | civil | zivil | civile | civil | civil |
| cold | холодний, холодно | zimny, zimno | frío | kalt | freddo | frio | froid |
| everybody | кожен | wszyscy | todos | jeder | tutti | todos | tout le monde |
| faith | віра | wiara | fe | Glaube | fede | fé | foi |
| flexible | гнучкий | elastyczny | flexible | flexibel | flessibile | flexível | flexible |
| fried | смажений | smażony | frito | gebraten | fritto | frito | frit |
| hello | привіт | cześć, dzień dobry | hola | hallo | ciao | olá | bonjour, salut |
| instructions | вказівки | instrukcje | instrucciones | Anweisungen | istruzioni | instruções | instructions |
| island | острів | wyspa | isla | Insel | isola | ilha | île |
| leg | нога | noga | pierna | Bein | gamba | perna | jambe |
| litre | літр | litr | litro | Liter | litro | litro | litre |
| machine | машина, механізм | maszyna | máquina | Maschine | macchina | máquina | machine |
| mistake | помилка | błąd | error | Fehler | errore | erro | erreur |
| nightmare | кошмар | koszmar | pesadilla | Albtraum | incubo | pesadelo | cauchemar |
| package | пакет | paczka | paquete | Paket | pacco | pacote | colis |
| pity | жаль | szkoda | lástima | schade | peccato | pena | dommage |
| popular | відомий, популярний | popularny | popular | beliebt | popolare | popular | populaire |
| rely | покладатися | polegać | confiar | sich verlassen | contare su | confiar | compter sur |
| remark | зауваження | uwaga | comentario | Bemerkung | osservazione | comentário | remarque |
| river | річка | rzeka | río | Fluss | fiume | rio | rivière |
| sale | продаж | sprzedaż | venta | Verkauf | vendita | venda | vente |
| satisfied | задоволений | zadowolony | satisfecho | zufrieden | soddisfatto | satisfeito | satisfait |
| sign | знак | znak | señal | Zeichen | segno | sinal | signe |
| slope | схил | zbocze | pendiente | Hang | pendio | encosta | pente |
| song | пісня | piosenka | canción | Lied | canzone | canção | chanson |
| soul | душа | dusza | alma | Seele | anima | alma | âme |
| storm | шторм | burza | tormenta | Sturm | tempesta | tempestade | tempête |
| survey | опитування | ankieta | encuesta | Umfrage | sondaggio | inquérito | enquête |
| theatre | театр | teatr | teatro | Theater | teatro | teatro | théâtre |
| tropical | тропічний | tropikalny | tropical | tropisch | tropicale | tropical | tropical |
| uncomfortable | незручно | niewygodny | incómodo | unbequem | scomodo | desconfortável | inconfortable |
| vitamin | вітаміни | witamina | vitamina | Vitamin | vitamina | vitamina | vitamine |
| website | інтернет-сайт | witryna internetowa | sitio web | Website | sito web | site | site web |

## Сумнівні українські переклади (не змінював)

Перекладаючи, помітив 141 місць, де український варіант неточний, має не ту частину мови чи помилку. Інші мови перекладено з англійської, тож їх ці помилки не зачепили. Що виправляти — вирішуєш ти.

**Критичні — виправлено:** `egg` → «яйце» (+ категорія «Їжа»), `noon` → «полудень», `wool` → «вовна», `angle` → «кут», `proud` → «гордий», `coast` → «узбережжя». Решту не змінював.

| en | зараз (uk) | пропозиція |
|---|---|---|
| accident | випадок | нещасний випадок, аварія |
| accurate | точні | точний |
| affect | вплив | впливати |
| afraid | боязкий | наляканий |
| aggressive | агресивно | агресивний |
| along | разом | уздовж |
| angle | ~~ангел~~ | ✅ виправлено: кут |
| apparently | безсумнівно | очевидно, мабуть |
| arise | виникають | виникати |
| arrangement | аранжування | домовленість |
| as | оскільки | як |
| ashamed | соромно | присоромлений |
| asleep | спить | сплячий |
| assignment | призначення | завдання |
| awesome | приголомшливо | приголомшливий |
| awful | жахливо | жахливий |
| band | пов’язка | гурт, смуга |
| basic | звичайний | основний, базовий |
| bond | довгострокове зобов’язання | облігація, зв’язок |
| bother | турбувати(ся) | турбувати (дужки не пройдуть валідацію при редагуванні) |
| bunch | група | в’язка, купа |
| calm | спокійно | спокійний |
| care | турбуватись | турбота, піклуватися |
| careful | обережно | обережний |
| chairman | головний | голова (зборів) |
| chance | спроба | шанс |
| choice | вибирати | вибір |
| civil | цивільна людина | цивільний, громадянський |
| coast | ~~пляж~~ | ✅ виправлено: узбережжя |
| command | веління | наказ, команда |
| complex | складні | складний |
| concentrate | концентрація | зосереджуватися |
| congratulations! | вітання! | вітаю (знак оклику не пройде валідацію при редагуванні) |
| consist | складаються | складатися |
| cooker | газова плита | плита |
| council | нарада | рада |
| county | дрібномаєтне дворянство | округ, графство |
| couple | декілька | пара |
| crucial | вирішальне значення | вирішальний |
| cupboard | комод | шафа |
| curly | кучеряве | кучерявий |
| declare | виголосити | оголошувати, заявляти |
| defend | оборонятись | захищати |
| deliberate | навмисне | навмисний |
| depend | залежить | залежати |
| disagree | не згоден | не погоджуватися |
| disappear | зник | зникати |
| dislike | не подобається | не любити |
| domestic | вітчизняні | домашній, внутрішній |
| duty | борг | обов’язок |
| egg | ~~яйце їжа/food]~~ | ✅ виправлено: яйце (зламаний запис; категорія «Їжа» загубилась) |
| earring | сережки | сережка |
| emotion | емоції | емоція |
| engaged | заангажований | заручений, зайнятий |
| ensure | забезпечує | забезпечувати |
| equal | рівні | рівний |
| estimate | кошторис | оцінка |
| everybody | кожен | усі |
| exciting | чудовий | захопливий |
| fast | швидко | швидкий, швидко |
| fast food | некорисна їжа | фастфуд |
| female | жіноча стать | жінка, жіночий |
| float | поплавець | плавати (на поверхні) |
| folk | народні | народний |
| fortune | доля | статок, удача |
| found | заснувати | засновувати |
| frozen | заморожені | заморожений |
| fun | веселий | розвага, весело |
| gain | збільшення | отримувати, здобувати |
| giant | величезний | велетень, гігантський |
| guidebook | інструкція | путівник |
| impossible | неможливо | неможливий |
| incredible | неймовірно | неймовірний |
| insight | в полі зору | розуміння, проникливість |
| instance | екземпляр | приклад, випадок |
| intend | мають намір | мати намір |
| it | інформаційні технології | (англ. «it» тут — абревіатура IT; варто перейменувати на «IT» або прибрати, бо плутається із займенником) |
| labor | трудитися | праця |
| land | землі | земля, суходіл |
| large | величезний | великий |
| lay | лежати | класти |
| lip | губи | губа |
| loose | пухкий | вільний, розхитаний |
| major | майор | головний, значний |
| media | змі | ЗМІ |
| metal | металевий | метал |
| multiple | багаторазовий | численний, кілька |
| necessary | необхідно | необхідний |
| network | мережі | мережа |
| noon | ~~північ~~ | ✅ виправлено: полудень (зараз переклад протилежний за змістом) |
| numerous | численні | численний |
| occasionally | випадково | час від часу |
| occur | відбуваються | відбуватися |
| opposed | виступив проти | проти, налаштований проти |
| organization | організації | організація |
| pattern | принт | візерунок, зразок |
| percent | відсотків | відсоток |
| performance | представлення | виступ, вистава |
| personality | особистості | особистість |
| perspective | перспективи | перспектива, погляд |
| play | бавитись | грати, гратися |
| poetry | поезії | поезія |
| policy | політики | політика |
| point | вказувати на | точка, вказувати |
| prime | простий | головний, основний |
| proper | належне | належний |
| proud | ~~гардість~~ | ✅ виправлено: гордий (зараз помилка в слові) |
| puzzle | пазли | головоломка, пазл |
| reaction | реагувати | реакція |
| related | пов'язані | пов’язаний |
| relevant | актуальні | доречний, актуальний |
| remain | залишаються | залишатися |
| represent | відображає | представляти |
| reservation | резервація | бронювання |
| roll | рол | булочка, котити |
| rule | правила | правило |
| seem | здається | здаватися |
| separate | окремо | окремий |
| series | серії | серія, серіал |
| settle | розраховуватися | оселятися, владнати |
| similar | подібні | подібний |
| social | соціальні | соціальний |
| society | суспільства | суспільство |
| species | видів | вид (біол.) |
| specific | конкретні | конкретний |
| surface | поверхні | поверхня |
| surrounding | оточуючих | навколишній |
| technology | технології | технологія |
| tend | прагнути | мати схильність |
| textbook | друкований зошит | підручник |
| thunderstorm | грім | гроза |
| tidy | чистий | охайний |
| tour guide | путівник | гід, екскурсовод |
| traffic | транспорт | дорожній рух |
| uncomfortable | незручно | незручний |
| unfair | несправедливо | несправедливий |
| united | об'єднані | об’єднаний |
| vitamin | вітаміни | вітамін |
| whisper | пошепки | шепотіти |
| windy | вітряно | вітряний |
| wool | ~~бавовна~~ | ✅ виправлено: вовна (зараз інше значення: бавовна — це cotton) |
