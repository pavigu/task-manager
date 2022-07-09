# Менеджер задач

Бэкенд на Java для трекера задач. Программа отвечает за формирование модели данных для такого прототипа:

![Прототип менеджера задач](sketch.png "Прототип менеджера задач")

## Приветственный экран

Когда задач не добавлено, выводится приветственный экран.

<pre><code>[!] Список задач пуст

СОЗДАТЬ ЗАДАЧУ:
1 — простую
2 — с подзадачами
0 — выйти</code></pre>

### Меню «Создать задачу»

В меню доступно создание простой задачи или задачи с подзадачами.

<pre><code>[!] Список задач пуст

<span style="background:#214283">СОЗДАТЬ ЗАДАЧУ:
1 — простую
2 — с подзадачами</span>
0 — выйти</code></pre>

### Команда «Выйти»

Команда завершает программу.

<pre><code>[!] Список задач пуст

СОЗДАТЬ ЗАДАЧУ:
1 — простую
2 — с подзадачами
<span style="background:#214283">0 — выйти</span>
<span style="color:green">0</span>
<span style="background:#214283">Программа завершена</span></code></pre>

## Рабочая область

Если в памяти программы есть задачи, вместо приветственного экрана отображается рабочая область c секциями
«Запланированные», «Взятые на выполнение», «Завершённые» и списком доступных команд: вызвать меню задачи, вызвать меню
программы, создать задачу или завершить работу программы.

В каждой секции выводятся задачи соответствующего статуса: NEW в секции «Запланированные», IN_PROCESS — в секции «Взятые
на выполнение» или DONE в секции «Завершённые».

<pre><code><span style="background:#214283">ЗАПЛАНИРОВАННЫЕ</span>
id[1] Отправить презентацию Святославу (см. детали) | <span style="background:#214283">NEW</span>
id[2] Исправить баг с изменением пароля | <span style="background:#214283">NEW</span>

id[7] Создать страницу продукта | <span style="background:#214283">NEW</span>
  ↳id[8] Создать макет страницы | NEW
  ↳id[9] Подготовить тексты и изображения | NEW
  ↳id[10] Сверстать страницу | NEW

<span style="background:#214283">ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ</span>
id[4] Подготовить ТЗ | <span style="background:#214283">IN_PROCESS</span>
  ↳id[5] Написать текст ТЗ (см. детали) | IN_PROCESS
  ↳id[6] Собрать ресурсы в помощь (см. детали) | NEW

<span style="background:#214283">ЗАВЕРШЁННЫЕ</span>
id[3] Созвониться по видео с новенькими в команде | <span style="background:#214283">DONE</span>

Введите:
номер id[] — <span style="background:#214283">вызвать меню задачи</span>
m — <span style="background:#214283">вызвать меню программы</span>
t — <span style="background:#214283">создать задачу</span>
q — <span style="background:#214283">выйти</span></code></pre>

По номеру задачи вызывается её контекстное меню, команды в виде букв m, t, q вызывают соответствующие действия.

Если ввести что-то иное, программа укажет на ошибку ввода.

Предупреждение, если ввести словесную команду, которой не существует:

<pre><code><span style="background:#214283">Введите:</span>
номер id[] — вызвать меню задачи
m — вызвать меню программы
t — создать задачу
q — выйти
<span style="color:green">sdf</span>
<span style="background:#214283">[!] Ожидается ввод команды из предложенных</span>
Введите:
номер id[] — вызвать меню задачи
m — вызвать меню программы
t — создать задачу
q — выйти</code></pre>

Предупреждение, если ввести номер задачи, которой нет:
<pre><code>ЗАПЛАНИРОВАННЫЕ
id[<span style="background:#214283">1</span>] Отправить презентацию Святославу (см. детали) | NEW
id[<span style="background:#214283">2</span>] Исправить баг с изменением пароля | NEW
id[<span style="background:#214283">3</span>] Созвониться по видео с новенькими в команде | NEW

id[<span style="background:#214283">4</span>] Подготовить ТЗ | NEW
  ↳id[<span style="background:#214283">5</span>] Написать текст ТЗ (см. детали) | NEW
  ↳id[<span style="background:#214283">6</span>] Собрать ресурсы в помощь (см. детали) | NEW

id[<span style="background:#214283">7</span>] Создать страницу продукта | NEW
  ↳id[<span style="background:#214283">8</span>] Создать макет страницы | NEW
  ↳id[<span style="background:#214283">9</span>] Подготовить тексты и изображения | NEW
  ↳id[<span style="background:#214283">10</span>] Сверстать страницу | NEW

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
Нет задач такого типа

ЗАВЕРШЁННЫЕ
Нет задач такого типа

<span style="background:#214283">Введите:
номер id[] — вызвать меню задачи</span>
m — вызвать меню программы
t — создать задачу
q — выйти
<span style="color:green">99</span>
<span style="background:#214283">[!] Нет задачи с таким id[]</span>
Введите:
номер id[] — вызвать меню задачи
m — вызвать меню программы
t — создать задачу
q — выйти</code></pre>

Если ввести номер задачи, которая существует, программа покажет её контекстное меню.

## Контекстное меню задачи

Контекстное меню задачи выводит детали по задаче и доступные действия, в зависимости от её типа и статуса. Всего
предусмотрено 7 вариаций контекстного меню задачи.

### Контекстное меню для обычной задачи

Обычная задача — задача без подзадач.

Возможные статусы обычной задачи:
* NEW — добавлена, но не взята на выполнение
* IN_PROCESS — взята на выполнение, но не завершена
* DONE — завершена

Какие действия доступны в контекстном меню задачи, зависит от статуса задачи.

#### Контекстное меню для обычной задачи со статусом NEW

<pre><code>ЗАПЛАНИРОВАННЫЕ
<span style="background:#214283">id[1] Отправить презентацию Святославу (см. детали) | NEW</span>

id[4] Подготовить ТЗ | NEW
  ↳id[5] Написать текст ТЗ (см. детали) | NEW
  ↳id[6] Собрать ресурсы в помощь (см. детали) | NEW

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
Нет задач такого типа

ЗАВЕРШЁННЫЕ
Нет задач такого типа

<span style="background:#214283">Введите:
номер id[] — вызвать меню задачи</span>
m — вызвать меню программы
t — создать задачу
q — выйти
<span style="color:green">1</span>
МЕНЮ ЗАДАЧИ
id[1] Отправить презентацию Святославу | NEW
Детали: @svyat_svyat_svyat

<span style="background:#214283">ЧТО СДЕЛАТЬ С ЭТОЙ ЗАДАЧЕЙ?</span>
1 — добавить к ней подзадачу
2 — перенести в подзадачи к другой задаче
3 — удалить
4 — пометить взятой на выполнение
5 — пометить завершённой
0 — выйти из меню задачи</code></pre>

#### Контекстное меню для обычной задачи со статусом IN_PROCESS

<pre><code>ЗАПЛАНИРОВАННЫЕ
id[4] Подготовить ТЗ | NEW
  ↳id[5] Написать текст ТЗ (см. детали) | NEW
  ↳id[6] Собрать ресурсы в помощь (см. детали) | NEW

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
<span style="background:#214283">id[1] Отправить презентацию Святославу (см. детали) | IN_PROCESS</span>

