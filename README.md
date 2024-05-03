# Проект по автоматизации тестирования API для сайта [Reqres](https://reqres.in/)
<p align="center"><a href="https://reqres.in/"><img src="images/icons/reqres.png" align="center"  height="200"  alt="reqres"/></a></p>

> Reqres - сервис, предоставляющий публичный API для практики тестирования и разработки

## Содержание:

- [Технологии и инструменты](#tools)
- [Тестовое покрытие](#cases)
- [Локальный запуск тестов](#localrun)
- [Запуск тестов в Jenkins](#remoterun)
- [Allure отчёт](#report)
- [Интеграция с Allure TestOps](#testops)
- [Интеграция с Jira](#jira)
- [Уведомления в Telegram](#telegram)
- [Видео с примером запуска тестов в Selenoid](#video)


<a id="tools"></a>

## Технологии и инструменты:

<div align="center">
<a href="https://www.jetbrains.com/idea/"><img alt="InteliJ IDEA" height="50" src="images/icons/IntelliJ_IDEA.png" width="50"/></a>
<a href="https://github.com/"><img alt="GitHub" height="50" src="images/icons/GitHub.png" width="50"/></a>  
<a href="https://www.java.com/"><img alt="Java" height="50" src="images/icons/Java_logo.png" width="50"/></a>
<a href="https://gradle.org/"><img alt="Gradle" height="50" src="images/icons/Gradle.png" width="50"/></a>  
<a href="https://junit.org/junit5/"><img alt="JUnit 5" height="50" src="images/icons/JUnit5.png" width="50"/></a>
<a href="https://selenide.org/"><img alt="Selenide" height="50" src="images/icons/Selenide.png" width="50"/></a>
<a href="https://aerokube.com/selenoid/"><img alt="Selenoid" height="50" src="images/icons/Selenoid.png" width="50"/></a>
<a href="https://rest-assured.io/"><img alt="RestAssured" height="50" src="images/icons/RestAssured.png" width="50"/></a>
<a href="https://www.jenkins.io/"><img alt="Jenkins" height="50" src="images/icons/Jenkins.png" width="50"/></a>
<a href="https://github.com/allure-framework/"><img alt="Allure Report" height="50" src="images/icons/AllureReports.png" width="50"/></a>
<a href="https://qameta.io/"><img alt="Allure TestOps" height="50" src="images/icons/AllureTestOps.svg" width="50"/></a>
<a href="https://www.atlassian.com/software/jira"><img alt="Jira" height="50" src="images/icons/Jira.png" width="50"/></a>  
<a href="https://telegram.org/"><img alt="Telegram" height="50" src="images/icons/Telegram.png" width="50"/></a>
</div>

<p></p>

Автотесты для данного проекта написаны на <code>Java</code> с использованием фреймворка <code>Selenoid</code>. При проектировании тестов применён паттерн <code>PageObject</code>. Для части автотестов проекта предусловие выполняется через запросы по API с помощью <code>REST-assured</code>.

В качестве фреймворка для запуска тестов используется <code>Junit5</code>, а в качестве сборщика проекта - <code>Gradle</code>.

Произведена настройка CI в <code>Jenkins</code>, при запуске прогонов из которого тесты выполняются в удалённом браузере в <code>Selenoid</code>.

По результатам каждого тестового прогона создаётся <code>Allure</code> отчёт для визуализации результатов прогона.

Для тест-менеджмента настроена интеграция с <code>Allure TestOps</code>, которая, в свою очередь, интегрирована с таск-трекером <code>Jira</code>

После прогона тестов <code>Telegram</code> бот присылает сообщение с информацией о прошедшем прогоне

<a id="cases"></a>

## Тестовое покрытие:
### Регистрация пользователя
🐞 Проверка получения уведомления об отправке СМС сообщения при успешной регистрации

🐞 Проверка получения ошибки о наличии согласия с правилами сайта

🐞 Проверка получения ошибки ввода капчи

### Поиск товаров
🐞 Проверка поиска товаров с поисковым запросом с результатами

🐞 Проверка поиска товаров с поисковым запросом без результатов

🐞 Проверка поиска товаров без поискового запроса

### Корзина товаров
🐞 Проверка очистки корзины

🐞 Проверка удаления одного товара из корзины

🐞 Проверка отмены очистки корзины

<a id="localrun"></a>

## Локальный запуск тестов
Для локального запуска тестов из IDE или из терминала необходимо выполнить следующую команду

```
gradle clean test -Denv=local
```

<a id="remoterun"></a>

## Запуск тестов в [Jenkins](https://jenkins.autotests.cloud/job/C24-tarabne-maxidom_ui_tests/)
Для запуска тестов в Jenkins нужно нажать на кнопку Build Now в соответствующей сборке

<p align="center">
<img src="images/screenshots/screen1.png">
</p>

<a id="report"></a>

## [Allure отчёт](https://jenkins.autotests.cloud/job/C24-tarabne-maxidom_ui_tests/allure/)
### Графики

<p align="center">
<img src="images/screenshots/screen2.png">
</p>

### Тесты

<p align="center">
<img src="images/screenshots/screen3.png">
</p>

<a id="testops"></a>

## Интеграция с [Allure TestOps](https://allure.autotests.cloud/project/4211/test-cases)

<p align="center">
<img src="images/screenshots/screen4.png">
</p>

<a id="jira"></a>

## Интеграция с [Jira](https://jira.autotests.cloud/browse/HOMEWORK-1210)

<p align="center">
<img src="images/screenshots/screen5.png">
</p>

<a id="telegram"></a>

## Уведомление в Telegram

По результатам каждого прогона тестов в Jenkins отправляется сообщение в Telegram. Сообщение содержит информацию о прогоне, а также диаграмму со статистикой прохождения тестов.

<p align="center">
<img src="images/screenshots/screen6.png" width="400">
</p>

<a id="video"></a>

## Видео с примером запуска тестов в Selenoid
В Allure отчёте к каждому тесту прикладываются скриншот с последнего шага, и видео прохождения теста. Примеры таких видео:

<p align="center">
<img src="images/video/video1.gif " width="1000">
</p>

<p align="center">
<img src="images/video/video2.gif" width="1000">
</p>
