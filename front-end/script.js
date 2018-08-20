$(document).ready(function () {

    const apiRoot = 'http://localhost:8080/game/'
    const board = $('[board]');
    const gStatus = $('[gameStatus]');

    var availableRulesSets = {};
    var availableRulesSetsLength;
    
    //init
    gStatus.text("Game not started.");
    getAllAvailableRulesSets();

    function getAllAvailableRulesSets() {
        var requestUrl = apiRoot + 'getRulesSets';

        $.ajax({
            url: requestUrl,
            method: 'GET',
            contentType: "application/json",
            success: function (list) {
                availableRulesSetsLength = (list.rules).length;
                for (var i = 0; i < availableRulesSetsLength; i++) {
                    availableRulesSets[i] = list.rules[i];
                }
                prepareRulesSelectOptions();
            }
        });
    }

    function prepareRulesSelectOptions() {
        var list = $('[rules-set-select]');

        for (var i = 0; i < availableRulesSetsLength; i++) {
            var option = document.createElement("option");
            option.value = availableRulesSets[i].name;
            option.text = availableRulesSets[i].name;
            list.append(option);
        }
    }

    function createGame() {
        const requestUrl = apiRoot + 'newGame';
        
        var gameName = $('[game-name]').val();
        var gameRulesName = $('[rules-set-select]').val();
        var blackPlayer = "false";
        if ($('[black-player-select]').val() == "Computer") {
            blackPlayer = "true";
        }
        var whitePlayer = "false";
        if ($('[white-player-select]').val() == "Computer") {
            whitePlayer = "true";
        }

        if (gameName == "") {
            gStatus.text("You have to enter game name!");
            return;
        }
        gStatus.text("Game started.");

        $.ajax({
            url: requestUrl,
            method: 'POST',
            processData: false,
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: JSON.stringify({
                name: gameName,
                rulesName: gameRulesName,
                isBlackAIPlayer: blackPlayer,
                isWhiteAIPlayer: whitePlayer
            }),
            error: function () {
                gStatus.text("Application error.");
            }
        });
    }

    function getBoard() {
        const requestUrl = apiRoot + 'getBoard';
        
        alert(board.children()[0].children()[0].text);

        $.ajax({
            url: requestUrl,
            method: 'GET',
            contentType: "application/json;charset=UTF-8",
            success: function (chessboard) {
                gStatus.text(chessboard.gameStatus);
                chessboard.rows.forEach(row => {
                    row.figures.forEach(figure =>  {
                        if (figure.name == "pawn") {
                            if (figure.color == "true") {
                                board.children()[row.name].children()[figure.col].children()[0].visible = true;
                            }
                            else {
                                board.children()[row.name].children()[figure.col].children()[2].visible = true;
                            }
                        }
                        if (figure.name == "queen") {
                            if (figure.color == "true") {
                                board.children()[row.name].children()[figure.col].children()[1].visible = true;
                            }
                            else {
                                board.children()[row.name].children()[figure.col].children()[3].visible = true;
                            }
                        }   
                    });
                });
            },
            error: function (xhr, textStatus, err) {
                gStatus.text("Application error.");
            }
        });
    }

    function sendMove(row1, col1, row2, col2) {
        var requestUrl = apiRoot + 'sendMove';

        $.ajax({
            url: requestUrl,
            method: 'POST',
            processData: true,
            contentType: "application/json;charset=UTF-8",
            dataType: 'json',
            data: JSON.stringify({
                row1: row1,
                col1: col1,
                row2: row2,
                col2: col2
            }),
            success: function (data) {
                ProcessRequest(data);
            }
        });
    }

    $('[create-game-button]').click(createGame);
    $('[get-board]').click(getBoard);

    /*

        function getAllTasks() {
        const requestUrl = apiRoot + 'getTasks';

        $.ajax({
            url: requestUrl,
            method: 'GET',
            contentType: "application/json",
            success: function (tasks) {
                tasks.forEach(task => {
                    availableTasks[task.id] = task;
                });

                getAllAvailableBoards(handleDatatableRender, tasks);
            }
        });
    }


    const datatableRowTemplate = $('[data-datatable-row-template]').children()[0];
    const $tasksContainer = $('[data-tasks-container]');

    var availableBoards = {};
    var availableTasks = {};

    // init

    getAllTasks();

    function getAllAvailableBoards(callback, callbackArgs) {
        var requestUrl = trelloApiRoot + 'getTrelloBoards';

        $.ajax({
            url: requestUrl,
            method: 'GET',
            contentType: 'application/json',
            success: function (boards) { callback(callbackArgs, boards); }
        });
    }

    function createElement(data) {
        const element = $(datatableRowTemplate).clone();

        element.attr('data-task-id', data.id);
        element.find('[data-task-name-section] [data-task-name-paragraph]').text(data.title);
        element.find('[data-task-name-section] [data-task-name-input]').val(data.title);

        element.find('[data-task-content-section] [data-task-content-paragraph]').text(data.content);
        element.find('[data-task-content-section] [data-task-content-input]').val(data.content);

        return element;
    }



    function handleDatatableRender(taskData, boards) {
        $tasksContainer.empty();
        boards.forEach(board => {
            availableBoards[board.id] = board;
        });

        taskData.forEach(function (task) {
            var $datatableRowEl = createElement(task);
            var $availableBoardsOptionElements = prepareBoardOrListSelectOptions(boards);

            $datatableRowEl.find('[data-board-name-select]')
              .append($availableBoardsOptionElements);

            $datatableRowEl
              .appendTo($tasksContainer);
        });
    }



    function handleTaskUpdateRequest() {
        var parentEl = $(this).parents('[data-task-id]');
        var taskId = parentEl.attr('data-task-id');
        var taskTitle = parentEl.find('[data-task-name-input]').val();
        var taskContent = parentEl.find('[data-task-content-input]').val();
        var requestUrl = apiRoot + 'updateTask';

        $.ajax({
            url: requestUrl,
            method: "PUT",
            processData: false,
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: JSON.stringify({
                id: taskId,
                title: taskTitle,
                content: taskContent
            }),
            success: function (data) {
                parentEl.attr('data-task-id', data.id).toggleClass('datatable__row--editing');
                parentEl.find('[data-task-name-paragraph]').text(taskTitle);
                parentEl.find('[data-task-content-paragraph]').text(taskContent);
            }
        });
    }

    function handleTaskDeleteRequest() {
        var parentEl = $(this).parents('[data-task-id]');
        var taskId = parentEl.attr('data-task-id');
        var requestUrl = apiRoot + 'deleteTask';

        $.ajax({
            url: requestUrl + '/?' + $.param({
                taskId: taskId
            }),
            method: 'DELETE',
            success: function () {
                parentEl.slideUp(400, function () { parentEl.remove(); });
            }
        })
    }

    function handleTaskSubmitRequest(event) {
        event.preventDefault();

        var taskTitle = $(this).find('[name="title"]').val();
        var taskContent = $(this).find('[name="content"]').val();

        var requestUrl = apiRoot + 'createTask';

        $.ajax({
            url: requestUrl,
            method: 'POST',
            processData: false,
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: JSON.stringify({
                title: taskTitle,
                content: taskContent
            }),
            complete: function (data) {
                if (data.status === 200) {
                    getAllTasks();
                }
            }
        });
    }

    function toggleEditingState() {
        var parentEl = $(this).parents('[data-task-id]');
        parentEl.toggleClass('datatable__row--editing');

        var taskTitle = parentEl.find('[data-task-name-paragraph]').text();
        var taskContent = parentEl.find('[data-task-content-paragraph]').text();

        parentEl.find('[data-task-name-input]').val(taskTitle);
        parentEl.find('[data-task-content-input]').val(taskContent);
    }

    function handleCardCreationRequest(event) {
        var requestUrl = trelloApiRoot + 'createTrelloCard';
        var $relatedTaskRow = $(event.target).parents('[data-task-id]');
        var relatedTaskId = $relatedTaskRow.attr('data-task-id');
        var relatedTask = availableTasks[relatedTaskId];
        var selectedListId = $relatedTaskRow.find('[data-list-name-select]').val();

        if (!selectedListId) {
            alert('You have to select a board and a list first!');
            return;
        }

        $.ajax({
            url: requestUrl,
            method: 'POST',
            processData: false,
            contentType: "application/json; charset=utf-8",
            dataType: 'json',
            data: JSON.stringify({
                name: relatedTask.title,
                description: relatedTask.content,
                listId: selectedListId
            }),
            success: function (data) {
                console.log('Card created - ' + data.shortUrl);
                alert('Card created - ' + data.shortUrl);
            }
        });
    }

    $('[data-task-add-form]').on('submit', handleTaskSubmitRequest);

    $tasksContainer.on('change', '[data-board-name-select]', handleBoardNameSelect);
    $tasksContainer.on('click', '[data-trello-card-creation-trigger]', handleCardCreationRequest);
    $tasksContainer.on('click', '[data-task-edit-button]', toggleEditingState);
    $tasksContainer.on('click', '[data-task-edit-abort-button]', toggleEditingState);
    $tasksContainer.on('click', '[data-task-submit-update-button]', handleTaskUpdateRequest);
    $tasksContainer.on('click', '[data-task-delete-button]', handleTaskDeleteRequest);*/
});