ЗАВЕРШЁННЫЕ
Нет задач такого типа

<span style="background:#214283">Введите:
номер id[] — вызвать меню задачи</span>
m — вызвать меню программы
t — создать задачу
q — выйти
<span style="color:green">1</span>
МЕНЮ ЗАДАЧИ
id[1] Отправить презентацию Святославу | IN_PROCESS
Детали: @svyat_svyat_svyat

<span style="background:#214283">ЧТО СДЕЛАТЬ С ЭТОЙ ЗАДАЧЕЙ?</span>
1 — перенести в подзадачи к другой задаче
2 — удалить
3 — пометить завершённой
0 — выйти из меню задачи</code></pre>

#### Контекстное меню для обычной задачи со статусом DONE

<pre><code>ЗАПЛАНИРОВАННЫЕ
id[4] Подготовить ТЗ | NEW
  ↳id[5] Написать текст ТЗ (см. детали) | NEW
  ↳id[6] Собрать ресурсы в помощь (см. детали) | NEW

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
Нет задач такого типа

ЗАВЕРШЁННЫЕ
<span style="background:#214283">id[1] Отправить презентацию Святославу (см. детали) | DONE</span>

<span style="background:#214283">Введите:
номер id[] — вызвать меню задачи</span>
m — вызвать меню программы
t — создать задачу
q — выйти
<span style="color:green">1</span>
МЕНЮ ЗАДАЧИ
id[1] Отправить презентацию Святославу | DONE
Детали: @svyat_svyat_svyat

<span style="background:#214283">ЧТО СДЕЛАТЬ С ЭТОЙ ЗАДАЧЕЙ?</span>
1 — удалить
2 — вернуть задаче статус взятой на выполнение
0 — выйти из меню задачи</code></pre>

### Контекстное меню для задачи с подзадачами

Задача с её подзадачами с одной стороны является единой сущностью, с другой стороны каждая из подзадач ведёт себя похоже
на обычную задачу — можно вызвать контектное меню для подзадачи.

Статус родительской задачи зависит от статусов её подзадач: нельзя напрямую поменять статус родительской задачи, но
можно влиять на него, меняя статусы подзадач.

<table>
	<tbody>
		<tr>
			<td rowspan="2">№ ситуации</td><td colspan="3">Статус подзадач</td><td rowspan="2">Статус родительской задачи</td>
		</tr>
		<tr>
			<td>Подзадача 1</td><td>Подзадача 2</td><td>Подзадача 3</td>
		</tr>
		<tr>
			<td>1</td>
			<td><span class="console--inline">NEW</span></td>
			<td><span class="console--inline">NEW</span></td>
			<td><span class="console--inline">NEW</span></td>
			<td><span class="console--inline">NEW</span></td>
		</tr>
		<tr>
			<td>2</td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
			<td><span class="console--inline">NEW</span></td>
			<td><span class="console--inline">NEW</span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
		</tr>
		<tr>
			<td>3</td>
			<td><span class="console--inline">DONE</span></td>
			<td><span class="console--inline">NEW</span></td>
			<td><span class="console--inline">NEW</span></td>
			<td><span class="console--inline">NEW</span></td>
		</tr>
		<tr>
			<td>4</td>
			<td><span class="console--inline">NEW</span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
			<td><span class="console--inline">NEW</span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
		</tr>
		<tr>
			<td>5</td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
			<td><span class="console--inline">NEW</span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
		</tr>
		<tr>
			<td>6</td>
			<td><span class="console--inline">DONE</span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
			<td><span class="console--inline">NEW</span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
		</tr>
		<tr>
			<td>7</td>
			<td><span class="console--inline">NEW</span></td>
			<td><span class="console--inline">DONE</span></td>
			<td><span class="console--inline">NEW</span></td>
			<td><span class="console--inline">NEW</span></td>
		</tr>
		<tr>
			<td>8</td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
			<td><span class="console--inline">DONE</span></td>
			<td><span class="console--inline">NEW</span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
		</tr>
		<tr>
			<td>9</td>
			<td><span class="console--inline">DONE</span></td>
			<td><span class="console--inline">DONE</span></td>
			<td><span class="console--inline">NEW</span></td>
			<td><span class="console--inline">NEW</span></td>
		</tr>
		<tr>
			<td>10</td>
			<td><span class="console--inline">NEW</span></td>
			<td><span class="console--inline">NEW</span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
		</tr>
		<tr>
			<td>11</td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
			<td><span class="console--inline">NEW</span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
		</tr>
		<tr>
			<td>12</td>
			<td><span class="console--inline">DONE</span></td>
			<td><span class="console--inline">NEW</span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
		</tr>
		<tr>
			<td>13</td>
			<td><span class="console--inline">NEW</span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
		</tr>
		<tr>
			<td>14</td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
		</tr>
		<tr>
			<td>15</td>
			<td><span class="console--inline">DONE</span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
		</tr>
		<tr>
			<td>16</td>
			<td><span class="console--inline">NEW</span></td>
			<td><span class="console--inline">DONE</span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
		</tr>
		<tr>
			<td>17</td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
			<td><span class="console--inline">DONE</span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
		</tr>
		<tr>
			<td>18</td>
			<td><span class="console--inline">DONE</span></td>
			<td><span class="console--inline">DONE</span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
		</tr>
		<tr>
			<td>19</td>
			<td><span class="console--inline">NEW</span></td>
			<td><span class="console--inline">NEW</span></td>
			<td><span class="console--inline">DONE</span></td>
			<td><span class="console--inline">NEW</span></td>
		</tr>
		<tr>
			<td>20</td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
			<td><span class="console--inline">NEW</span></td>
			<td><span class="console--inline">DONE</span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
		</tr>
		<tr>
			<td>21</td>
			<td><span class="console--inline">DONE</span></td>
			<td><span class="console--inline">NEW</span></td>
			<td><span class="console--inline">DONE</span></td>
			<td><span class="console--inline">NEW</span></td>
		</tr>
		<tr>
			<td>22</td>
			<td><span class="console--inline">NEW</span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
			<td><span class="console--inline">DONE</span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
		</tr>
		<tr>
			<td>23</td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
			<td><span class="console--inline">DONE</span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
		</tr>
		<tr>
			<td>24</td>
			<td><span class="console--inline">DONE</span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
			<td><span class="console--inline">DONE</span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
		</tr>
		<tr>
			<td>25</td>
			<td><span class="console--inline">NEW</span></td>
			<td><span class="console--inline">DONE</span></td>
			<td><span class="console--inline">DONE</span></td>
			<td><span class="console--inline">NEW</span></td>
		</tr>
		<tr>
			<td>26</td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
			<td><span class="console--inline">DONE</span></td>
			<td><span class="console--inline">DONE</span></td>
			<td><span class="console--inline"><span class="console--inline">IN_PROCESS</span></span></td>
		</tr>
		<tr>
			<td>27</td>
			<td><span class="console--inline">DONE</span></td>
			<td><span class="console--inline">DONE</span></td>
			<td><span class="console--inline">DONE</span></td>
			<td><span class="console--inline">DONE</span></td>
		</tr>
	</tbody>
</table>

