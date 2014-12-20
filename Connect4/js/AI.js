//Returns an array of winning combinations
var getWl = function() {
    var wl = [];
    //- vertical combinations
    for (var r = 0; r < rowSize; r++) {
        for (var c = 0; c < columnSize - 3; c++) {
            wl.push(
                [{
                    row: r,
                    col: c
                }, {
                    row: r,
                    col: c + 1
                }, {
                    row: r,
                    col: c + 2
                }, {
                    row: r,
                    col: c + 3
                }]
            );
        }
    }
    //| horizontal combinations
    for (var c = 0; c < columnSize; c++) {
        for (var r = 0; r < rowSize - 3; r++) {
            wl.push(
                [{
                    row: r,
                    col: c
                }, {
                    row: r + 1,
                    col: c
                }, {
                    row: r + 2,
                    col: c
                }, {
                    row: r + 3,
                    col: c
                }]
            );
        }
    }
    // / diagonal combinations
    for (var r = 0; r < rowSize - 3; r++) {
        for (var c = 0; c < columnSize - 3; c++) {
            wl.push(
                [{
                    row: r,
                    col: c
                }, {
                    row: r + 1,
                    col: c + 1
                }, {
                    row: r + 2,
                    col: c + 2
                }, {
                    row: r + 3,
                    col: c + 3
                }]
            );
        }
    }
    // \diagonal combinations

    for (var r = 0; r < rowSize - 3; r++) {
        for (var c = 3; c < columnSize; c++) {
            wl.push(
                [{
                    row: r,
                    col: c
                }, {
                    row: r + 1,
                    col: c - 1
                }, {
                    row: r + 2,
                    col: c - 2
                }, {
                    row: r + 3,
                    col: c - 3
                }]
            );
        }
    }
    return wl;
};
//returns an object containing the current possible winning combinations
//according to the number of pieces each have.
//Each array element has the number of empty slots in 
//the winning combination that can be filled right away
var analyzeBoard = function(player) {
    var combs = {
        four: [],
        three: [],
        two: [],
        one: []
    };
    var opponent = getOpponent(player);
    for (var i in winningLines) {
        var matches = 0;
        var emptySlots = [];
        for (var k in winningLines[i]) {
            if (board[winningLines[i][k].row][winningLines[i][k].col] === player) {
                matches++;
            } else if (board[winningLines[i][k].row][winningLines[i][k].col] === opponent) {
                matches = 0;
                break;
            } else if (board[winningLines[i][k].row][winningLines[i][k].col] === 0) {
                emptySlots.push({
                    row: winningLines[i][k].row,
                    col: winningLines[i][k].col
                });
            }
        }
        switch (matches) {
            case 4:
                combs.four.push(1);
                break;
            case 3:
                var under = 0;
                for (var t in emptySlots) {
                    if (emptySlots[t].row === rowSize - 1 || board[emptySlots[t].row + 1][emptySlots[t].col] !== 0) {
                        under++;
                    }
                }
                combs.three.push(under);
                break;
            case 2:
                var under = 0;
                for (var t in emptySlots) {
                    if (emptySlots[t].row === rowSize - 1 || board[emptySlots[t].row + 1][emptySlots[t].col] !== 0) {
                        under++;
                    }
                }
                combs.two.push(under);
                break;
            case 1:
                var under = 0;
                for (var t in emptySlots) {
                    if (emptySlots[t].row === rowSize - 1 || board[emptySlots[t].row + 1][emptySlots[t].col] !== 0) {
                        under++;
                    }
                }
                combs.one.push(under);
                break;
        }
    }
    return combs;
};
//Evaluate function for the AI
var eval = function(player) {
    var lines;
	if(player === 1) {
		lines = boardComb1;
	} else if (player === 2) {
		lines = boardComb2;
	}
    var score = 0;
    /*if (lines.four.length > 0) {
        return 10000;
    }*/
    for (var n in lines.three) {
        if (n > 0) {
            if (lines.three[0] > 0 && lines.three[1] > 0) {
                score += 800;
            } else {
                score += 250;
            }
        } else {
            score += 200;
        }
    }
    for (var n in lines.two) {
        score += 25 * (lines.two[n] + 1);
    }
    for (var n in lines.one) {
        score += 5;
    }
    return score;
};
var evaluate = function(player, opponent) {
    return eval(player);
};
//A recursive negamax algorithm with alpha beta prunning for the AI
var negamax = function(depth, alpha, beta, player) {
    var opponent = getOpponent(player);
    var winner = checkWin();

    if (winner === player) {
        return 10000 - (MAX_DEPTH - depth);
    } else if (winner === opponent) {
        return -10000 + (MAX_DEPTH - depth);
    }

    if (depth === 0) {
        return evaluate(player, opponent) - (MAX_DEPTH - depth);
    }
    var thisAlpha = alpha;
    var i;
    var m;
    var shuffleCols = sCols(lastPlay);
    for (m in shuffleCols) {
        i = shuffleCols[m];
        var row = droppableColumn(i);
        if (row >= 0) {
			var temp1 = copy(boardComb1);
			var temp2 = copy(boardComb2);
			if(row > 0) {
				var before1u = getuCombinations(1, i, row-1);
				var before2u = getuCombinations(2, i, row-1);
			}
			var before1 = getCombinations(1, i, row);
			var before2 = getCombinations(2, i, row);
			
            board[row][i] = player;
			
			var after = getCombinations(2, i, row);
			addCombinations(before1,before2,after,2);
			if(row > 0) {
				updateUnder(before1u, before2u, i,row);
			}
			
		} else {
            continue;
        }
		lastPlay = i;
        var val = -negamax(depth - 1, -beta, -thisAlpha, opponent);
        board[row][i] = 0;
		boardComb1 = temp1;
		boardComb2 = temp2;
        if (val >= beta) {
            return beta;
        }
        if (val > thisAlpha) {
            thisAlpha = val;
        }
    }
    return thisAlpha;
};
//Parameter is the player's turn
//Returns the best move possible as calculated by the negamax and evaluate algorithms
var pickMove = function(player) {
    var bestCol = 0;
    var alpha = -10000;
    var beta = 10000;
	var opponent = getOpponent(player);
    var i;
    var m;
    var shuffleCols = sCols(lastPlay);
    for (m in shuffleCols) {
        i = shuffleCols[m];
        var row = droppableColumn(i);
        if (row >= 0) {
			var temp1 = copy(boardComb1);
			var temp2 = copy(boardComb2);
			if(row > 0) {
				var before1u = getuCombinations(1, i, row-1);
				var before2u = getuCombinations(2, i, row-1);
			}
			var before1 = getCombinations(1, i, row);
			var before2 = getCombinations(2, i, row);
			
            board[row][i] = player;
			
			var after = getCombinations(2, i, row);
			addCombinations(before1,before2,after,2);
			if(row > 0) {
				updateUnder(before1u, before2u, i,row);
			}
        } else {
            if (bestCol === i) {
                bestCol = shuffleCols[m + 1];
            }
            continue;
        }
		lastPlay = i;
        var val = -negamax(MAX_DEPTH, -beta, -alpha, opponent);
        board[row][i] = 0;
		boardComb1 = temp1;
		boardComb2 = temp2;
		//console.log("col:"+i+"value:"+val);
        if (val >= beta) {
            return i;
        }
        if (val > alpha) {
            alpha = val;
            bestCol = i;
        }
    }
    return bestCol;
};
//Makes the computer move. Updates both board combinations to keep the analyzed board updated
var compMove = function() {
    var col = pickMove(2);
    var row = droppableColumn(col);
    var slots = $(".row")[row].childNodes;
    var element = slots[col];
	if(row > 0) {
		var before1u = getuCombinations(1, col, row-1);
		var before2u = getuCombinations(2, col, row-1);
	}
	var before1 = getCombinations(1, col, row);
	var before2 = getCombinations(2, col, row);
	
    drop(element, 2);
	
	var after = getCombinations(2, col, row);
	addCombinations(before1,before2,after,2);
	
	if(row > 0) {
		updateUnder(before1u, before2u, col,row);
	}
};