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
    gStatus.text("Not connected to application.");
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
            }
        });
    }

    function createGame() {
        const requestUrl = apiRoot + 'newGame';
        
        gameName = $('[game-name]').val();
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

        if (gameRulesName == null) {
            gStatus.text("You have to select rules set!");
            return;
        }

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
                if(blackPlayer == "false" || whitePlayer == "false")
                    gStatus.text("Application error.");
            }
        });

        $('[created-game-name]').text(gameName);
        $('[new-game-section]')[0].style.display = 'none';
        $('[created-game-section]')[0].style.display = 'block';

        if (blackPlayer == "true" && whitePlayer == "true") {
            gStatus.text("Game in progress, please wait for a while...");
            var start = new Date().getTime();
            for (var i = 0; i < 1e7; i++) {
                if ((new Date().getTime() - start) > 2000) {
                    break;
                }
            }
            getBoard();
        }
        else {
            gStatus.text("Game started.");
            $('[next-move-section]')[0].style.display = 'block';
            getBoard();
            $('[next-move]').focus();
        }
    }

    function sendMove() {
        var requestUrl = apiRoot + 'sendMove?gameName=' + gameName;
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
                        $('[winner-or-draw]').text("DRAW");
                        $('[type-of-game-finish]').text("(Each player has done 15 moves in the row by a king.)");
                    }
                    else {
                        if (gameDetails.winner) {
                            $('[winner-or-draw]').text("BLACK WINS");
                            $('[game-finished]')[0].style.background = 'black';
                            $('[game-finished]')[0].style.color = 'white';
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
            }
        });
    }

    $('[create-game-button]').click(createGame);
    $('[get-board]').click(getBoard);
    $('[send-move-button]').click(sendMove);
    $('[rules-set-select]').change(function () {
        getSelectedRulesSet();
    });
        
});