Из таблицы видно, что задача с подзадачами:
* имеет статус DONE только в случае такого статуса у всех её подзадач
* имеет статус IN_PROCESS, когда хотя бы одна из её подзадач имеет статус IN_PROCESS
* имеет статус NEW, когда нет ни единой подзадачи со статусом IN_PROCESS, и есть хотя бы одна подзадача со статусом NEW.

Так как статус задачи с подзадачами нельзя менять напрямую, контекстное меню задачи с подзадачами такое:

<pre><code>ЗАПЛАНИРОВАННЫЕ
<span style="background:#214283">id[4] Подготовить ТЗ | NEW</span>  
  ↳id[5] Написать текст ТЗ (см. детали) | NEW
  ↳id[6] Собрать ресурсы в помощь (см. детали) | NEW

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
Нет задач такого типа

ЗАВЕРШЁННЫЕ
id[1] Отправить презентацию Святославу (см. детали) | DONE

<span style="background:#214283">Введите:
номер id[] — вызвать меню задачи</span>
m — вызвать меню программы
t — создать задачу
q — выйти
<span style="color:green">4</span>
МЕНЮ ЗАДАЧИ
id[4] Подготовить ТЗ | NEW
Подзадачи (2):
  ↳id[5] Написать текст ТЗ (см. детали) | NEW
  ↳id[6] Собрать ресурсы в помощь (см. детали) | NEW

<span style="background:#214283">ЧТО СДЕЛАТЬ С ЭТОЙ ЗАДАЧЕЙ?</span>
1 — удалить вместе с подзадачами
2 — добавить подзадачу
0 — выйти из меню задачи</code></pre>

###Контекстное меню для подзадачи

Подзадача — обычная задача, которая имеет родительскую задачу.

Возможные статусы обычной задачи:
* NEW — добавлена, но не взята на выполнение
* IN_PROCESS — взята на выполнение, но не завершена
* DONE — завершена

Какие действия доступны в контекстном меню подзадачи, зависит от статуса подзадачи.

####Контекстное меню подзадачи со статусом NEW
<pre><code>ЗАПЛАНИРОВАННЫЕ
id[4] Подготовить ТЗ | NEW
  <span style="background:#214283">↳id[5] Написать текст ТЗ (см. детали) | NEW</span>
  ↳id[6] Собрать ресурсы в помощь (см. детали) | NEW

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
id[1] Отправить презентацию Святославу (см. детали) | IN_PROCESS

ЗАВЕРШЁННЫЕ
Нет задач такого типа

<span style="background:#214283">Введите:
номер id[] — вызвать меню задачи</span>
m — вызвать меню программы
t — создать задачу
q — выйти
<span style="color:green">5</span>
МЕНЮ ЗАДАЧИ
↳id[5] Написать текст ТЗ | NEW
Детали: Что сделать, требования, ресурсы, в каком виде нужен результат
Родительская задача: id[4] Подготовить ТЗ | NEW

<span style="background:#214283">ЧТО СДЕЛАТЬ С ПОДЗАДАЧЕЙ id[5]?</span>
1 — удалить
2 — пометить взятой на выполнение
3 — пометить завершённой
0 — выйти из меню задачи</code></pre>

####Контекстное меню подзадачи со статусом IN_PROCESS
<pre><code>ЗАПЛАНИРОВАННЫЕ
Нет задач такого типа

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
id[1] Отправить презентацию Святославу (см. детали) | IN_PROCESS

id[4] Подготовить ТЗ | IN_PROCESS
  <span style="background:#214283">↳id[5] Написать текст ТЗ (см. детали) | IN_PROCESS</span>
  ↳id[6] Собрать ресурсы в помощь (см. детали) | NEW

ЗАВЕРШЁННЫЕ
Нет задач такого типа

<span style="background:#214283">Введите:
номер id[] — вызвать меню задачи</span>
m — вызвать меню программы
t — создать задачу
q — выйти
<span style="color:green">5</span>
МЕНЮ ЗАДАЧИ
↳id[5] Написать текст ТЗ | IN_PROCESS
Детали: Что сделать, требования, ресурсы, в каком виде нужен результат
Родительская задача: id[4] Подготовить ТЗ | IN_PROCESS

<span style="background:#214283">ЧТО СДЕЛАТЬ С ПОДЗАДАЧЕЙ id[5]?</span>
1 — удалить
2 — пометить завершённой
0 — выйти из меню задачи</code></pre>

####Контекстное меню подзадачи со статусом DONE
<pre><code>ЗАПЛАНИРОВАННЫЕ
id[4] Подготовить ТЗ | NEW
  <span style="background:#214283">↳id[5] Написать текст ТЗ (см. детали) | DONE</span>
  ↳id[6] Собрать ресурсы в помощь (см. детали) | NEW

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
id[1] Отправить презентацию Святославу (см. детали) | IN_PROCESS

ЗАВЕРШЁННЫЕ
Нет задач такого типа

<span style="background:#214283">Введите:
номер id[] — вызвать меню задачи</span>
m — вызвать меню программы
t — создать задачу
q — выйти
<span style="color:green">5</span>
МЕНЮ ЗАДАЧИ
↳id[5] Написать текст ТЗ | DONE
Детали: Что сделать, требования, ресурсы, в каком виде нужен результат
Родительская задача: id[4] Подготовить ТЗ | NEW

<span style="background:#214283">ЧТО СДЕЛАТЬ С ПОДЗАДАЧЕЙ id[5]?</span>
1 — удалить
2 — вернуть задаче статус взятой на выполнение
0 — выйти из меню задачи
</code></pre>

##Действия, доступные для задач из их контекстного меню
###Команда «Удалить»
Доступна для задач всех типов и статусов. Удаляет задачу.

<pre><code>ЗАПЛАНИРОВАННЫЕ
id[4] Подготовить ТЗ | NEW
  ↳id[5] Написать текст ТЗ (см. детали) | DONE
  ↳id[6] Собрать ресурсы в помощь (см. детали) | NEW

<span style="background:#214283">ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
id[1] Отправить презентацию Святославу (см. детали) | IN_PROCESS</span>

ЗАВЕРШЁННЫЕ
Нет задач такого типа

<span style="background:#214283">Введите:
номер id[] — вызвать меню задачи</span>
m — вызвать меню программы
t — создать задачу
q — выйти
<span style="color:green">1</span>
МЕНЮ ЗАДАЧИ
id[1] Отправить презентацию Святославу | IN_PROCESS
Детали: @svyat_svyat_svyat

<span style="background:#214283">ЧТО СДЕЛАТЬ С ЭТОЙ ЗАДАЧЕЙ?</span>
1 — перенести в подзадачи к другой задаче
<span style="background:#214283">2 — удалить</span>
3 — пометить завершённой
0 — выйти из меню задачи
<span style="color:green">2</span>
УДАЛИТЬ
Задача id[1] «Отправить презентацию Святославу» удалена
Нажмите любую клавишу, чтобы продолжить

ЗАПЛАНИРОВАННЫЕ
id[4] Подготовить ТЗ | NEW
  ↳id[5] Написать текст ТЗ (см. детали) | DONE
  ↳id[6] Собрать ресурсы в помощь (см. детали) | NEW

<span style="background:#214283">ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ</span>
Нет задач такого типа

