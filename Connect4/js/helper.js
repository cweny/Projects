var sCols = function(play) {
    var shuffled = [];
    var t = 1;
    var start = parseInt(play);
    shuffled.push(start);
    while (t + start < columnSize || start - t >= 0) {
        if (start - t >= 0) {
            shuffled.push(start - t);
        }
        if (start + t < columnSize) {
            shuffled.push(start + t);
        }
        t++;
    }
    return shuffled;
};

var newBoard = function() {
    var newB = [];

    for (var i = 0; i < rowSize; i++) {
        var temp = [];
        for (var n = 0; n < columnSize; n++) {
            temp[n] = 0;
        }
        newB[i] = temp;
    }
    return newB;
};
var droppableColumn = function(col) {
    var row = rowSize - 1;
    while (board[row][col] !== 0) {
        row--;
        if (row === -1) {
            return row;
        }
    }
    return row;
};
var undoColumn = function(col) {
    var row = 0;
    while (board[row][col] === 0) {
        row++;
        if (row === rowSize) {
            console.log("error");
            return;
        }
    }

    board[row][col] = 0;
};
var setRed = function() {
    this.style.backgroundColor = "red";
};
var setBlue = function() {
    this.style.backgroundColor = "blue";
};
var setWhite = function() {
    this.style.backgroundColor = "white";
};
var checkWin = function() {
    for (var row = 0; row < board.length; row++) {
        for (var col = 0; col < board[0].length; col++) {
            var player = board[row][col];
            if (player === 0) {
                continue;
            }
            if (row + 3 < board.length && board[row + 1][col] === player && board[row + 2][col] === player && board[row + 3][col] === player)
                return player;
            if (col + 3 < board[0].length && board[row][col + 1] === player && board[row][col + 2] === player && board[row][col + 3] === player)
                return player;
            if ((row + 3 < board.length && col + 3 < board[0].length) && board[row + 1][col + 1] === player && board[row + 2][col + 2] === player && board[row + 3][col + 3] === player)
                return player;
            if ((row - 3 > 0 && col + 3 < board[0].length) && board[row - 1][col + 1] === player && board[row - 2][col + 2] === player && board[row - 3][col + 3] === player)
                return player;
        }
    }
    return 0;
};
var drop = function(element, player) {
    var row = rowSize - 1;
    while (board[row][element.id] !== 0) {
        row--;
        if (row === -1) {
            break;
        }
    }
    if (row !== -1) {
        if (redTurn) {
            board[row][element.id] = 1;
            $('#player').text("Player Blue Turn");
        } else {
            board[row][element.id] = 2;
            $('#player').text("Player Red Turn");
        }
        var slots = $(".row")[row].childNodes;
        var x = slots[element.id];
        if (redTurn) {
            x.style.backgroundColor = "red";
            x.onmouseout = setRed;
        } else {
            x.style.backgroundColor = "blue";
            x.onmouseout = setBlue;
        }

        redTurn = !redTurn;
    }
    if (row > 0 && player === 1) {
        row--;
        var slots = $(".row")[row].childNodes;
        var x = slots[element.id];
        x.style.backgroundColor = "pink";
    }
    if (row !== -1) {
        return true;
    } else {
        return false;
    }
};