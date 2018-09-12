$(document).ready(function () {

    const apiRoot = 'http://localhost:8080/game/'
    const board = $('[board]');
    const gStatus = $('[gameStatus]');
    const mHistory = $('[movesHistory]');

    var availableRulesSets = {};
    var availableRulesSetsLength;

    $('[next-move]').on('keydown', function () {
        if (event.keyCode == 13) {
            $('[send-move-button]').click();
        }
    });

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

        getBoard();
    }

    function sendMove() {
        var requestUrl = apiRoot + 'sendMove';
        var moveToSend = $('[next-move]').val();

        $.ajax({
            url: requestUrl,
            method: 'POST',
            processData: false,
            contentType: "application/json;charset=UTF-8",
            dataType: 'json',
            data: JSON.stringify({
                move: moveToSend
            }),
            success: function () {
                getBoard();
                $('[next-move]').val("");
                $('[next-move]').focus();
            },
            error: function (xhr, textStatus, err) {
                gStatus.text("Application error.");
            }
        });
    }

    function getBoard() {
        const requestUrl = apiRoot + 'getBoard';
        
        $.ajax({
            url: requestUrl,
            method: 'GET',
            contentType: "application/json;charset=UTF-8",
            success: function (chessboard) {
                for (var i = 0; i < 7; i+=2) {
                    for (var j = 2; j < 9; j+=2) {
                        for (var k = 0; k < 4; k++) {
                            board.children()[i].children[j].children[k].style.display = 'none';
                        }
                    }
                }
                for (var i = 1; i < 8; i += 2) {
                    for (var j = 1; j < 8; j += 2) {
                        for (var k = 0; k < 4; k++) {
                            board.children()[i].children[j].children[k].style.display = 'none';
                        }
                    }
                }
                gStatus.text(chessboard.gameStatus);
                chessboard.rows.forEach(row => {
                    row.figures.forEach(figure =>  {
                        if (figure.name == "pawn") {
                            if (figure.color == "true") {
                                board.children()[row.name].children[figure.col].children[0].style.display = 'block';
                            }
                            else {
                                board.children()[row.name].children[figure.col].children[2].style.display = 'block';
                            }
                        }
                        if (figure.name == "queen") {
                            if (figure.color == "true") {
                                board.children()[row.name].children[figure.col].children[1].style.display = 'block';
                            }
                            else {
                                board.children()[row.name].children[figure.col].children[3].style.display = 'block';
                            }
                        }   
                    });
                });
                mHistory.text(chessboard.movesHistory);
            },
            error: function (xhr, textStatus, err) {
                gStatus.text("Application error.");
            }
        });
    }

    $('[create-game-button]').click(createGame);
    $('[get-board]').click(getBoard);
    $('[send-move-button]').click(sendMove);
        
});