ЗАВЕРШЁННЫЕ
Нет задач такого типа

Введите:
номер id[] — вызвать меню задачи
m — вызвать меню программы
t — создать задачу
q — выйти</code></pre>

Задачи с подзадачами удаляются целиком со своими подзадачами.
<pre><code><span style="background:#214283">ЗАПЛАНИРОВАННЫЕ</span>
id[2] Исправить баг с изменением пароля | NEW

<span style="background:#214283">id[4] Подготовить ТЗ | NEW</span>
  ↳id[5] Написать текст ТЗ (см. детали) | NEW
  ↳id[6] Собрать ресурсы в помощь (см. детали) | NEW

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
Нет задач такого типа

ЗАВЕРШЁННЫЕ
Нет задач такого типа

<span style="background:#214283">Введите:
номер id[] — вызвать меню задачи</span>
m — вызвать меню программы
t — создать задачу
q — выйти
<span style="color:green">4</span>
МЕНЮ ЗАДАЧИ
id[4] Подготовить ТЗ | NEW
Подзадачи (2):
  ↳id[5] Написать текст ТЗ (см. детали) | NEW
  ↳id[6] Собрать ресурсы в помощь (см. детали) | NEW

<span style="background:#214283">ЧТО СДЕЛАТЬ С ЭТОЙ ЗАДАЧЕЙ?</span>
<span style="background:#214283">1 — удалить вместе с подзадачами</span>
2 — добавить подзадачу
0 — выйти из меню задачи
<span style="color:green">1</span>
УДАЛИТЬ ВМЕСТЕ С ПОДЗАДАЧАМИ
Удалить задачу id[4] «Подготовить ТЗ»  вместе с подзадачами?
<span style="background:#214283">1 — да,</span> 0 — нет
<span style="color:green">1</span>
Задача id[4] «Подготовить ТЗ» окончательно удалена со своими подзадачами
Нажмите любую клавишу, чтобы продолжить

<span style="background:#214283">ЗАПЛАНИРОВАННЫЕ</span>
id[2] Исправить баг с изменением пароля | NEW

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
Нет задач такого типа

ЗАВЕРШЁННЫЕ
Нет задач такого типа

Введите:
номер id[] — вызвать меню задачи
m — вызвать меню программы
t — создать задачу
q — выйти</code></pre>

Если удалить у задачи с подзадачами её последнюю подзадачу, то такая задача станет обычной.

<pre><code>ЗАПЛАНИРОВАННЫЕ
id[1] Отправить презентацию Святославу (см. детали) | NEW
id[2] Исправить баг с изменением пароля | NEW

id[4] Подготовить ТЗ | NEW
  <span style="background:#214283">↳id[5] Написать текст ТЗ (см. детали) | NEW</span>

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
Нет задач такого типа

ЗАВЕРШЁННЫЕ
Нет задач такого типа

<span style="background:#214283">Введите:
номер id[] — вызвать меню задачи</span>
m — вызвать меню программы
t — создать задачу
q — выйти
<span style="color:green">5</span>
МЕНЮ ЗАДАЧИ
↳id[5] Написать текст ТЗ | NEW
Детали: Что сделать, требования, ресурсы, в каком виде нужен результат
Родительская задача: id[4] Подготовить ТЗ | NEW

<span style="background:#214283">ЧТО СДЕЛАТЬ С ПОДЗАДАЧЕЙ id[5]?</span>
<span style="background:#214283">1 — удалить</span>
2 — пометить взятой на выполнение
3 — пометить завершённой
0 — выйти из меню задачи
<span style="color:green">1</span>
УДАЛИТЬ
id[4] Подготовить ТЗ | NEW
→ ↳id[5] Написать текст ТЗ (см.—детали) | NEW

Удалить подзадачу id[5]?
<span style="background:#214283">1 — да,</span> 0 — нет
<span style="color:green">1</span>
Подзадача ↳id[5] «Написать текст ТЗ» удалена
Нажмите любую клавишу, чтобы продолжить

ЗАПЛАНИРОВАННЫЕ
id[1] Отправить презентацию Святославу (см. детали) | NEW
id[2] Исправить баг с изменением пароля | NEW
<span style="background:#214283">id[4] Подготовить ТЗ | NEW</span>

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
Нет задач такого типа

ЗАВЕРШЁННЫЕ
Нет задач такого типа

Введите:
номер id[] — вызвать меню задачи
m — вызвать меню программы
t — создать задачу
q — выйти
</code></pre>

###Команда «Перенести в подзадачи к другой задаче»

Доступна для обычных задач со статусами NEW и IN_PROCESS.

Переносит задачу в подзадачи к другой задаче.

Если у родительской задачи уже есть подзадачи, выбранная задача добавляется в конец списка подзадач и меняет свой тип с обычной задачи на подзадачу.

<pre><code>ЗАПЛАНИРОВАННЫЕ
<span style="background:#214283">id[13] Сверстать продуктовую страницу | NEW</span>

id[4] Подготовить ТЗ | NEW
  ↳id[5] Написать текст ТЗ (см. детали) | NEW
  ↳id[6] Собрать ресурсы в помощь (см. детали) | NEW

<span style="background:#214283">id[7]</span> Создать страницу продукта | NEW
  ↳id[8] Создать макет страницы | NEW
  ↳id[9] Подготовить тексты и изображения | NEW

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
Нет задач такого типа

ЗАВЕРШЁННЫЕ
Нет задач такого типа

<span style="background:#214283">Введите:
номер id[] — вызвать меню задачи</span>
m — вызвать меню программы
t — создать задачу
q — выйти
<span style="color:green">13</span>
МЕНЮ ЗАДАЧИ
id[13] Сверстать продуктовую страницу | NEW

<span style="background:#214283">ЧТО СДЕЛАТЬ С ЭТОЙ ЗАДАЧЕЙ?</span>
1 — добавить к ней подзадачу
<span style="background:#214283">2 — перенести в подзадачи к другой задаче</span>
3 — удалить
4 — пометить взятой на выполнение
5 — пометить завершённой
0 — выйти из меню задачи
<span style="color:green">2</span>
ПЕРЕНЕСТИ В ПОДЗАДАЧИ К ДРУГОЙ ЗАДАЧЕ
id[13] Сверстать продуктовую страницу | NEW
↓
<span style="background:#214283">В подзадачи к какой задаче добавить:</span>
? id[4] Подготовить ТЗ | NEW
? <span style="background:#214283">id[7] Создать страницу продукта | NEW</span>
↑
<span style="background:#214283">Введите номер id[] задачи, в подзадачи к которой
добавить задачу</span> id[13] «Сверстать продуктовую страницу»,
или 0, чтобы вернуться назад:
<span style="color:green">7</span>
Задача перенесена в подзадачи
id[7] Создать страницу продукта | NEW
  ↳id[8] Создать макет страницы | NEW
  ↳id[9] Подготовить тексты и изображения | NEW
  ↳id[13] Сверстать продуктовую страницу | NEW — добавлено сейчас

Нажмите любую клавишу, чтобы продолжить

ЗАПЛАНИРОВАННЫЕ
id[4] Подготовить ТЗ | NEW
  ↳id[5] Написать текст ТЗ (см. детали) | NEW
  ↳id[6] Собрать ресурсы в помощь (см. детали) | NEW

