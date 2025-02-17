<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!DOCTYPE html>
<html lang="ru">
<head>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Квест: Темный Лес</title>
    <link rel="stylesheet" href="styles/main.css">
</head>
<body>
<h1>Квест: Темный Лес</h1>


<div id="options-container">
    <form id="options-form">

        <c:if test="${stage == 'START'}">
            <p>Ты оказался в сердце густого, мрачного леса. Повсюду висят тяжелые туманные облака, приглушающие свет.
                Кроны деревьев образуют плотный потолок, через который не проникает солнце. Вдалеке слышны загадочные
                шорохи, и кажется, что за тобой кто-то наблюдает.</p>
            <p><strong>Ты:</strong> «Ну что ж, вперед в неизвестность. Кажется, других вариантов нет...»</p>
            <p>Что ты выберешь?</p>
            <button type="button" class="button" onclick="selectOption('LEFT')">Пойти влево</button>
            <button type="button" class="button" onclick="selectOption('RIGHT')">Пойти направо</button>
            <div class="image-container">
                <img src="images/forestTrace.jpg" alt="Мрачный лес">
            </div>
        </c:if>

        <c:if test="${stage == 'LEFT'}">
            <p>Ты встречаешь дровосека, который отдыхает, сидя на старом пне. У него улыбка на лице, и он приветливо
                машет тебе рукой.</p>
            <p><strong>Дровосек:</strong> «Привет, путник! Заблудился? Не бойся, я помогу найти путь. Но для этого тебе
                нужно доказать, что ты не простой гуляка. Как насчет загадки?»</p>
            <p><strong>Ты:</strong> «Ну, почему бы и нет. Спрашивай!»</p>
            <p><strong>Дровосек:</strong> «Вот моя загадка:
                <em>Что всегда впереди, но никогда не может быть достигнуто?»</em></p>
            <button type="button" class="button" onclick="selectOption('ANSWER_CORRECT')">Будущее</button>
            <button type="button" class="button" onclick="selectOption('ANSWER_WRONG')">Вчерашний день</button>
            <div class="image-container">
                <img src="images/loc1.png" alt="Мрачный лес">
            </div>
        </c:if>

        <c:if test="${stage == 'ANSWER_CORRECT'}">
            <p><strong>Дровосек:</strong> «Верно! Ты умен, путник. Вот тропа, которая выведет тебя из леса. Береги
                себя!»</p>
            <button type="button" class="button" onclick="selectOption('NEW_PATH')">Продолжить путь</button>
        </c:if>

        <c:if test="${stage == 'ANSWER_WRONG'}">
            <p><strong>Дровосек:</strong> «Эх, неверно... Ну что ж, дам тебе еще один шанс, но на этот раз ты должен мне
                помочь с работой.»</p>
            <button type="button" class="button" onclick="selectOption('WORK')">Согласиться помочь</button>
        </c:if>

        <c:if test="${stage == 'WORK'}">
            <p>Дровосек протягивает тебе старую мотыгу и кивает в сторону небольшой поляны.</p>
            <p><strong>Дровосек:</strong> «Здесь заросла земля. Если расчистишь её, я помогу тебе. Может, и тебе это на
                пользу пойдет.»</p>
            <p>Ты берешь инструмент и начинаешь работать. Корни деревьев, камни и старая, твердая земля затрудняют
                работу, но спустя некоторое время ты замечаешь что-то странное. Среди корней блестит кусочек
                металла.</p>
            <button type="button" class="button" onclick="selectOption('KEEP_KEY')">Поднять ключ и оставить себе
            </button>
            <button type="button" class="button" onclick="selectOption('GIVE_KEY')">Отдать ключ дровосеку</button>
        </c:if>

        <c:if test="${stage == 'NEW_PATH'}">
            <p>Ты идешь по узкой тропинке, окруженной высокими, мрачными деревьями. Вдруг впереди блеснул золотой свет.
                Ты подходишь ближе и видишь старинный сундук. Его поверхность покрыта причудливым узором из лоз и звезд,
                а в замке мерцает слабый голубой свет, словно внутри заключена сама магия.</p>
            <p><strong>Сундук выглядит загадочно, маня своей тайной. Откроешь ли ты его?</strong></p>
            <button type="button" class="button" onclick="selectOption('OPEN_CHEST')">Открыть сундук</button>
            <button type="button" class="button" onclick="selectOption('IGNORE_CHEST')">Не рисковать, пойти дальше
            </button>
            <div class="image-container">
                <img src="images/keys.png" alt="Мрачный лес">
            </div>
        </c:if>

        <c:if test="${stage == 'OPEN_CHEST'}">
            <p>Сундук открывается с мягким щелчком, и изнутри вырывается яркий голубой свет. Ты чувствуешь, как время
                замедляется, и все вокруг окутывает туман.</p>
            <p><strong>Ты:</strong> «Что это за магия? Почему все стало таким... другим?»</p>
            <p>Внезапно ты осознаешь, что находишься там же, где начался твой путь. Туман расступается, и знакомая сцена
                возникает перед тобой. Кажется, время повернулось вспять.</p>
            <p>Ты понимаешь, что оказался там где и начал свой путь.</p>
            <button type="button" class="button" onclick="selectOption('LEFT')">Пойти влево</button>
            <button type="button" class="button" onclick="selectOption('RIGHT')">Пойти направо</button>
            <div class="image-container">
                <img src="images/forestTrace.jpg" alt="Мрачный лес">
            </div>
        </c:if>

        <c:if test="${stage == 'IGNORE_CHEST'}">
            <p>Ты бросаешь последний взгляд на загадочный сундук и продолжаешь путь. Что бы там ни было, риск кажется
                слишком большим.</p>
            <p>Тропинка приводит тебя к выходу из леса. За пределами деревьев ты видишь свет и чувствуешь, как страх и
                мрак остаются позади. Твое приключение подошло к концу.</p>
            <button type="button" class="button" onclick="selectOption('WIN')">Выйти из леса</button>
        </c:if>

        <c:if test="${stage == 'KEEP_KEY'}">
            <p>Ты поднимаешь ключ, и сердце подсказывает, что это важная находка. Дровосек замечает, как ты что-то
                убираешь в карман, но ничего не говорит. Работа закончена, и он разрешает тебе пройти дальше.</p>
            <p><strong>Дровосек:</strong> Вот тропа, которая выведет тебя из леса. Береги себя!</p>
            <button type="button" class="button" onclick="selectOption('NEW_PATH')">Продолжить путь</button>
        </c:if>

        <c:if test="${stage == 'GIVE_KEY'}">
            <p>Ты показываешь ключ дровосеку. Он берет его, внимательно осматривает и тихо говорит: «Этот ключ слишком
                стар, чтобы быть от чего-то важного. Забери его себе, может, пригодится.»</p>
            <p>Ты убираешь ключ в карман, чувствуя, что он откроет перед тобой нечто необычное.</p>
            <button type="button" class="button" onclick="selectOption('NEW_PATH')">Продолжить путь</button>
        </c:if>

        <c:if test="${stage == 'RIGHT'}">
            <p>Ты идешь направо по старой, заросшей тропинке. Лес кажется все более зловещим, а туман густеет. Вскоре ты
                замечаешь впереди очертания старой хижины. Она выглядит заброшенной, но изнутри доносятся странные
                звуки.</p>
            <p><strong>Ты:</strong> «Что это за место? Возможно, стоит проверить, что внутри.»</p>
            <p>Что ты выберешь?</p>
            <button type="button" class="button" onclick="selectOption('ENTER_HOUSE')">Войти в хижину</button>
            <button type="button" class="button" onclick="selectOption('AVOID_HOUSE')">Обойти хижину стороной</button>
            <div class="image-container">
                <img src="images/house.png" alt="Мрачный лес">
            </div>
        </c:if>

        <c:if test="${stage == 'ENTER_HOUSE'}">
            <p>Ты осторожно открываешь скрипучую дверь хижины. Внутри темно, но на столе ты замечаешь старую книгу с
                необычной обложкой. На полу валяются обломки мебели, а в углу стоит приоткрытый массивный сундук,
                покрытый пылью.</p>
            <p><strong>Событие:</strong> Ты слышишь странный шепот, который, кажется, исходит из книги. Что будешь
                делать?</p>
            <button type="button" class="button" onclick="selectOption('READ_BOOK')">Прочитать книгу</button>
            <button type="button" class="button" onclick="selectOption('OPEN_CHEST')">Открыть сундук</button>
        </c:if>

        <c:if test="${stage == 'READ_BOOK'}">
            <p>Ты раскрываешь книгу и начинаешь читать. Слова на страницах словно мерцают, притягивая твой взгляд. Ты
                чувствуешь, как всё вокруг исчезает, и остаёшься наедине с историей, которую она рассказывает. Это
                древняя легенда о могущественном артефакте, спрятанном где-то в этом лесу.</p>
            <p><strong>Событие:</strong> Ты настолько погружаешься в чтение, что не замечаешь, как за окном раздаются
                хищные рычания. Внезапно дверь хижины с треском распахивается, и ты видишь волков, злобно скалящих зубы.
            </p>
            <p><strong>Что будешь делать?</strong></p>
            <button type="button" class="button" onclick="selectOption('DEFEND')">Схватить что-то, чтобы защититься
            </button>
            <button type="button" class="button" onclick="selectOption('ESCAPE')">Попробовать сбежать через окно
            </button>
        </c:if>

        <c:if test="${stage == 'ESCAPE'}">
            <p>Ты приземляешься на землю за хижиной, ощущая холод сырой травы. Волки, не успевшие добежать до тебя,
                вырываются через дверь, обнюхивая воздух. Ты понимаешь, что времени мало — они вот-вот учуют тебя!</p>
            <p><strong>Что ты предпримешь?</strong></p>
            <button type="button" class="button" onclick="selectOption('RUN_DEEP')">Бежать глубже в лес</button>
            <button type="button" class="button" onclick="selectOption('CLIMB_TREE')">Попытаться залезть на дерево
            </button>
        </c:if>

        <c:if test="${stage == 'RUN_DEEP'}">
            <p>Ты бросаешься вглубь леса, ветки хлещут по лицу, а сердце колотится так, словно готово выскочить из
                груди. Волки уже близко, их вой наполняет воздух. Но впереди ты видишь ручей — может, он поможет сбить
                твой запах?</p>
            <p><strong>Что ты сделаешь?</strong></p>
            <button type="button" class="button" onclick="selectOption('CROSS_STREAM')">Перепрыгнуть через ручей
            </button>
            <button type="button" class="button" onclick="selectOption('HIDE_BEHIND_LOG')">Спрятаться за упавшим
                деревом
            </button>
        </c:if>

        <c:if test="${stage == 'CLIMB_TREE'}">
            <p>Ты быстро находишь крепкое дерево и карабкаешься вверх, чувствуя, как когтистые лапы волков едва не
                задевают твои ноги. Ты находишь укрытие среди густых ветвей, надеясь, что хищники уйдут. Они рычат у
                основания дерева, но вскоре начинают расходиться.</p>
            <p><strong>Ты спасаешься, но нужно решить, куда двигаться дальше.</strong></p>
            <button type="button" class="button" onclick="selectOption('WAIT_UNTIL_SAFE')">Ждать, пока всё утихнет
            </button>
        </c:if>

        <c:if test="${stage == 'DEFEND'}">
            <p>Ты быстро оглядываешься по сторонам в поисках чего-нибудь, чем можно защититься. В темноте твоё внимание
                привлекают два предмета: старая кочерга у камина и стеклянная бутылка с мутной жидкостью на полу рядом с
                полкой.</p>
            <p><strong>Что ты выберешь?</strong></p>
            <button type="button" class="button" onclick="selectOption('USE_POKER')">Схватить кочергу</button>
            <button type="button" class="button" onclick="selectOption('USE_BOTTLE')">Схватить стеклянную бутылку
            </button>
        </c:if>

        <c:if test="${stage == 'AVOID_HOUSE'}">
            <p>Ты решаешь не рисковать и обходишь хижину стороной. Лес становится менее густым, и вскоре ты видишь свет
                впереди. Ты выходишь из леса, чувствуя облегчение.</p>
            <p><strong>Ты:</strong> «Может, это было мудрое решение. Кто знает, что могло скрываться в той хижине?»</p>
            <button type="button" class="button" onclick="selectOption('WIN')">Выйти из леса</button>
        </c:if>

        <c:if test="${stage == 'USE_BOTTLE'}">
            <p>Ты хватаешь стеклянную бутылку и поднимаешь её, готовясь отбиться. Один из волков прыгает на тебя, и ты
                размахиваешь бутылкой, но она разбивается в воздухе, не причиняя вреда зверю. Волки набрасываются на
                тебя, и всё погружается во мрак.</p>
            <p><strong>Конец игры:</strong> Ты погиб.</p>
            <button type="button" class="button" onclick="selectOption('START')">Играть снова</button>
            <button type="button" class="button" onclick="window.location.href='/start.jsp'">Выйти в меню</button>
        </c:if>

        <c:if test="${stage == 'USE_POKER'}">
            <p>Ты хватаешь старую кочергу и встаёшь в оборонительную позицию. Волк бросается на тебя первым, но ты
                наносишь сильный удар, отбивая нападение. Волки пятятся назад, выказывая нерешительность, и спустя
                мгновение убегают в лес, оставляя тебя одного.</p>
            <p>На полу, прямо под местом, где стоял сундук, ты замечаешь старую карту. Ты поднимаешь её и понимаешь, что
                она ведёт к выходу из леса.</p>
            <button type="button" class="button" onclick="selectOption('WIN')">следовать по тропе</button>
        </c:if>

        <c:if test="${stage == 'WAIT_UNTIL_SAFE'}">
            <p>Ты сидишь на ветке, стараясь не издать ни звука. Проходит время, и ты замечаешь, что лес постепенно
                затихает. Рычание и шаги волков больше не слышны.</p>
            <p>Ты внимательно осматриваешься. Кажется, они ушли. Спустившись с дерева, ты аккуратно оглядываешься, чтобы
                убедиться, что опасность миновала. Затем ты продолжаешь движение по тропе, всё ещё ощущая лёгкую дрожь
                от пережитого.</p>
            <button type="button" class="button" onclick="selectOption('WIN')">Следовать дальше по тропе</button>
        </c:if>

        <c:if test="${stage == 'THROUGH_FOREST'}">
            <p>Ты сталкиваешься с волком, его глаза горят. Это не просто волк, это больше что-то. Ты можешь:</p>
            <button type="button" class="button" onclick="selectOption('FIGHT_WOLF')">Сразиться с ним</button>
            <button type="button" class="button" onclick="selectOption('THROUGH_FOREST')">Бежать</button>
        </c:if>

        <c:if test="${stage == 'WIN'}">
            <p>Ты выходишь из леса, ощущая, как свет солнца мягко ложится на твоё лицо. Позади остаются мрачные тени
                деревьев, странные звуки и опасные испытания. Ты чувствуешь гордость за своё мужество и
                находчивость.</p>
            <p>В руках у тебя карта и загадочный ключ, который, кажется, скрывает ещё одну тайну. Но это уже другая
                история...</p>
            <p><strong>Поздравляем!</strong> Ты прошёл игру и выбрался из Таинственного леса. Победа!</p>
            <button type="button" class="button" onclick="selectOption('START')">Играть снова</button>
            <button type="button" class="button" onclick="window.location.href='/start.jsp'">Выйти в меню</button>
        </c:if>

    </form>
</div>

<div class="user-info">
    <p><strong>Имя:</strong> ${userName}</p>
    <p><strong>Количество прохождений:</strong> ${countGamesMysteriousForest}</p>
</div>
<script>
    function selectOption(action) {
        $.ajax({
            type: 'POST',
            url: '/mysteriousForest',
            data: {action: action},
            success: function () {
                location.reload(); // Перезагрузка страницы
            },
        });
    }
    function restart(action) {
        $.ajax({
            type: 'POST',
            url: '/startGameServlet',
            data: {action: action},
            success: function(data) {
                if (data.redirect) {
                    window.location.href = data.redirect;
                }
            }
        });
    }
</script>
</body>
</html>