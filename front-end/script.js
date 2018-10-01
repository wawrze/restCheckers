$(document).ready(function () {

    const apiRoot = 'http://localhost:8080/game/'
    const board = $('[board]');
    const gStatus = $('[gameStatus]');
    const mHistory = $('[movesHistory]');
    const whitePlayer = $('[isWhiteAIPlayer]');
    const blackPlayer = $('[isBlackAIPlayer]');
    const status = $('[status]');

    var availableRulesSets = {};
    var availableRulesSetsLength;
    var gameName;

    $('[next-move]').on('keydown', function () {
        if (event.keyCode == 13) {
            $('[send-move-button]').click();
        }
    });

    //init
    gStatus.text("Not connected to application. Please, wait for a while...");
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
                gStatus.text("Game not started.");
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

    function getSelectedRulesSet() {
        var gameRulesName = $('[rules-set-select]').val()
        var requestUrl = apiRoot + 'getRulesSet?rulesSetName=' + gameRulesName;

        $.ajax({
            url: requestUrl,
            method: 'GET',
            success: function (rules) {
                $('[rules-set-name]').text(rules.name);
                $('[rules-set-description]').text(rules.description);
                $('[victory-conditions]').text(rules.victoryConditionsReversed ? "reversed" : "standard");
                $('[capture]').text(rules.captureAny ? "any" : "longest");
                $('[man-move-backward]').text(rules.pawnMoveBackward ? "yes" : "no");
                $('[man-capture-backward]').text(rules.pawnCaptureBackward ? "yes" : "no");
                $('[king-range]').text(rules.queenRangeOne ? "one field" : "any");
                $('[king-move-after-capture]').text(rules.queenRangeOneAfterCapture ? "one field" : "any");
            },
            statusCode: {
                403: function () {
                    gStatus.text("There is no rules set named \"" + gameRulesName + "\" - possible application error.");
                }
            }
        });
    }
    
    function createGame() {
        const requestUrl = apiRoot + 'newGame';
        
        gameName = $('[game-name]').val();
        var gameRulesName = $('[rules-set-select]').val();
        var blackPlayer = "false";
        var error = 0;

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

        if (gameRulesName == null) {
            gStatus.text("You have to select rules set!");
            return;
        }

        gStatus.text("Game started.");
        
        $.ajax({
            url: requestUrl,
            method: 'POST',
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
        $('[created-game-name]').text(gameName);
        $('[new-game-section]')[0].style.display = 'none';
        $('[created-game-section]')[0].style.display = 'block';
        $('[next-move-section]')[0].style.display = 'block';
        $('[next-move-input]')[0].style.display = 'inline-block';
        $('[status]')[1].style.display = 'block';
        $('[next-move]')[0].style.display = 'inline-block';
        $('[send-move-button]')[0].style.display = 'inline-block';
        
        if (blackPlayer == "true" && whitePlayer == "true") {
            gStatus[0].style.display = 'none';
            $('[next-move-input]').text("Enter button to see next move:");
            $('[next-move]')[0].style.display = 'none';
            $('[send-move-button]').text("Next move");
            $('[send-move-button]').focus();
        }
        else {
            $('[next-move-input]').text("Enter your next move:");
            $('[send-move-button]').text("Send move");
            $('[next-move]').focus();
        }

        var start = new Date().getTime();
        for (var i = 0; i < 1e7; i++) {
            if ((new Date().getTime() - start) > 500) {
                break;
            }
        }
    }

    function sendMove() {
        var requestUrl = apiRoot + 'sendMove?gameName=' + gameName;
        var moveToSend;

        if ($('[black-player-select]').val() == "Computer" && $('[white-player-select]').val() == "Computer") {
            moveToSend = "next";
        }
        else {
            moveToSend = $('[next-move]').val();
        }

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
            },
            statusCode: {
                403: function () {
                    gStatus.text("There is no game named \"" + gameName + "\" - possible application error.");
                }
            }
        });
    }

    function getBoard() {
        const requestUrl = apiRoot + 'getBoard?gameName=' + gameName;
        
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
                if (chessboard.blackAIPlayer) {
                    blackPlayer.text("(Computer)");
                }
                else {
                    if (chessboard.activePlayer) {
                        blackPlayer.text("(Human)\n(ACTIVE)");
                    }
                    else {
                        blackPlayer.text("(Human)");
                    }
                }
                if (chessboard.whiteAIPlayer) {
                    whitePlayer.text("(Computer)");
                }
                else {
                    if (chessboard.activePlayer) {
                        whitePlayer.text("(Human)");
                    }
                    else {
                        whitePlayer.text("(Human)\n(ACTIVE)");
                    }
                }
                if (chessboard.activePlayer) {
                    status.children()[0].style.background = 'black';
                    status.children()[1].style.background = 'black';
                    status.children()[0].style.color = 'white';
                    status.children()[1].style.color = 'white';
                }
                else {
                    status.children()[0].style.background = "white";
                    status.children()[1].style.background = "white";
                    status.children()[0].style.color = 'black';
                    status.children()[1].style.color = 'black';
                }
                updateGameDetails();
            },
            error: function (xhr, textStatus, err) {
                gStatus.text("Application error.");
            },
            statusCode: {
                403: function () {
                    gStatus.text("There is no game named \"" + gameName + "\" - possible application error.");
                }
            }
        });
    }

    function updateGameDetails() {
        const requestUrl = apiRoot + 'getGameProgressDetails?gameName=' + gameName;

        $.ajax({
            url: requestUrl,
            method: 'GET',
            contentType: "application/json;charset=UTF-8",
            success: function (gameDetails) {
                if (gameDetails.finished) {
                    $('[game-in-progress]')[0].style.display = 'none';
                    $('[game-finished]')[0].style.display = 'block';
                    $('[next-move-section]')[0].style.display = 'block';
                    if (gameDetails.draw) {
                        $('[game-finished]')[0].style.background = 'grey';
                        $('[game-finished]')[0].style.color = 'black';
                        $('[status]')[1].style.background = 'grey';
                        $('[next-move-section]')[0].style.background = 'grey';
                        $('[winner-or-draw]').text("DRAW");
                        $('[type-of-game-finish]').text("(Each player has done 15 moves in the row by a king.)");
                    }
                    else {
                        if (gameDetails.winner) {
                            $('[winner-or-draw]').text("BLACK WINS");
                            $('[game-finished]')[0].style.background = 'black';
                            $('[game-finished]')[0].style.color = 'white';
                            $('[status]')[1].style.background = 'black';
                            $('[next-move-section]')[0].style.background = 'black';
                            if (gameDetails.whitePawns == 0 && gameDetails.whiteQueens == 0) {
                                $('[type-of-game-finish]').text("(White player lost all his figures.)");
                            }
                            else {
                                $('[type-of-game-finish]').text("(White player cannot move.)");
                            }
                        }
                        else {
                            $('[winner-or-draw]').text("WHITE WINS");
                            $('[game-finished]')[0].style.background = 'white';
                            $('[game-finished]')[0].style.color = 'black';
                            $('[status]')[1].style.background = 'white';
                            $('[next-move-section]')[0].style.background = 'white';
                            if (gameDetails.blackPawns == 0 && gameDetails.blackQueens == 0) {
                                $('[type-of-game-finish]').text("(Black player lost all his figures.)");
                            }
                            else {
                                $('[type-of-game-finish]').text("(Black player cannot move.)");
                            }
                        }
                    }
                    $('[new-game-section]')[0].style.display = 'block';
                    $('[created-game-section]')[0].style.display = 'none';
                    $('[next-move-input]')[0].style.display = 'none';
                    $('[next-move]')[0].style.display = 'none';
                    $('[send-move-button]')[0].style.display = 'none';
                }
                else {
                    $('[game-in-progress]')[0].style.display = 'block';
                    $('[game-finished]')[0].style.display = 'none';
                    $('[moves-done]').text(gameDetails.moves);
                    $('[white-man]').text(gameDetails.whitePawns);
                    $('[black-man]').text(gameDetails.blackPawns);
                    $('[white-kings]').text(gameDetails.whiteQueens);
                    $('[black-kings]').text(gameDetails.blackQueens);
                    $('[white-kings-moves]').text(gameDetails.whiteQueenMoves);
                    $('[black-kings-moves]').text(gameDetails.blackQueenMoves);
                }
            },
            error: function (xhr, textStatus, err) {
                gStatus.text("Application error.");
            },
            statusCode: {
                403: function () {
                    gStatus.text("There is no game named \"" + gameName + "\" - possible application error.");
                }
            }
        });
    }

    function surrender() {
        $('[game-in-progress]')[0].style.display = 'none';
        $('[game-finished]')[0].style.display = 'block';
        $('[next-move-section]')[0].style.display = 'block';
        $('[game-finished]')[0].style.background = 'grey';
        $('[game-finished]')[0].style.color = 'black';
        $('[status]')[1].style.background = 'grey';
        $('[next-move-section]')[0].style.background = 'grey';
        $('[winner-or-draw]').text("GAME ENDED");
        $('[type-of-game-finish]').text("(Game ended with no winner.)");
        $('[new-game-section]')[0].style.display = 'block';
        $('[created-game-section]')[0].style.display = 'none';
        $('[next-move-input]')[0].style.display = 'none';
        $('[next-move]')[0].style.display = 'none';
        $('[send-move-button]')[0].style.display = 'none';

        var requestUrl = apiRoot + 'deleteGame?gameName=' + gameName;

        $.ajax({
            url: requestUrl,
            method: 'DELETE',
            statusCode: {
                403: function () {
                    gStatus.text("There is no game named \"" + gameName + "\" - possible application error.");
                }
            }
        });
    }

    $('[create-game-button]').click(createGame);
    $('[surrender]').click(surrender);
    $('[send-move-button]').click(sendMove);
    $('[rules-set-select]').change(function () {
        getSelectedRulesSet();
    });
        
});