<span style="background:#214283">id[7] Создать страницу продукта | NEW</span>
  ↳id[8] Создать макет страницы | NEW
  ↳id[9] Подготовить тексты и изображения | NEW
  <span style="background:#214283">↳id[13] Сверстать продуктовую страницу | NEW</span>

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
Нет задач такого типа

ЗАВЕРШЁННЫЕ
Нет задач такого типа

Введите:
номер id[] — вызвать меню задачи
m — вызвать меню программы
t — создать задачу
q — выйти</code></pre>

###Команда «Добавить подзадачу»
Доступна для задачи с подзадачами, статус может быть любой.

Добавляет подзадачу в конец списка подзадач конкретной задачи.
<pre><code>ЗАПЛАНИРОВАННЫЕ
<span style="background:#214283">id[4] Подготовить ТЗ | NEW</span>
  ↳id[5] Написать текст ТЗ (см. детали) | NEW
  ↳id[6] Собрать ресурсы в помощь (см. детали) | NEW

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
Нет задач такого типа

ЗАВЕРШЁННЫЕ
Нет задач такого типа

<span style="background:#214283">Введите:
номер id[] — вызвать меню задачи</span>
m — вызвать меню программы
t — создать задачу
q — выйти
<span style="color:green">4</span>
МЕНЮ ЗАДАЧИ
id[4] Подготовить ТЗ | NEW
Подзадачи (2):
  ↳id[5] Написать текст ТЗ (см. детали) | NEW
  ↳id[6] Собрать ресурсы в помощь (см. детали) | NEW

<span style="background:#214283">ЧТО СДЕЛАТЬ С ЭТОЙ ЗАДАЧЕЙ?</span>
1 — удалить вместе с подзадачами
<span style="background:#214283">2 — добавить подзадачу</span>
0 — выйти из меню задачи
<span style="color:green">2</span>
ДОБАВИТЬ ПОДЗАДАЧУ
id[4] Подготовить ТЗ | NEW
  ↳id[5] Написать текст ТЗ (см. детали) | NEW
  ↳id[6] Собрать ресурсы в помощь (см. детали) | NEW
  ↳ добавить сюда подзадачу
		↑
<span style="background:#214283">Заголовок:</span> <span style="color:green">Подобрать исполнителя</span>
<span style="background:#214283">Описание</span> (если нужно): 
id[4] Подготовить ТЗ | NEW
  ↳id[5] Написать текст ТЗ (см. детали) | NEW
  ↳id[6] Собрать ресурсы в помощь (см. детали) | NEW
  ↳id[13] Подобрать исполнителя | NEW — добавлено сейчас

Нажмите любую клавишу, чтобы продолжить

ЗАПЛАНИРОВАННЫЕ
<span style="background:#214283">id[4]</span> Подготовить ТЗ | NEW
  ↳id[5] Написать текст ТЗ (см. детали) | NEW
  ↳id[6] Собрать ресурсы в помощь (см. детали) | NEW
  <span style="background:#214283">↳id[13] Подобрать исполнителя | NEW</span>

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
Нет задач такого типа

ЗАВЕРШЁННЫЕ
Нет задач такого типа

Введите:
номер id[] — вызвать меню задачи
m — вызвать меню программы
t — создать задачу
q — выйти
</code></pre>

###Команда изменения статуса задачи на IN_PROCESS

В контекстном меню в зависимости от текущего статуса задачи, формулировки различаются.

У обычной задачи и подзадачи со статусами NEW формулировка команды: «Пометить взятой на исполнение».

<pre><code><span style="background:#214283">ЗАПЛАНИРОВАННЫЕ</span>
<span style="background:#214283">id[1]</span> Отправить презентацию Святославу (см. детали) | <span style="background:#214283">NEW</span>
id[2] Исправить баг с изменением пароля | NEW
id[3] Созвониться по видео с новенькими в команде | NEW

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
Нет задач такого типа

ЗАВЕРШЁННЫЕ
Нет задач такого типа

<span style="background:#214283">Введите:
номер id[] — вызвать меню задачи</span>
m — вызвать меню программы
t — создать задачу
q — выйти
<span style="color:green">1</span>
МЕНЮ ЗАДАЧИ
id[1] Отправить презентацию Святославу | NEW
Детали: @svyat_svyat_svyat

<span style="background:#214283">ЧТО СДЕЛАТЬ С ЭТОЙ ЗАДАЧЕЙ?</span>
1 — добавить к ней подзадачу
2 — перенести в подзадачи к другой задаче
3 — удалить
<span style="background:#214283">4 — пометить взятой на выполнение</span>
5 — пометить завершённой
0 — выйти из меню задачи
<span style="color:green">4</span>
ПОМЕТИТЬ ВЗЯТОЙ НА ВЫПОЛНЕНИЕ
Статус задачи id[1] «Отправить презентацию Святославу» изменён:
NEW → IN_PROCESS
Нажмите любую клавишу, чтобы продолжить

ЗАПЛАНИРОВАННЫЕ
id[2] Исправить баг с изменением пароля | NEW
id[3] Созвониться по видео с новенькими в команде | NEW

<span style="background:#214283">ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ</span>
<span style="background:#214283">id[1]</span> Отправить презентацию Святославу (см. детали) | <span style="background:#214283">IN_PROCESS</span>

ЗАВЕРШЁННЫЕ
Нет задач такого типа

Введите:
номер id[] — вызвать меню задачи
m — вызвать меню программы
t — создать задачу
q — выйти
</code></pre>

У обычной задачи или подзадачи со статусами DONE формулировка команды: «Вернуть задаче статус взятой на выполнение».

<pre><code>ЗАПЛАНИРОВАННЫЕ
id[2] Исправить баг с изменением пароля | NEW
id[3] Созвониться по видео с новенькими в команде | NEW

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
Нет задач такого типа

<span style="background:#214283">ЗАВЕРШЁННЫЕ</span>
<span style="background:#214283">id[1]</span> Отправить презентацию Святославу (см. детали) | <span style="background:#214283">DONE</span>

<span style="background:#214283">Введите:
номер id[] — вызвать меню задачи</span>
m — вызвать меню программы
t — создать задачу
q — выйти
<span style="color:green">1</span>
МЕНЮ ЗАДАЧИ
id[1] Отправить презентацию Святославу | DONE
Детали: @svyat_svyat_svyat

<span style="background:#214283">ЧТО СДЕЛАТЬ С ЭТОЙ ЗАДАЧЕЙ?</span>
1 — удалить
<span style="background:#214283">2 — вернуть задаче статус взятой на выполнение</span>
0 — выйти из меню задачи
<span style="color:green">2</span>
СНОВА ПОМЕТИТЬ ВЗЯТОЙ НА ВЫПОЛНЕНИЕ
Статус задачи id[1] «Отправить презентацию Святославу» изменён:
DONE → IN_PROCESS
Нажмите любую клавишу, чтобы продолжить

ЗАПЛАНИРОВАННЫЕ
id[2] Исправить баг с изменением пароля | NEW
id[3] Созвониться по видео с новенькими в команде | NEW

<span style="background:#214283">ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ</span>
<span style="background:#214283">id[1]</span> Отправить презентацию Святославу (см. детали) | <span style="background:#214283">IN_PROCESS</span>

ЗАВЕРШЁННЫЕ
Нет задач такого типа

Введите:
номер id[] — вызвать меню задачи
m — вызвать меню программы
t — создать задачу
q — выйти
</code></pre>

Если статус подзадачи меняется на IN_PROCESS, автоматически меняется статус её родительской задачи на такой же.

###Команда «Пометить завершённой»

Доступна для обычных задач и подзадач со статусами NEW и IN_PROCESS. Меняет статус задачи или подзадачи на DONE.

Если задача или подзадача имеют статус IN_PROCESS, то они помечаются DONE за одно действие.

<pre><code>ЗАПЛАНИРОВАННЫЕ
id[2] Исправить баг с изменением пароля | NEW
id[3] Созвониться по видео с новенькими в команде | NEW

<span style="background:#214283">ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ</span>
<span style="background:#214283">id[1]</span> Отправить презентацию Святославу (см. детали) | <span style="background:#214283">IN_PROCESS</span>

ЗАВЕРШЁННЫЕ
Нет задач такого типа

<span style="background:#214283">Введите:
номер id[] — вызвать меню задачи</span>
m — вызвать меню программы
t — создать задачу
q — выйти
<span style="color:green">1</span>
МЕНЮ ЗАДАЧИ
id[1] Отправить презентацию Святославу | IN_PROCESS
Детали: @svyat_svyat_svyat

<span style="background:#214283">ЧТО СДЕЛАТЬ С ЭТОЙ ЗАДАЧЕЙ?</span>
1 — перенести в подзадачи к другой задаче
2 — удалить
<span style="background:#214283">3 — пометить завершённой</span>
0 — выйти из меню задачи
<span style="color:green">3</span>
ПОМЕТИТЬ ЗАВЕРШЁННОЙ
Статус задачи id[1] «Отправить презентацию Святославу» изменён:
IN_PROCESS → DONE
Нажмите любую клавишу, чтобы продолжить

ЗАПЛАНИРОВАННЫЕ
id[2] Исправить баг с изменением пароля | NEW
id[3] Созвониться по видео с новенькими в команде | NEW

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
Нет задач такого типа

<span style="background:#214283">ЗАВЕРШЁННЫЕ</span>
<span style="background:#214283">id[1]</span> Отправить презентацию Святославу (см. детали) | <span style="background:#214283">DONE</span>

Введите:
номер id[] — вызвать меню задачи
m — вызвать меню программы
t — создать задачу
q — выйти
</code></pre>

Если задача или подзадача имеют статус NEW, то сначала программа предложит пометить их статусом IN_PROCESS, и, только если пользователь настаивает, они помечаются статусом DONE.

<pre><code><span style="background:#214283">ЗАПЛАНИРОВАННЫЕ</span>
<span style="background:#214283">id[2]</span> Исправить баг с изменением пароля | <span style="background:#214283">NEW</span>
id[3] Созвониться по видео с новенькими в команде | NEW

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
Нет задач такого типа

ЗАВЕРШЁННЫЕ
id[1] Отправить презентацию Святославу (см. детали) | DONE

<span style="background:#214283">Введите:
номер id[] — вызвать меню задачи</span>
m — вызвать меню программы
t — создать задачу
q — выйти
<span style="color:green">2</span>
МЕНЮ ЗАДАЧИ
id[2] Исправить баг с изменением пароля | NEW

<span style="background:#214283">ЧТО СДЕЛАТЬ С ЭТОЙ ЗАДАЧЕЙ?</span>
1 — добавить к ней подзадачу
2 — перенести в подзадачи к другой задаче
3 — удалить
4 — пометить взятой на выполнение
<span style="background:#214283">5 — пометить завершённой</span>
0 — выйти из меню задачи
<span style="color:green">5</span>
ПОМЕТИТЬ ЗАВЕРШЁННОЙ
Задача id[2] «Исправить баг с изменением пароля» <span style="background:#214283">ещё не была на выполнении.
Всё равно пометить завершённой?</span>
<span style="background:#214283">1 — да,</span> 0 — нет, 2 — пометить взятой на выполнение
<span style="color:green">1</span>
Статус задачи id[2] «Исправить баг с изменением пароля» изменён:
NEW → DONE
Нажмите любую клавишу, чтобы продолжить

ЗАПЛАНИРОВАННЫЕ
id[3] Созвониться по видео с новенькими в команде | NEW

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
Нет задач такого типа

<span style="background:#214283">ЗАВЕРШЁННЫЕ</span>
id[1] Отправить презентацию Святославу (см. детали) | DONE
<span style="background:#214283">id[2]</span> Исправить баг с изменением пароля | <span style="background:#214283">DONE</span>

Введите:
номер id[] — вызвать меню задачи
m — вызвать меню программы
t — создать задачу
q — выйти
</code></pre>

###Команда «Выйти из меню задачи»
Доступна для задач всех типов и статусов.

Позволяет выйти из контекстного меню задачи без совершения каких-либо действий с задачей.

<pre><code>ЗАПЛАНИРОВАННЫЕ
<span style="background:#214283">id[3]</span> Созвониться по видео с новенькими в команде | NEW

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
Нет задач такого типа

ЗАВЕРШЁННЫЕ
id[1] Отправить презентацию Святославу (см. детали) | DONE
id[2] Исправить баг с изменением пароля | DONE

<span style="background:#214283">Введите:
номер id[] — вызвать меню задачи</span>
m — вызвать меню программы
t — создать задачу
q — выйти
<span style="color:green">3</span>
МЕНЮ ЗАДАЧИ
id[3] Созвониться по видео с новенькими в команде | NEW

ЧТО СДЕЛАТЬ С ЭТОЙ ЗАДАЧЕЙ?
1 — добавить к ней подзадачу
2 — перенести в подзадачи к другой задаче
3 — удалить
4 — пометить взятой на выполнение
5 — пометить завершённой
<span style="background:#214283">0 — выйти из меню задачи</span>
<span style="color:green">0</span>
↓ возврат
Нажмите любую клавишу, чтобы продолжить

ЗАПЛАНИРОВАННЫЕ
id[3] Созвониться по видео с новенькими в команде | NEW

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
Нет задач такого типа

ЗАВЕРШЁННЫЕ
id[1] Отправить презентацию Святославу (см. детали) | DONE
id[2] Исправить баг с изменением пароля | DONE

Введите:
номер id[] — вызвать меню задачи
m — вызвать меню программы
t — создать задачу
q — выйти
</code></pre>

##Меню программы
Помимо контекстного меню в рабочей области можно вызвать меню программы.

###Удалить все задачи
Действие удаляет все введённые в память менеджера задачи на текущий момент.
<pre><code>ЗАПЛАНИРОВАННЫЕ
id[<span style="background:#214283">1</span>] Отправить презентацию Святославу (см. детали) | NEW
id[<span style="background:#214283">2</span>] Исправить баг с изменением пароля | NEW
id[<span style="background:#214283">3</span>] Созвониться по видео с новенькими в команде | NEW

id[<span style="background:#214283">4</span>] Подготовить ТЗ | NEW
  ↳id[<span style="background:#214283">5</span>] Написать текст ТЗ (см. детали) | NEW
  ↳id[<span style="background:#214283">6</span>] Собрать ресурсы в помощь (см. детали) | NEW

id[<span style="background:#214283">7</span>] Создать страницу продукта | NEW
  ↳id[<span style="background:#214283">8</span>] Создать макет страницы | NEW
  ↳id[<span style="background:#214283">9</span>] Подготовить тексты и изображения | NEW
  ↳id[<span style="background:#214283">10</span>] Сверстать страницу | NEW

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
Нет задач такого типа

ЗАВЕРШЁННЫЕ
Нет задач такого типа

<span style="background:#214283">Введите:</span>
номер id[] — вызвать меню задачи
<span style="background:#214283">m — вызвать меню программы</span>
t — создать задачу
q — выйти
<span style="color:green">m</span>
НАСТРОЙКИ МЕНЕДЖЕРА ЗАДАЧ
<span style="background:#214283">1 — удалить все задачи</span>
2 — история просмотра задач
0 — назад
<span style="color:green">1</span>
УДАЛИТЬ ВСЕ ЗАДАЧИ
Удалить все задачи без возможности восстановления?
<span style="background:#214283">1 — да,</span> 0 — нет
<span style="color:green">1</span>
Все задачи удалены
<span style="background:#214283">[!] Список задач пуст</span>

СОЗДАТЬ ЗАДАЧУ:
1 — простую
2 — с подзадачами
0 — выйти</code></pre>

###Посмотреть историю просмотров задач
Позволяет посмотреть последние 10 задач, с которыми любым способом взаимодействовал пользователь: вызывал меню задачи, менял её статус, пользовался любыми действиями, доступными задаче. 

Фиксируется каждый раз, когда пользователь взаимодействует с задачей: в истории просмотра могут быть дубли.

<pre><code>ЗАПЛАНИРОВАННЫЕ
id[2] Исправить баг с изменением пароля | NEW
id[3] Созвониться по видео с новенькими в команде | NEW

id[7] Создать страницу продукта | NEW
  ↳id[8] Создать макет страницы | NEW
  ↳id[9] Подготовить тексты и изображения | NEW
  ↳id[10] Сверстать страницу | NEW

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
id[1] Отправить презентацию Святославу (см. детали) | IN_PROCESS

ЗАВЕРШЁННЫЕ
id[4] Подготовить ТЗ | DONE
  ↳id[5] Написать текст ТЗ (см. детали) | DONE
  ↳id[6] Собрать ресурсы в помощь (см. детали) | DONE

<span style="background:#214283">Введите:</span>
номер id[] — вызвать меню задачи
<span style="background:#214283">m — вызвать меню программы</span>
t — создать задачу
q — выйти
<span style="color:green">m</span>
НАСТРОЙКИ МЕНЕДЖЕРА ЗАДАЧ
1 — удалить все задачи
<span style="background:#214283">2 — история просмотра задач</span>
0 — назад
<span style="color:green">2</span>
ИСТОРИЯ ПРОСМОТРА ЗАДАЧ
<span style="background:#214283">Последние 10 просмотренных задач</span> (от старых к новым):
↳id[<span style="background:#214283">5</span>] Написать текст ТЗ (см. детали) | DONE
↳id[<span style="background:#214283">6</span>] Собрать ресурсы в помощь (см. детали) | DONE
↳id[<span style="background:#214283">5</span>] Написать текст ТЗ (см. детали) | DONE
↳id[<span style="background:#214283">6</span>] Собрать ресурсы в помощь (см. детали) | DONE
id[<span style="background:#214283">2</span>] Исправить баг с изменением пароля | NEW
id[<span style="background:#214283">2</span>] Исправить баг с изменением пароля | NEW
id[<span style="background:#214283">7</span>] Создать страницу продукта | NEW
id[<span style="background:#214283">2</span>] Исправить баг с изменением пароля | NEW
id[<span style="background:#214283">3</span>] Созвониться по видео с новенькими в команде | NEW
id[<span style="background:#214283">1</span>] Отправить презентацию Святославу (см. детали) | IN_PROCESS
Нажмите любую клавишу, чтобы продолжить
</code></pre>

##Команда «Создать задачу»
<pre><code>ЗАПЛАНИРОВАННЫЕ
id[1] Отправить презентацию Святославу (см. детали) | NEW
id[2] Исправить баг с изменением пароля | NEW
id[3] Созвониться по видео с новенькими в команде | NEW

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
Нет задач такого типа

ЗАВЕРШЁННЫЕ
Нет задач такого типа

<span style="background:#214283">Введите:</span>
номер id[] — вызвать меню задачи
m — вызвать меню программы
<span style="background:#214283">t — создать задачу</span>
q — выйти
<span style="color:green">t</span>
<span style="background:#214283">СОЗДАТЬ ЗАДАЧУ:
1 — простую
2 — с подзадачами</span>
0 — назад
</code></pre>

Если в меню программы выбрать создание обычной задачи, создаётся однострочная задача.
<pre><code>ЗАПЛАНИРОВАННЫЕ
id[2] Исправить баг с изменением пароля | NEW
id[3] Созвониться по видео с новенькими в команде | NEW

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
Нет задач такого типа

ЗАВЕРШЁННЫЕ
Нет задач такого типа

<span style="background:#214283">Введите:</span>
номер id[] — вызвать меню задачи
m — вызвать меню программы
<span style="background:#214283">t — создать задачу</span>
q — выйти
<span style="color:green">t</span>
<span style="background:#214283">СОЗДАТЬ ЗАДАЧУ:</span>
<span style="background:#214283">1 — простую</span>
2 — с подзадачами
0 — назад
<span style="color:green">1</span>
СОЗДАТЬ ПРОСТУЮ ЗАДАЧУ
<span style="background:#214283">Заголовок:</span> <span style="color:green">Отправить презентацию Святославу</span>
<span style="background:#214283">Описание</span> (если нужно): <span style="color:green">@svyat_svyat_svyat</span>
id[13] Отправить презентацию Святославу (см. детали) | NEW — добавлено сейчас
Нажмите любую клавишу, чтобы продолжить

ЗАПЛАНИРОВАННЫЕ
id[2] Исправить баг с изменением пароля | NEW
id[3] Созвониться по видео с новенькими в команде | NEW
<span style="background:#214283">id[13] Отправить презентацию Святославу (см. детали) | NEW</span>

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
Нет задач такого типа

ЗАВЕРШЁННЫЕ
Нет задач такого типа

Введите:
номер id[] — вызвать меню задачи
m — вызвать меню программы
t — создать задачу
q — выйти</code></pre>

Если в меню программы выбрать создание задачи с подзадачами, создаётся родительская задача со своей первой подзадачей.

<pre><code><span style="background:#214283">ЗАПЛАНИРОВАННЫЕ</span>
id[2] Исправить баг с изменением пароля | NEW
id[3] Созвониться по видео с новенькими в команде | NEW
id[13] Отправить презентацию Святославу (см. детали) | NEW

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
Нет задач такого типа

ЗАВЕРШЁННЫЕ
Нет задач такого типа

<span style="background:#214283">Введите:</span>
номер id[] — вызвать меню задачи
m — вызвать меню программы
<span style="background:#214283">t — создать задачу</span>
q — выйти
<span style="color:green">t</span>
СОЗДАТЬ ЗАДАЧУ:
1 — простую
<span style="background:#214283">2 — с подзадачами</span>
0 — назад
<span style="color:green">2</span>
СОЗДАТЬ ЗАДАЧУ С ПОДЗАДАЧАМИ
<span style="background:#214283">Заголовок:</span> <span style="color:green">Забрать товар со склада</span>
<span style="background:#214283">Описание</span> (если нужно): <span style="color:green">Склад «Красные ворота», въезд 12, заказ #81503</span>
id[14] Забрать товар со склада (см. детали) | NEW
  <span style="background:#214283">↳ добавить сюда подзадачу</span>
		↑
<span style="background:#214283">Заголовок:</span> <span style="color:green">Зарезервировать фургон с водителем</span>
<span style="background:#214283">Описание</span> (если нужно): 
id[14] Забрать товар со склада (см. детали) | NEW
  ↳id[15] Зарезервировать фургон с водителем | NEW — добавлено сейчас

Нажмите любую клавишу, чтобы продолжить

<span style="background:#214283">ЗАПЛАНИРОВАННЫЕ</span>
id[2] Исправить баг с изменением пароля | NEW
id[3] Созвониться по видео с новенькими в команде | NEW
id[13] Отправить презентацию Святославу (см. детали) | NEW

<span style="background:#214283">id[14] Забрать товар со склада (см. детали) | NEW
  ↳id[15] Зарезервировать фургон с водителем | NEW</span>

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
Нет задач такого типа

ЗАВЕРШЁННЫЕ
Нет задач такого типа

Введите:
номер id[] — вызвать меню задачи
m — вызвать меню программы
t — создать задачу
q — выйти</code></pre>

Программа следит, чтобы не создавались дубли задач.

Нельзя создать две одинаковые обычные задачи.
<pre><code>ЗАПЛАНИРОВАННЫЕ
id[2] <span style="background:#214283">Исправить баг с изменением пароля</span> | NEW

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
Нет задач такого типа

ЗАВЕРШЁННЫЕ
Нет задач такого типа

<span style="background:#214283">Введите:</span>
номер id[] — вызвать меню задачи
m — вызвать меню программы
<span style="background:#214283">t — создать задачу</span>
q — выйти
<span style="color:green">t</span>
СОЗДАТЬ ЗАДАЧУ:
<span style="background:#214283">1 — простую</span>
2 — с подзадачами
0 — назад
<span style="color:green">1</span>
СОЗДАТЬ ПРОСТУЮ ЗАДАЧУ
<span style="background:#214283">Заголовок:</span> <span style="color:green">Исправить баг с изменением пароля</span>
<span style="background:#214283">Описание</span> (если нужно): 
<span style="background:#214283">[!] Такая задача уже есть</span>
Нажмите любую клавишу, чтобы продолжить</code></pre>

Нельзя создать задачу с подзадачами, если есть обычная задача с такими заголовком и описанием.
<pre><code>ЗАПЛАНИРОВАННЫЕ
id[2] <span style="background:#214283">Исправить баг с изменением пароля</span> | NEW

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
Нет задач такого типа

ЗАВЕРШЁННЫЕ
Нет задач такого типа

<span style="background:#214283">Введите:</span>
номер id[] — вызвать меню задачи
m — вызвать меню программы
<span style="background:#214283">t — создать задачу</span>
q — выйти
<span style="color:green">t</span>
СОЗДАТЬ ЗАДАЧУ:
1 — простую
<span style="background:#214283">2 — с подзадачами</span>
0 — назад
<span style="color:green">2</span>
СОЗДАТЬ ЗАДАЧУ С ПОДЗАДАЧАМИ
<span style="background:#214283">Заголовок:</span> <span style="color:green">Исправить баг с изменением пароля</span>
<span style="background:#214283">Описание</span> (если нужно): 
<span style="background:#214283">[!] Такая задача уже есть</span>
Нажмите любую клавишу, чтобы продолжить</code></pre>

Но можно создавать подзадачи с одинаковыми названиями и описаниями внутри разных задач с подзадачами. Cхожие большие задачи могут иметь схожие части.

<pre><code>ЗАПЛАНИРОВАННЫЕ
id[2] Забрать со склада заказ №450 | NEW
  ↳id[3] <span style="background:#214283">Зарезервировать фургон</span> | NEW

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
Нет задач такого типа

ЗАВЕРШЁННЫЕ
Нет задач такого типа

<span style="background:#214283">Введите:</span>
номер id[] — вызвать меню задачи
m — вызвать меню программы
<span style="background:#214283">t — создать задачу</span>
q — выйти
<span style="color:green">t</span>
СОЗДАТЬ ЗАДАЧУ:
1 — простую
<span style="background:#214283">2 — с подзадачами</span>
0 — назад
<span style="color:green">2</span>
СОЗДАТЬ ЗАДАЧУ С ПОДЗАДАЧАМИ
<span style="background:#214283">Заголовок:</span> <span style="color:green">Забрать со склада заказ №451</span>
<span style="background:#214283">Описание</span> (если нужно): 
id[4] Забрать со склада заказ №451 | NEW
  <span style="background:#214283">↳ добавить сюда подзадачу</span>
		↑
<span style="background:#214283">Заголовок:</span> <span style="color:green">Зарезервировать фургон</span>
<span style="background:#214283">Описание</span> (если нужно): 
id[4] Забрать со склада заказ №451 | NEW
  ↳id[5] Зарезервировать фургон | NEW — добавлено сейчас

Нажмите любую клавишу, чтобы продолжить

ЗАПЛАНИРОВАННЫЕ
id[2] Забрать со склада заказ №450 | NEW
  ↳id[3] <span style="background:#214283">Зарезервировать фургон</span> | NEW

id[4] Забрать со склада заказ №451 | NEW
  ↳id[5] <span style="background:#214283">Зарезервировать фургон</span> | NEW

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
Нет задач такого типа

ЗАВЕРШЁННЫЕ
Нет задач такого типа

Введите:
номер id[] — вызвать меню задачи
m — вызвать меню программы
t — создать задачу
q — выйти
</code></pre>

Задачи ↳id[3] и ↳id[5] одинаковы, но конфликта имён нет, потому что и путаницы нет: подзадачи явно относятся каждая к своей родительской задаче.

##Команда «выйти из программы»
Команда «выйти из программы» завершает программу.
<pre><code>ЗАПЛАНИРОВАННЫЕ
id[2] Забрать со склада заказ №450 | NEW
  ↳id[3] Зарезервировать фургон | NEW

id[4] Забрать со склада заказ №451 | NEW
  ↳id[5] Зарезервировать фургон | NEW

ВЗЯТЫЕ НА ВЫПОЛНЕНИЕ
Нет задач такого типа

ЗАВЕРШЁННЫЕ
Нет задач такого типа

Введите:
номер id[] — вызвать меню задачи
m — вызвать меню программы
t — создать задачу
<span style="background:#214283">q — выйти</span>
<span style="color:green">q</span>
<span style="background:#214283">Завершение программы</span></code></pre>